package it.unisa.saporidiunisa.unit;

import it.unisa.saporidiunisa.model.dao.DipendenteDAO;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unisa.saporidiunisa.controller.autenticazione.AutenticazioneController.modificaPin;
import static org.junit.jupiter.api.Assertions.*;

final class UpdatePinTest
{
    static final String PIN_NEW = "9999";
    static final String PIN_OLD = "5678";
    static final Dipendente.Ruolo RUOLO = Dipendente.Ruolo.CASSIERE;

    // Pre-condizione
    @BeforeEach
    void beforeEach()
    {
        assertNull(DipendenteDAO.findDipendenteByPin(PIN_NEW));
    }

    // Metodo
    @Test
    void test()
    {
        assertTrue(modificaPin(PIN_NEW, RUOLO));
    }

    // Post-condizione
    @AfterEach
    void afterEach()
    {
        assertNotNull(DipendenteDAO.findDipendenteByPin(PIN_NEW));
    }

    // Rollback
    @AfterAll
    static void afterAll()
    {
        assertTrue(modificaPin(PIN_OLD, RUOLO));
    }
}