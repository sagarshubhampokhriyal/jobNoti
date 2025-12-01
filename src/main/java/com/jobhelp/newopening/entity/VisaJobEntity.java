package com.jobhelp.newopening.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "visa_job_entity", schema = "jobhelp")
public class VisaJobEntity {

    @Id
    private String jobNumber; // Use this as primary key

    private String title;
    private String url;
    private String location;
    private String team;

    public VisaJobEntity() {
    }

    public VisaJobEntity(String jobNumber, String title, String url, String location, String team) {
        this.jobNumber = jobNumber;
        this.title = title;
        this.url = url;
        this.location = location;
        this.team = team;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    // Getters and Setters
}