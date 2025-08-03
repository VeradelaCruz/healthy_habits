package com.example.habit_service.controllerTest;

import com.example.habit_service.controller.HabitController;
import com.example.habit_service.models.Habit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(HabitController.class)

public class HabitControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllHabitsTest_ShouldReturnList() {
        webTestClient.get()
                .uri("/habits/getAll")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Habit.class)
                .value(list -> assertTrue(list.size() > 0))  // usa assertTrue o AssertJ
                .consumeWith(response -> {
                    List<Habit> habits = response.getResponseBody();
                    assertNotNull(habits);
                });
    }


    @Test
    public void getHabitById_ShouldReturnMono() {
        String habitId = "688b8dabbac26c38f37dca34";

        webTestClient.get()
                .uri("/habits/{id}", habitId)
                .exchange() //--> ejecuta el llamado de get
                .expectStatus().isOk()
                .expectBody(Habit.class)
                .consumeWith(response -> {
                    Habit habit = response.getResponseBody();
                    assertNotNull(habit);
                    assertEquals(habitId, habit.getId());



                });
    }

}
