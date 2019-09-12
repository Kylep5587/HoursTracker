package com.example.kyle.hourstracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageHours extends AppCompatActivity {

    Switch limitDatesSwitch;
    TextView startLbl;
    TextView endLbl;
    EditText startDate;
    EditText endDate;
    DatabaseHelper db = new DatabaseHelper(this);
    RecyclerView hoursListRecycler;
    ArrayList<Hours> hoursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_hours);

        Toolbar toolbar = findViewById(R.id.title_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        RecyclerView rvHours = (RecyclerView) findViewById(R.id.hourList);
        hoursList = Hours.createHourList(20);

        /* Need to fix
        HoursAdapter adapter = new HoursAdapter(hoursList);
        rvHours.setAdapter(adapter);
        rvHours.setLayoutManager(new LinearLayoutManager(this));
        */

        final Spinner jobList = findViewById(R.id.jobList);
        Button viewHoursBtn = findViewById(R.id.showHoursBtn);
        hoursListRecycler = findViewById(R.id.hourList);
        startLbl = findViewById(R.id.dateStartLbl);
        endLbl = findViewById(R.id.dateEndLbl);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);


        // View Button Click
        viewHoursBtn.setOnClickListener(new View.OnClickListener() {
            //String jobName = jobList.getSelectedItem().toString();
            @Override
            public void onClick(View v) {
                //showHours(jobName);
            }
        });

        // Show/Hide date fields
        limitDatesSwitch = findViewById(R.id.limitDatesSwitch);
        limitDatesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make views visible
                if (limitDatesSwitch.isChecked()) {
                    startLbl.setVisibility(View.VISIBLE);
                    endLbl.setVisibility(View.VISIBLE);
                    startDate.setVisibility(View.VISIBLE);
                    endDate.setVisibility(View.VISIBLE);
                }
                // Hide views
                else {
                    startDate.getText().clear();
                    endDate.getText().clear();
                    startLbl.setVisibility(View.INVISIBLE);
                    endLbl.setVisibility(View.INVISIBLE);
                    startDate.setVisibility(View.INVISIBLE);
                    endDate.setVisibility(View.INVISIBLE);
                }
            }
        });


        // Open Home Activity
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.goHome();
            }
        });

        // Job Names Spinner
        List<String> jobNames;
        jobNames = db.getJobNames();
        jobNames.add("All");
        ArrayAdapter<String> jobSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobNames);
        jobSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobList.setAdapter(jobSpinnerAdapter);
        jobList.setSelection(0);

        // Open Add Hours Activity
        Button addHoursBtn = findViewById(R.id.addHoursBtn);
        addHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.goAddHours();
            }
        });


        // Open Add Job Activity
        Button addJobBtn = findViewById(R.id.addJobBtn);
        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.goAddJob();
            }
        });


        // Open Add Job Activity
        Button manageBtn = findViewById(R.id.manageBtn);
        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.goManageJobs();
            }
        });
    }

    /*   Menu Inflater
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


    /*  Handles view hours button click
     *******************************/
    private void showHours(String jobName) {
        String queryString;

        if (jobName != "N/A")
            queryString = "SELECT hoursDate, workedHours, lunch FROM Hours WHERE jobName = '" + jobName + "ORDER BY hoursDate DESC";
        else
            queryString = "SELECT hoursDate, workedHours, lunch FROM Hours ORDER BY hoursDate DESC";

        //List<String> hourInfo = db.hourQuery(queryString);
    }

}
