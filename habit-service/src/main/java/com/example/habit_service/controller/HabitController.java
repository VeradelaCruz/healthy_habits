package com.example.habit_service.controller;

import com.example.habit_service.models.Habit;
import com.example.habit_service.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/habits")
public class HabitController {
    private  final HabitService habitService;

    @PostMapping("/addHabit")
    public Mono<ResponseEntity<Habit>> addHabit(@Valid @RequestBody Habit habit) {
        return habitService.createHabit(habit)
                .map(savedHabit -> ResponseEntity.status(HttpStatus.CREATED).body(savedHabit));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Habit>> getHabit(@PathVariable String id) {
        return habitService.getHabitById(id)
                .map(habit -> ResponseEntity.ok(habit))
                .defaultIfEmpty(
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body((Habit) Map.of("message", "Habit with id " + id + " not found"))
                );
    }


    @GetMapping("/getAll")
    Flux<Habit> findAllHabits(){
        return habitService.getAllHabits();
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> removeHabit(@PathVariable String id) {
        return habitService.getHabitById(id)
                .flatMap(habit ->
                        habitService.deleteHabit(id)
                                .then(Mono.just(ResponseEntity.ok().body("Habit deleted successfully.")))
                )
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found."));
    }

    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<List<Habit>>> getHabitsByUser(@PathVariable String userId) {
        return habitService.getHabitsByUserId(userId)
                .collectList()
                .flatMap(habits -> {
                    if (habits.isEmpty()) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Collections.emptyList())); // o un mensaje, pero el body debe ser List<Habit>
                    } else {
                        return Mono.just(ResponseEntity.ok(habits));
                    }
                });
    }

    @GetMapping("/category/{category}")
    public Mono<ResponseEntity<?>> getHabitsByCategory(@PathVariable String category) {
        return habitService.getHabitsByCategory(category)
                .collectList()
                .flatMap(habits -> {
                    if (habits.isEmpty()) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of("message", "No habits found for category: " + category)));
                    } else {
                        return Mono.just(ResponseEntity.ok(habits));
                    }
                });
    }



}
