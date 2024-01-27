package it.unisa.saporidiunisa.controller.autenticazione.servlet;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class UpdatePinServletTest extends ServletTest
{
    @BeforeEach
    void beforeEach()
    {
        init();
        mockSession();
        mockDipendente(Dipendente.Ruolo.ADMIN);
    }

    @AfterEach
    void afterEach() throws ServletException, IOException
    {
        try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS))
        {
            val dipendente = new Dipendente(1, Dipendente.Ruolo.CASSIERE, "7786");

            val captor = ArgumentCaptor.forClass(String.class);
            utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

            new UpdatePinServlet().doPost(request, response);

            System.out.println(captor.getValue());
        }
    }

    @Test
    @DisplayName("3.2.1")
    void tc_3_2_1()
    {
        populateRequest(ofEntries(entry("newPin", "bob456"), entry("ruolo", "CASSIERE")));
    }

    @Test
    @DisplayName("3.2.2")
    void tc_3_2_2()
    {
        populateRequest(ofEntries(entry("newPin", "7786"), entry("ruolo", "CASSIERE")));
    }

    @Test
    @DisplayName("3.2.3")
    void tc_3_2_3()
    {
        populateRequest(ofEntries(entry("newPin", "1823"), entry("ruolo", "CASSIERE")));
    }
}