package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
        driver.findElement(By.xpath("//label[contains(text(),'Male')]")).click();
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

        String gender = driver.findElement(By.xpath("//td[contains(text(),'Gender')]/following::td[1]")).getText();
        Assertions.assertEquals("Male", gender);

        String mobile = driver.findElement(By.xpath("//td[contains(text(),'Mobile')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.phone, mobile);
    }

    @Test
    public void phoneWrongTest() throws InterruptedException {
        driver.findElement(By.cssSelector("#userNumber")).clear();
        driver.findElement(By.cssSelector("#userNumber")).sendKeys(TestData.phoneWrong);
        driver.findElement(By.cssSelector("#submit")).click();
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertFalse(Methods.searchElement(driver));
    }

    @Test
    public void eMailTest() {
        driver.findElement(By.cssSelector("#userEmail")).sendKeys(TestData.eMail);
        driver.findElement(By.cssSelector("#submit")).click();
        String mail = driver.findElement(By.xpath("//td[contains(text(),'Student Email')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.eMail, mail);
    }

    @Test
    public void eMailWrongTest() throws InterruptedException {
        driver.findElement(By.cssSelector("#userEmail")).sendKeys(TestData.eMailWrong);
        driver.findElement(By.cssSelector("#submit")).click();
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertFalse(Methods.searchElement(driver));
    }

    @Test
    public void maleRadioTest() {
        driver.findElement(By.cssSelector("#submit")).click();
        String gender = driver.findElement(By.xpath("//td[contains(text(),'Gender')]/following::td[1]")).getText();
        Assertions.assertEquals("Male", gender);

    }
    @Test
    public void femaleRadioTest() {
        driver.findElement(By.xpath("//label[contains(text(),'Female')]")).click();
        driver.findElement(By.cssSelector("#submit")).click();
        String gender = driver.findElement(By.xpath("//td[contains(text(),'Gender')]/following::td[1]")).getText();
        Assertions.assertEquals("Female", gender);
    }

    @Test
    public void otherGenderRadioTest() {
        driver.findElement(By.xpath("//label[contains(text(),'Other')]")).click();
        driver.findElement(By.cssSelector("#submit")).click();
        String gender = driver.findElement(By.xpath("//td[contains(text(),'Gender')]/following::td[1]")).getText();
        Assertions.assertEquals("Other", gender);
    }

    @Test
    public void checkboxesTest() {
        driver.findElement(By.xpath("//label[contains(text(),'Sports')]")).click();
        driver.findElement(By.xpath("//label[contains(text(),'Reading')]")).click();
        driver.findElement(By.xpath("//label[contains(text(),'Music')]")).click();

        driver.findElement(By.cssSelector("#submit")).click();
        String hobbies = driver.findElement(By.xpath("//td[contains(text(),'Hobbies')]/following::td[1]")).getText();
        Assertions.assertEquals("Sports, Reading, Music", hobbies);
    }

    @Test //Этот тест должен всегда падать из-за бага в форме - в диалоге нет строки Subjects
    public void subjectsTest() {
        driver.findElement(By.cssSelector("#subjectsInput")).sendKeys(TestData.inputText);
        driver.findElement(By.cssSelector("#submit")).click();
        String subjects = driver.findElement(By.xpath("//td[contains(text(),'Subjects')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.inputText, subjects);
    }

    @Test
    public void addressTest() {
        driver.findElement(By.cssSelector("#currentAddress")).sendKeys(TestData.inputText);
        driver.findElement(By.cssSelector("#submit")).click();
        String address = driver.findElement(By.xpath("//td[contains(text(),'Address')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.inputText, address);
    }

    @Test
    public void addFileTest() {
        File myFile = new File(TestData.filePath);
        String path = myFile.getAbsolutePath();
        String fileName = myFile.getName();
        driver.findElement(By.cssSelector("#uploadPicture")).sendKeys(path);
        driver.findElement(By.cssSelector("#submit")).click();
        String observedName = driver.findElement(By.xpath("//td[contains(text(),'Picture')]/following::td[1]")).getText();
        Assertions.assertEquals(fileName, observedName);
    }

    @Test
    public void cityDisabledTest() {
        Assertions.assertFalse(driver.findElement(By.cssSelector("#react-select-4-input")).isEnabled());
    }

    @Test
    public void countryListTest() {
        driver.findElement(By.xpath("//*[@focusable='false'][1]")).click();
        driver.findElement(By.xpath("//*[@class='css-2613qy-menu']")).click();



//        WebElement stateList = driver.findElement(By.cssSelector("[#state]"));
//        List<WebElement> list = stateList.findElement(By.cssSelector("input")).get;
//
//        Assertions.assertFalse(driver.findElement(By.cssSelector("#react-select-4-input")).isEnabled());
//
//        for (WebElement input: inputs) {
//            System.out.println((input.getAttribute()));
        }



    @AfterEach
    public void tearDown() throws InterruptedException {
    TimeUnit.SECONDS.sleep(1);
        if (driver != null) {
            driver.quit();
        }
    }
}