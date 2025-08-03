package com.example.tracker_service.controller;

import com.example.tracker_service.dtos.TrackerWithUserAndHabit;
import com.example.tracker_service.models.Tracker;
import com.example.tracker_service.service.TrackerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracker")
@Tag(name = "Tracker", description = "Operaciones relacionadas con el seguimiento de hábitos")
public class TrackerController {
    private final TrackerService trackerService;

    @Operation(summary = "Crear un nuevo tracker", description = "Guarda un nuevo registro de seguimiento de hábito")
    @ApiResponse(responseCode = "201", description = "Tracker creado exitosamente",
            content = @Content(schema = @Schema(implementation = Tracker.class)))
    @PostMapping("/addTracker")
    public Mono<ResponseEntity<Tracker>> addTracker(
            @Parameter(description = "Objeto Tracker a crear", required = true)
            @Valid @RequestBody Tracker tracker) {
        return trackerService.createTracker(tracker)
                .map(trackerSaved -> ResponseEntity.status(HttpStatus.CREATED).body(trackerSaved));
    }

    @Operation(summary = "Obtener tracker por ID", description = "Devuelve el tracker con el ID especificado")
    @ApiResponse(responseCode = "200", description = "Tracker encontrado",
            content = @Content(schema = @Schema(implementation = Tracker.class)))
    @ApiResponse(responseCode = "404", description = "Tracker no encontrado")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Tracker>> findById(
            @Parameter(description = "ID del tracker", required = true)
            @PathVariable String id) {
        return trackerService.getTrackerById(id)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Obtener todos los trackers", description = "Lista todos los registros de seguimiento")
    @GetMapping("/getAll")
    public Flux<Tracker> findAllTrackers() {
        return trackerService.getAllTrackers();
    }

    @Operation(summary = "Eliminar tracker por ID", description = "Elimina el tracker con el ID especificado")
    @ApiResponse(responseCode = "204", description = "Tracker eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Tracker no encontrado")
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> removeTracker(
            @Parameter(description = "ID del tracker a eliminar", required = true)
            @PathVariable String id) {
        return trackerService.getTrackerById(id)
                .flatMap(tracker -> trackerService.deleteTracker(id)
                        .thenReturn(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(summary = "Obtener trackers por userId", description = "Lista trackers de un usuario específico")
    @GetMapping("/byUserId/{userId}")
    public Flux<Tracker> findByUserId(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId) {
        return trackerService.getTrackersByUserId(userId);
    }

    @Operation(summary = "Obtener trackers por habitId", description = "Lista trackers de un hábito específico")
    @GetMapping("/byHabitId/{habitId}")
    public Flux<Tracker> findByHabitId(
            @Parameter(description = "ID del hábito", required = true)
            @PathVariable String habitId) {
        return trackerService.getTrackersByHabitId(habitId);
    }

    @Operation(summary = "Obtener tracker con detalles de usuario y hábito", description = "Devuelve un tracker con la información detallada del usuario y el hábito")
    @ApiResponse(responseCode = "200", description = "Tracker con detalles encontrado",
            content = @Content(schema = @Schema(implementation = TrackerWithUserAndHabit.class)))
    @ApiResponse(responseCode = "404", description = "Tracker no encontrado")
    @GetMapping("/with-user-habits/{id}")
    public Mono<ResponseEntity<TrackerWithUserAndHabit>> getTrackerWithDetails(
            @Parameter(description = "ID del tracker", required = true)
            @PathVariable String id) {
        return trackerService.findTrackerWithUserAndHabit(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
