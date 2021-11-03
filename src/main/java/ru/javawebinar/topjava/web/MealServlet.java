package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    //private MealRepository repository;
    private MealRestController mealRestController;

    @Override
    public void init() {
        //repository = new InMemoryMealRepository();

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            this.mealRestController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        int userId = getUserId(request);

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                userId);

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        //repository.save(meal, userId);
        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = getUserId(request);

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                //repository.delete(id, userId);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, userId) :
                        mealRestController.get(getId(request));
                //repository.get(getId(request), userId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter": {
                log.info("filter action");
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                request.setAttribute("meals", mealRestController.filter(startDate, endDate, startTime, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                //MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private LocalDate parseLocalDate(String localDateString) {
        if (!StringUtils.hasLength(localDateString)) {
            return null;
        }
        return LocalDate.parse(localDateString);
    }

    private LocalTime parseLocalTime(String localTimeString) {
        if (!StringUtils.hasLength(localTimeString)) {
            return null;
        }
        return LocalTime.parse(localTimeString);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private int getUserId(HttpServletRequest request) {
        String paramId = request.getParameter("userId");
        if (paramId != null) {
            SecurityUtil.setAuthUserId(Integer.parseInt(paramId));
        }
        return SecurityUtil.authUserId();
    }
}
