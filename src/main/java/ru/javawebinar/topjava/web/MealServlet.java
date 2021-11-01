package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String ACTION_ADD = "add";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_EDIT = "edit";

    private MealService service = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case ACTION_ADD: {
                    log.debug("forward to add meal");
                    request.getRequestDispatcher("newEditMeal.jsp").forward(request, response);
                    return;
                }
                case ACTION_EDIT: {
                    int id = Integer.parseInt(request.getParameter("id"));
                    MealTo mealTo = service.getMealToById(id);
                    request.setAttribute("mealTo", mealTo);
                    log.debug("forward to edit meal");
                    request.getRequestDispatcher("newEditMeal.jsp").forward(request, response);
                    return;
                }
                case ACTION_DELETE: {
                    log.debug("deleting meal");
                    int id = Integer.parseInt(request.getParameter("id"));
                    service.deleteMeal(id);
                    break;
                }
            }
        }

        request.setAttribute("allMealsTo", service.getAllMealsTo());
        log.debug("forward to meals");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));

            if (action.equals(ACTION_ADD)) {
                log.debug("adding meal");
                service.createMeal(dateTime, description, calories);
            } else if (action.equals(ACTION_EDIT)) {
                log.debug("updating meal");
                int id = Integer.parseInt(request.getParameter("id"));
                service.updateMeal(id, dateTime, description, calories);
            }
        }

        request.setAttribute("allMealsTo", service.getAllMealsTo());
        log.debug("forward to meals");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
