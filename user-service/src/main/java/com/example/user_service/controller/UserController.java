package com.example.user_service.controller;

import com.example.user_service.exception.DuplicateUserException;
import com.example.user_service.models.User;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/addUser")
    public Mono<ResponseEntity<Object>> addUser(@Valid @RequestBody User user) {
        return userService.createUser(user)
                .map(userCreated -> ResponseEntity.status(HttpStatus.CREATED).body((Object) userCreated))
                .onErrorResume(DuplicateUserException.class, ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body((Object) ex.getMessage()))
                )
                .onErrorResume(ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) ("Unexpected error: " + ex.getMessage())))
                );
    }


    @GetMapping("/getAll")
    Flux<User> findAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    Mono<ResponseEntity<?>> findUserById(@PathVariable String id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/delete/{id}")
    Mono<ResponseEntity<?>> removeUser(@PathVariable String id) {
        return userService.getUserById(id)
                .thenReturn(ResponseEntity.ok(
                        Map.of("message", "User with id " + id + " was deleted successfully.")));
    }
    @GetMapping("/byName/userName")
    Mono<ResponseEntity<?>> findUserByName(@Valid @PathVariable String userName){
        return userService.getUserByName(userName)
                .map(foundUser -> ResponseEntity.ok().body(foundUser));
    }

}

