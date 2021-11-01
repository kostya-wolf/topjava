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
    public MealTo getMealToById(int id) {
        return getAllMealsTo()
                .stream()
                .filter(mealTo -> id == mealTo.getId())
                .findFirst()
                .orElse(null);
    }

    private Meal getMealById(int id) {
        return MealsUtil.getAllMeals()
                .stream()
                .filter(m -> id == m.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteMeal(int id) {
        Meal meal = getMealById(id);
        MealsUtil.getAllMeals().remove(meal);
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
    public Meal updateMeal(int id, LocalDateTime dateTime, String description, Integer calories) {
        Meal meal = getMealById(id);
        int index = MealsUtil.getAllMeals().indexOf(meal);
        MealsUtil.getAllMeals().remove(index);
        MealsUtil.getAllMeals().add(index, new Meal(id, dateTime, description, calories));
        return meal;
    }
}
