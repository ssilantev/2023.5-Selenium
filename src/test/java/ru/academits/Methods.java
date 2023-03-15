package ru.academits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Methods {

    public static boolean searchElement(WebDriver driver) {
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