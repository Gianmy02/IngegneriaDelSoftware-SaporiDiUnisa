package it.unisa.saporidiunisa.unit;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.dao.VendutoDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AddGiornoVenditeTest
{
    /*la funzione testa la addGiornoVendite di VenditaController, funzione dedita all'aggiunta
    del giorno corrrente nel db, siccome lavorativo
    */
    @Test
    public void addGiornoVenditeTest()
    {
        VenditaController.addGiornoVendite();
        assertFalse(VendutoDAO.searchGiornoLavorativo(), "Il test addGiornoVendite NON è passato");
        System.out.println("Test addGiornoVendite passato");
    }
}