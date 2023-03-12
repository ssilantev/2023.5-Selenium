package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FormTests {
    private WebDriver driver;

    @BeforeEach
    public void defineDriver() {

        String browser = System.getProperty("browser");
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
        }
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    @Test
    public void assetUrl() {
        Assertions.assertEquals("https://demoqa.com/automation-practice-form", driver.getCurrentUrl());
    }
    @Test
    public void minimal() {

        WebElement nameLabel = driver.findElement(By.cssSelector("#userName-label"));
        String nameLabelText = nameLabel.getText();
        Assertions.assertEquals("Name", nameLabelText);



    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}