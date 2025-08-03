package com.example.habit_service.controller;

import com.example.habit_service.dtos.HabitWithUserDTO;
import com.example.habit_service.exception.HabitNotFoundException;
import com.example.habit_service.exception.UserNotFoundException;
import com.example.habit_service.models.Habit;
import com.example.habit_service.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequiredArgsConstructor
@RestController
@RequestMapping("/habits")
@Tag(name = "Habits", description = "Operaciones para gestionar hábitos")
public class HabitController {
    private final HabitService habitService;

    @Operation(summary = "Crear un nuevo hábito")
    @ApiResponse(responseCode = "201", description = "Hábito creado correctamente")
    @PostMapping("/addHabit")
    public Mono<ResponseEntity<Habit>> addHabit(@Valid @RequestBody Habit habit) {
        return habitService.createHabit(habit)
                .map(savedHabit -> ResponseEntity.status(HttpStatus.CREATED).body(savedHabit));
    }

    @Operation(summary = "Obtener un hábito por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hábito encontrado"),
            @ApiResponse(responseCode = "404", description = "Hábito no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Habit>> getHabit(@PathVariable String id) {
        return habitService.getHabitById(id)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Listar todos los hábitos")
    @GetMapping("/getAll")
    public Flux<Habit> findAllHabits(){
        return habitService.getAllHabits();
    }

    @Operation(summary = "Eliminar un hábito por ID")
    @ApiResponse(responseCode = "200", description = "Hábito eliminado correctamente")
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> removeHabit(@PathVariable String id) {
        return habitService.deleteHabit(id)
                .thenReturn(ResponseEntity.ok("Habit deleted successfully."));
    }

    @Operation(summary = "Obtener hábitos por ID de usuario")
    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<List<Habit>>> getHabitsByUser(@PathVariable String userId) {
        return habitService.getHabitsByUserId(userId)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Obtener hábitos por categoría")
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

    @Operation(summary = "Obtener hábito con usuario asociado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hábito y usuario encontrados"),
            @ApiResponse(responseCode = "404", description = "Hábito o usuario no encontrado")
    })
    @GetMapping("/with-user/{id}")
    public Mono<ResponseEntity<HabitWithUserDTO>> getHabitWithUser(@PathVariable String id) {
        return habitService.getHabitWithUser(id)
                .map(ResponseEntity::ok)
                .onErrorResume(HabitNotFoundException.class, e -> Mono.just(ResponseEntity.notFound().build()))
                .onErrorResume(UserNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)));
    }
}
