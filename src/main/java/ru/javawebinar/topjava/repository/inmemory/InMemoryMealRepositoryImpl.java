package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 18, 11, 0), "Гречка", 600), 2);
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 18, 9, 0), "Кефир", 150), 2);
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 17, 21, 0), "Ужин", 900), 2);
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 16, 10, 0), "Апельсин", 250), 2);
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 17, 12, 0), "Суп", 750), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else  if (get(meal.getId(), userId) == null)
            return null;

        Map<Integer, Meal> userMeal = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMeal.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal != null && userMeal.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal == null ? null : userMeal.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal == null ?
                Collections.emptyList() : userMeal.values().stream()
                                                            .sorted(revers)
                                                            .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startTime, LocalDate endTime, int userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal.values().stream()
                                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startTime, endTime))
                                .sorted(revers)
                                .collect(Collectors.toList());
    }

    private Comparator<Meal> revers = Comparator.comparing(Meal::getDateTime).reversed();
}

