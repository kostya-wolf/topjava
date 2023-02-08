package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    void createMeal(LocalDateTime dateTime, String description, int calories);

    void deleteMeal(int id);

    List<MealTo> getAllMealsTo();

    MealTo getMealToById(int id);

    void updateMeal(int id, LocalDateTime dateTime, String description, int calories);
}
