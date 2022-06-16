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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_ui);

        foodGroupSpinner = findViewById(R.id.foodGroupSpinner);
        ingredientNameET = findViewById(R.id.ingredientET);
        ingredientServingsET = findViewById(R.id.servingsET);
        ingredientCaloriesET = findViewById(R.id.ingredientCaloriesET);
        mealName = findViewById(R.id.nameET);
        currentIngredientSpinner = findViewById(R.id.currentIngredientSpinner);
        newMeal = MealManager.meals.get(EditMealPage.currentItem);
        oldName = MealManager.meals.get(EditMealPage.currentItem).name;
        oldIngredientStr = String.join(",", MealManager.meals.get(EditMealPage.currentItem).ingredients);

        ArrayList<String> foodGroups = new ArrayList<String>();
        foodGroups.add("Other");
        foodGroups.add("Vegetables");
        foodGroups.add("Grains");
        foodGroups.add("Dairy");
        foodGroups.add("Meat");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, foodGroups);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodGroupSpinner.setAdapter(arrayAdapter);

        generateIngredientSpinner();
        setDefaultItems();

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
                //I have no idea
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void editIngredient(View view)
    {
        Log.e("ingredient", "I am here!");
        /*if (ingredientNameET.getText().toString().equals(""))
        {
            //return a meal with no ingredients
            float calories = Float.parseFloat(ingredientCaloriesET.getText().toString());
            float servings = Float.parseFloat(ingredientCaloriesET.getText().toString());
            MealManager.addMeal(MealInputPage.mealName, new ArrayList<String>(), calories, new ArrayList<String>(), new ArrayList<Float>());
        }*/
        //calories
        /*totalCalories += Float.parseFloat(ingredientCaloriesET.getText().toString()) * Float.parseFloat(ingredientServingsET.getText().toString());
        Log.e("ingredient", "I am here1!");

        //ingredient name
        ingredientArrayList.add(ingredientNameET.getText().toString());
        Log.e("ingredient", "I am here2!");
        //food groups
        foodGroupsArrayList.add(foodGroupSpinner.getSelectedItem().toString());
        Log.e("ingredient", "I am here3!");

        //servings
        servingsArrayList.add(Float.parseFloat(ingredientServingsET.getText().toString()));
        Log.e("ingredient", "wow");*/

        //ingredient name
        newMeal.ingredients.set(currentIngredient, ingredientNameET.getText().toString());

        //calories list
        newMeal.caloriesList.set(currentIngredient, Float.parseFloat(ingredientCaloriesET.getText().toString()));

        //servings
        newMeal.servings.set(currentIngredient, Float.parseFloat(ingredientServingsET.getText().toString()));

        //groups
        newMeal.groups.set(currentIngredient, foodGroupSpinner.getSelectedItem().toString());

        //MealManager.addMeal(MealInputPage.mealName, ingredientArrayList, totalCalories, foodGroupsArrayList, servingsArrayList);
        Log.e("ingredient", "I am also here!");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNewIngredient(View view)
    {
        shouldSetSpinner = false;
        spinnerIngredientList.add("new ingredient");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerIngredientList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentIngredientSpinner.setAdapter(arrayAdapter);
        /*currentIngredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                //I have no idea
            }
        });//*/

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveChanges(View view)
    {
        //update the changes just in case
        //ingredient name
        newMeal.ingredients.set(currentIngredient, ingredientNameET.getText().toString());

        //calories list
        newMeal.caloriesList.set(currentIngredient, Float.parseFloat(ingredientCaloriesET.getText().toString()));

        //servings
        newMeal.servings.set(currentIngredient, Float.parseFloat(ingredientServingsET.getText().toString()));

        //groups
        newMeal.groups.set(currentIngredient, foodGroupSpinner.getSelectedItem().toString());

        float totalServings = 0;
        for (int i = 0; i < newMeal.servings.size(); i++)
        {
            totalServings += newMeal.servings.get(i);
            newMeal.calories += newMeal.caloriesList.get(i);
        }
        newMeal.totalServings = totalServings;

        newMeal.name = mealName.getText().toString();

        MealManager.updateMeal(newMeal, oldName, oldIngredientStr);

        //MealManager.addMeal(MealInputPage.mealName, ingredientArrayList, totalCalories, foodGroupsArrayList, servingsArrayList);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void removeIngredient(View view)
    {
        Log.e("editingmeal", "hello");
        spinnerIngredientList.remove(currentIngredient);
        Log.e("editingmeal", "hello1");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerIngredientList);
        Log.e("editingmeal", "hello2");
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.e("editingmeal", "hello3");
        currentIngredientSpinner.setAdapter(arrayAdapter);

        for(int i = 0; i < newMeal.ingredients.size(); i++)
        {
            Log.e("editingMeal", "Why "+newMeal.ingredients.get(i));
        }
        newMeal.ingredients.remove(currentIngredient);
        newMeal.caloriesList.remove(currentIngredient);
        newMeal.servings.remove(currentIngredient);
        newMeal.groups.remove(currentIngredient);
        currentIngredient = 0;
        setItems(currentIngredient);
    }

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

    private void setDefaultItems()
    {
        currentIngredientSpinner.setSelection(0);
        currentIngredient = 0;

        setDefaultItems(0);
        mealName.setText(MealManager.meals.get(EditMealPage.currentItem).name);
        //ingredientCaloriesET.setText(Float.parseFloat(MealManager.meals.get(EditMealPage.currentItem).calories.get(currentIngredient)));
    }

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

    private void setItems(int item)
    {
        for(int i = 0; i < newMeal.ingredients.size(); i++)
        {
            Log.e("editingMeal", "Whys "+newMeal.ingredients.get(i)+" "+item);
        }
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
