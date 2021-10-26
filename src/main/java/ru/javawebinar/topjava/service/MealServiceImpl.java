package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

public class MealServiceImpl implements MealService {
    @Override
    public List<MealTo> getAllMealsTo() {
        return MealsUtil.getAllMealsTo();
    }

    @Override
    public MealTo getMealToById(Integer id) {
        return null;
    }

    @Override
    public void deleteMeal(Integer id) {

    }

    @Override
    public Meal createMeal(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(getNewId(), dateTime, description, calories);
        MealsUtil.getAllMeals().add(meal);
        return meal;
    }

    private int getNewId() {
        return 1 + getAllMealsTo()
                .stream()
                .mapToInt(MealTo::getId)
                .max()
                .orElse(0);
    }

    @Override
    public Meal updateMeal(Integer id) {
        return null;
    }
}
