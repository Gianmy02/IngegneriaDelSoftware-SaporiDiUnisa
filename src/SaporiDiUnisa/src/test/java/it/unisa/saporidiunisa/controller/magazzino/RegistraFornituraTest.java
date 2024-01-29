package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura.AggiungiLotto;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import lombok.val;
import org.junit.jupiter.api.*;
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

    @Nested
    class Incorrect {

        private String errorString;

        @AfterEach
        void afterEach() throws ServletException, IOException {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS)) {
                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.sendMessage(captor.capture(), eq(response))).thenAnswer(Answers.RETURNS_DEFAULTS);

                new AggiungiLotto().doPost(request, response);

                System.out.println(captor.getValue());
                Assertions.assertEquals(errorString, captor.getValue());
            }
        }

        @Test
        @DisplayName("1.1.1")
        void tc_1_1_1() {
            populatePart("x", "Barilla", "50.00", "100", "2024-08-24");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il nome deve essere compreso tra 2 e 255 caratteri\n";
        }

        @Test
        @DisplayName("1.1.2")
        void tc_1_1_2() {
            populatePart("Pasta2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss" +
                            "9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn" +
                            "4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0" +
                            "Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6a1Bb2Cc3Dd4Ee5Ff6Gg7Hh8" +
                            "Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2",
                    "Curtiriso",
                    "80.00",
                    "120",
                    "2024-10-05"
            );
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il nome deve essere compreso tra 2 e 255 caratteri\n";
        }

        @Test
        @DisplayName("1.1.3")
        void tc_1_1_3() {
            populatePart("Pas@ta al pomod!!or=o", "Barilla", "50.00", "100", "2024-12-03");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il nome non rispetta il formato\n";
        }

        @Test
        @DisplayName("1.1.4")
        void tc_1_1_4() {
            populatePart("Cereali", "c", "60.00", "80", "2025-07-05");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il marchio deve essere compreso tra 2 e 255 caratteri\n";
        }

        @Test
        @DisplayName("1.1.5")
        void tc_1_1_5() {
            populatePart("Mandorle",
                    "prova2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt" +
                            "0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2" +
                            "Ww3Xx4Yy5Zz6Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Z" +
                            "z6a1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6Aa1Bb2",
                    "100.00",
                    "40",
                    "2024-07-10"
            );
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il marchio deve essere compreso tra 2 e 255 caratteri\n";
        }

        @Test
        @DisplayName("1.1.6")
        void tc_1_1_6() {
            populatePart("Yogurt", "F!a--ge", "80.00", "50", "2025-01-07");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il marchio non rispetta il formato\n";
        }

        @Test
        @DisplayName("1.1.7")
        void tc_1_1_7() {
            populatePart("Cioccolato", "Lindt", "0.00", "100", "2024-03-07");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il prezzo deve essere compreso tra 0.01 € e 100000.00 €\n";
        }

        @Test
        @DisplayName("1.1.8")
        void tc_1_1_8() {
            populatePart("Sottilette", "Mulino Bianco", "100000.00", "150", "2025-01-27");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il prezzo deve essere compreso tra 0.01 € e 100000.00 €\n";
        }

        @Test
        @DisplayName("1.1.9")
        void tc_1_1_9() {
            populatePart("Crostini", "Mulino Bianco", "50.7849", "120", "2024-02-20");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "Il prezzo non rispetta il formato\n";
        }

        @Test
        @DisplayName("1.1.10")
        void tc_1_1_10() {
            populatePart("Biscotti", "Digestive", "50.00", "cil", "2024-03-20");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "La quantità deve essere un numero\n";
        }

        @Test
        @DisplayName("1.1.11")
        void tc_1_1_11() {
            populatePart("Fette biscottate", "Natura", "70.00", "0", "2024-04-30");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "La quantità deve essere maggiore di 0\n";
        }

        @Test
        @DisplayName("1.1.12")
        void tc_1_1_12() {
            populatePart("Fesa di tacchino", "Piacersi", "70.00", "1000000", "2025-01-11");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "La quantità deve essere minore di 1000000\n";
        }

        @Test
        @DisplayName("1.1.13")
        void tc_1_1_13() {
            populatePart("Burro", "Santa Lucia", "80.00", "150", "11/017-24");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "La data di scadenza non è valida\n";
        }

        @Test
        @DisplayName("1.1.14")
        void tc_1_1_14() {
            populatePart("Philadelphia", "Benessere", "80.00", "150", "2010-05-10");
            makeImage("Foto.png", 1024 * 1024, "image/png");
            errorString = "La data di scadenza deve essere posteriore a quella odierna\n";
        }

        @Test
        @DisplayName("1.1.15")
        void tc_1_1_15() {
            populatePart("Pangrattato", "Mulino Bianco", "60.00", "100", "2024-02-09");
            makeImage("foto.java", 1024 * 1024, "image/png");
            errorString = "La foto deve essere un'immagine con estensione: jpg, jpeg o png\n";
        }

        @Test
        @DisplayName("1.1.16")
        void tc_1_1_16() {
            populatePart("Burro di arachidi", "Fiorentini", "80.00", "130", "2024-04-17");
            makeImage("Foto.png", 5 * 1024 * 1024, "image/png");
            errorString = "La foto deve essere minore di 2MB\n";
        }
    }

    @Nested
    class Correct {
        @AfterEach
        void afterEach() throws ServletException, IOException {
            when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
            new AggiungiLotto().doPost(request, response);
        }

        @Test
        @DisplayName("1.1.17")
        void tc_1_1_17() {
            populatePart("Pandoro", "Balocco", "65.00", "150", "2024-08-19");
            makeImage("Foto.png", 1024 * 1024, "image/png");
        }
    }

    private void populatePart(String nome, String marchio, String prezzo, String quantita, String dataScadenza)
    {
        val mockNomePart = mock(Part.class);
        val mockMarchioPart = mock(Part.class);
        val mockPrezzoPart = mock(Part.class);
        val mockQuantitaPart = mock(Part.class);
        val mockScadenzaPart = mock(Part.class);
        val mockFotoPart = mock(Part.class);

        try {
            when(request.getPart("nome")).thenReturn(mockNomePart);
            when(request.getPart("marchio")).thenReturn(mockMarchioPart);
            when(request.getPart("prezzo")).thenReturn(mockPrezzoPart);
            when(request.getPart("quantita")).thenReturn(mockQuantitaPart);
            when(request.getPart("dataScadenza")).thenReturn(mockScadenzaPart);
            when(request.getPart("foto")).thenReturn(mockFotoPart);

            when(mockNomePart.getInputStream()).thenReturn(new ByteArrayInputStream(nome.getBytes()));
            when(mockMarchioPart.getInputStream()).thenReturn(new ByteArrayInputStream(marchio.getBytes()));
            when(mockPrezzoPart.getInputStream()).thenReturn(new ByteArrayInputStream(prezzo.getBytes()));
            when(mockQuantitaPart.getInputStream()).thenReturn(new ByteArrayInputStream(quantita.getBytes()));
            when(mockScadenzaPart.getInputStream()).thenReturn(new ByteArrayInputStream(dataScadenza.getBytes()));
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeImage(String fileName, long fileSize, String contentType){
        byte[] fileContent = new byte[(int) fileSize];
        final InputStream inputStream = new ByteArrayInputStream(fileContent);
        final Part imagePart = mock(Part.class);
        try {
            when(request.getPart("foto")).thenReturn(imagePart);
            when(imagePart.getInputStream()).thenReturn(inputStream);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        when(imagePart.getSubmittedFileName()).thenReturn(fileName);
        when(imagePart.getContentType()).thenReturn(contentType);
        when(imagePart.getSize()).thenReturn(fileSize);
        when(imagePart.getHeader("content-disposition")).thenReturn("filename=\"" + fileName + "\"");
    }
}
