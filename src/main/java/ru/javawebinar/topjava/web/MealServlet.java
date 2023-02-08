package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //logger.debug("redirect to meals");
        logger.debug("forward to meals");
        req.setAttribute("allMealsTo", MealsUtil.getAllMealsTo());
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        //resp.sendRedirect("meals.jsp");
    }
}
