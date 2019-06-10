package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDAOMemory implements MealDAO {


    private static List<Meal> list;
    private static Map<Integer, Meal> map = new ConcurrentHashMap<>();

    static {
        list = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 28, 16, 0), "Полдник", 300),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 800),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 16, 0), "Полдник", 300),
                new Meal(LocalDateTime.of(2015, Month.MAY, 28, 12, 0), "Обед", 800),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        list.forEach(meal -> map.put(meal.getId(), meal));
    }


    @Override
    public void add(Meal meal) {
        map.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) { ;
        map.remove(id);
    }

    @Override
    public Collection<Meal> getList() {
        return map.values();
    }

    @Override
    public Meal get(int id) {
        return map.get(id);
    }
}
