package com.finalproject.todoapp.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTaskUiTest {
    private static ChromeOptions options;
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void canDeleteTaskThroughUi() {
        driver.get("http://localhost:8080");

        // Step 1: Add a unique task
        String uniqueName = "task-" + System.currentTimeMillis();
        WebElement nameInput = driver.findElement(By.id("taskName"));
        nameInput.sendKeys(uniqueName);
        driver.findElement(By.id("submitTask")).click();

        // Step 2: Wait for the task to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='task-name' and text()='" + uniqueName + "']")));

        // Step 3: Click the Delete button next to the task
        WebElement deleteBtn = driver.findElement(
                By.xpath("//span[@class='task-name' and text()='" + uniqueName + "']/following-sibling::button[@class='delete-btn']")
        );
        deleteBtn.click();

        // Step 4: Wait for the task to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='task-name' and text()='" + uniqueName + "']")));

        // Step 5: Verify the task is gone
        assertFalse(driver.getPageSource().contains(uniqueName));
    }
}
