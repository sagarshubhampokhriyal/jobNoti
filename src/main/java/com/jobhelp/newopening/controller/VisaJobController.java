package com.jobhelp.newopening.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jobhelp.newopening.service.JobListing;
import org.springframework.web.bind.annotation.RestController;

import com.jobhelp.newopening.service.JobProcessorService;
import com.jobhelp.newopening.webscraping.VisaJobScraperService;

@RestController
@RequestMapping("/api/jobs")
public class VisaJobController {

    @Autowired
    private VisaJobScraperService scraperService;
    @Autowired
    private JobProcessorService jobProcessorService;

    @GetMapping("/visa/new")
    public List<JobListing> getAndAddNewVisaJobs() {
        List<?> rawJobs = jobProcessorService.getAndStoreNewJobs();
        List<JobListing> jobs = rawJobs.stream()
                .map(job -> (JobListing) job)
                .toList();
        return jobs;
    }
}