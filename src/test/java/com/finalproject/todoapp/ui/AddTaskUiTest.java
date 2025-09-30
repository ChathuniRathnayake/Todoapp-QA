package com.finalproject.todoapp.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.*;

class AddTaskUiTest {
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
    void canAddTaskThroughUi() {
        // This assumes you have an HTML form at / (if not, you can use REST tests instead)
        driver.get("http://localhost:8080");
        // Example: locate title input + submit - change selectors to match your UI
        WebElement nameInput = driver.findElement(By.id("taskName")); // match your UI
        nameInput.sendKeys("Buy milk");
        WebElement submit = driver.findElement(By.id("submitTask"));
        submit.click();
        assertTrue(driver.getPageSource().contains("Buy milk"));
    }
}