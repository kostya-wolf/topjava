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
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> filteredByStreamWithExceeded = getFilteredByStreamWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(System.out::println);
        filteredByStreamWithExceeded.forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        // Заполняем мапу: дата -- сумма калорий
        for (UserMeal userMeal : mealList) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            caloriesMap.put(localDate, caloriesMap.getOrDefault(localDate, 0) + userMeal.getCalories());
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(
                        dateTime,
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesMap.get(dateTime.toLocalDate()) > caloriesPerDay)
                );
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredByStreamWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalTime, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalTime(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(
                        m.getDateTime(),
                        m.getDescription(),
                        m.getCalories(),
                        caloriesSumByDate.get(m.getDateTime().toLocalTime()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
