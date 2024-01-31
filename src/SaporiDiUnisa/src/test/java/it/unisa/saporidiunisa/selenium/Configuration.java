package it.unisa.saporidiunisa.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class Configuration {
    final static WebDriver driver = new ChromeDriver();
    final static String baseUrl = "http://localhost:8080/SaporiDiUnisa_war_exploded/";

    final static String PIN_ADMIN = "1234";
    final static String PIN_CASSIERE = "5678";
    final static String PIN_FINANZE = "5432";
    final static String PIN_MAGAZZINIERE = "9876";

    static void init(){
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofMillis(1000));
    }

    static void login(String pin){
        final WebElement inputPin = driver.findElement(By.id("pin"));
        inputPin.sendKeys(pin);
        inputPin.sendKeys(Keys.ENTER);
    }
}
