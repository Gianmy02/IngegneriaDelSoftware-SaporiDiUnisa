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
public class VenditaController {
    /**
     * Il metodo conferma le vendite richieste di tutti i prodotti
     * @param venduti sono tutti i prodotti venduti in un determinato momento a un cliente ipotetico
     * @return booleano di conferma
     */
    public static boolean venditaProdotti(ArrayList<Venduto> venduti) {
        boolean b = false;
        /*itero sulla lista di prodotti che si vogliono acquistare*/
        for (Venduto v : venduti) {
            float guadagno = 0;
            //prendo tutti i prodotti in esposizione del prodotto specificato
            ArrayList<Esposizione> e = EsposizioneDAO.getLottibyProdottoWithoutScaduti(v.getProdotto());
            int quantitaCount = v.getQuantita();
            //itero per la quantita di prodotti da comprare
            for (Esposizione es : e) {
                //se la quantita richiesta e maggiore di quella esposta del lotto preso in questione si continua
                if (quantitaCount > es.getQuantita()) {
                    guadagno += (v.getCosto() - es.getLotto().getCostoProdotto()) * es.getQuantita();
                    quantitaCount = quantitaCount - es.getQuantita();
                    EsposizioneDAO.diminuisciEsposizione(es.getQuantita(), es, true);
                }
                //se e minore o ugualela quantita esposta di quel lotto va bene e quindi sarà l'ultima
                else {
                    guadagno += (v.getCosto() - es.getLotto().getCostoProdotto()) * v.getQuantita();
                    EsposizioneDAO.diminuisciEsposizione(quantitaCount, es, false);
                    break;
                }
            }
            v.setGuadagno(guadagno);
                /*salva nel db i nuovi venduti del prodotto,
            vedo se al giorno d'oggi ci sono state altre vendite del prodotto*/
            Venduto attuale = VendutoDAO.getVendutiGiornalieroByProdotto(v.getProdotto());
            if (attuale != null) {
                b = VendutoDAO.doUpdateVendita(v);
            } else {
                b = VendutoDAO.doSaveVendita(v);
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
    public static ArrayList<Venduto> visualizzaStoricoVendite(LocalDate dataInizio, LocalDate dataFine) {
        return VendutoDAO.getStorico(dataInizio, dataFine);
    }

    /**
     * Il metodo <code>visualizzaProdottiEsposti</code>mette in correlazione il db con le servlet ed e utile per la vista del cassiere sui prodotti
     *
     * @return prodotti esposti
     */
    public ArrayList<Esposizione> visualizzaProdottiEsposti() {
        return EsposizioneDAO.visualizzaProdottiEspostiCassiere();
    }


    public float getIncassi(LocalDate dataInizio, LocalDate dataFine) {
        return 0;
    }

    /**
     * Il metodo <code>getIncassiTotali</code> restituisce gli incassi fatti dal primo giorno di apertura
     *
     * @return float incassi
     */
    public static float getIncassiTotali() {
        return VendutoDAO.getIncassiTotali();
    }

    /**
     * Il metodo <code>getIncassiTotali</code> restituisce i guadagni fatti dal primo giorno di apertura
     *
     * @return float guadagni
     */
    public static float getGuadagniTotali() {
        return VendutoDAO.getGuadagniTotali();
    }

    /**
     * Il metodo <code>addGiornoVendite</code> in caso di avvenuta vendita controlla se il giorno lavorativo odierno e gia salvato,
     * nel caso non fosse così vorrebbe dire che si sta per effettuare la prima vendita della giornata.
     */
    public static void addGiornoVendite() {
        if (VendutoDAO.searchGiornoLavorativo())
            VendutoDAO.doSaveGiornoLavorativo();
    }

    public static boolean checkVenduto(Venduto v){
        return v.validate(EsposizioneDAO.getEspostiByProdotto(v.getProdotto()));
    }

}
