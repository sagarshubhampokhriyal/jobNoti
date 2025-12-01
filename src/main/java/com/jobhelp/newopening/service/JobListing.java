package com.jobhelp.newopening.service;

public class JobListing {
    private String title;
    private String location;
    private String url;
    private String jobNumber;
    private String team;

    public JobListing() {
    }

    public JobListing(String title, String location, String url, String jobNumber, String team) {
        this.title = title;
        this.location = location;
        this.url = url;
        this.jobNumber = jobNumber;
        this.team = team;
    }

    // getters & setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}
