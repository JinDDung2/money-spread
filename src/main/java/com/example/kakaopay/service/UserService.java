package com.example.kakaopay.service;

import com.example.kakaopay.domain.dto.UserDto;
import com.example.kakaopay.domain.entity.User;
import com.example.kakaopay.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userDto.toEntity();
        userRepository.save(user);

        return UserDto.toDto(user);
    }
}
