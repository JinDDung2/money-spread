package com.example.kakaopay.domain.dto;

import com.example.kakaopay.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private int budget;

    public UserDto() {}

    public UserDto(int budget) {
        this.budget = budget;
    }

    public User toEntity() {
        User user = new User();
        user.setBudget(budget);
        return user;
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setBudget(user.getBudget());

        return userDto;
    }
}

