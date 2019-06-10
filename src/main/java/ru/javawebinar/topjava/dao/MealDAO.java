package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealDAO {
    Collection<Meal> getList();
    void add(Meal meal);
    void delete(int id);
    Meal get(int id);
}
