package com.example.androidtaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Timestamp;

public class CreateTask extends AppCompatActivity {

    TextInputEditText taskName;
    TextInputEditText taskDescription;
    EditText dateStart;
    EditText dateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

    }

    public void createTask(View view)
    {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}