package it.unisa.saporidiunisa.unit;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.dao.VendutoDAO;
import org.junit.jupiter.api.Assertions;

public class UnitTest {

    /*la funzione testa la addGiornoVendite di VenditaController, funzione dedita all'aggiunta
    del giorno corrrente nel db, siccome lavorativo
    */
    public static void main(String[] args) {
        addGiornoVenditeTest();// Chiamiamo il nostro test
    }
    public static void addGiornoVenditeTest(){
        VenditaController.addGiornoVendite();
        Assertions.assertFalse(VendutoDAO.searchGiornoLavorativo(), "Il test addGiornoVendite NON è passato");
        System.out.println("Test addGiornoVendite passato");
    }

}
