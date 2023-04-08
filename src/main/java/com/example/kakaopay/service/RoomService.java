package com.example.kakaopay.service;

import com.example.kakaopay.domain.dto.RoomDto;
import com.example.kakaopay.domain.dto.response.SprinkleRuntimeException;
import com.example.kakaopay.domain.entity.Room;
import com.example.kakaopay.repository.RoomRepository;
import com.example.kakaopay.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.kakaopay.domain.dto.response.ErrorCode.NOT_FOUND_ROOM;
import static com.example.kakaopay.domain.dto.response.ErrorCode.NOT_FOUND_USER;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public RoomDto createRoom(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_USER, "존재하지 않는 유저"));

        Room room = new Room();
        roomRepository.save(room);

        return RoomDto.toDto(room);
    }

    public RoomDto enterRoom(Long userId, Long roomId) {
        userRepository.findById(userId).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_USER, "존재하지 않는 유저"));

        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new SprinkleRuntimeException(NOT_FOUND_ROOM, "존재하지 않는 방"));

        return RoomDto.toDto(room);
    }
}
