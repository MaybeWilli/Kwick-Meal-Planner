package com.example.ics3u;

import java.util.ArrayList;

public class Meal {
    //variables
    public String name; //name of the meal, e.x: Chicken Salad, Beef Stew, etc
    //ingredients will be implemented later (probably)
    public ArrayList<String> ingredients; //ingredient list, e.x: [lettuce, chicken, fruit]
    public ArrayList<String> groups; //food groups, e.x: [vegetable, vegetable, dairy]
    public ArrayList<Float> servings; //servings for each of the ingredients, e.x: [1, 2, 3]
    public ArrayList<Float> calories;
    public float totalServings;
    public float totalCalories;

    public Meal() //dunno why you'd ever use this...
    {
        name = "Empty Meal";
    }

    public Meal(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Meal(String name, ArrayList<String> ingredients, float calories, ArrayList<String> groups, ArrayList<Float> servings, ArrayList<Float> calorieList, float totCals) {
        this.name = name;
        this.ingredients = ingredients;
        this.totalCalories = calories;
        this.groups = groups;
        this.servings = servings;
        this.totalCalories = totCals;
    }
}

