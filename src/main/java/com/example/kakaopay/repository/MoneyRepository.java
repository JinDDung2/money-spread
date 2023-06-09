package com.example.kakaopay.repository;

import com.example.kakaopay.domain.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoneyRepository extends JpaRepository<Money, Long> {
    Optional<Money> findByToken(String token);
}
