package com.example.kakaopay.domain.dto;

import com.example.kakaopay.domain.entity.Money;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MoneyDto {
    private Long id;
    private String token;
    private RoomDto room;
    private UserDto owner;
    private List<ReceivedMoneyUserDto> receivedMoneyUsers = new ArrayList<>();
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdAt;
    private int quantity;
    private int budget;
    private int totalReceivedMoney;
    private int balance;

    public Money toEntity() {
        Money money = new Money();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        money.setToken(token);

        if (room != null) {
            money.setRoom(room.toEntity());
        }

        if (owner != null) {
            money.setOwner(owner.toEntity());
        }
        money.setBudget(budget);
        money.setQuantity(quantity);
        money.setCreatedAt(now);
        money.setBalance(budget-balance);
        return money;
    }

    public static MoneyDto toDto(Money money) {
        MoneyDto moneyDto = new MoneyDto();

        moneyDto.setId(money.getId());
        moneyDto.setToken(money.getToken());
        moneyDto.setRoom(RoomDto.toDto(money.getRoom()));
        moneyDto.setOwner(UserDto.toDto(money.getOwner()));
        // 리스트에서 리스트 넣기
        money.getReceivedMoneyUsers().forEach(receivedMoneyUser -> moneyDto.getReceivedMoneyUsers().add(ReceivedMoneyUserDto.toDto(receivedMoneyUser)));
        moneyDto.setCreatedAt(money.getCreatedAt());
        moneyDto.setBudget(money.getBudget());
        moneyDto.setTotalReceivedMoney(money.getBudget() - money.getBalance());
        moneyDto.setQuantity(money.getQuantity());

        return moneyDto;
    }
}
