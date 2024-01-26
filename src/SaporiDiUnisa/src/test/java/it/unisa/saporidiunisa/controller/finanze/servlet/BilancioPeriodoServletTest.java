package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import lombok.val;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BilancioPeriodoServletTest extends ServletTest
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
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS))
            {
                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new BilancioPeriodoServlet().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("5.1.1")
        void tc_5_1_1()
        {
            populateRequest(ofEntries(entry("inizio", "2024-01/01"), entry("fine", "2024-02-01")));
        }

        @Test
        @DisplayName("5.1.2")
        void tc_5_1_2()
        {
            populateRequest(ofEntries(entry("inizio", "2025-01-01"), entry("fine", "2024-02-01")));
        }

        @Test
        @DisplayName("5.1.3")
        void tc_5_1_3()
        {
            populateRequest(ofEntries(entry("inizio", "2024-01-01"), entry("fine", "2024-02/01")));
        }

        @Test
        @DisplayName("5.1.4")
        void tc_5_1_4()
        {
            populateRequest(ofEntries(entry("inizio", "2024-01-10"), entry("fine", "2024-01-01")));
        }

        @Test
        @DisplayName("5.1.5")
        void tc_5_1_5()
        {
            populateRequest(ofEntries(entry("inizio", "2024-01-01"), entry("fine", "2025-01-01")));
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

                new BilancioPeriodoServlet().doPost(request, response);
            }
        }

        @Test
        @DisplayName("5.1.6")
        void tc_5_1_6()
        {
            populateRequest(ofEntries(entry("inizio", "2024-01-10"), entry("fine", "2024-01-12")));
        }
    }
}
