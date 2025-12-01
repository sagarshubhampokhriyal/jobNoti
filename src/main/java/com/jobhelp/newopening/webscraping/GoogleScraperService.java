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
public class GoogleScraperService {
    private static final String JOB_SEARCH_URL = "https://www.google.com/about/careers/applications/jobs/results/?target_level=EARLY&degree=BACHELORS&employment_type=FULL_TIME&sort_by=date&location=Bangalore%20India&location=Gurgaon%2C%20India&location=Hyderabad%2C%20India&location=Mumbai%2C%20India";

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

            List<WebElement> jobCards = driver.findElements(By.cssSelector("div.sMn82b"));

            for (WebElement card : jobCards) {
                try {
                    String title = card.findElement(By.cssSelector("h3.QJPWVe")).getText().trim();

                    // Company (next to corporate_fare icon)
                    WebElement companyEl = card
                            .findElement(By.xpath(".//i[text()='corporate_fare']/following-sibling::span"));
                    String company = companyEl.getText().trim();

                    // Locations: multiple spans with class r0wTof
                    List<WebElement> locationElements = card.findElements(By.cssSelector("span.r0wTof"));
                    StringBuilder locations = new StringBuilder();
                    for (WebElement locEl : locationElements) {
                        if (locations.length() > 0)
                            locations.append("; ");
                        locations.append(locEl.getText().trim());
                    }

                    // Job URL: inside <a> with jsname="hSRGPd"
                    WebElement link = card.findElement(By.cssSelector("a[jsname='hSRGPd']"));
                    String jobUrl = "https://careers.google.com" + link.getAttribute("href");

                    JobListing job = new JobListing(title, locations.toString(), jobUrl, "", company);
                    jobs.add(job);

                    System.out.println("✔ Found: " + title + " at " + company);

                } catch (Exception e) {
                    // Skip job card if any element is missing
                    System.err.println("⚠️ Skipping one job card due to error: " + e.getMessage());
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
