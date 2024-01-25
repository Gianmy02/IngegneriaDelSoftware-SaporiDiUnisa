package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura.AggiungiLotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

public class RegistraFornituraTest {

    AggiungiLotto servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;
    
    @BeforeEach
    void beforeEach() throws ServletException, IOException {
        servlet = new AggiungiLotto();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        val session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(any(), any());
    }

    @AfterEach
    void afterEach() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(dispatcher, atLeastOnce()).forward(request, response);

        val captor = ArgumentCaptor.forClass(String.class);
        verify(request, times(1)).setAttribute(eq("message"), captor.capture());

        System.out.println(captor.getValue());
    }

    void populateRequest(String nome, String marchio, String prezzo, String quantita, String dataScadenza){
        when(request.getParameter("nome")).thenReturn(nome);
        when(request.getParameter("marchio")).thenReturn(marchio);
        when(request.getParameter("prezzo")).thenReturn(prezzo);
        when(request.getParameter("quantita")).thenReturn(quantita);
        when(request.getParameter("dataScadenza")).thenReturn(dataScadenza);
        
        long fileSize = 1 * 1024 * 1024;
        byte[] fileContent = new byte[(int) fileSize];
        final InputStream inputStream = new ByteArrayInputStream(fileContent);
        final Part imagePart = mock(Part.class);
        try {
            when(imagePart.getInputStream()).thenReturn(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        when(imagePart.getContentType()).thenReturn("image/jpeg");
        when(imagePart.getName()).thenReturn("image");
        when(imagePart.getSubmittedFileName()).thenReturn("test-image.jpg");
        when(imagePart.getSize()).thenReturn(fileSize);
        try {
            when(request.getPart("foto")).thenReturn(imagePart);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("1.1.1")
    void tc_1_1_1() {
        populateRequest("x", "Barilla", "50.00", "100", "2024-08-24");
    }

    @Test
    @DisplayName("1.1.2")
    void tc_1_1_2() {
        populateRequest("Pasta2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm" +
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

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) 
            return "";
        return fileName.substring(lastDotIndex + 1);
    }

    @Test
    @DisplayName("1.1.3")
    void tc_1_1_3() {
        populateRequest("Pas@ta al pomod!!or=o", "Barilla", "50.00", "100", "2024-12-03");
    }

    @Test
    @DisplayName("1.1.4")
    void tc_1_1_4() {
        populateRequest("Cereali", "c", "60.00", "80", "2025-07-05");
    }

    @Test
    @DisplayName("1.1.5")
    void tc_1_1_5() {
        populateRequest("Mandorle", "prova2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm" +
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
        populateRequest("Yogurt", "F!a--ge", "80.00", "50", "2024-01-07");
    }

    @Test
    @DisplayName("1.1.7")
    void tc_1_1_7() {
        populateRequest("Cioccolato", "Lindt", "0.00", "100", "2024-03-07");
    }

    @Test
    @DisplayName("1.1.8")
    void tc_1_1_8() {
        populateRequest("Sottilette", "Mulino Bianco", "100000.00", "150", "2025-01-27");
    }

    @Test
    @DisplayName("1.1.9")
    void tc_1_1_9() {
        populateRequest("Crostini", "Mulino Bianco", "50.7849", "120", "2024-02-20");
    }

    @Test
    @DisplayName("1.1.10")
    void tc_1_1_10() {
        populateRequest("Biscotti", "Digestive", "50.00", "cil", "2024-03-20");
    }

    @Test
    @DisplayName("1.1.11")
    void tc_1_1_11() {
        populateRequest("Fette biscottate", "Natura", "70.00", "0", "2024-04-30");
    }

    @Test
    @DisplayName("1.1.12")
    void tc_1_1_12() {
        populateRequest("Fesa di tacchino", "Piacersi", "70.00", "1000000", "2025-01-11");
    }

    @Test
    @DisplayName("1.1.13")
    void tc_1_1_13() {
        populateRequest("Burro", "Santa Lucia", "80.00", "150", "11/017-24");
    }

    @Test
    @DisplayName("1.1.14")
    void tc_1_1_14() {
        populateRequest("Philadelphia", "Benessere", "80.00", "150", "2010-05-10");
    }

    @Test
    @DisplayName("1.1.15")
    void tc_1_1_15() {
        populateRequest("Pangrattato", "Mulino Bianco", "60.00", "100", "2024-02-09");
    }

    @Test
    @DisplayName("1.1.16")
    void tc_1_1_16() {
        populateRequest("Burro di arachidi", "Fiorentini", "80.00", "130", "2024-04-17");
    }

    @Test
    @DisplayName("1.1.17")
    void tc_1_1_17() {
        populateRequest("Pandoro", "Balocco", "65.00", "150", "2024-08-19");
    }
}
