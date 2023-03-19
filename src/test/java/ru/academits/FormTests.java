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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormTests {

    private static WebDriver driver;

    @BeforeAll
    public static void setup() {

        String browser = System.getProperty("browser");

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();

        driver.findElement(By.cssSelector("#firstName")).sendKeys(TestData.name);
        driver.findElement(By.cssSelector("#lastName")).sendKeys(TestData.lastName);
        driver.findElement(By.cssSelector("#userEmail")).sendKeys(TestData.eMail);

        driver.findElement(By.xpath("//label[contains(text(),'" + TestData.gender + "')]")).click(); //gender

        driver.findElement(By.cssSelector("#userNumber")).sendKeys(TestData.phone);

        //date start
        driver.findElement(By.cssSelector("#dateOfBirthInput")).click();

        WebElement elemYear = driver.findElement(By.xpath("//*[@class='react-datepicker__year-select']"));
        Select selectY = new Select(elemYear);
        selectY.selectByVisibleText(TestData.newYear);

        WebElement elemMonth = driver.findElement(By.xpath("//*[@class='react-datepicker__month-select']"));
        Select selectM = new Select(elemMonth);
        selectM.selectByVisibleText(TestData.newMonth);

        driver.findElement(By.xpath("//*[contains(@aria-label,'" + TestData.newMonth + "')and(text()='" + TestData.newDay + "')]")).click();
        //date end

        driver.findElement(By.cssSelector("#subjectsInput")).sendKeys(TestData.subject1_short);
        driver.findElement(By.cssSelector("#subjectsInput")).sendKeys(Keys.RETURN);
        driver.findElement(By.cssSelector("#subjectsInput")).sendKeys(TestData.subject2_short);
        driver.findElement(By.cssSelector("#subjectsInput")).sendKeys(Keys.RETURN);

        driver.findElement(By.xpath("//label[contains(text(),'" + TestData.hobby + "')]")).click();

        driver.findElement(By.cssSelector("#uploadPicture")).sendKeys(Methods.fullFilePath());

        driver.findElement(By.cssSelector("#currentAddress")).sendKeys(TestData.textSample);

        driver.findElement(By.xpath("//*[@id='state']//input")).sendKeys(TestData.state);
        driver.findElement(By.xpath("//*[@id='state']//input")).sendKeys(Keys.TAB);
        driver.findElement(By.xpath("//*[@id='city']//input")).sendKeys(TestData.city);
        driver.findElement(By.xpath("//*[@id='city']//input")).sendKeys(Keys.RETURN);

        driver.findElement(By.cssSelector("#submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 2, 50);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(TestData.dialogLocator)));
    }

    @Test
    public void submitTest() {
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@role='dialog']")).isDisplayed());
    }

    @Test
    public void studentNameTest() {
        String studentName = driver.findElement(By.xpath("//td[contains(text(),'Student Name')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.name + " " + TestData.lastName, studentName);
    }

    @Test
    public void emailTest() {
        String resultEmail = driver.findElement(By.xpath("//td[contains(text(),'Student Email')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.eMail, resultEmail);
    }

    @Test
    public void genderTest() {
        String resultGender = driver.findElement(By.xpath("//td[contains(text(),'Gender')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.gender, resultGender);
    }

    @Test
    public void mobileTest() {
        String resultMobile = driver.findElement(By.xpath("//table//td[contains(text(),'Mobile')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.phone, resultMobile);
    }
    @Test
    public void dateOfBirthTest() {
        String resultDate = driver.findElement(By.xpath("//td[contains(text(),'Date of Birth')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.newDay + " "+ TestData.newMonth + "," + TestData.newYear, resultDate);
    }

    @Test
    public void subjectsTest() {
        String resultSubjects = driver.findElement(By.xpath("//td[contains(text(),'Subjects')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.subject1_full + ", "+ TestData.subject2_full, resultSubjects);
    }

    @Test
    public void hobbyTest() {
        String resultHobby = driver.findElement(By.xpath("//td[contains(text(),'Hobbies')]/following::td[1]")).getText();
        Assertions.assertEquals(TestData.hobby, resultHobby);
    }

    @Test
    public void addFileTest() {
        String fileName = Methods.fileName();
        String resultName = driver.findElement(By.xpath("//td[contains(text(),'Picture')]/following::td[1]")).getText();
        Assertions.assertEquals(fileName, resultName);
    }

    @Test
    public void setStateCityTest() {
        String observedStateCity = driver
                .findElement(By.xpath("//td[contains(text(),'State and City')]/following::td[1]"))
                .getText();
        Assertions.assertEquals(TestData.state + " " + TestData.city, observedStateCity);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}