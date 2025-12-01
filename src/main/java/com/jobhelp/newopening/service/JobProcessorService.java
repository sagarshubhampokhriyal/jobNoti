package com.jobhelp.newopening.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jobhelp.newopening.entity.VisaJobEntity;
import com.jobhelp.newopening.webscraping.GoogleScraperService;
import com.jobhelp.newopening.repository.UserRepository;
import com.jobhelp.newopening.repository.JobRepository;
import com.jobhelp.newopening.webscraping.AmazonScraperService;
import com.jobhelp.newopening.webscraping.VisaJobScraperService;

@Service
public class JobProcessorService {
    @Autowired
    private VisaJobScraperService visaJobScraperService;
    @Autowired
    GoogleScraperService googleScraperService;
    @Autowired
    private JobRepository visaJobRepository;
    @Autowired
    private AmazonScraperService amazonScraperService;
    @Autowired
    private WhatsAppSender whatsAppSender;
    @Autowired
    private UserRepository UserRopo;

    @Scheduled(cron = "0 1/30 * * * *") // Every hour
    public List<JobListing> getAndStoreNewJobs() {
        List<JobListing> scrapedJobs = visaJobScraperService.scrapeJobs();
        // List<JobListing> googleJobs = googleScraperService.scrapeJobs();
        // scrapedJobs.addAll(googleJobs);

        List<JobListing> amazonJobs = amazonScraperService.scrapeJobs();
        scrapedJobs.addAll(amazonJobs);
        List<String> existingIds = visaJobRepository.findAll()
                .stream()
                .map(VisaJobEntity::getJobNumber)
                .collect(Collectors.toList());
        List<JobListing> newJobs = scrapedJobs.stream()
                .filter(job -> !existingIds.contains(job.getJobNumber()))
                .collect(Collectors.toList());
        List<VisaJobEntity> entities = newJobs.stream()
                .map(job -> {
                    VisaJobEntity entity = new VisaJobEntity();
                    entity.setJobNumber(job.getJobNumber());
                    entity.setTitle(job.getTitle());
                    entity.setUrl(job.getUrl());
                    entity.setLocation(job.getLocation());
                    entity.setTeam(job.getTeam());
                    return entity;
                })
                .collect(Collectors.toList());

        List<String> users = UserRopo.findAll().stream().map(u -> u.gerPhoneNo()).collect(Collectors.toList());
        try {
            for (String user : users) {
                System.out.println("User phone number: " + user);
                for (JobListing job : newJobs) {
                    whatsAppSender.sendMessage(job, user);
                }
            }
            visaJobRepository.saveAll(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newJobs;
    }
}