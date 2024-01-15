package it.unisa.saporidiunisa.controller.scaffale;

import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;

import java.util.ArrayList;

public class ScaffaleController
{
    public boolean aggiungiProdottiScaffale(ArrayList<Esposizione> prodottiEsposizione)
    {
        return false;
    }

    /**
     * Il metodo <code>eliminaScadutiScaffale</code> elimina tutti gli scaduti dallo scaffale facendo da tramite tra le servlet e il db
     */
    public void eliminaScadutiScaffale()
    {
        EsposizioneDAO edao = new EsposizioneDAO();
        ArrayList<Esposizione> esposizioneScaffaleScaduti = edao.getEsposizioneScaduti();
        for(Esposizione e: esposizioneScaffaleScaduti){
            edao.rimuoviScaduto(e);
        }
    }

    /**
     * Il metodo <code>visualizzaProdottiScaffale()</code>  serve per avere tutti i prodotti in esposizione al giorno della chiamata
     * @return ritorna l'arraylist con tutti in esposizione
     */
    public static ArrayList<Esposizione> visualizzaProdottiScaffale()
    {
        return EsposizioneDAO.getEsposizione();
    }

    public static ArrayList<Lotto> visualizzaProdottiMagazzino()
    {
        return LottoDAO.getLottiWithoutEsposizione();
    }
}
