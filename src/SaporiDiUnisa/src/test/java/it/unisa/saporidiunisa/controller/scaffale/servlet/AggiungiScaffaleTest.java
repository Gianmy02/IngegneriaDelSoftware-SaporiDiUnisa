package it.unisa.saporidiunisa.controller.scaffale.servlet;

import it.unisa.saporidiunisa.controller.ServletTest;
import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import lombok.val;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class AggiungiScaffaleTest extends ServletTest
{
    @BeforeEach
    void beforeEach()
    {
        init();
        mockSession();
        mockDipendente(Dipendente.Ruolo.MAGAZZINIERE);
        mockDispatcher();
    }

    @Nested
    class Incorrect
    {
        @AfterEach
        void afterEach() throws ServletException, IOException {
            try (val utils = mockStatic(Utils.class, Answers.CALLS_REAL_METHODS);
                 val scaffaleController = mockStatic(ScaffaleController.class, Answers.CALLS_REAL_METHODS))
            {
                val prodotto1 = new Prodotto(1, "Farina", "Caputo", 1.00F, 0, null, null, null);
                val lotto1 = new Lotto(1, 500F, LocalDate.of(2025, 3, 3), 700, 500, null, prodotto1);
                val esposizione1 = new Esposizione();
                esposizione1.setLotto(lotto1);
                esposizione1.setQuantita(50);
                esposizione1.setProdotto(prodotto1);
                ArrayList<Esposizione> lottiScaffale = new ArrayList<>();
                lottiScaffale.add(esposizione1);

                val prodotto2 = new Prodotto(2, "Biscotti", "Gocciole", 1.00F, 0, null, null, null);
                val lotto2 = new Lotto(2, 500F, LocalDate.of(2025, 3, 3), 500, 500, null, prodotto2);
                ArrayList<Lotto> lottiMagazzino = new ArrayList<>();
                lottiMagazzino.add(lotto2);

                scaffaleController.when(ScaffaleController::visualizzaProdottiScaffale).thenReturn(lottiScaffale);
                scaffaleController.when(ScaffaleController::visualizzaProdottiMagazzino).thenReturn(lottiMagazzino);

                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchError(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new AggiungiScaffale().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("4.1.1")
        void tc_4_1_1() { populateRequest(ofEntries(entry("qntScaffale1", "-1"), entry("qntMagazzino2", "10"))); }

        @Test
        @DisplayName("4.1.2")
        void tc_4_1_2()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "1000000"), entry("qntMagazzino2", "10")));
        }

        @Test
        @DisplayName("4.1.3")
        void tc_4_1_3()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "500a"), entry("qntMagazzino2", "10")));
        }

        @Test
        @DisplayName("4.1.4")
        void tc_4_1_4()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "501"), entry("qntMagazzino2", "10")));
        }

        @Test
        @DisplayName("4.1.5")
        void tc_4_1_5()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "10"), entry("qntMagazzino2", "-1")));
        }

        @Test
        @DisplayName("4.1.6")
        void tc_4_1_6()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "10"), entry("qntMagazzino2", "1000000")));
        }

        @Test
        @DisplayName("4.1.7")
        void tc_4_1_7()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "10"), entry("qntMagazzino2", "500a")));
        }

        @Test
        @DisplayName("4.1.8")
        void tc_4_1_8()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "10"), entry("qntMagazzino2", "501")));
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
                val captor = ArgumentCaptor.forClass(String.class);
                utils.when(() -> Utils.dispatchSuccess(captor.capture(), any(), any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                new AggiungiScaffale().doPost(request, response);

                System.out.println(captor.getValue());
            }
        }

        @Test
        @DisplayName("4.1.9")
        void tc_4_1_9()
        {
            populateRequest(ofEntries(entry("qntScaffale1", "10"), entry("qntMagazzino2", "10")));
        }
    }
}
