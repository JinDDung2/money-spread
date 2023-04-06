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
    private UserDto user;
    private int budget;
    private int quantity;
    private List<ReceivedMoneyUserDto> receivedMoneyUsers = new ArrayList<>();
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdAt;
    private int balance;

    public Money toEntity() {
        Money money = new Money();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        money.setToken(token);

        if (room != null) {
            money.setRoom(room.toEntity());
        }

        if (user != null) {
            money.setOwner(user.toEntity());
        }
        money.setBudget(budget);
        money.setQuantity(quantity);
        money.setCreatedAt(now);
        money.setBalance(budget-balance);
        return money;
    }

    public MoneyDto toDto(Money money) {
        MoneyDto moneyDto = new MoneyDto();

        moneyDto.setId(money.getId());
        moneyDto.setToken(money.getToken());
        moneyDto.setRoom(RoomDto.toDto(money.getRoom()));
        moneyDto.setUser(UserDto.toDto(money.getOwner()));
        moneyDto.setBudget(money.getBudget());
        // 리스트에서 리스트 넣기
        moneyDto.setCreatedAt(money.getCreatedAt());
        moneyDto.setBudget(money.getBalance());

        return moneyDto;
    }
}
