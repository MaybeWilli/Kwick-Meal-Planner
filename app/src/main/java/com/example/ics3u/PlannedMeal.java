package com.example.ics3u;

import java.time.LocalDate;

public class PlannedMeal {
    public Meal meal;
    public String date;

    public PlannedMeal(Meal meal, String date)
    {
        this.meal = meal;
        this.date = date;
    }
}
