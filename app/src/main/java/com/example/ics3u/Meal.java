package com.example.ics3u;

import java.util.ArrayList;

public class Meal {
    //variables
    public String name; //name of the meal, e.x: Chicken Salad, Beef Stew, etc
    //ingredients will be implemented later (probably)
    public ArrayList<String> ingredients; //ingredient list, e.x: [lettuce, chicken, fruit]
    public float calories;

    public Meal() //dunno why you'd ever use this...
    {
        name = "Empty Meal";
    }

    public Meal(String name)
    {
        this.name = name;
    }

    public Meal(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Meal(String name, ArrayList<String> ingredients, float calories) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
    }
}
