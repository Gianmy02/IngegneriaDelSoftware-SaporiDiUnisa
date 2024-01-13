package it.unisa.saporidiunisa.controller.scaffale;

import it.unisa.saporidiunisa.model.dao.ScaffaleDAO;
import it.unisa.saporidiunisa.model.entity.Esposizione;

import java.util.ArrayList;

public class ScaffaleController
{
    public boolean aggiungiProdottiScaffale(ArrayList<Esposizione> prodottiEsposizione)
    {
        return false;
    }

    public boolean eliminaScadutiScaffale(ArrayList<Esposizione> esposizioneScaffale)
    {
        return false;
    }

    /**
     * Il metodo <code>visualizzaProdottiScaffale()</code>  serve per avere tutti i prodotti in esposizione al giorno della chiamata
     * @return ritorna l'arraylist con tutti in esposizione
     */
    public ArrayList<Esposizione> visualizzaProdottiScaffale()
    {
        ScaffaleDAO gdao = new ScaffaleDAO();
        return gdao.getEsposizione();
    }
}
