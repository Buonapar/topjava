package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    @Autowired
    private MealRestController mealController;

    @GetMapping("/meals")
    public String showAll(Model model) {
        model.addAttribute("meals", mealController.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest httpServletRequest) {
        int id = Integer.parseInt(httpServletRequest.getParameter("id"));
        mealController.delete(id);
        return "redirect:meals";
    }

    @GetMapping("/filter")
    public String filter(HttpServletRequest httpServletRequest, Model model) {
        LocalDate startDate = parseLocalDate(httpServletRequest.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(httpServletRequest.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(httpServletRequest.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(httpServletRequest.getParameter("endTime"));
        model.addAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, Model model) {
        String id = httpServletRequest.getParameter("id");
        Meal meal;
        if (id == null) {
            meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        } else {
            meal = mealController.get(Integer.parseInt(id));
        }

    model.addAttribute("meal", meal);
    return "mealForm";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest httpServletRequest) {
        Meal meal = new Meal(
                LocalDateTime.parse(httpServletRequest.getParameter("dateTime")),
                httpServletRequest.getParameter("description"),
                Integer.parseInt(httpServletRequest.getParameter("calories")));
        mealController.create(meal);
        return "redirect:meals";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest httpServletRequest) {
        int id = Integer.parseInt(httpServletRequest.getParameter("id"));
        Meal meal = new Meal(
                LocalDateTime.parse(httpServletRequest.getParameter("dateTime")),
                httpServletRequest.getParameter("description"),
                Integer.parseInt(httpServletRequest.getParameter("calories")));
        mealController.update(meal, id);
        return "redirect:meals";
    }
}
