package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

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

    @BeforeEach
    public void minimalDataTypeSet() {

        driver.findElement(By.cssSelector("#firstName")).sendKeys(TestData.name);
        driver.findElement(By.cssSelector("#lastName")).sendKeys(TestData.lastName);
        driver.findElement(By.cssSelector("#userNumber")).sendKeys(TestData.phone);
        driver.findElement(By.cssSelector("[for='gender-radio-1']")).click();
    }

    @Test
    public void assertTitle() {
        String title = driver.getTitle();
        Assertions.assertEquals("DEMOQA", title);
        System.out.println(title);
    }

    @Test
    public void minRegistrationTest() {
        driver.findElement(By.cssSelector("#submit")).isDisplayed();
        driver.findElement(By.cssSelector("#submit")).click();

        driver.findElement(By.cssSelector("[role='dialog']")).isDisplayed();
        String studentName = driver.findElement(By.xpath("//*//tbody/tr[1]/td[2]")).getText();
        Assertions.assertEquals(TestData.name + " " + TestData.lastName, studentName);

        String gender = driver.findElement(By.xpath("//*//tbody/tr[3]/td[2]")).getText();
        Assertions.assertEquals("Male", gender);

        String mobile = driver.findElement(By.xpath("//*//tbody/tr[4]/td[2]")).getText();
        Assertions.assertEquals(TestData.phone, mobile);
    }

    @Test
    public void phoneWrongTest() throws InterruptedException {
        driver.findElement(By.cssSelector("#userNumber")).clear();
        driver.findElement(By.cssSelector("#userNumber")).sendKeys(TestData.phoneWrong);//false
        driver.findElement(By.cssSelector("#submit")).click();
        String locator = "\"[role='dialog']\"";
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertTrue(!Methods.searchElement(driver, locator));//dialog not opened ifphone is wrong
    }

    @Test
    public void maleRadioTest() {
        driver.findElement(By.cssSelector("#submit")).click();
        String gender = driver.findElement(By.xpath("//*//tbody/tr[3]/td[2]")).getText();
        Assertions.assertEquals("Male", gender);
    }
    @Test
    public void femaleRadioTest() {
        driver.findElement(By.cssSelector("[for='gender-radio-2']")).click();
        driver.findElement(By.cssSelector("#submit")).click();
        String gender = driver.findElement(By.xpath("//*//tbody/tr[3]/td[2]")).getText();
        Assertions.assertEquals("Female", gender);
    }

    @Test
    public void otherGenderRadioTest() {
        driver.findElement(By.cssSelector("[for='gender-radio-3']")).click();
        driver.findElement(By.cssSelector("#submit")).click();
        String gender = driver.findElement(By.xpath("//*//tbody/tr[3]/td[2]")).getText();
        Assertions.assertEquals("Other", gender);
    }

    @Test
    public void checkboxesTest() {
        driver.findElement(By.cssSelector("[for='hobbies-checkbox-1']")).click();
        driver.findElement(By.cssSelector("[for='hobbies-checkbox-2']")).click();
        driver.findElement(By.cssSelector("[for='hobbies-checkbox-3']")).click();

        driver.findElement(By.cssSelector("#submit")).click();
        String hobbies = driver.findElement(By.xpath("//*//tbody/tr[7]/td[2]")).getText();
        Assertions.assertEquals("Sports, Reading, Music", hobbies);
    }


    @AfterEach
    public void tearDown() throws InterruptedException {
    TimeUnit.SECONDS.sleep(1);
        if (driver != null) {
            driver.quit();
        }
    }
}