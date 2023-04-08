package com.example.kakaopay.domain.dto;

import com.example.kakaopay.domain.entity.ReceivedMoneyUser;
import com.example.kakaopay.repository.MoneyRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter @Setter
public class ReceivedMoneyUserDto {

    @JsonIgnore
    @Autowired
    MoneyRepository moneyRepository;
    @JsonIgnore
    private Long id;
    private Long moneyId;
    private UserDto receiver;
    private int receivedMoney;

    public ReceivedMoneyUser toEntity() {
        ReceivedMoneyUser receivedMoneyUser = new ReceivedMoneyUser();

        receivedMoneyUser.addMoneySprinkling(moneyRepository.findById(moneyId).get());
        receivedMoneyUser.setUser(receiver.toEntity());
        receivedMoneyUser.setReceivedMoney(receivedMoney);

        return receivedMoneyUser;
    }

    public static ReceivedMoneyUserDto toDto(ReceivedMoneyUser receivedMoneyUser) {
        ReceivedMoneyUserDto receivedMoneyUserDto = new ReceivedMoneyUserDto();

        receivedMoneyUserDto.setId(receivedMoneyUser.getId());
        receivedMoneyUserDto.setMoneyId(receivedMoneyUser.getMoney().getId());
        if (receivedMoneyUser.getUser() != null) {
            receivedMoneyUserDto.setReceiver(UserDto.toDto(receivedMoneyUser.getUser()));
        }
        receivedMoneyUserDto.setReceivedMoney(receivedMoneyUser.getReceivedMoney());

        return receivedMoneyUserDto;
    }
}
