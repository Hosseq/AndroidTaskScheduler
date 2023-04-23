package com.example.androidtaskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    ListView dailySchedule;
    static public TasksDao tasksDao;
    Button addTaskButton;
    String[] tasksByHours;
    ArrayList<List<Tasks>> currentDayTasksByHours;

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
        tasksDao = db.tasksDao();

        //Set-up list with tasks
        tasksByHours = new String[24];                                                        //Array for output in ViewList by hours
        currentDayTasksByHours = new ArrayList<List<Tasks>>(24);                  //Array for detailed description by hours
        for (int i = 0; i < 24; i++) {
            currentDayTasksByHours.add(new ArrayList<Tasks>());
        }


        //Adapter for ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tasksByHours
        );

        //Show detailed description by clicking on item in list
        dailySchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String DialogMessage = "";
                List<Tasks> dayList = currentDayTasksByHours.get((int) id);

                for (Tasks task : dayList) {
                    if (task == null) {
                        break;
                    } else {
                        DialogMessage += "Name:\t" + task.getName() + "\n" +
                                "Description:\t" + task.getDescription() + "\n" +
                                "Date of start:\t" + task.getStartDate() + "\n" +
                                "Date of finish:\t" + task.getFinishDate() + "\n\n";
                    }
                }

                if (DialogMessage.equals("")) {
                    return;
                }     //if no tasks

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Task Description")
                        .setMessage(DialogMessage)
                        .setCancelable(true)
                        .show();
            }
        });


        //Show list of tasks by clicking on a date in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //Convert calendar date to milliseconds
                long dayTime = OffsetDateTime.of(i, i1 + 1, i2, 0, 0, 0, 0, ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli();

                //Get tasks from database
                List<Tasks> currentDayTasks = tasksDao.findByDay(dayTime);

                //Output for Listview
                for (int j = 0; j < tasksByHours.length; j++) {
                    tasksByHours[j] = j + ":00 \n";

                    for (Tasks task : currentDayTasks) {
                        //if between hours(1:00-2:00)
                        if ((dayTime + (3600000 * j)) < task.getStartDate() && task.getStartDate() <= (dayTime + (3600000 * (j + 1)))) {
                            tasksByHours[j] += task.getName() +
                                    "\n Date start: " + new Timestamp(task.getStartDate()) +
                                    "\n Date end: " + new Timestamp(task.getFinishDate()) + "\n";

                            //Save day for detailed description
                            currentDayTasksByHours.get(j).add(task);

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