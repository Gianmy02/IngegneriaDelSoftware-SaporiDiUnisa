package it.unisa.saporidiunisa.selenium;

import lombok.val;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistraFornituraTest extends Configuration{
    
    @BeforeAll
    static void setUp() {
        init();

        login(PIN_MAGAZZINIERE);

        // Arrivo alla pagina di registrazione fornitura
        for(int i=0; i < 3; i++)
            driver.findElements(By.tagName("form")).getFirst().click();
    }

    @AfterAll
    static void afterAll()
    {
        driver.close();
    }
    
    @Nested
    class Incorrect{
        String errorString;

        @AfterEach
        void afterEach(){
            Assertions.assertEquals(driver.findElement(By.id("errors")).getText(), errorString);
        }

        @Test
        @DisplayName("1.1.1")
        void tc_1_1_1() {
            fillForm("x", "Barilla", "50.00", "100", "2024-08-24", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il nome deve essere compreso tra 2 e 255 caratteri";
        }

        @Test
        @DisplayName("1.1.2")
        void tc_1_1_2() {
            fillForm("Pasta2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss" +
                            "9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn" +
                            "4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0" +
                            "Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6a1Bb2Cc3Dd4Ee5Ff6Gg7Hh8" +
                            "Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2",
                    "Curtiriso",
                    "80.00",
                    "120",
                    "2024-10-05"
                    , new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il nome deve essere compreso tra 2 e 255 caratteri";
        }

        @Test
        @DisplayName("1.1.3")
        void tc_1_1_3() {
            fillForm("Pas@ta al pomod!!or=o", "Barilla", "50.00", "100", "2024-12-03", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il nome non rispetta il formato";
        }

        @Test
        @DisplayName("1.1.4")
        void tc_1_1_4() {
            fillForm("Cereali", "c", "60.00", "80", "2025-07-05", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il marchio deve essere compreso tra 2 e 255 caratteri";
        }

        @Test
        @DisplayName("1.1.5")
        void tc_1_1_5() {
            fillForm("Mandorle",
                    "prova2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt" +
                            "0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2" +
                            "Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Z" +
                            "z6a1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2",
                    "100.00",
                    "40",
                    "2024-07-10"
                    , new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il marchio deve essere compreso tra 2 e 255 caratteri";
        }

        @Test
        @DisplayName("1.1.6")
        void tc_1_1_6() {
            fillForm("Yogurt", "F!a--ge", "80.00", "50", "2025-01-07", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il marchio non rispetta il formato";
        }

        @Test
        @DisplayName("1.1.7")
        void tc_1_1_7() {
            fillForm("Cioccolato", "Lindt", "0.00", "100", "2024-03-07", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il prezzo deve essere compreso tra 0.01 € e 100000.00 €";
        }

        @Test
        @DisplayName("1.1.8")
        void tc_1_1_8() {
            fillForm("Sottilette", "Mulino Bianco", "100000.00", "150", "2025-01-27", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il prezzo deve essere compreso tra 0.01 € e 100000.00 €";
        }

        @Test
        @DisplayName("1.1.9")
        void tc_1_1_9() {
            fillForm("Crostini", "Mulino Bianco", "50.7849", "120", "2024-02-20", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "Il prezzo non rispetta il formato";
        }

        @Test
        @DisplayName("1.1.10")
        void tc_1_1_10() {
            fillForm("Biscotti", "Digestive", "50.00", "cil", "2024-03-20", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La quantità deve essere un numero";
        }

        @Test
        @DisplayName("1.1.11")
        void tc_1_1_11() {
            fillForm("Fette biscottate", "Natura", "70.00", "0", "2024-04-30", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La quantità deve essere maggiore di 0";
        }

        @Test
        @DisplayName("1.1.12")
        void tc_1_1_12() {
            fillForm("Fesa di tacchino", "Piacersi", "70.00", "1000000", "2025-01-11", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La quantità deve essere minore di 1000000";
        }

        @Test
        @DisplayName("1.1.13")
        void tc_1_1_13() {
            fillForm("Burro", "Santa Lucia", "80.00", "150", "11/017-24", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La data di scadenza non è valida";
        }

        @Test
        @DisplayName("1.1.14")
        void tc_1_1_14() {
            fillForm("Philadelphia", "Benessere", "80.00", "150", "2010-05-10", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La data di scadenza deve essere posteriore a quella odierna";
        }

        @Test
        @DisplayName("1.1.15")
        void tc_1_1_15() {
            fillForm("Pangrattato", "Mulino Bianco", "60.00", "100", "2024-02-09", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La foto deve essere un'immagine con estensione: jpg, jpeg o png";
        }

        @Test
        @DisplayName("1.1.16")
        void tc_1_1_16() {
            fillForm("Burro di arachidi", "Fiorentini", "80.00", "130", "2024-04-17", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
            errorString = "La foto deve essere minore di 2MB";
        }
    }

    @Nested
    class Correct{
        @AfterEach
        void afterEach() {
            Assertions.assertEquals(driver.findElement(By.id("errors")).getText(), "");
        }

        @Test
        @DisplayName("1.1.17")
        void tc_1_1_17() {
            fillForm("Pandoro", "Balocco", "65.00", "150", "2024-08-19", new File("src/main/webapp/img/favicon/favicon-32x32.png"));
        }
    }

    
    void fillForm(String nome, String marchio, String prezzo, String quantita, String dataScadenza, File file){
        val jsExecutor = (JavascriptExecutor) driver;

        val nomeInput = driver.findElement(By.id("nome"));
        nomeInput.clear();
        nomeInput.sendKeys(nome);
        val marchioInput = driver.findElement(By.id("marchio"));
        marchioInput.clear();
        marchioInput.sendKeys(marchio);
        val quantitaInput = driver.findElement(By.id("quantita"));
        quantitaInput.clear();
        jsExecutor.executeScript("arguments[0].value = arguments[1]", driver.findElement(By.id("quantita")), quantita);
        val prezzoInput = driver.findElement(By.id("prezzo"));
        prezzoInput.clear();
        jsExecutor.executeScript("arguments[0].value = arguments[1]", driver.findElement(By.id("prezzo")), prezzo);
        val dataScadenzaInput = driver.findElement(By.id("dataScadenza"));
        dataScadenzaInput.clear();
        dataScadenzaInput.sendKeys(convertStringDate(dataScadenza));
        val fotoInput = driver.findElement(By.id("foto"));
        fotoInput.clear();
        fotoInput.sendKeys(file.getAbsolutePath());

        driver.findElement(By.id("ajax-caller")).click();
    }

    String convertStringDate(String date){
        try {
            val startFormat = new SimpleDateFormat("yyyy-MM-dd");
            val endFormat = new SimpleDateFormat("dd-MM-yyyy");
            return endFormat.format(startFormat.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
}
