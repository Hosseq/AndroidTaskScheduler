package com.example.androidtaskscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    static public DaysDao daysDao;
    Button addTaskButton;
    String[] tasksByHours;
    Days[] currentDayTasksByHours;

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

        tasksByHours = new String[24];
        currentDayTasksByHours = new Days[24];

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tasksByHours
        );


        dailySchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                Days day = currentDayTasksByHours[(int) id];

                if(day == null) { return; }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Task Description")
                        .setMessage("Name:\t" + day.getName() + "\n" +
                                    "Description:\t" + day.getDescription() + "\n" +
                                    "Date of start:\t" + day.getStartDate() + "\n" +
                                    "Date of finish:\t" + day.getFinishDate())
                        .show();
            }});





        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                List<Days> currentDayTasks = daysDao.getAll();


                long dayTime = OffsetDateTime.of(i, i1 + 1, i2, 0, 0, 0, 0, ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli();

                for(int j = 0; j < tasksByHours.length; j++) {
                    tasksByHours[j] = j + ":00 \n";

                    for (Days day: currentDayTasks) {
                        if((dayTime + (3600000 * j)) <= day.getStartDate() && day.getFinishDate() <= (dayTime + (3600000 * (j + 1)))) {
                            tasksByHours[j] += day.getName() + "\n Date start: " + new Timestamp(day.getStartDate()) + "\n Date end: " + new Timestamp(day.getFinishDate()) + "\n";

                            currentDayTasksByHours[j] = day;

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