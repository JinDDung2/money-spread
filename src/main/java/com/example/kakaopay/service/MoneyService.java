package com.example.kakaopay.service;

import com.example.kakaopay.domain.dto.MoneyDto;
import com.example.kakaopay.domain.dto.response.SprinkleRuntimeException;
import com.example.kakaopay.domain.entity.Money;
import com.example.kakaopay.domain.entity.ReceivedMoneyUser;
import com.example.kakaopay.domain.entity.Room;
import com.example.kakaopay.domain.entity.User;
import com.example.kakaopay.repository.MoneyRepository;
import com.example.kakaopay.repository.ReceivedMoneyUserRepository;
import com.example.kakaopay.repository.RoomRepository;
import com.example.kakaopay.repository.UserRepository;
import com.example.kakaopay.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.example.kakaopay.domain.dto.response.ErrorCode.*;

@Slf4j
@Service
public class MoneyService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MoneyRepository moneyRepository;
    private final ReceivedMoneyUserRepository receivedMoneyUserRepository;

    public MoneyService(UserRepository userRepository, RoomRepository roomRepository, MoneyRepository moneyRepository, ReceivedMoneyUserRepository receivedMoneyUserRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.moneyRepository = moneyRepository;
        this.receivedMoneyUserRepository = receivedMoneyUserRepository;
    }

    public MoneyDto getMoneySprinkle(Long userId, String token) {
        Money moneySprinkle = moneyRepository.findByToken(token).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_TOKEN, "존재 하지 않는 토큰"));

        if (moneySprinkle.getOwner().getId() != userId) {
            throw new SprinkleRuntimeException(NOT_EQUAL_OWNER_SPRINKLE, "해당 유저가 뿌리기 한 것이 아닙니다.");
        }
        if (ChronoUnit.DAYS.between(moneySprinkle.getCreatedAt(), LocalDateTime.now()) > 7) {
            throw new SprinkleRuntimeException(TIME_OUT, "조회 기간이 만료되었습니다.");
        }
        return MoneyDto.toDto(moneySprinkle);
    }

    @Transactional
    public void closeMoneySprinkle(Money money) {
        if (money.getBalance() != 0) {
            User owner = money.getOwner();
            owner.setBudget(owner.getBudget() + money.getBalance());
            money.setBalance(0);

            userRepository.save(owner);
            moneyRepository.delete(money);
        }

    }

    @Transactional
    public MoneyDto createMoneySprinkle(Long userId, Long roomId, MoneyDto moneyDto) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_USER, "존재하지 않는 유저"));

        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_ROOM, "존재하지 않는 방"));

        Money money = moneyDto.toEntity();

        if (user.getBudget() < money.getBudget()) {
            throw new SprinkleRuntimeException(NOT_ENOUGH_BALANCE, "잔액이 부족합니다.");
        }

        setOwnerBudget(user, money);
        money = setMoney(user, room, money);
        setReceivedSprinkleUsers(money);

        /// FIXME: 2023/04/07 확인하면 지우기
        List<ReceivedMoneyUser> receivedMoneyUsersList = money.getReceivedMoneyUsers();
        for (ReceivedMoneyUser receivedMoneyUser : receivedMoneyUsersList) {
            log.info("받아랏!={}", receivedMoneyUser.getUser());
            log.info("받아랏ID={}", receivedMoneyUser.getMoney().getId());
        }

        return MoneyDto.toDto(moneyRepository.save(money));
    }

    public Money setMoney(User user, Room room, Money money) {
        money.setToken(RandomUtils.getInstance().generateToken());
        money.setBalance(money.getBalance());
        money.setOwner(user);
        money.setRoom(room);

        return moneyRepository.save(money);
    }

    public void setOwnerBudget(User user, Money money) {
        user.setBudget(user.getBudget() - money.getBudget());
        userRepository.save(user);
    }

    private void setReceivedSprinkleUsers(Money money) {
        List<ReceivedMoneyUser> receivedMoneyUsers = divideBudget(money);
        receivedMoneyUsers.forEach(receivedMoneyUser ->
                receivedMoneyUserRepository.save(receivedMoneyUser));

    }

    private List<ReceivedMoneyUser> divideBudget(Money money) {
        int divideNum = money.getQuantity();
        int budget = money.getBudget();

        List<ReceivedMoneyUser> receivedMoneyUsers = new ArrayList<>();
        for (int i = 0; i < divideNum; i++) {
            ReceivedMoneyUser receivedMoneyUser = new ReceivedMoneyUser();
            receivedMoneyUser.setReceivedMoney(1);
            receivedMoneyUser.addMoneySprinkling(money);
            receivedMoneyUsers.add(receivedMoneyUser);
            budget--;
        }

        return divide(receivedMoneyUsers, budget);
    }

    private List<ReceivedMoneyUser> divide(List<ReceivedMoneyUser> receivedMoneyUsers, int budget) {
        if (budget < 2) {
            int index = RandomUtils.getInstance().getRandom(receivedMoneyUsers.size());
            receivedMoneyUsers.get(index).setReceivedMoney(receivedMoneyUsers.get(index).getReceivedMoney() + budget);
            return receivedMoneyUsers;
        }
        int divideNum = RandomUtils.getInstance().getRandom(budget);
        int index = RandomUtils.getInstance().getRandom(receivedMoneyUsers.size());

        receivedMoneyUsers.get(index).setReceivedMoney(receivedMoneyUsers.get(index).getReceivedMoney() + divideNum);

        budget -= divideNum;
        return divide(receivedMoneyUsers, budget);
    }
}
