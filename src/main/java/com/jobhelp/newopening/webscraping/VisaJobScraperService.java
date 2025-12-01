package com.jobhelp.newopening.webscraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.jobhelp.newopening.service.JobListing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisaJobScraperService {

    private static final String JOB_SEARCH_URL = "https://corporate.visa.com/en/jobs/?categories=Data%20Architect%2FEngineering%2FScience&categories=Data%20Science%2FData%20Engineering&categories=Software%20Development%2FEngineering&cities=Bangalore&cities=Mumbai&sortProperty=createdOn&sortOrder=DESC";

    public List<JobListing> scrapeJobs() {
        List<JobListing> jobs = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(JOB_SEARCH_URL);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            List<WebElement> jobElements = driver.findElements(By.cssSelector("li.vs-underline"));
            System.out.println("Found " + jobElements.size() + " job listings.");
            for (WebElement job : jobElements) {
                WebElement link = job.findElement(By.cssSelector("a.vs-link-job"));
                String title = link.getText().trim();
                String jobUrl = link.getAttribute("href");

                String location = extractJobDetail(job, "Location");
                String jobNumber = extractJobDetail(job, "Job #");
                String team = extractJobDetail(job, "Team");
                JobListing visaJob = new JobListing(title, location, jobUrl, jobNumber, team);
                jobs.add(visaJob);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger in production
        } finally {
            driver.quit();
        }

        return jobs;
    }

    private String extractJobDetail(WebElement jobElement, String label) {
        try {
            WebElement p = jobElement.findElement(By.xpath(".//p[span[text()='" + label + " ']]"));
            return p.getText().replace(label, "").trim();
        } catch (Exception e) {
            return "";
        }
    }
}