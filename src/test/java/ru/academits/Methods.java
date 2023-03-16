package ru.academits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Methods {

    public static WebDriverWait letsWait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 2, 50);
        return wait;
    }

    public static boolean waitDialog(WebDriver driver) {
        WebDriverWait waitDialog = new WebDriverWait(driver, 2, 50);
        try {
            driver.findElement(By.cssSelector(TestData.dialogLocator));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        return (dateFormat.format(Calendar.getInstance().getTime()));
    }

    public static String combineXpath() {
        return "//*[contains(@aria-label,'" + TestData.newMonth + "')and(text()='" + TestData.newDay + "')]";
    }
}