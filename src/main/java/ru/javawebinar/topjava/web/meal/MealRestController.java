package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {

    private  final Logger log = LoggerFactory.getLogger(getClass());
    private int userId = SecurityUtil.authUserId();


    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll(userId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        meal.setId(id);
        service.update(meal, userId);
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getWithExcess(
                            service.getBetween(
                                    startDate != null ? LocalDateTime.of(startDate, LocalTime.MIN) : LocalDateTime.of(LocalDate.MIN, startTime),
                                    endDate != null ? LocalDateTime.of(endDate, LocalTime.MAX) : LocalDateTime.of(LocalDate.MAX, endTime),
                                    userId),
                            SecurityUtil.authUserCaloriesPerDay());
    }
}