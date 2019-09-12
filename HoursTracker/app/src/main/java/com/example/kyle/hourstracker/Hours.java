package com.example.kyle.hourstracker;

import java.util.ArrayList;

public class Hours {
    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private String workedHours;
    private String lunchDuration;
    private String jobName;

    public Hours() {}

    public Hours(String date, String startTime, String endTime, String workedHours, String lunchDuration, String jobName) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workedHours = workedHours;
        this.lunchDuration = lunchDuration;
        this.jobName = jobName;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLunchDuration(String lunchDuration) {
        this.lunchDuration = lunchDuration;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    // Getters
    public String getDate() {
        return date;
    }
    public String getLunchDuration() { return lunchDuration; }
    public String getWorkedHours() { return workedHours; }
    public String getJobName() { return jobName; }

    public static ArrayList<Hours> createHourList(int numHours) {
        ArrayList<Hours> hoursList = new ArrayList<Hours>();

        for (int i=1; i< numHours; i++) {
            hoursList.add(new Hours("", "", "", "", "", ""));
        }
        return hoursList;
    }
}
