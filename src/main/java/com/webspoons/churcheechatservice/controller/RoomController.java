package com.webspoons.churcheechatservice.controller;

import com.webspoons.churcheechatservice.model.Room;
import com.webspoons.churcheechatservice.pojo.GenResponse;
import com.webspoons.churcheechatservice.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/room")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("check")
    public ResponseEntity<GenResponse> check(@RequestBody Room room) throws Exception{
        return roomService.checkIfRoomNameExistForCreator(room);
    }

    @PostMapping("create")
    public ResponseEntity<GenResponse> create(@RequestBody Room room) throws Exception{
        return roomService.createRoom(room);
    }

    @PutMapping("edit/{roomID}")
    public ResponseEntity<GenResponse> edit(@RequestBody Room room, @PathVariable("roomID") String roomID) throws Exception{
        return roomService.editRoomDetail(room, roomID);
    }

    @GetMapping("get/{roomID}")
    public ResponseEntity<GenResponse> get(@PathVariable("roomID") String roomID) throws Exception {
        return roomService.getRoomDetail(roomID);
    }

    @GetMapping("get")
    public ResponseEntity<GenResponse> get() throws Exception {
        return roomService.getAllRoomDetail();
    }

    @GetMapping("getMembers/{roomID}")
    public ResponseEntity getMembers(@PathVariable("roomID") String roomID) throws Exception{
        return roomService.getMembersInRoom(roomID);
    }

}
