package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import lombok.val;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.time.LocalDate;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.mockito.Mockito.*;

class ImpostaScontoServletTest extends ServletTest
{

    @BeforeEach
    void beforeEach()
    {
        init();
        mockSession();
        mockDipendente(Dipendente.Ruolo.FINANZE);
        mockDispatcher();
    }

    @Nested
    class Incorrect
    {
        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS);
                 val magazzinoController = mockStatic(MagazzinoController.class, Answers.CALLS_REAL_METHODS))
            {
                val prodotto1 = new Prodotto(1, "Farina", "Caputo", 10.00F, 0, null, null, null);
                val prodotto2 = new Prodotto(5, "Farina", "Caputo", 10.00F, 7.50F, LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 5), null);

                magazzinoController.when(() -> MagazzinoController.getProdottoById(1)).thenReturn(prodotto1);
                magazzinoController.when(() -> MagazzinoController.getProdottoById(5)).thenReturn(prodotto2);

                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new ImpostaScontoServlet().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("5.2.1")
        void tc_5_2_1()
        {
            populateRequest(ofEntries(entry("prodotto", "0"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-02"), entry("sconto", "1")));
        }

        @Test
        @DisplayName("5.2.2")
        void tc_5_2_2()
        {
            populateRequest(ofEntries(entry("prodotto", "5"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-02"), entry("sconto", "1")));
        }

        @Test
        @DisplayName("5.2.3")
        void tc_5_2_3()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-00"), entry("dataFineSconto", "2025-01-02"), entry("sconto", "1")));
        }

        @Test
        @DisplayName("5.2.4")
        void tc_5_2_4()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2020-01-01"), entry("dataFineSconto", "2025-01-02"), entry("sconto", "1")));
        }

        @Test
        @DisplayName("5.2.5")
        void tc_5_2_5()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-00"), entry("sconto", "1")));
        }

        @Test
        @DisplayName("5.2.6")
        void tc_5_2_6()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-02"), entry("dataFineSconto", "2025-01-01"), entry("sconto", "1")));
        }

        @Test
        @DisplayName("5.2.7")
        void tc_5_2_7()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-02"), entry("sconto", "-1")));
        }

        @Test
        @DisplayName("5.2.8")
        void tc_5_2_8()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-00"), entry("sconto", "101")));
        }

        @Test
        @DisplayName("5.2.9")
        void tc_5_2_9()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-00"), entry("sconto", "50a")));
        }
    }

    @Nested
    class Correct
    {
        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS);
                 val magazzinoController = mockStatic(MagazzinoController.class, Answers.CALLS_REAL_METHODS))
            {
                val prodotto1 = new Prodotto(10, "Farina", "Caputo", 10.00F, 0, null, null, null);

                magazzinoController.when(() -> MagazzinoController.getProdottoById(1)).thenReturn(prodotto1);

                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchSuccess(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new ImpostaScontoServlet().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("5.2.10")
        void tc_5_2_10()
        {
            populateRequest(ofEntries(entry("prodotto", "1"), entry("dataInizioSconto", "2025-01-01"), entry("dataFineSconto", "2025-01-02"), entry("sconto", "1")));
        }
    }
}
