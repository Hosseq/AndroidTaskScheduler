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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

public class CreateTask extends AppCompatActivity {

    TextInputEditText taskName;
    TextInputEditText taskDescription;
    EditText dateStart;
    EditText dateEnd;
    DateTimeFormatter formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        dateStart = findViewById(R.id.dateStart);
        dateEnd = findViewById(R.id.dateEnd);

        //Set current time as text to dateStart and dateEnd
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateStart.setText(formatter.format(LocalDateTime.now()));
        dateEnd.setText(formatter.format(LocalDateTime.now()));

    }

    public void createTask(View view) {
        LocalDateTime dateTime;
        //Parse dateStart  input
        String dateStartTime = dateStart.getText().toString();
        try{
            dateTime = LocalDateTime.parse(dateStartTime, formatter);
        } catch (DateTimeParseException ex) {
            Toast.makeText(CreateTask.this,"Invalid date format!",Toast.LENGTH_SHORT).show();
            return;
        }

        long dateStart = OffsetDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 0, 0, ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();

        //Parse dateEnd  input
        String dateEndTime = dateEnd.getText().toString();
        try{
            dateTime = LocalDateTime.parse(dateEndTime, formatter);
        } catch (DateTimeParseException ex) {
            Toast.makeText(CreateTask.this,"Invalid date format!",Toast.LENGTH_SHORT).show();
            return;
        }

        long dateEnd = OffsetDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 0, 0, ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();


        Days day = new Days(taskName.getText().toString(),taskDescription.getText().toString(), dateStart,dateEnd);
        AsyncTask.execute(() -> daysDao.insert(day));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}