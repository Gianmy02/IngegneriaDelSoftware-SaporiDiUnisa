package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;

class LoginServletTest extends ServletTest
{
    @BeforeEach
    void beforeEach()
    {
        init();
        mockSession();
    }

    @Nested
    class Incorrect
    {
        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            try (val autenticazioneController = mockStatic(AutenticazioneController.class, Answers.CALLS_REAL_METHODS);
                 val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS))
            {
                val dipendente = new Dipendente(1, Dipendente.Ruolo.ADMIN, "8547");
                autenticazioneController.when(() -> AutenticazioneController.login("8547")).thenReturn(dipendente);

                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new LoginServlet().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("3.1.1")
        void tc_3_1_1()
        {
            populateRequest(ofEntries(entry("pin", "ciao1010")));
        }

        @Test
        @DisplayName("3.1.2")
        void tc_3_1_2()
        {
            populateRequest(ofEntries(entry("pin", "1235")));
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

                new LoginServlet().doPost(request, response);
            }
        }

        @Test
        @DisplayName("3.1.3")
        void tc_3_1_3()
        {
            populateRequest(ofEntries(entry("pin", "1234")));
        }
    }
}