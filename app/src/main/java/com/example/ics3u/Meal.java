package com.example.ics3u;

public class Meal {
    //variables
    public String name; //name of the meal, e.x: Chicken Salad, Beef Stew, etc
    //ingredients will be implemented later (probably)
    public String[] ingredients; //ingredient list, e.x: [lettuce, chicken, fruit]

    public Meal(String name)
    {
        this.name = name;
    }

    public Meal(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
