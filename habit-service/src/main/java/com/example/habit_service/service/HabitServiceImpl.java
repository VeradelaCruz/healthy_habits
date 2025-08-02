package com.example.habit_service.service;

import com.example.habit_service.models.Habit;
import com.example.habit_service.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService{
    @Autowired
    private final HabitRepository habitRepository;

    @Override
    public Mono<Habit> createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    @Override
    public Flux<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    @Override
    public Mono<Habit> getHabitById(String id) {
        return habitRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteHabit(String id) {
        return habitRepository.deleteById(id);
    }

    @Override
    public Flux<Habit> getHabitsByUserId(String userId) {
        return habitRepository.findByUserId(userId);
    }

    @Override
    public Flux<Habit> getHabitsByCategory(String category) {
        return habitRepository.findByCategory(category);
    }


}
