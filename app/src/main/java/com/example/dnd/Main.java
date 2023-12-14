package com.example.dnd;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Main extends AppCompatActivity {

    Switch onOff, repeating;
    TextView startTimeTextView, endTimeTextView;
    ImageButton add;
    float startHour, startMinute, endHour, endMinute;
    final Calendar c = Calendar.getInstance();
    private static final String FILENAME = "dnd.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onOff = findViewById(R.id.onOff);
        startTimeTextView = findViewById(R.id.startTime);
        endTimeTextView = findViewById(R.id.endTime);
        repeating = findViewById(R.id.repeating);
        add = findViewById(R.id.add);

        startTimeTextView.setText(Load().get(0).toString());
        endTimeTextView.setText(Load().get(1).toString());
    }

    public void Save(TextView startTimeTextView, TextView endTimeTextView) {
        String[] time = {startTimeTextView.getText().toString(), endTimeTextView.getText().toString()};
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(FILENAME, MODE_PRIVATE);

            for (String string: time) {
                fileOutputStream.write(string.getBytes());
                fileOutputStream.write("\n".getBytes());
            }

            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            }
    }
    public List Load() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            List<String> TimeList = new ArrayList<String>();

            while (line != null) {
                TimeList.add(line);
                line = bufferedReader.readLine();
            }

            return TimeList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void setOnOff(View view) {
        if(onOff.isChecked()) {
            Mute();
        }
        else {
            AnMute();
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
        TimePickerDialog. OnTimeSetListener onTimeSetListener = new TimePickerDialog. OnTimeSetListener () {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                startHour = selectedHour;
                startMinute = selectedMinute;
                startTimeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", (int) startHour, (int) startMinute));

                Save(startTimeTextView, endTimeTextView);
                Usporedi();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.timePicker,onTimeSetListener, (int) startHour, (int) startMinute, true);
        timePickerDialog.show();


    }
    public void endTimeTimePicker(View view) {
        
        TimePickerDialog. OnTimeSetListener onTimeSetListener = new TimePickerDialog. OnTimeSetListener () {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                endHour = selectedHour;
                endMinute = selectedMinute;
                endTimeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", (int) endHour, (int) endMinute));

                Save(startTimeTextView, endTimeTextView);
                Usporedi();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.timePicker,onTimeSetListener, (int) endHour, (int) endMinute, true);
        timePickerDialog.show();
    }
    public void Mute() {
        if (ContextCompat.checkSelfPermission(Main.this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main.this, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},101);
        }
        else {
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !notificationManager.isNotificationPolicyAccessGranted()) {

                Intent intent = new Intent(
                        Settings
                                .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                startActivity(intent);
            }

//            settingRingerMode to sailent
            final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
    public void AnMute() {
        if (ContextCompat.checkSelfPermission(Main.this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main.this, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},101);
        }
        else {
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !notificationManager.isNotificationPolicyAccessGranted()) {

                Intent intent = new Intent(
                        Settings
                                .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                startActivity(intent);
            }

//            settingRingerMode to normal
            final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
    public void Usporedi() {
        float hour = c.get(Calendar.HOUR_OF_DAY);
        float minute = c.get(Calendar.MINUTE);

        float startTime = startHour + (startMinute / 60);
        float endTime = endHour + (endMinute / 60);
        float time = hour + (minute / 60);

        if(time >= startTime && time <= endTime) {
            this.Mute();
        }
        else {
            this.AnMute();
        }
    }

}