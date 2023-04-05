package com.example.kakaopay.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;

    @ManyToOne // 단방향
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne // 단방향
    @JoinColumn(name = "user_id")
    private User owner;

    private int budget; // 뿌린 금액

    @OneToMany(mappedBy = "user")
    private List<ReceivedMoneyUser> receivedMoneyUsers = new ArrayList<>();

}
