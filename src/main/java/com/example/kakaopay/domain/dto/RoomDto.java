package com.example.kakaopay.domain.dto;

import com.example.kakaopay.domain.entity.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long id;

    public RoomDto() {
    }

    public RoomDto(Long id) {
        this.id = id;
    }

    public Room toEntity() {
        return new Room();
    }

    public static RoomDto toDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());

        return roomDto;
    }
}
