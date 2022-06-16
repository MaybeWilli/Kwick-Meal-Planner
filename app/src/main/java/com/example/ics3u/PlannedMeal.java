package com.example.ics3u;

public class PlannedMeal {
    public Meal meal;
    public String date;
    public float servings;

    public PlannedMeal(Meal meal, String date, float servings)
    {
        this.meal = meal;
        this.date = date;
        this.servings = servings;
    }
}
