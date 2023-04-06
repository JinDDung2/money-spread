package com.example.kakaopay.service;

import com.example.kakaopay.repository.ReceivedMoneyUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceivedMoneyUserService {

    private final ReceivedMoneyUserRepository receivedMoneyUserRepository;

    public ReceivedMoneyUserService(ReceivedMoneyUserRepository receivedMoneyUserRepository) {
        this.receivedMoneyUserRepository = receivedMoneyUserRepository;
    }
}
