package it.unisa.saporidiunisa.controller.vendite;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.entity.Venduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VenditaControllerTest {

    private VenditaController venditaController;

    @BeforeEach
    void setUp() {
        venditaController = new VenditaController();
    }

    @Test
    void testVenditaProdotti() {
        ArrayList<Venduto> venduti = new ArrayList<>();

        // Caso 1: Quantità, costo e guadagno positivi
        Venduto venduto1 = new Venduto();
        // Imposta le proprietà del prodotto venduto
        // ...
        venduto1.setGiorno(LocalDate.now());
        venduto1.setCosto(-5);  // Imposta il costo a un valore positivo
        venduto1.setQuantita(-3);  // Imposta la quantità a un valore positivo
        venduto1.setGuadagno(-57);  // Imposta il guadagno a un valore positivo
        // Imposta un prodotto valido
        Prodotto p1 = new Prodotto();
        p1.setId(1);
        p1.setMarchio("Baiocchi");
        p1.setPrezzo(11);
        p1.setInizioSconto(LocalDate.now().minusDays(7));
        p1.setFineSconto(LocalDate.now());
        p1.setNome("biscotti");
        venduto1.setProdotto(p1);

        venduti.add(venduto1);

        // Caso 2: Quantità, costo e guadagno negativi
        Venduto venduto2 = new Venduto();
        // Imposta le proprietà del prodotto venduto
        // ...
        venduto2.setGiorno(LocalDate.now());
        venduto2.setCosto(-5);  // Imposta il costo a un valore negativo
        venduto2.setQuantita(5);  // Imposta la quantità a un valore negativo
        venduto2.setGuadagno(-57);  // Imposta il guadagno a un valore negativo
        // Imposta un prodotto valido
        Prodotto p2 = new Prodotto();
        p2.setId(2);
        p2.setMarchio("Oreo");
        p2.setPrezzo(15);
        p2.setInizioSconto(LocalDate.now().minusDays(5));
        p2.setFineSconto(LocalDate.now());
        p2.setNome("biscotti al cioccolato");
        venduto2.setProdotto(p2);

        venduti.add(venduto2);

        Venduto venduto3 = new Venduto();
        // Imposta le proprietà del prodotto venduto
        // ...
        venduto3.setGiorno(LocalDate.now());
        venduto3.setCosto(-5);  // Imposta il costo a un valore negativo
        venduto3.setQuantita(5);  // Imposta la quantità a un valore negativo
        venduto3.setGuadagno(-57);  // Imposta il guadagno a un valore negativo
        // Imposta un prodotto valido
        Prodotto p3 = new Prodotto();
        p3.setId(2);
        p3.setMarchio("Oreo");
        p3.setPrezzo(15);
        p3.setInizioSconto(LocalDate.now().minusDays(5));
        p3.setFineSconto(LocalDate.now());
        p3.setNome("biscotti al cioccolato");
        venduto3.setProdotto(p2);

        venduti.add(venduto3);
        // Chiamata al metodo da testare
        boolean result = VenditaController.venditaProdotti(venduti);

        // Verifica il risultato
        assertFalse(result, "La vendita dei prodotti non dovrebbe essere confermata con dati invalidi");

    }
}