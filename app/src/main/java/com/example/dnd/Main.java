package com.example.dnd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    Switch onOff;
    Switch repeating;
    TextView setFromTime;
    TextView setEndTime;
    ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onOff = findViewById(R.id.onOff);
        setFromTime = findViewById(R.id.setFromTime);
        setEndTime = findViewById(R.id.setEndTime);
        repeating = findViewById(R.id.repeating);
        add = findViewById(R.id.add);
    }
    
    public void setOnOff(View view) {
        if(onOff.isChecked()) {
            Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
        }
    }
    public void setRepeating(View view) {
        if(repeating.isChecked()) {
            Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
        }
    }

    public void stratTimeTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();

        newFragment.show(getSupportFragmentManager(), "TimePicker");
    }

    public void endTimeTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();

        newFragment.show(getSupportFragmentManager(), "TimePicker");
    }
}