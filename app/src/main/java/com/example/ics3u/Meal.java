package com.example.ics3u;

import java.util.ArrayList;

public class Meal {
    //variables
    public String name; //name of the meal, e.x: Chicken Salad, Beef Stew, etc
    //ingredients will be implemented later (probably)
    public ArrayList<String> ingredients; //ingredient list, e.x: [lettuce, chicken, fruit]
    public ArrayList<String> groups; //food groups, e.x: [vegetable, vegetable, dairy]
    public ArrayList<Float> servings; //servings for each of the ingredients, e.x: [1, 2, 3]
    public ArrayList<Float> caloriesList;
    public float totalServings;
    public float calories;

    public Meal() //dunno why you'd ever use this...
    {
        name = "Empty Meal";
    }

    public Meal(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Meal(String name, ArrayList<String> ingredients, float calories, ArrayList<String> groups, ArrayList<Float> servings, ArrayList<Float> calorieList, float totServings) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
        this.groups = groups;
        this.servings = servings;
        this.caloriesList = calorieList;
        this.totalServings = totServings;
    }
}

