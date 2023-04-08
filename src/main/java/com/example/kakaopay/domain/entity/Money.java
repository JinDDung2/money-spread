package com.example.kakaopay.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE MONEY SET deleted_at = true where id = ?")
public class Money{

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
    private int quantity; // 받는 사람 수
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "money")
    private List<ReceivedMoneyUser> receivedMoneyUsers = new ArrayList<>(); // 받는 사람들

    private int balance; // 잔액

}
