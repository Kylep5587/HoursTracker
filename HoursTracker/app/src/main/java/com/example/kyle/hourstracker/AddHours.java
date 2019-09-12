/* *************************************
 *  AddHours.java                      *
 *  Java file for the AddHours         *
 *       activity                      *
 *  Created: 11/25/18                  *
 *  Last Updated: 11/28/18             *
 **************************************/

package com.example.kyle.hourstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AddHours extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    // Form Fields
    EditText hoursDate;
    EditText start;
    EditText end;
    EditText lunch;
    Spinner job;
    Spinner lunchType;
    Spinner startHourType;
    Spinner endHourType;
    Button btnSave;

    String date, startTime, endTime, jobName;
    double lunchDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_hours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.title_bar);
        setSupportActionBar(toolbar);


        // Open Home Activity
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        // Open Add Job Activity
        Button addJobBtn = findViewById(R.id.addJobBtn);
        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddJob();
            }
        });

        // Open Manage Job Activity
        Button manageBtn = findViewById(R.id.manageBtn);
        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goManageJobs();
            }
        });

        // Get references
        hoursDate = findViewById(R.id.hoursDate);
        start  = findViewById(R.id.startTime);
        end = findViewById(R.id.endTime);
        lunch  = findViewById(R.id.lunchDuration);
        job  = findViewById(R.id.jobList);
        btnSave  = findViewById(R.id.saveButton);
        lunchType = findViewById(R.id.lunchDurationType);
        startHourType = findViewById(R.id.startTimeAMPM);
        endHourType = findViewById(R.id.endTimeAMPM);

        // Populate start and end time values
        String[] defaults = db.getAppDefaults();
        start.setText(defaults[0]);
        end.setText(defaults[1]);
        lunch.setText(defaults[2]);

        // Call function to insert hours when SAVE button clicked
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHours();
            }
        });

        /*  Set date EditText to today's date
         *******************************/
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        hoursDate.setText(sdf.format(new Date() ));

        /*   Populate Spinners
         *******************************/
        Spinner startTimeSpinner = findViewById(R.id.startTimeAMPM);
        Spinner endTimeSpinner = findViewById(R.id.endTimeAMPM);
        Spinner lunchDurationSpinner = findViewById(R.id.lunchDurationType);

        // AM/PM Spinners
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.ampmSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startTimeSpinner.setAdapter(adapter);
        startTimeSpinner.setSelection(0);

        endTimeSpinner.setAdapter(adapter);
        endTimeSpinner.setSelection(1);

        // Lunch Duration Spinner
        ArrayAdapter lunchAdapter = ArrayAdapter.createFromResource(this, R.array.durationType, android.R.layout.simple_spinner_item);
        lunchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lunchDurationSpinner.setAdapter(lunchAdapter);
        lunchDurationSpinner.setSelection(0);

        // Job Names Spinner
        List<String> jobNamesList = db.getJobNames();
        ArrayAdapter<String> jobSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobNamesList);
        jobSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job.setAdapter(jobSpinnerAdapter);
        job.setSelection(0);
    }

    /*  Displays a toast
     *******************************/
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    /*  Insert Hours
     *******************************/
    public void AddHours() {
        date = hoursDate.getText().toString();
        startTime = start.getText().toString();
        endTime = end.getText().toString();
        lunchDuration = Double.valueOf(lunch.getText().toString());
        jobName = job.getSelectedItem().toString();
        String startAMPM = startHourType.getSelectedItem().toString();
        String endAMPM = endHourType.getSelectedItem().toString();
        String lunchUnit = lunchType.getSelectedItem().toString();

        try {
            boolean querySuccess = db.insertHours(date, startTime, startAMPM, endTime, endAMPM, lunchDuration, lunchUnit, jobName);

            if (querySuccess == true)
                showToast("Hours added to the database");
            else
                showToast("Failed to add hours");

            Intent intent = new Intent(AddHours.this, MainActivity.class);
            this.startActivity(intent);
        }
        catch (android.database.SQLException e) {
            showToast("Failed to add hours");
        }

    }

    /*  Menu Inflater
     *******************************/
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }


    /*  Handles option menu clicks
     *******************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manageHoursOption:
                MainActivity.goManageHours();
                return true;
            case R.id.settingsOption:
                MainActivity.goSettings();
                return true;
            case R.id.reportsOption:
                MainActivity.goReports();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*  Launch Home Activity
     *************************************/
    private void goHome() {
        Intent intent = new Intent(AddHours.this, MainActivity.class);
        this.startActivity(intent);
    }

    /*  Launch Add Job Activity
     *************************************/
    private void goAddJob() {
        Intent intent = new Intent(AddHours.this, AddJob.class);
        this.startActivity(intent);
    }

    /*  Launch Job Manager Activity
     *************************************/
    private void goManageJobs() {
        Intent intent = new Intent(AddHours.this, ManageJobs.class);
        this.startActivity(intent);
    }

    /*
    *  Display Calendar when clicking on date input field
     *
    Calendar myCalendar = Calendar.getInstance();

    EditText hoursDate = findViewById(R.id.hoursDate);
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    hoursDate.setOnFocusChangeListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(AddHours.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });

    private void updateLabel() {
        String dateFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        hoursDate.setText(sdf.format(myCalendar,getTime()));
    }
    */
}
