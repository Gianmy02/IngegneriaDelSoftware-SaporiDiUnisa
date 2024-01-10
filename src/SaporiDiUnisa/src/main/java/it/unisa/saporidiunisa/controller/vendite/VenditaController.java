package it.unisa.saporidiunisa.controller.vendite;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.entity.Venduto;

import java.time.LocalDate;
import java.util.ArrayList;

public class VenditaController
{
    public boolean venditaProdotti(ArrayList<Prodotto> prodotti)
    {
        return false;
    }

    public ArrayList<Venduto> visualizzaStoricoVendite(LocalDate dataInizio, LocalDate dataFine)
    {
        return null;
    }

    public float getIncassi(LocalDate dataInizio, LocalDate dataFine)
    {
        return 0;
    }

    public float getIncassiTotali()
    {
        return 0;
    }
}
