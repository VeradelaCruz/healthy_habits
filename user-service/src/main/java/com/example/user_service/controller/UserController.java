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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "Operaciones relacionadas con usuarios")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Crear un usuario nuevo", description = "Crea un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
            content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "409", description = "Usuario duplicado")
    @PostMapping("/addUser")
    public Mono<ResponseEntity<Object>> addUser(
            @Parameter(description = "Usuario a crear", required = true)
            @Valid @RequestBody User user) {
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

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista completa de usuarios")
    @GetMapping("/getAll")
    public Flux<User> findAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "Buscar usuario por ID", description = "Devuelve un usuario dado su ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/id/{id}")
    public Mono<ResponseEntity<?>> findUserById(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Eliminar usuario por ID", description = "Elimina un usuario del sistema dado su ID")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<?>> removeUser(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable String id) {
        return userService.getUserById(id)
                .thenReturn(ResponseEntity.ok(
                        Map.of("message", "User with id " + id + " was deleted successfully.")));
    }

    @Operation(summary = "Buscar usuario por nombre", description = "Busca un usuario usando su nombre de usuario")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/byName/{userName}")
    public Mono<ResponseEntity<?>> findUserByName(
            @Parameter(description = "Nombre de usuario para búsqueda", required = true)
            @PathVariable String userName){
        return userService.getUserByName(userName)
                .map(foundUser -> ResponseEntity.ok().body(foundUser));
    }
}


