package ru.academits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Methods {

    public static boolean searchElement(WebDriver driver, String locator) {

        try {
            driver.findElement(By.cssSelector(locator));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
