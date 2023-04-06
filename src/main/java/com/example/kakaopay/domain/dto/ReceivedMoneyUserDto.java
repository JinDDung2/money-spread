package com.example.kakaopay.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReceivedMoneyUserDto {
    private Long id;
    private Long moneyId;
    private Long userId;
    private int receivedMoney;
}
