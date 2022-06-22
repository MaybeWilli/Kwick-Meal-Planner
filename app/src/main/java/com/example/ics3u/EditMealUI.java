/*
* Contains the functions called by the four buttons on the activity_edit_meal_ui xml page.
* Calls the relevant functions from the MealManager whenever the buttons are pressed.
* Automatically fills in the editTexts with the data the user last used, so that they can
* see what they inputed last time.
 */
package com.example.ics3u;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditMealUI extends AppCompatActivity {

    private EditText mealName;
    private Spinner foodGroupSpinner;
    private Spinner currentIngredientSpinner;
    private EditText ingredientNameET;
    private EditText ingredientServingsET;
    private EditText ingredientCaloriesET;
    private Meal newMeal = new Meal();

    private ArrayList<String> ingredientArrayList = new ArrayList<String>();
    private ArrayList<String> foodGroupsArrayList = new ArrayList<String>();
    private float totalCalories = 0;
    private ArrayList<Float> servingsArrayList = new ArrayList<Float>();

    private ArrayList<String> spinnerIngredientList = new ArrayList<String>();
    private int currentIngredient = 0;
    private String oldName;
    private String oldIngredientStr;
    private boolean shouldSetSpinner = true;


    //Set the editTexts and spinners with the values the user inputed last time
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_ui);

        //get variables
        foodGroupSpinner = findViewById(R.id.foodGroupSpinner);
        ingredientNameET = findViewById(R.id.ingredientET);
        ingredientServingsET = findViewById(R.id.servingsET);
        ingredientCaloriesET = findViewById(R.id.ingredientCaloriesET);
        mealName = findViewById(R.id.nameET);
        currentIngredientSpinner = findViewById(R.id.currentIngredientSpinner);
        newMeal = MealManager.meals.get(EditMealPage.currentItem);
        oldName = MealManager.meals.get(EditMealPage.currentItem).name;
        oldIngredientStr = String.join(",", MealManager.meals.get(EditMealPage.currentItem).ingredients);

        //add items to the food groups spinner
        ArrayList<String> foodGroups = new ArrayList<String>();
        foodGroups.add("Other");
        foodGroups.add("Vegetables");
        foodGroups.add("Grains");
        foodGroups.add("Dairy");
        foodGroups.add("Meat");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, foodGroups);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodGroupSpinner.setAdapter(arrayAdapter);

        //add items to the spinner that lets the user choose what ingredient to edit
        generateIngredientSpinner();

        //fill the editTexts with information that the user used last time
        setDefaultItems();

        //make the editTexts update with new information whenever the user selects a new
        //ingredient from the dropdown spinner
        currentIngredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (shouldSetSpinner)
                {
                    currentIngredient = i;
                    setItems(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing happens
            }
        });
    }

    //edit the meal's data in the arrayList.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void editIngredient(View view)
    {
        //ingredient name
        newMeal.ingredients.set(currentIngredient, ingredientNameET.getText().toString());

        //calories list
        newMeal.caloriesList.set(currentIngredient, Float.parseFloat(ingredientCaloriesET.getText().toString()));

        //servings
        newMeal.servings.set(currentIngredient, Float.parseFloat(ingredientServingsET.getText().toString()));

        //groups
        newMeal.groups.set(currentIngredient, foodGroupSpinner.getSelectedItem().toString());
    }

    //create a new ingredient, pass it into the spinner, and give it default values (0 and "other" for everything")
    //it's name is "new ingredient" by default
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNewIngredient(View view)
    {
        //reset the ingredient selection spinner
        shouldSetSpinner = false;
        spinnerIngredientList.add("new ingredient");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerIngredientList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentIngredientSpinner.setAdapter(arrayAdapter);

        //set the default values for the new ingredient
        newMeal.ingredients.add("new ingredient");
        for(int i = 0; i < newMeal.ingredients.size(); i++)
        {
            Log.e("editingMeal", newMeal.ingredients.get(i));
        }
        newMeal.caloriesList.add(0f);
        newMeal.servings.add(0f);
        newMeal.groups.add("Other");
        currentIngredient = spinnerIngredientList.size()-1;
        setItems(currentIngredient);
        shouldSetSpinner = true;
    }

    //pass all the information into the MealManager, so the changes are actually saved
    //in the database for next time the app is opened
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveChanges(View view)
    {
        //fetch the changes in the current EditTexts
        //ingredient name
        newMeal.ingredients.set(currentIngredient, ingredientNameET.getText().toString());

        //calories list
        newMeal.caloriesList.set(currentIngredient, Float.parseFloat(ingredientCaloriesET.getText().toString()));

        //servings
        newMeal.servings.set(currentIngredient, Float.parseFloat(ingredientServingsET.getText().toString()));

        //groups
        newMeal.groups.set(currentIngredient, foodGroupSpinner.getSelectedItem().toString());

        //update the total servings and total calories
        float totalServings = 0;
        for (int i = 0; i < newMeal.servings.size(); i++)
        {
            totalServings += newMeal.servings.get(i);
            newMeal.calories += newMeal.caloriesList.get(i);
        }
        newMeal.totalServings = totalServings;

        //update the name
        newMeal.name = mealName.getText().toString();

        //pass the information into the MealManager to update the ArrayList and the database
        MealManager.updateMeal(newMeal, oldName, oldIngredientStr);

        //close the UI page
        finish();
    }

    //removes an ingredient from the meal
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void removeIngredient(View view)
    {
        //remove the ingredient from the ingredients spinner
        spinnerIngredientList.remove(currentIngredient);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerIngredientList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentIngredientSpinner.setAdapter(arrayAdapter);

        //remove the ingredient from all the arrayLists
        newMeal.ingredients.remove(currentIngredient);
        newMeal.caloriesList.remove(currentIngredient);
        newMeal.servings.remove(currentIngredient);
        newMeal.groups.remove(currentIngredient);
        currentIngredient = 0;

        //set the spinner to a new item
        setItems(currentIngredient);
    }

    //add all the ingredients to the spinner
    private void generateIngredientSpinner()
    {
        for (int i = 0; i < MealManager.meals.get(EditMealPage.currentItem).ingredients.size(); i++)
        {
            spinnerIngredientList.add(MealManager.meals.get(EditMealPage.currentItem).ingredients.get(i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerIngredientList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentIngredientSpinner.setAdapter(arrayAdapter);
    }

    //select an item in the spinner, and fill in all the editTexts with ingredient 0's data
    private void setDefaultItems()
    {
        currentIngredientSpinner.setSelection(0);
        currentIngredient = 0;

        setDefaultItems(0);
        mealName.setText(MealManager.meals.get(EditMealPage.currentItem).name);
    }

    //fill in the editTexts for a specified ingredient, but using the MealManager values
    private void setDefaultItems(int item)
    {
        //ingredient name
        ingredientNameET.setText(MealManager.meals.get(EditMealPage.currentItem).ingredients.get(item));

        //calories
        ingredientCaloriesET.setText(MealManager.meals.get(EditMealPage.currentItem).caloriesList.get(item).toString());

        //food group
        if (MealManager.meals.get(EditMealPage.currentItem).groups.get(item).equals("Vegetables"))
        {
            foodGroupSpinner.setSelection(1);
        }
        else if (MealManager.meals.get(EditMealPage.currentItem).groups.get(item).equals("Grains"))
        {
            foodGroupSpinner.setSelection(2);
        }
        else if (MealManager.meals.get(EditMealPage.currentItem).groups.get(item).equals("Dairy"))
        {
            foodGroupSpinner.setSelection(3);
        }
        else if (MealManager.meals.get(EditMealPage.currentItem).groups.get(item).equals("Meat"))
        {
            foodGroupSpinner.setSelection(4);
        }
        else if (MealManager.meals.get(EditMealPage.currentItem).groups.get(item).equals("Other"))
        {
            foodGroupSpinner.setSelection(0);
        }

        //servings
        ingredientServingsET.setText(MealManager.meals.get(EditMealPage.currentItem).servings.get(item).toString());
    }

    //set editText values from the newMeal meal instead of the MealManager
    private void setItems(int item)
    {
        //ingredient name
        ingredientNameET.setText(newMeal.ingredients.get(item));

        //calories
        ingredientCaloriesET.setText(newMeal.caloriesList.get(item).toString());

        //food group
        if (newMeal.groups.get(item).equals("Vegetables"))
        {
            foodGroupSpinner.setSelection(1);
        }
        else if (newMeal.groups.get(item).equals("Grains"))
        {
            foodGroupSpinner.setSelection(2);
        }
        else if (newMeal.groups.get(item).equals("Dairy"))
        {
            foodGroupSpinner.setSelection(3);
        }
        else if (newMeal.groups.get(item).equals("Meat"))
        {
            foodGroupSpinner.setSelection(4);
        }
        else if (newMeal.groups.get(item).equals("Other"))
        {
            foodGroupSpinner.setSelection(0);
        }

        //servings
        ingredientServingsET.setText(newMeal.servings.get(item).toString());
    }
}
