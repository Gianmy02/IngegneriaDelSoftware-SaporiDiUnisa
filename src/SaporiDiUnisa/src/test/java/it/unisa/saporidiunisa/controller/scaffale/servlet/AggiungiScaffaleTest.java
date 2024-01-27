package it.unisa.saporidiunisa.controller.scaffale.servlet;

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
import static org.mockito.Mockito.mockStatic;

public class AggiungiScaffaleTest extends ServletTest{
    @BeforeEach
    void beforeEach()
    {
        init();
        mockSession();
        mockDipendente(Dipendente.Ruolo.MAGAZZINIERE);
        mockDispatcher();
    }

    @Nested
    class Incorrect{
        @AfterEach
        void afterEach() throws ServletException, IOException {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS)){
                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new AggiungiScaffale().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("4.1.1")
        void tc_4_1_1()
        {
            populateRequest(ofEntries(entry("qntScaffale", "-1")));
        }

        @Test
        @DisplayName("4.1.2")
        void tc_4_1_2()
        {
            populateRequest(ofEntries(entry("qntScaffale", "1000000")));
        }

        @Test
        @DisplayName("4.1.3")
        void tc_4_1_3()
        {
            populateRequest(ofEntries(entry("qntScaffale", "500a")));
        }

        @Test
        @DisplayName("4.1.4")
        void tc_4_1_4()
        {
            populateRequest(ofEntries(entry("qntScaffale", "501"), entry("qntMagazzino", "500")));
        }
    }

    @Nested
    class Correct{
        @AfterEach
        void afterEach() throws ServletException, IOException {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS)){
                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new AggiungiScaffale().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("4.1.5")
        void tc_4_1_5()
        {
            populateRequest(ofEntries(entry("qntScaffale", "10"), entry("qntMagazzino", "500")));        }
    }
}
