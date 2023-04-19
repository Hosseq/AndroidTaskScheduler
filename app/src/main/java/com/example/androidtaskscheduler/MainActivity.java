package com.example.androidtaskscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.lang.Thread;

import io.reactivex.rxjava3.core.Single;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    ListView dailySchedule;
    DaysDao daysDao;
    Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set-up widgets
        calendar = findViewById(R.id.calendar);
        dailySchedule = findViewById(R.id.dailySchedule);
        addTaskButton = findViewById(R.id.addTaskButton);

        //Set-up database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        daysDao = db.userDao();

        //Add sample task
        Days day = new Days("wafaewf","DESC", calendar.getDate(), calendar.getDate());
        AsyncTask.execute(() -> daysDao.insert(day));

        //Set-up list with tasks
        String[] tasksByHours = new String[24];

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tasksByHours
        );



        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                //Timestamp ts = new Timestamp(calendar.getDate());
                //Toast.makeText(getApplicationContext(),ts.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),String.valueOf(test.name),Toast.LENGTH_LONG).show();

                List<Days> currentDayTasks = daysDao.getAll();

                //LocalDate localDate = Instant.ofEpochMilli(dayTime).atZone(ZoneId.of("UTC")).toLocalDate();
                // dayTime = localDate.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli();
                long dayTime = OffsetDateTime.of(i, i1 + 1, i2, 0, 0, 0, 0, ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli();

                for(int j = 0; j < tasksByHours.length; j++) {
                    tasksByHours[j] = j + ":00 \n";

                    for (Days day: currentDayTasks) {
                        if((dayTime + (3600000 * j)) <= day.dateStart && day.dateStart <= (dayTime + (3600000 * (j + 1)))) {
                            tasksByHours[j] += day.name + "\n Date start: " + new Timestamp(day.dateStart) + "\n Date end: " + new Timestamp(day.dateFinish) + "\n";
                        }
                    }
                }

                dailySchedule.setAdapter(adapter);
            }
        });
    }

    public void goToCreateTaskLayout(View view) {
        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }

}