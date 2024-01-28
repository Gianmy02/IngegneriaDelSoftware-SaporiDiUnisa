package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import lombok.val;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.mockito.Mockito.*;

class AggiungiVenditaServletTest extends ServletTest
{
    @BeforeEach
    void beforeEach()
    {
        init();
        mockSession();
        mockDipendente(Dipendente.Ruolo.CASSIERE);
        mockDispatcher();
    }

    void populateJson(String prodotto, String quantita, String prezzo) throws IOException
    {
        val jsonString = "[{\"productId\": " + prodotto + ", \"quantity\": " + quantita + ", \"price\": " + prezzo + "}]";

        // Creare un InputStream dalla stringa JSON
        val inputStream = new ByteArrayInputStream(jsonString.getBytes());

        // Creare un BufferedReader dallo InputStream
        val bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Configurare il mock per restituire il BufferedReader
        when(request.getReader()).thenReturn(bufferedReader);
    }


    @Nested
    class Incorrect
    {

        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            try (val magazzinoController = mockStatic(MagazzinoController.class, Answers.CALLS_REAL_METHODS);
                 val scaffaleController = mockStatic(ScaffaleController.class, Answers.CALLS_REAL_METHODS);
                 val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS))
            {
                val prodotto1 = new Prodotto(1, "Farina", "Caputo", 1.00F, 0, null, null, null);
                val prodotto2 = new Prodotto(2, "Farina", "Caputo", 1.00F, 0, null, null, null);

                magazzinoController.when(() -> MagazzinoController.getProdottoById(1)).thenReturn(prodotto1);
                magazzinoController.when(() -> MagazzinoController.getProdottoById(2)).thenReturn(prodotto2);

                scaffaleController.when(() -> ScaffaleController.getEspostiByProdotto(prodotto1)).thenReturn(10);
                scaffaleController.when(() -> ScaffaleController.getEspostiByProdotto(prodotto2)).thenReturn(1);

                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.sendMessage(captor.capture(), eq(response))).thenAnswer(Answers.RETURNS_DEFAULTS);

                new AggiungiVenditaServlet().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("2.1.1")
        void tc_2_1_1() throws IOException
        {
            populateJson("0", "1", "1.00");
        }

        @Test
        @DisplayName("2.1.2")
        void tc_2_1_2() throws IOException
        {
            populateJson("1", "a", "1.00");
        }

        @Test
        @DisplayName("2.1.3")
        void tc_2_1_3() throws IOException
        {
            populateJson("1", "0", "1.00");
        }

        @Test
        @DisplayName("2.1.4")
        void tc_2_1_4() throws IOException
        {
            populateJson("1", "1000000", "1.00");
        }

        @Test
        @DisplayName("2.1.5")
        void tc_2_1_5() throws IOException
        {
            populateJson("2", "5", "1.00");
        }

        @Test
        @DisplayName("2.1.6")
        void tc_2_1_6() throws IOException
        {
            populateJson("1", "5", "1.5555");
        }

        @Test
        @DisplayName("2.1.7")
        void tc_2_1_7() throws IOException
        {
            populateJson("1", "5", "-1.00");
        }

        @Test
        @DisplayName("2.1.8")
        void tc_2_1_8() throws IOException
        {
            populateJson("1", "1", "100000.00");
        }
    }

    @Nested
    class Correct
    {
        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS))
            {
                utils.verify(() -> Utils.dispatchError(any(), any(), any()), never());
                utils.verify(() -> Utils.sendMessage(any(), any()), never());

                new AggiungiVenditaServlet().doPost(request, response);
            }
        }

        @Test
        @DisplayName("2.1.9")
        void tc_2_1_9() throws IOException
        {
            populateJson("1", "1", "1.00");
        }
    }


}