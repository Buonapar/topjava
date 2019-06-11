package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDAO mealDAO = new MealDAOMemory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            log.debug("Delete meal with id = " + mealDAO.get(id).getId());
            mealDAO.delete(id);
            resp.sendRedirect("meals");
        } else if (action != null && action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            log.debug("redirect to editMeal, meal with id = " + mealDAO.get(id).getId());
            req.setAttribute("table", mealDAO.get(id));
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        } else {
            req.setAttribute("table", MealsUtil.getFilteredWithExcess(mealDAO.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories").trim());
        String id = req.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (id.isEmpty()) {
            Meal meal = new Meal(dateTime, description, calories);
            mealDAO.add(meal);
            log.debug("Add meal with id = " + meal.getId());
        } else {
            Meal meal = new Meal(Integer.parseInt(id), dateTime, description, calories);
            mealDAO.add(meal);
            log.debug("Edit meal with id = " + meal.getId());
        }
        req.setAttribute("table", MealsUtil.getFilteredWithExcess(mealDAO.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

}
