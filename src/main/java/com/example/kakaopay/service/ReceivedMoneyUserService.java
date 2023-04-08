package com.example.kakaopay.service;

import com.example.kakaopay.domain.dto.MoneyDto;
import com.example.kakaopay.domain.dto.ReceivedMoneyUserDto;
import com.example.kakaopay.domain.dto.response.SprinkleRuntimeException;
import com.example.kakaopay.domain.entity.Money;
import com.example.kakaopay.domain.entity.ReceivedMoneyUser;
import com.example.kakaopay.domain.entity.User;
import com.example.kakaopay.repository.MoneyRepository;
import com.example.kakaopay.repository.ReceivedMoneyUserRepository;
import com.example.kakaopay.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kakaopay.domain.dto.response.ErrorCode.*;

@Slf4j
@Service
public class ReceivedMoneyUserService {

    private final UserRepository userRepository;
    private final MoneyRepository moneyRepository;
    private final MoneyService moneyService;
    private final ReceivedMoneyUserRepository receivedMoneyUserRepository;

    public ReceivedMoneyUserService(UserRepository userRepository, MoneyRepository moneyRepository, MoneyService moneyService, ReceivedMoneyUserRepository receivedMoneyUserRepository) {
        this.userRepository = userRepository;
        this.moneyRepository = moneyRepository;
        this.moneyService = moneyService;
        this.receivedMoneyUserRepository = receivedMoneyUserRepository;
    }

    @Transactional
    public ReceivedMoneyUserDto receiveMoneySprinkle(MoneyDto moneyDto, Long userId, Long roomId) {

        Money moneySprinkle = moneyRepository.findByToken(moneyDto.getToken()).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_TOKEN, "토큰을 찾을 수 없습니다."));

        User user = userRepository.findById(userId).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_USER, "유저 아이디를 찾을 수 없습니다."));

        // 이미 뿌린 유저는 못받게 하기
        if (moneySprinkle.getOwner().getId() == userId) {
            throw new SprinkleRuntimeException(NOT_RECEIVE_MY_SPRINKLE, "자신이 뿌린 유저는 받을 수 없습니다.");
        }
        if (!moneySprinkle.getRoom().getId().equals(roomId)) {
            throw new SprinkleRuntimeException(NOT_ENTER_ROOM, "해당 방에 참여하고 있지 않습니다.");
        }
        // 이미 받은 유저는 못받게 하기
        if (checkReceivedUser(moneySprinkle.getReceivedMoneyUsers(), userId)) {
            throw new SprinkleRuntimeException(ALREADY_USER_RECEIVED, "이미 받은 유저는 다시 받을 수 없습니다.");
        }
        // 시간 지나면 못받게 하기
        if (ChronoUnit.MINUTES.between(moneySprinkle.getCreatedAt(), LocalDateTime.now()) > 10) {
            moneyService.closeMoneySprinkle(moneySprinkle);
            throw new SprinkleRuntimeException(TIME_OUT, "유효하지 않은 시간입니다.");
        }

        for (ReceivedMoneyUser receivedMoneyUser : moneySprinkle.getReceivedMoneyUsers()) {
            if (receivedMoneyUser.getUser() == null) {
                receivedMoneyUser.setUser(user);
                moneySprinkle.setBalance(moneySprinkle.getBalance() - receivedMoneyUser.getReceivedMoney());
                user.setBudget(user.getBudget() + receivedMoneyUser.getReceivedMoney());

                userRepository.save(user);
                moneyRepository.save(moneySprinkle);
                return ReceivedMoneyUserDto.toDto(receivedMoneyUserRepository.save(receivedMoneyUser));
            }
        }

        return null;
    }

    private boolean checkReceivedUser(List<ReceivedMoneyUser> receivedMoneyUsers, Long userId) {
        List<ReceivedMoneyUser> receivedMoneyUserList = receivedMoneyUsers.stream()
                .filter(receivedMoneyUser -> receivedMoneyUser.getUser() != null)
                .filter(money -> money.getUser().getId() == userId)
                .collect(Collectors.toList());

        if (receivedMoneyUserList.size() == 0) return false;
        return true;
    }
}
