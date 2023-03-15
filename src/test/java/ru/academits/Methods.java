package ru.academits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Methods {

    public static boolean searchElement(WebDriver driver) {
        try {
            driver.findElement(By.cssSelector(TestData.dialogLocator));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

}
