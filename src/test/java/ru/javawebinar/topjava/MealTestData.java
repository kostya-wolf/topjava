package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.*;

public class MealTestData {
    public static final Map<Integer, List<Meal>> meals = new HashMap<>();

    private static final int START_SEQ = GUEST_ID;
    private static final AtomicInteger counter = new AtomicInteger(START_SEQ);

    static {
        meals.put(USER_ID, Arrays.asList(
                new Meal(counter.incrementAndGet(), LocalDateTime.parse("2023-01-30T10:00"), "Завтрак", 500),
                new Meal(counter.incrementAndGet(), LocalDateTime.parse("2023-01-30T12:00"), "Обед", 500),
                new Meal(counter.incrementAndGet(), LocalDateTime.parse("2023-01-30T20:00"), "Ужин", 500))
        );
        meals.put(ADMIN_ID, Arrays.asList(
                new Meal(counter.incrementAndGet(), LocalDateTime.parse("2022-05-11T20:00"), "Ужин", 500),
                new Meal(counter.incrementAndGet(), LocalDateTime.parse("2022-05-11T22:00"), "Ням-ням", 500))
        );
//        meals.put(GUEST_ID, Collections.emptyList());
    }

    public static Meal getFirstUserMeal() {
        return meals.get(USER_ID).get(0);
    }

    public static int getFirstUserMealId() {
        return getFirstUserMeal().getId();
    }

    public static List<Meal> getTestMeals(int userId) {
        return meals.get(userId);
    }

    public static List<Meal> getSortedTestMeals(int userId) {
        return getTestMeals(userId)
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static Meal getNewTestMeal() {
        return new Meal(LocalDateTime.parse("2022-01-01T00:00"), "Ням-ням", 1005);
    }

    public static Meal getUpdatedUserTestMeal() {
        Meal meal = new Meal(getFirstUserMeal());
        meal.setCalories(1234);
        meal.setDateTime(LocalDateTime.parse("2021-12-31T23:55"));
        meal.setDescription("Новогодний ужин");
        return meal;
    }

}
