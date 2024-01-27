package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura.AggiungiLotto;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.*;

import static org.mockito.Mockito.*;

public class RegistraFornituraTest extends ServletTest {
    
    @BeforeEach
    void beforeEach() {
        init();
        mockSession();
        mockDispatcher();
    }

    @AfterEach
    void afterEach() throws ServletException, IOException {
        try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS))
        {
            val captor = ArgumentCaptor.forClass(String.class);
            utils.when(() -> Utils.sendMessage(captor.capture(), eq(response))).thenAnswer(Answers.RETURNS_DEFAULTS);

            new AggiungiLotto().doPost(request, response);

            System.out.println(captor.getValue());
        }
    }

    void populatePart(String nome, String marchio, String prezzo, String quantita, String dataScadenza)
    {
        val mockNomePart = mock(Part.class);
        val mockMarchioPart = mock(Part.class);
        val mockPrezzoPart = mock(Part.class);
        val mockQuantitaPart = mock(Part.class);
        val mockScadenzaPart = mock(Part.class);

        try {
            when(request.getPart("nome")).thenReturn(mockNomePart);
            when(request.getPart("marchio")).thenReturn(mockMarchioPart);
            when(request.getPart("prezzo")).thenReturn(mockPrezzoPart);
            when(request.getPart("quantita")).thenReturn(mockQuantitaPart);
            when(request.getPart("dataScadenza")).thenReturn(mockScadenzaPart);

            when(mockNomePart.getInputStream()).thenReturn(new ByteArrayInputStream(nome.getBytes()));
            when(mockMarchioPart.getInputStream()).thenReturn(new ByteArrayInputStream(marchio.getBytes()));
            when(mockPrezzoPart.getInputStream()).thenReturn(new ByteArrayInputStream(prezzo.getBytes()));
            when(mockQuantitaPart.getInputStream()).thenReturn(new ByteArrayInputStream(quantita.getBytes()));
            when(mockScadenzaPart.getInputStream()).thenReturn(new ByteArrayInputStream(dataScadenza.getBytes()));
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    @DisplayName("1.1.1")
    void tc_1_1_1() {
        populatePart("x", "Barilla", "50.00", "100", "2024-08-24");
    }

    @Test
    @DisplayName("1.1.2")
    void tc_1_1_2() {
        populatePart("Pasta2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm" +
                "3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4" +
                "Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk" +
                "1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2" +
                "Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh" +
                "8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0" +
                "Uu1Vv2Ww3Xx4Yy5Zz6",
                "Curtiriso",
                "80.00",
                "120",
                "2024-10-05"
        );
    }

    @Test
    @DisplayName("1.1.3")
    void tc_1_1_3() {
        populatePart("Pas@ta al pomod!!or=o", "Barilla", "50.00", "100", "2024-12-03");
    }

    @Test
    @DisplayName("1.1.4")
    void tc_1_1_4() {
        populatePart("Cereali", "c", "60.00", "80", "2025-07-05");
    }

    @Test
    @DisplayName("1.1.5")
    void tc_1_1_5() {
        populatePart("Mandorle", "prova2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm" +
                "3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Y" +
                "y5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1L" +
                "l2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww" +
                "3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj" +
                "0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv" +
                "2Ww3Xx4Yy5Zz6",
                "100.00",
                "40",
                "2024-07-10"
        );
    }

    @Test
    @DisplayName("1.1.6")
    void tc_1_1_6() {
        populatePart("Yogurt", "F!a--ge", "80.00", "50", "2024-01-07");
    }

    @Test
    @DisplayName("1.1.7")
    void tc_1_1_7() {
        populatePart("Cioccolato", "Lindt", "0.00", "100", "2024-03-07");
    }

    @Test
    @DisplayName("1.1.8")
    void tc_1_1_8() {
        populatePart("Sottilette", "Mulino Bianco", "100000.00", "150", "2025-01-27");
    }

    @Test
    @DisplayName("1.1.9")
    void tc_1_1_9() {
        populatePart("Crostini", "Mulino Bianco", "50.7849", "120", "2024-02-20");
    }

    @Test
    @DisplayName("1.1.10")
    void tc_1_1_10() {
        populatePart("Biscotti", "Digestive", "50.00", "cil", "2024-03-20");
    }

    @Test
    @DisplayName("1.1.11")
    void tc_1_1_11() {
        populatePart("Fette biscottate", "Natura", "70.00", "0", "2024-04-30");
    }

    @Test
    @DisplayName("1.1.12")
    void tc_1_1_12() {
        populatePart("Fesa di tacchino", "Piacersi", "70.00", "1000000", "2025-01-11");
    }

    @Test
    @DisplayName("1.1.13")
    void tc_1_1_13() {
        populatePart("Burro", "Santa Lucia", "80.00", "150", "11/017-24");
    }

    @Test
    @DisplayName("1.1.14")
    void tc_1_1_14() {
        populatePart("Philadelphia", "Benessere", "80.00", "150", "2010-05-10");
    }

    @Test
    @DisplayName("1.1.15")
    void tc_1_1_15() {
        populatePart("Pangrattato", "Mulino Bianco", "60.00", "100", "2024-02-09");
    }

    @Test
    @DisplayName("1.1.16")
    void tc_1_1_16() {
        populatePart("Burro di arachidi", "Fiorentini", "80.00", "130", "2024-04-17");
    }

    @Test
    @DisplayName("1.1.17")
    void tc_1_1_17() {
        populatePart("Pandoro", "Balocco", "65.00", "150", "2024-08-19");
    }
}
