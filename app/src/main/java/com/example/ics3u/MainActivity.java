package com.example.ics3u;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    public static SavedMealDatabase savedMealDatabase;// = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
    public static MealDao mealDao;
    public static SavedMealDatabase savedPlannedMealDatabase;// = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
    public static MealDao plannedMealDao;
    public static MediaPlayer mediaPlayer;
    public static boolean isFirstOpen = true;
    //public MealDao mealDao = savedMealDatabase.mealDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.e("hmm", "Hello");

        //Deal with database stuff
        savedMealDatabase = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
        mealDao = savedMealDatabase.mealDao();
        savedPlannedMealDatabase = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedPlannedMealDatabase").build();
        plannedMealDao = savedPlannedMealDatabase.mealDao();

        if (isFirstOpen) {
            FetchData fetchData = new FetchData();
            fetchData.thread.start();
        }
        isFirstOpen = false;
        mediaPlayer = MediaPlayer.create(this, R.raw.il_vento_doro);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //mealDao.insertOneMeal(new SavedMeal());
        //List<SavedMeal> savedMeals = mealDao.getAll();
        //SavedMeal[] savedMeals = mealDao.getAll();
        //MealManager.meals.clear();

        /*for (int i = 0; i < savedMeals.size(); i++)
        {
            MealManager.meals.add(new Meal(savedMeals.get(i).name));
        }*/


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button2 = (Button) findViewById(R.id.inputMealPageButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMealInputPage();
            }
        });

        button3 = (Button) findViewById(R.id.displayMealPageButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplayMealPage();
            }
        });

        button4 = (Button) findViewById(R.id.editMealPageButton);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditMealPage();
            }
        });
        button5 = (Button) findViewById(R.id.songButton);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSongSelection();
            }
        });
        button6 = (Button) findViewById(R.id.shoppingListButton);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShoppingList();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, CalendarPage.class);
        startActivity(intent);
    }

    public void openMealInputPage()
    {
        Intent intent = new Intent(this, MealInputPage.class);
        startActivity(intent);
    }

    public void openDisplayMealPage()
    {
        Intent intent = new Intent(this, DisplayMealPage.class);
        startActivity(intent);
    }
    public void openEditMealPage()
    {
        Intent intent = new Intent(this, EditMealPage.class);
        startActivity(intent);
    }

    public void openSongSelection()
    {
        Intent intent = new Intent(this, MusicSelection.class);
        startActivity(intent);
        MusicSelection.context = this;
    }

    public void openShoppingList()
    {
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);
    }

    /*public static void ChooseSong(int song)
    {
        if (song == 0)
        {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(this, R.raw.domestic_no_kanojo_op);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }*/

    class FetchData implements Runnable {
        Thread thread = new Thread(this, "fetch_data");

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            Log.e("fetching", "oh dear");
            MealManager.InstantiateArrays();
            Log.e("hmm", "Bruh whyyyy");
        }
    }
};