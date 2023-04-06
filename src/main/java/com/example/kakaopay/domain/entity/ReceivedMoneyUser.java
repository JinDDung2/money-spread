package com.example.kakaopay.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ReceivedMoneyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "money_id")
    private Money money;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int receivedMoney;

    public void addMoneySprinkling(Money money) {
        this.money = money;
        money.getReceivedMoneyUsers().add(this);
    }

}
