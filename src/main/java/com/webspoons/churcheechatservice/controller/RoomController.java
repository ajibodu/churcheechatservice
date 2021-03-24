package com.webspoons.churcheechatservice.controller;

import com.webspoons.churcheechatservice.Mics.CustomValidationException;
import com.webspoons.churcheechatservice.model.Room;
import com.webspoons.churcheechatservice.pojo.GenResponse;
import com.webspoons.churcheechatservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("create")
    public ResponseEntity<GenResponse> create(@RequestBody Room room) throws Exception{
        return roomService.createRoom(room);
    }

    @PutMapping("edit")
    public ResponseEntity<GenResponse> edit(@RequestBody Room room) throws Exception{
        return roomService.editRoomDetail(room);
    }

    @GetMapping("get/{roomID}")
    public ResponseEntity<GenResponse> get(@PathVariable("roomID") String roomID) throws Exception {
        return roomService.getRoomDetail(roomID);
    }

}
