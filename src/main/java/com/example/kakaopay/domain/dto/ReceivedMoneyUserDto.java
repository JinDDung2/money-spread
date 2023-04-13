package com.example.kakaopay.domain.dto;

import com.example.kakaopay.domain.entity.ReceivedMoneyUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReceivedMoneyUserDto {

    @JsonIgnore
    private Long id;
    private Long moneyId;
    private Long userId;
    private int receivedMoney;

    public static ReceivedMoneyUserDto toDto(ReceivedMoneyUser receivedMoneyUser) {
        ReceivedMoneyUserDto receivedMoneyUserDto = new ReceivedMoneyUserDto();

        receivedMoneyUserDto.setId(receivedMoneyUser.getId());
        receivedMoneyUserDto.setMoneyId(receivedMoneyUser.getMoney().getId());
        if (receivedMoneyUser.getUser() != null) {
            receivedMoneyUserDto.setUserId(receivedMoneyUser.getId());
        }
        receivedMoneyUserDto.setReceivedMoney(receivedMoneyUser.getReceivedMoney());

        return receivedMoneyUserDto;
    }
}
