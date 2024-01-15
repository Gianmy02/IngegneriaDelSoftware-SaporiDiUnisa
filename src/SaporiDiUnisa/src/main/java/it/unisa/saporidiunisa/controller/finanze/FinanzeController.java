package it.unisa.saporidiunisa.controller.finanze;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Bilancio;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;

public class FinanzeController
{
    /**
     * La funzione <code>visualizzaBilancio</code> restituisce il bilancio aggiornato al modello della chiamata
     * @return Bilancio
     */
    public static Bilancio visualizzaBilancio()
    {
        val bilancio = new Bilancio();

        // NOTA: da testare
        // val perdite = (float)LottoDAO.getPerditeTotali().stream().mapToDouble(l -> l.getCostoProdotto() * l.getQuantitaAttuale()).sum();
        var perdite = 0.0f;
        for (val l : LottoDAO.getPerditeTotali())
            perdite += l.getCostoProdotto() * l.getQuantitaAttuale();

        bilancio.setPerdite(perdite);
        bilancio.setGuadagno(VenditaController.getGuadagniTotali());
        bilancio.setIncasso(VenditaController.getIncassiTotali());
        bilancio.setSpese(MagazzinoController.getSpeseTotali());

        return bilancio;
    }

    public static ArrayList<Integer> visualizzaAndamentoProdotto(LocalDate dataInizio, LocalDate dataFine, Prodotto prodotto)
    {
        if (dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine))
        {

        }

        return null;
    }

    public static boolean impostaSconto(Prodotto prodotto, int sconto, LocalDate dataInizio, LocalDate dataFine)
    {
        if (!prodotto.isSconto())
        {
            prodotto.setPrezzoScontato(prodotto.getPrezzo() - ((prodotto.getPrezzo()*sconto)/100));
            prodotto.setInizioSconto(dataInizio);
            prodotto.setFineSconto(dataFine);
            return ProdottoDAO.updateSconto(prodotto);
        }

        return false;
    }

    public static ArrayList<Prodotto> visualizzaProdotti()
    {
        return ProdottoDAO.selectAll();
    }
}
