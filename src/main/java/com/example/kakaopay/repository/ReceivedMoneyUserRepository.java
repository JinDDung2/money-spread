package com.example.kakaopay.repository;

import com.example.kakaopay.domain.entity.ReceivedMoneyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedMoneyUserRepository extends JpaRepository<ReceivedMoneyUser, Long> {
}
