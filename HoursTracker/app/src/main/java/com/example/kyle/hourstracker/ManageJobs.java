package com.example.kyle.hourstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ManageJobs extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.title_bar);
        setSupportActionBar(toolbar);

        final TextView jobID = findViewById(R.id.jobID);
        final EditText jobName = findViewById(R.id.jobList);
        final Spinner jobList = findViewById(R.id.jobsList);
        final Switch jobActiveSwitch = findViewById(R.id.jobActiveSwitch);
        Button saveEditBtn = findViewById(R.id.saveEditBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        Button deleteJobBtn = findViewById(R.id.deleteJobBtn);

        // Save Button Handler
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobEnabled;
                if (jobActiveSwitch.isChecked())
                    jobEnabled = "yes";
                else
                    jobEnabled = "no";

                if (db.updateJob(jobID.getText().toString(), jobName.getText().toString(), jobEnabled))
                    showToast("Job updated");
                else
                    showToast("Failed to update job info");

                Intent intent = new Intent(HoursTracker.getAppContext(), ManageJobs.class);
                HoursTracker.getAppContext().startActivity(intent);
            }
        });

        // Cancel Button Handler
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoursTracker.getAppContext(), ManageJobs.class);
                HoursTracker.getAppContext().startActivity(intent);
            }
        });

        // Delete Button Handler
        deleteJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.deleteJob(Integer.valueOf(jobID.getText().toString()))) {
                    showToast("Job deleted");
                    Intent intent = new Intent(HoursTracker.getAppContext(), ManageJobs.class);
                    HoursTracker.getAppContext().startActivity(intent);
                }
                else {
                    showToast("Error deleting job!");
                }
            }
        });

        // Job Names Spinner
        List<String> jobNames = db.getJobNames();
        ArrayAdapter<String> jobSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobNames);
        jobSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobList.setAdapter(jobSpinnerAdapter);
        jobList.setSelection(0);

        jobList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String jobInfo[] = db.retrieveJob(jobList.getSelectedItem().toString());
                jobID.setText(jobInfo[0]);
                jobName.setText(jobInfo[1]);
                if (jobInfo[2] == "no")
                    jobActiveSwitch.setChecked(false);
                else
                    jobActiveSwitch.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    }


    /*  Displays a toast
     *******************************/
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
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
