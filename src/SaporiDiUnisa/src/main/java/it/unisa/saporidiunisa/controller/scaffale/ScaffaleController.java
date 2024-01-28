package it.unisa.saporidiunisa.controller.scaffale;

import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;

import java.util.ArrayList;

/**
 * @author Simone Vittoria
 * La classe <code>ScaffaleController</code> si interpone fra il database e le operazioni da effettuare sullo scaffale
 */
public class ScaffaleController
{

    /**
     * Il metodo <code>inserisciEsposizione</code> inserisce nei prodotti esposti un nuovo prodotto appartenente a un lotto diverso da quelli gia in esposizione
     * @param quantita quantità da mettere in esposizione
     * @param l lotto dal quale si stanno prendendo i prodotti
     */
    public static void inserisciEsposizione(int quantita, Lotto l)
    {
        EsposizioneDAO.inserisciEsposizione(quantita, l);
    }

    /**
     * Il metodo <code>eliminaScadutiScaffale</code> elimina tutti gli scaduti dallo scaffale facendo da tramite tra le servlet e il db
     */
    public static void eliminaScadutiScaffale()
    {
        ArrayList<Esposizione> esposizioneScaffaleScaduti = EsposizioneDAO.getEsposizioneScaduti();
        for(Esposizione e: esposizioneScaffaleScaduti){
            EsposizioneDAO.rimuoviScaduto(e);
        }
    }

    /**
     * Il metodo <code>visualizzaProdottiScaffale()</code>  serve per avere tutti i prodotti in esposizione al giorno della chiamata
     * @return ritorna un ArrayList con tutti in esposizione
     */
    public static ArrayList<Esposizione> visualizzaProdottiScaffale()
    {
        return EsposizioneDAO.getEsposizione();
    }

    /**
     * Il metodo <code>visualizzaProdottiScaffaleScaduti</code> viene utilizzato per avere tutti i prodotti scaduti rimasti nello scaffale
     * @return ArrayList di oggetti scaduti
     */
    public static ArrayList<Esposizione> visualizzaProdottiScaffaleScaduti()
    {
        return EsposizioneDAO.getEsposizioneScaduti();
    }

    /**
     * Il metodo <code>visualizzaProdottiMagazzino</code> visualizza i lotti in magazzino che non hanno nessun prodotto esposto e che hanno disponibilità
     * @return ArrayList di lotti
     */

    public static ArrayList<Lotto> visualizzaProdottiMagazzino()
    {
        return LottoDAO.getLottiWithoutEsposizione();
    }

    /**
     * Il metodo <code>aumentaEsposizione</code> aumenta la quantità di un prodotto gia in esposizione di un determinato lotto
     * @param qnt quantità da aggiungere
     * @param e prodotto e lotto a cui fare riferimento
     */
    public static void aumentaEsposizione(int qnt, Esposizione e)
    {
        EsposizioneDAO.aumentaEsposizione(qnt, e);
    }

    /**
     * Il metodo <code>diminuisciLotto</code> diminuisce la quantità del lotto in magazzino
     * @param id lotto al quale diminuire la quantità
     * @param qnt quantità da detrarre
     */
    public static void diminuisciLotto(int id, int qnt)
    {
        LottoDAO.diminuisciLotto(id, qnt);
    }

    /**
     * Il metodo <code>getEspostiByProdotto</code> restituisce la quantità totale di prodotti esposti sullo scaffale
     * @param p Prodotto
     * @return intero, quantità di esposti
     */
    public static int getEspostiByProdotto(Prodotto p){
        return EsposizioneDAO.getEspostiByProdotto(p);
    }
}
