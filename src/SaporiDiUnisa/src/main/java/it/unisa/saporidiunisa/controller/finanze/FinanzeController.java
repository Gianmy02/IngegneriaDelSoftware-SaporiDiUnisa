package it.unisa.saporidiunisa.controller.finanze;

import it.unisa.saporidiunisa.model.entity.Bilancio;
import it.unisa.saporidiunisa.model.entity.Prodotto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinanzeController
{
    public Bilancio visualizzaBilancio(LocalDate dataInizio, LocalDate dataFine)
    {
        return null;
    }

    public ArrayList<Integer> visualizzaAndamentoProdotto(LocalDate dataInizio, LocalDate dataFine, Prodotto p)
    {
        return null;
    }

    public boolean impostaSconto(Prodotto prodotto, int sconto, LocalDate dataInizio, LocalDate dataFine)
    {
        return false;
    }

    public List<Prodotto> visualizzaProdotti()
    {
        return null;
    }
}
