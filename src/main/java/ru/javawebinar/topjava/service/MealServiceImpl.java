package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

public class MealServiceImpl implements MealService {
    @Override
    public void createMeal(LocalDateTime dateTime, String description, int calories) {
        MealsUtil.getAllMeals().add(new Meal(MealsUtil.getNewId(), dateTime, description, calories));
    }

    @Override
    public void deleteMeal(int id) {
        Meal meal = getMealById(id);
        MealsUtil.getAllMeals().remove(meal);
    }

    @Override
    public List<MealTo> getAllMealsTo() {
        return MealsUtil.getAllMealsTo();
    }

    private Meal getMealById(int id) {
        return MealsUtil.getAllMeals()
                .stream()
                .filter(meal -> id == meal.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public MealTo getMealToById(int id) {
        return getAllMealsTo()
                .stream()
                .filter(mealTo -> id == mealTo.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = getMealById(id);
        int index = MealsUtil.getAllMeals().indexOf(meal);
        MealsUtil.getAllMeals().remove(index);
        MealsUtil.getAllMeals().add(index, new Meal(id, dateTime, description, calories));
    }
}
