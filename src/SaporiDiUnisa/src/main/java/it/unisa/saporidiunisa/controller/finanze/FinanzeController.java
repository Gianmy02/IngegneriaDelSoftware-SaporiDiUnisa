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

/**
 * @author Antonio Facchiano, Gianmarco Riviello
 * La classe <code>FinanzeController</code> viene utilizzata per attingere al database e avere dati o funzioni utili in merito all'ambito finanziario
 */
public class FinanzeController
{
    /**
     * La funzione <code>visualizzaBilancio</code> restituisce il bilancio Totale aggiornato al modello della chiamata
     * @return Bilancio calcolato
     */
    public static Bilancio visualizzaBilancio()
    {
        val bilancio = new Bilancio();

        var perdite = 0.00f;
        for (val l : LottoDAO.getPerditeTotali())
            perdite += l.getCostoProdotto() * l.getQuantitaAttuale();

        bilancio.setPerdite(perdite);
        bilancio.setGuadagno(VenditaController.getGuadagniTotali());
        bilancio.setIncasso(VenditaController.getIncassiTotali());
        bilancio.setSpese(MagazzinoController.getSpeseTotali());
        return bilancio;
    }

    public static Bilancio visualizzaBilancioParziale(LocalDate inizio, LocalDate fine)
    {
        val bilancio = new Bilancio();

        var perdite = 0.00f;
        for (val l : LottoDAO.getPerdite(inizio, fine))
            perdite += l.getCostoProdotto() * l.getQuantitaAttuale();

        bilancio.setPerdite(perdite);
        bilancio.setIncasso(VenditaController.getIncassi(inizio, fine));
        bilancio.setGuadagno(VenditaController.getGuadagni(inizio, fine));
        bilancio.setSpese(MagazzinoController.getSpese(inizio, fine));

        return bilancio;
    }

    /*La funzione Prenderebbe un periodo e un prodotto correlato e restituirebbe un array di interi nel quale si ha per ogni giorno, vendite e acquisti del prodotto*/
    public static ArrayList<Integer> visualizzaAndamentoProdotto(LocalDate dataInizio, LocalDate dataFine, Prodotto prodotto)
    {
        if (dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine))
        {
            // TODO: da implementare
            return new ArrayList<>();
        }

        return null;
    }

    /**
     * La funzione <code>impostaSconto</code> serve ad applicare uno sconto a un prodotto scelto
     * @param prodotto prodotto al quale applicare lo sconto
     * @param sconto percentuale dello sconto da applicare da 1 a 100
     * @param dataInizio data di inizio dello sconto
     * @param dataFine data di fine dello sconto
     * @return booleano di conferma
     */
    public static boolean impostaSconto(Prodotto prodotto, int sconto, LocalDate dataInizio, LocalDate dataFine)
    {
        if (!prodotto.isSconto())
        {
            prodotto.setPrezzoScontato(prodotto.getPrezzo() - ((prodotto.getPrezzo() * sconto) / 100));
            prodotto.setInizioSconto(dataInizio);
            prodotto.setFineSconto(dataFine);
            return ProdottoDAO.updateSconto(prodotto);
        }

        return false;
    }
}
