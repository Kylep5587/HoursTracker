package com.example.kyle.hourstracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb = new DatabaseHelper(this);      // Database object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.title_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Show total hours
        String totalHours = Long.toString(myDb.totalHours());
        TextView totalHourEntries = findViewById(R.id.totalHourEntries);
        totalHourEntries.setText(totalHours);


        // Show total jobs
        String totalJobs = Long.toString(myDb.totalJobs());
        TextView totalJobEntries = findViewById(R.id.totalJobEntries);
        totalJobEntries.setText(totalJobs);


        /* Populate recent hours area
        * recentHoursData[0] = date, [1] = start, [2] = end, [3] = hours,
        *      [4] = lunchDuration, [5] = jobName
        * *****************************/
        String recentHoursData[] = myDb.getHoursEntry();
        TextView recentHours = findViewById(R.id.recentHours);
        TextView recentDate = findViewById(R.id.recentDate);
        TextView recentJobName = findViewById(R.id.recentJobName);

        if (recentHoursData[0] != "false") {
            double actualHours = Double.valueOf(recentHoursData[3]);
            actualHours = ((actualHours * 60) - Double.valueOf(recentHoursData[4])) / 60;
            recentHours.setText(String.valueOf(actualHours));
            recentDate.setText(recentHoursData[0]);
            recentJobName.setText(recentHoursData[5]);
        }
        else {
            TextView recentHoursLbl = findViewById(R.id.recentHoursLbl);
            TextView recentDateLbl = findViewById(R.id.recentDateLbl);
            TextView recentJobNameLbl = findViewById(R.id.recentJobNameLbl);

            recentHoursLbl.setText("N/A");
            recentDateLbl.setText("");
            recentJobNameLbl.setText("");
            recentHours.setText("");
            recentDate.setText("");
            recentJobName.setText("");
        }


        // Open Add Hours Activity
        Button addHoursBtn = findViewById(R.id.addHoursBtn);
        addHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddHours();
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


        // Open Add Job Activity
        Button manageBtn = findViewById(R.id.manageBtn);
        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goManageJobs();
            }
        });

    }

    /*  Handles option menu clicks
     *******************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manageHoursOption:
                goManageHours();
                return true;
            case R.id.settingsOption:
                goSettings();
                return true;
            case R.id.reportsOption:
                goReports();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*  Displays a toast
     *******************************/
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    /*   Menu Inflater
    *******************************/
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }


    /*  Launch Home Activity
     *************************************/
    public static void goHome() {
        Intent intent = new Intent(HoursTracker.getAppContext(), MainActivity.class);
        HoursTracker.getAppContext().startActivity(intent);
    }


    /*
     * Launch Add Job Activity
     *************************************/
    public static void goAddJob() {
        Intent intent = new Intent(HoursTracker.getAppContext(), AddJob.class);
        HoursTracker.getAppContext().startActivity(intent);
    }


    /*
     * Launch Add Hours Activity
     *************************************/
    public static void goAddHours() {
        Intent intent = new Intent(HoursTracker.getAppContext(), AddHours.class);
        HoursTracker.getAppContext().startActivity(intent);
    }


    /*
     * Launch Job Manager Activity
     *************************************/
    public static void goManageJobs() {
        Intent intent = new Intent(HoursTracker.getAppContext(), ManageJobs.class);
        HoursTracker.getAppContext().startActivity(intent);
    }


    /*
     * Launch Hours Manager Activity
     *************************************/
    public static void goManageHours() {
        Intent intent = new Intent(HoursTracker.getAppContext(), ManageHours.class);
        HoursTracker.getAppContext().startActivity(intent);
    }


    /*
     * Launch Settings Activity
     *************************************/
    public static void goSettings() {
        Intent intent = new Intent(HoursTracker.getAppContext(), Settings.class);
        HoursTracker.getAppContext().startActivity(intent);
    }


    /*
     * Launch Reports Activity
     *************************************/
    public static void goReports() {
        Intent intent = new Intent(HoursTracker.getAppContext(), Reports.class);
        HoursTracker.getAppContext().startActivity(intent);
    }
}
