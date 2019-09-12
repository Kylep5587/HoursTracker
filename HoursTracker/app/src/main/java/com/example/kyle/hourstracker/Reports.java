package com.example.kyle.hourstracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Toolbar toolbar = findViewById(R.id.title_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Open Home Activity
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.goHome();
            }
        });

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


    /*
     *   Menu Inflater
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


}
