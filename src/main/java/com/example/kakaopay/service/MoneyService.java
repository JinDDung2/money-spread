package com.example.kakaopay.service;

import com.example.kakaopay.domain.dto.MoneyDto;
import com.example.kakaopay.domain.entity.Money;
import com.example.kakaopay.domain.entity.ReceivedMoneyUser;
import com.example.kakaopay.domain.entity.Room;
import com.example.kakaopay.domain.entity.User;
import com.example.kakaopay.repository.MoneyRepository;
import com.example.kakaopay.repository.RoomRepository;
import com.example.kakaopay.repository.UserRepository;
import com.example.kakaopay.utils.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MoneyRepository moneyRepository;

    public MoneyService(UserRepository userRepository, RoomRepository roomRepository, MoneyRepository moneyRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.moneyRepository = moneyRepository;
    }

    public Money getMoney(String token) {
        return moneyRepository.findByToken(token).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 토큰"));
    }


    @Transactional
    public MoneyDto createMoneySprinkle(Long userId, Long roomId, MoneyDto moneyDto) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저"));

        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 방"));

        Money money = moneyDto.toEntity();

        if (user.getBudget() < money.getBudget()) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }

        setOwnerBudget(user, money);
        money = setMoney(user, room, money);
        setReceivedSprinkleUsers(money);
        moneyRepository.save(money);

        return moneyDto.toDto(money);
    }

    public Money setMoney(User user, Room room, Money money) {
        money.setToken(RandomUtils.getInstance().generateToken());
        money.setBalance(money.getBalance());
        money.setOwner(user);
        money.setRoom(room);

        return money;
    }

    public void setOwnerBudget(User user, Money money) {
        user.setBudget(user.getBudget() - money.getBudget());
        userRepository.save(user);
    }

    private void setReceivedSprinkleUsers(Money money) {
        List<ReceivedMoneyUser> receivedMoneyUsers = divideBudget(money);
    }

    private List<ReceivedMoneyUser> divideBudget(Money money) {
        int divideNum = money.getQuantity();
        int budget = money.getBudget();

        List<ReceivedMoneyUser> receivedMoneyUsers = new ArrayList<>();
        for (int i = 0; i <divideNum; i++) {
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
