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
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
        }
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    @Test
    public void assetUrl() {
        Assertions.assertEquals("https://demoqa.com/automation-practice-form", driver.getCurrentUrl());
    }

    @BeforeEach
    public void minimalDataTypeSet() {

        driver.findElement(By.cssSelector("#firstName")).sendKeys(TestData.name);
        driver.findElement(By.cssSelector("#lastName")).sendKeys(TestData.lastName);
        driver.findElement(By.cssSelector("#userNumber")).sendKeys(TestData.phone);
        driver.findElement(By.cssSelector("[for='gender-radio-1']")).click();
    }

    @Test
    public void minRequiresRegTest() {
        driver.findElement(By.cssSelector("#submit")).isDisplayed();
        driver.findElement(By.cssSelector("#submit")).click();

        driver.findElement(By.cssSelector("[role='dialog']")).isDisplayed();





    }

}


//    @AfterEach
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}