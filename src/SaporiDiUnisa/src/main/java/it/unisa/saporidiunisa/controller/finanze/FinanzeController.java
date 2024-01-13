package it.unisa.saporidiunisa.controller.finanze;

import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Bilancio;
import it.unisa.saporidiunisa.model.entity.Prodotto;

import java.time.LocalDate;
import java.util.ArrayList;

public class FinanzeController
{
    public Bilancio visualizzaBilancio(LocalDate dataInizio, LocalDate dataFine)
    {
        if (dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine))
        {

        }

        return null;
    }

    public ArrayList<Integer> visualizzaAndamentoProdotto(LocalDate dataInizio, LocalDate dataFine, Prodotto prodotto)
    {
        if (dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine))
        {

        }

        return null;
    }

    public boolean impostaSconto(Prodotto prodotto, int sconto, LocalDate dataInizio, LocalDate dataFine)
    {
        if (!prodotto.isSconto())
        {

        }

        return false;
    }

    public static ArrayList<Prodotto> visualizzaProdotti()
    {
        return ProdottoDAO.findProdotti();
    }
}
