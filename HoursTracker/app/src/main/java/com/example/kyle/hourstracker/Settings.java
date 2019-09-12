package com.example.kyle.hourstracker;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    EditText defaultStartTime;
    EditText defaultEndTime;
    EditText defaultLunchDuration;
    Button clearHoursBtn;
    Button clearJobsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        // Populate Spinners
        Spinner defaultStartAMPM = findViewById(R.id.defaultStartAMPM);
        Spinner defaultEndAMPM = findViewById(R.id.defaultEndAMPM);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.ampmSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        defaultStartAMPM.setAdapter(adapter);
        defaultStartAMPM.setSelection(0);

        defaultEndAMPM.setAdapter(adapter);
        defaultEndAMPM.setSelection(1);

        populateDefualts();

        // Handle Save button click
        Button saveBtn = findViewById(R.id.saveDefaultBtn);
        defaultStartTime = findViewById(R.id.defaultStartTime);
        defaultEndTime = findViewById(R.id.defaultEndTime);
        defaultLunchDuration = findViewById(R.id.defaultLunchDuration);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.setDefaults(defaultStartTime.getText().toString(), defaultEndTime.getText().toString(), defaultLunchDuration.getText().toString())) {
                    showToast("Defaults updated");
                }
                else {
                    showToast("Error updating defaults");
                }
                Intent intent = new Intent(HoursTracker.getAppContext(), Settings.class);
                HoursTracker.getAppContext().startActivity(intent);
            }
        });

        // Handle Clear Hours
        clearHoursBtn = findViewById(R.id.clearHoursBtn);
        clearHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.deleteHours())
                    showToast("Hours deleted");
                else
                    showToast("Failed to delete hours!");
               /* AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getApplicationContext(), R.style.dialog));
                builder.setCancelable(true);
                builder.setTitle("Delete Hours");
                builder.setMessage("Are you sure you want to delete all hours in the database?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (db.deleteHours())
                                    showToast("Hours deleted");
                                else
                                    showToast("Failed to delete hours!");

                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                */
            }
        });

        // Clear Jobs
        clearJobsBtn = findViewById(R.id.clearJobsBtn);
        clearJobsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.deleteJobs())
                    showToast("Jobs deleted");
                else
                    showToast("Failed to delete jobs!");
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


    /*  Displays a toast
     *******************************/
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    /*  Populate fields
     *******************************/
    private void populateDefualts() {
        String[] appDefaults = db.getAppDefaults();

        EditText startTime = findViewById(R.id.defaultStartTime);
        EditText endTime = findViewById(R.id.defaultEndTime);
        EditText lunchDuration = findViewById(R.id.defaultLunchDuration);

        startTime.setText(appDefaults[0]);
        endTime.setText(appDefaults[1]);
        lunchDuration.setText(appDefaults[2]);
    }
}
