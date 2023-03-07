package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(getFirstUserMealId(), USER_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(getTestMeals(USER_ID).get(0));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(getFirstUserMealId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        int mealId = getFirstUserMealId();
        service.delete(mealId, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(mealId, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        int mealId = getFirstUserMealId();
        assertThrows(NotFoundException.class, () -> service.delete(mealId, NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        assertThat(service.getBetweenInclusive(LocalDate.parse("2021-05-11"), LocalDate.parse("2021-05-11"), ADMIN_ID)).hasSize(2);
    }

    @Test
    public void getAll() {
        List<Meal> actual = service.getAll(USER_ID);
        List<Meal> expected = getSortedTestMeals(USER_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void update() {
        Meal updated = getUpdatedUserTestMeal();
        service.update(updated, USER_ID);
        assertThat(service.get(updated.getId(), USER_ID)).usingRecursiveComparison().isEqualTo(getUpdatedUserTestMeal());
    }

    @Test
    public void updateNotFound() {
        Meal updated = getUpdatedUserTestMeal();
        assertThrows(NotFoundException.class, () -> service.update(updated, NOT_FOUND));
    }

    @Test
    public void create() {
        Meal created = service.create(getNewTestMeal(), USER_ID);
        Integer newId = created.getId();
        Meal expected = getNewTestMeal();
        expected.setId(newId);
        assertThat(created).usingRecursiveComparison().isEqualTo(expected);
        assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(getFirstUserMeal().getDateTime(), "Что-то новое", 100), USER_ID));
    }
}