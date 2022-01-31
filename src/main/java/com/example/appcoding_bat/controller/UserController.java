package com.example.appcoding_bat.controller;

import com.example.appcoding_bat.entity.User;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.models.UserDTO;
import com.example.appcoding_bat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    ElementNotFound messageTask = ElementNotFound.TASK;
    ElementNotFound messageAnswer = ElementNotFound.ANSWER;
    ElementNotFound messageUser = ElementNotFound.USER;

    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public HttpEntity<Result> getUserById(@PathVariable Integer id) {
        Result result = userService.getUserById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping
    public HttpEntity<Result> addUser(@Valid @RequestBody UserDTO userDTO) {
        Result result = userService.addUser(userDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageTask.getMessage()) ||
                result.getMessage().equals(messageAnswer.getMessage()) ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<Result> editUserById(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
        Result result = userService.editUserById(id, userDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageTask.getMessage()) ||
                result.getMessage().equals(messageAnswer.getMessage()) || result.getMessage().equals(messageUser.getMessage()) ?
                HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Result> deleteUserById(@PathVariable Integer id) {
        Result result = userService.deleteUserById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
