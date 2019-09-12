package com.example.kyle.hourstracker;

public class Jobs {
    private String name;
    private String jobActive;

    public Jobs() {}

    public Jobs(String name, String jobActive) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setJobActive(String jobActive) { this.jobActive = jobActive; }

    public String getJobActive() { return jobActive; }
}
