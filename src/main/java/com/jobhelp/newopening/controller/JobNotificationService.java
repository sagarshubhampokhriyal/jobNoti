package com.jobhelp.newopening.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jobhelp.newopening.service.JobListing;

@Service
public class JobNotificationService {
    private Set<String> seen = new HashSet<>(); // or load from DB

    public List<JobListing> findNew(List<JobListing> currentJobs) {
        List<JobListing> newOnes = new ArrayList<>();
        for (JobListing job : currentJobs) {
            String key = job.getLocation() + "|" + job.getTitle() + "|" + job.getUrl();
            if (!seen.contains(key)) {
                seen.add(key);
                newOnes.add(job);
            }
        }
        return newOnes;
    }
}
