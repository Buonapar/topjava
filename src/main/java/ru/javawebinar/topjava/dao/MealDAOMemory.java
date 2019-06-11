package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOMemory implements MealDAO {

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final  AtomicInteger count = new AtomicInteger(0);

    public MealDAOMemory(){
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 28, 16, 0), "Полдник", 300));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 800));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 16, 0), "Полдник", 300));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 28, 12, 0), "Обед", 800));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 28, 15, 0), "Гречка", 800));
    }

    @Override
    public void add(Meal meal) {
        if (meal.getId() == null)
            meal.setId(getIDAuto());

        repository.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) { ;
        repository.remove(id);
    }

    @Override
    public Collection<Meal> getList() {
        return repository.values();
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    private int getIDAuto() {
        return count.incrementAndGet();
    }

}
