package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private MealService service = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add": {
                    log.debug("forward to add meal");
                    request.getRequestDispatcher("newEditMeal.jsp").forward(request, response);
                    break;
                }
                case "delete": {
                    Integer id = Integer.parseInt(request.getParameter("id"));
                    service.deleteMeal(id);
                    break;
                }
            }
        } else {
            request.setAttribute("allMealsTo", service.getAllMealsTo());
            log.debug("forward to meals");
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        log.debug("adding meal");
        service.createMeal(dateTime, description, calories);

        request.setAttribute("allMealsTo", service.getAllMealsTo());
        log.debug("forward to meals");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
