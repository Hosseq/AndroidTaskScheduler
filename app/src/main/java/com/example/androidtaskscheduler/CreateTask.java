package com.example.androidtaskscheduler;

import static com.example.androidtaskscheduler.MainActivity.daysDao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CreateTask extends AppCompatActivity {

    TextInputEditText taskName;
    TextInputEditText taskDescription;
    EditText dateStart;
    EditText dateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        dateStart = findViewById(R.id.dateStart);
        dateEnd = findViewById(R.id.dateEnd);

    }

    public void createTask(View view) {
        String dateStartTime = dateStart.getText().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateStartTime, formatter);
        long dateStart = OffsetDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 0, 0, ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();

        String dateEndTime = dateEnd.getText().toString();
        dateTime = LocalDateTime.parse(dateEndTime, formatter);
        long dateEnd = OffsetDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 0, 0, ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();


        Days day = new Days(taskName.getText().toString(),taskDescription.getText().toString(), dateStart,dateEnd);
        AsyncTask.execute(() -> daysDao.insert(day));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}