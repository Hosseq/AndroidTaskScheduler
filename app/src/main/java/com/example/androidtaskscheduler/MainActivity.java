package com.example.androidtaskscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    RecyclerView dailySchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        DaysDao daysDao = db.userDao();
        //List<Days> days = daysDao.getAll();



        calendar = findViewById(R.id.calendar);
        dailySchedule = findViewById(R.id.dailySchedule);
        String text = "adaf";

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {


                //Timestamp ts = new Timestamp(calendar.getDate());
                //Toast.makeText(getApplicationContext(),ts.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}