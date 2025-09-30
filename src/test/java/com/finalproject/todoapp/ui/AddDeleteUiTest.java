package com.finalproject.todoapp.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class AddDeleteUiTest {
    private static ChromeOptions options;
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void canAddTaskThroughUi() {
        driver.get("http://localhost:8080/");
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("taskName")));
        nameInput.clear();
        nameInput.sendKeys("Buy milk");
        driver.findElement(By.id("submitTask")).click();

        // wait for the new task to appear in list
        wait.until(d -> d.findElements(By.xpath("//ul[@id='taskList']/li/span[text()='Buy milk']")).size() > 0);
        assertTrue(driver.getPageSource().contains("Buy milk"));
    }

    @Test
    void canDeleteTaskThroughUi() {
        driver.get("http://localhost:8080/");
        String name = "task-" + System.currentTimeMillis();

        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("taskName")));
        nameInput.clear();
        nameInput.sendKeys(name);
        driver.findElement(By.id("submitTask")).click();

        // wait until task appears
        wait.until(d -> d.findElements(By.xpath("//ul[@id='taskList']/li/span[text()='" + name + "']")).size() > 0);

        // locate delete button for the new task and click
        WebElement delBtn = driver.findElement(By.xpath("//ul[@id='taskList']/li[span/text()='" + name + "']//button[contains(@class,'delete-btn')]"));
        delBtn.click();

        // wait for it to be removed
        wait.until(d -> d.findElements(By.xpath("//ul[@id='taskList']/li/span[text()='" + name + "']")).size() == 0);

        assertEquals(0, driver.findElements(By.xpath("//ul[@id='taskList']/li/span[text()='" + name + "']")).size());
    }
}
