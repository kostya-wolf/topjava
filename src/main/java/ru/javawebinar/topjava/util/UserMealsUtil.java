package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredByStreamWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();

        // Заполняем мапу: дата -- сумма калорий
        for (UserMeal userMeal : mealList) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            if (caloriesMap.containsKey(localDate)) {
                caloriesMap.put(localDate, caloriesMap.get(localDate) + userMeal.getCalories());
            } else {
                caloriesMap.put(localDate, userMeal.getCalories());
            }
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMeal userMeal : mealList) {
            if (userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime)) {
                result.add(new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)
                );
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredByStreamWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = mealList
                .stream()
                .collect(Collectors.toMap(
                        m -> m.getDateTime().toLocalDate(),
                        m -> m.getCalories(),
                        (calories1, calories2) -> calories1 + calories2
                ));

        List<UserMealWithExceed> result = new ArrayList<>();
        mealList
                .stream()
                .filter(m -> m.getDateTime().toLocalTime().isAfter(startTime))
                .filter(m -> m.getDateTime().toLocalTime().isBefore(endTime))
                .forEach(m -> result.add(
                        new UserMealWithExceed(
                                m.getDateTime(),
                                m.getDescription(),
                                m.getCalories(),
                                caloriesMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay)
                        )
                );
        return result;
    }
}
