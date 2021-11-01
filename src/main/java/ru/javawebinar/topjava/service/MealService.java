package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    List<MealTo> getAllMealsTo();

    MealTo getMealToById(int id);

    void deleteMeal(int id);

    Meal createMeal(LocalDateTime dateTime, String description, int calories);

    Meal updateMeal(int id, LocalDateTime dateTime, String description, Integer calories);
}
