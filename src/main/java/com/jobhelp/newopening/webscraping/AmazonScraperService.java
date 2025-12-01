package com.jobhelp.newopening.webscraping;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.jobhelp.newopening.service.JobListing;

@Service
public class AmazonScraperService {

    public static String AWS_JOBS_URL = "https://amazon.jobs/en/search?offset=0&result_limit=10&sort=recent&category_type=Corporate&distanceType=Mi&radius=24km&industry_experience=one_to_three_years&latitude=41.88425&longitude=-87.63245&loc_group_id=&loc_query=Chicago%2C%20IL%2C%20United%20States&base_query=&city=Chicago&country=USA&region=Illinois&county=Cook&query_options=&";

    public List<JobListing> scrapeJobs() {
        List<JobListing> jobs = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(AWS_JOBS_URL);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            List<WebElement> jobCards = driver.findElements(By.cssSelector("div.job-tile"));

            for (WebElement card : jobCards) {
                try {
                    WebElement jobDiv = card.findElement(By.cssSelector("div.job"));

                    String jobId = jobDiv.getAttribute("data-job-id");

                    WebElement titleLink = jobDiv.findElement(By.cssSelector("h3.job-title > a"));
                    String title = titleLink.getText().trim();
                    String relativeUrl = titleLink.getAttribute("href");
                    String jobUrl = "https://aws.amazon.com" + relativeUrl;

                    // Locations
                    List<WebElement> locationEls = card.findElements(By.cssSelector("ul > li"));
                    List<String> locations = new ArrayList<>();
                    for (WebElement loc : locationEls) {
                        String text = loc.getText().trim();
                        if (!text.startsWith("Job ID") && !text.equals("|") && !text.contains("other locations")) {
                            locations.add(text);
                        }
                    }
                    String locationString = String.join("; ", locations);

                    // Posted date
                    String postedDate = "";
                    try {
                        WebElement postedDateEl = card.findElement(By.cssSelector(".posting-date"));
                        postedDate = postedDateEl.getText().trim();
                    } catch (Exception e) {
                        // Ignore if missing
                    }

                    // Optional: Basic qualifications
                    String qualifications = "";
                    try {
                        WebElement qualEl = card.findElement(By.cssSelector(".qualifications-preview"));
                        qualifications = qualEl.getText().trim();
                    } catch (Exception e) {
                        // Ignore if missing
                    }
                    System.out.println("Job ID: " + jobId);
                    System.out.println("Title: " + title);
                    JobListing job = new JobListing(title, locationString, jobUrl, jobId, "AWS");
                    jobs.add(job);

                    System.out.println("✔ Found AWS job: " + title);

                } catch (Exception innerEx) {
                    System.err.println("⚠️ Skipped one job due to error: " + innerEx.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return jobs;
    }
}
