package com.example.androidtaskscheduler;

import static com.example.androidtaskscheduler.MainActivity.tasksDao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateTask extends AppCompatActivity {

    TextInputEditText taskName;
    TextInputEditText taskDescription;
    EditText dateStart;
    EditText dateEnd;
    DateTimeFormatter formatter;
    LocalDateTime dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Set-up widgets
        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        dateStart = findViewById(R.id.dateStart);
        dateEnd = findViewById(R.id.dateEnd);

        //Set current time as text for dateStart and dateEnd
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateStart.setText(formatter.format(LocalDateTime.now()));
        dateEnd.setText(formatter.format(LocalDateTime.now()));

    }

    public void createTask(View view) {
        long dateStartTime,dateEndTime;
        String dateInput;

        //DATE PARSING
        try {
            //Parse dateStart  input
            dateInput = dateStart.getText().toString();
            dateTime = LocalDateTime.parse(dateInput, formatter);

            dateStartTime = OffsetDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 0, 0, ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli();

            //Parse dateEnd  input
            dateInput = dateEnd.getText().toString();
            dateTime = LocalDateTime.parse(dateInput, formatter);

            dateEndTime = OffsetDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 0, 0, ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli();

        } catch (DateTimeParseException ex) {
            Toast.makeText(CreateTask.this, "Invalid date format!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(taskName.getText() == null)
        {
            Toast.makeText(CreateTask.this, "Set name for a task!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create task and insert to database
        Tasks day = new Tasks(taskName.getText().toString(), taskDescription.getText().toString(), dateStartTime, dateEndTime);
        AsyncTask.execute(() -> tasksDao.insert(day));

        //Return to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}