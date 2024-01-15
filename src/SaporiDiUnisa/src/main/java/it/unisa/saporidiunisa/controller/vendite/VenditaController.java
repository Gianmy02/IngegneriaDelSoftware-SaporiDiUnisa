package it.unisa.saporidiunisa.controller.vendite;

import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.VendutoDAO;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Venduto;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Gianmarco Riviello
 * La classe <code>VenditaController</code>si interpone fra il database e le servlet rispetto alle vendite
 */
public class VenditaController
{
    /**
     * Il metodo conferma le vendite richieste di tutti i prodotti
     *
     * @param venduti sono tutti i prodotti venduti in un determinato momento a un cliente ipotetico
     * @return booleano di conferma
     */
    public boolean venditaProdotti(ArrayList<Venduto> venduti)
    {
        VendutoDAO vdao = new VendutoDAO();
        EsposizioneDAO gdao = new EsposizioneDAO();
        boolean b = false;
        /*itero sulla lista di prodotti che si vogliono acquistare*/
        for (Venduto v : venduti)
        {
            float guadagno = 0;
            ArrayList<Esposizione> e = gdao.getLottibyProdottoWithoutScaduti(v.getProdotto()); //prendo tutti i prodotti in esposizione del prodotto specificato
            int quantitaCount = v.getQuantita();
            //itero per la quantita di prodotti da comprare
            for (Esposizione es : e)
            {
                //se la quantita richiesta e maggiore di quella esposta del lotto preso in questione si continua
                if (quantitaCount > es.getQuantita())
                {
                    guadagno += (v.getCosto() - es.getLotto().getCostoProdotto()) * es.getQuantita();
                    quantitaCount = quantitaCount - es.getQuantita();
                    gdao.diminuisciEsposizione(es.getQuantita(), es);
                }
                //se e minore o ugualela quantita esposta di quel lotto va bene e quindi sarà l'ultima
                else
                {
                    guadagno += (v.getCosto() - es.getLotto().getCostoProdotto()) * v.getQuantita();
                    gdao.diminuisciEsposizione(v.getQuantita(), es);
                    break;
                }
            }
            v.setGuadagno(guadagno);
                /*salva nel db i nuovi venduti del prodotto,
            vedo se al giorno d'oggi ci sono state altre vendite del prodotto*/
            Venduto attuale = vdao.getVendutiGiornalieroByProdotto(v.getProdotto());
            if (attuale != null)
            {
                b = vdao.doUpdateVendita(v);
            }
            else
            {
                b = vdao.doSaveVendita(v);
            }
        }
        return b;
    }

    /**
     * Il metodo <code>visualizzaStoricoVendite</code> restituisce la somma delle attivita di vendita di tutti i Prodotti in un determinato periodo scelto
     *
     * @param dataInizio data che delimita l'inizio del periodo
     * @param dataFine   data che delimita la fine del periodo
     * @return ArrayList di venduti
     */
    public ArrayList<Venduto> visualizzaStoricoVendite(LocalDate dataInizio, LocalDate dataFine)
    {
        VendutoDAO vdao = new VendutoDAO();
        return vdao.getStorico(dataInizio, dataFine);
    }

    public ArrayList<Esposizione> visualizzaProdottiEsposti()
    {
        EsposizioneDAO edao = new EsposizioneDAO();
        return edao.visualizzaProdottiEspostiCassiere();
    }

    public float getIncassi(LocalDate dataInizio, LocalDate dataFine)
    {
        return 0;
    }

    public static float getIncassiTotali()
    {
        return VendutoDAO.getIncassiTotali();
    }

    public static float getGuadagniTotali()
    {
        return VendutoDAO.getGuadagniTotali();
    }
}
