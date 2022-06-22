/*
* This is the java file for the main page. Its job is to attack
* onClick methods to the different buttons that bring the user
* to different pages, as well as call MealManager's InstantiateArrays
* in a background thread, to fetch previously inputted data and load
* it into the ArrayList
 */
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

    //Attach onClick functions to all the buttons, set up the database connection,
    //and fetch data from the database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a connection to the database
        savedMealDatabase = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
        mealDao = savedMealDatabase.mealDao();
        savedPlannedMealDatabase = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedPlannedMealDatabase").build();
        plannedMealDao = savedPlannedMealDatabase.mealDao();

        //fetch data from the database (but only once in the entire duration of the app)
        if (isFirstOpen) {
            FetchData fetchData = new FetchData();
            fetchData.thread.start();
        }
        isFirstOpen = false;

        //begin playing relaxing background music
        mediaPlayer = MediaPlayer.create(this, R.raw.il_vento_doro);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //attach onClick methods to all the relevant buttons
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

    //open the calendar page
    public void openActivity2() {
        Intent intent = new Intent(this, CalendarPage.class);
        startActivity(intent);
    }

    //open the meal input page
    public void openMealInputPage()
    {
        Intent intent = new Intent(this, MealInputPage.class);
        startActivity(intent);
    }

    //open the display page
    public void openDisplayMealPage()
    {
        Intent intent = new Intent(this, DisplayMealPage.class);
        startActivity(intent);
    }

    //open the edit meal page
    public void openEditMealPage()
    {
        Intent intent = new Intent(this, EditMealPage.class);
        startActivity(intent);
    }

    //open the song selection page
    public void openSongSelection()
    {
        Intent intent = new Intent(this, MusicSelection.class);
        startActivity(intent);
        MusicSelection.context = this;
    }

    //open the shopping list page
    public void openShoppingList()
    {
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);
    }

    //run a backgroudn thread to fetch data from the database
    class FetchData implements Runnable {
        Thread thread = new Thread(this, "fetch_data");

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            MealManager.InstantiateArrays();
        }
    }
};