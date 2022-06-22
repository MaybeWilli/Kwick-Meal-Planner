/*
* This java file contains the definition for the PlannedMeal. It's the same as a meal,
* but it contains an extra two variables: one for a date, and one for the servings
* eaten for the day. It does not use inheritance, because the servings field could
* get confused with the servings list in the Meal class. It also doesn't use encapsulation,
* because it's not like a calendar class, where messing with the numbers from outside
* can cause exceptions.
 */
package com.example.ics3u;

public class PlannedMeal {
    public Meal meal; //meal being eaten
    public String date; //date of meal
    public float servings; //how many servings of meal being eaten

    public PlannedMeal(Meal meal, String date, float servings)
    {
        this.meal = meal;
        this.date = date;
        this.servings = servings;
    }
}
