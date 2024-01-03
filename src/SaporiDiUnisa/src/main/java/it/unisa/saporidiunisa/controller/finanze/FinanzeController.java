package it.unisa.saporidiunisa.controller.finanze;

import it.unisa.saporidiunisa.model.entity.finanze.AndamentoProdotto;
import it.unisa.saporidiunisa.model.entity.finanze.Bilancio;
import it.unisa.saporidiunisa.model.entity.magazzino.Prodotto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FinanzeController
{
    public Bilancio visualizzaBilancio(LocalDate dataInizio, LocalDate dataFine)
    {
        return null;
    }

    public AndamentoProdotto visualizzaAndamentoProdotto(LocalDate dataInizio, LocalDate dataFine, Prodotto p)
    {
        return null;
    }

    public boolean impostaSconto(Prodotto prodotto, BigDecimal sconto, LocalDate dataInizio, LocalDate dataFine)
    {
        return false;
    }

    public List<Prodotto> visualizzaProdotti()
    {
        return null;
    }
}
