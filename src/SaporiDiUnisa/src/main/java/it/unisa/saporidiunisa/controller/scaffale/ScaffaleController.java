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
     * Il metodo <code>inserisciEsposizione</code> mette in esposizione un prodotto appartenente a un lotto che non si trovava in esposizione
     * @param quantita quantità da mettere in esposizione
     * @param l lotto contenente i prodotti
     */
    public static void inserisciEsposizione(int quantita, Lotto l)
    {
        EsposizioneDAO.inserisciEsposizione(quantita, l);
    }

    /**
     * Il metodo <code>eliminaScadutiScaffale</code> elimina tutti gli scaduti dallo scaffale
     */
    public static void eliminaScadutiScaffale()
    {
        ArrayList<Esposizione> esposizioneScaffaleScaduti = EsposizioneDAO.getEsposizioneScaduti();
        for(Esposizione e: esposizioneScaffaleScaduti){
            EsposizioneDAO.rimuoviScaduto(e);
        }
    }

    /**
     * Il metodo <code>visualizzaProdottiScaffale()</code> restituisce tutti i prodotti in esposizione al momento della chiamata
     * @return ritorna un ArrayList dei prodotti in esposizione
     */
    public static ArrayList<Esposizione> visualizzaProdottiScaffale()
    {
        return EsposizioneDAO.getEsposizione();
    }

    /**
     * Il metodo <code>visualizzaProdottiScaffaleScaduti</code> restituisce tutti i prodotti scaduti in esposizione
     * @return ritorna un ArrayList dei prodotti scaduti in esposizione
     */
    public static ArrayList<Esposizione> visualizzaProdottiScaffaleScaduti()
    {
        return EsposizioneDAO.getEsposizioneScaduti();
    }

    /**
     * Il metodo <code>visualizzaProdottiMagazzino</code> restituisce i lotti presenti in magazzino che non hanno nessun prodotto esposto
     * @return ritorna un ArrayList dei lotti presenti in magazzino
     */
    public static ArrayList<Lotto> visualizzaProdottiMagazzino()
    {
        return LottoDAO.getLottiWithoutEsposizione();
    }

    /**
     * Il metodo <code>aumentaEsposizione</code> aumenta la quantità di un prodotto gia in esposizione per un determinato lotto
     * @param qnt quantità da aggiungere
     * @param e contiene il prodotto e il lotto da aumentare
     */
    public static void aumentaEsposizione(int qnt, Esposizione e)
    {
        EsposizioneDAO.aumentaEsposizione(qnt, e);
    }

    /**
     * Il metodo <code>diminuisciLotto</code> diminuisce la quantità del lotto in magazzino dopo aver effettuato un <code>aumentaEsposizione</code>
     * @param id lotto del quale diminuire la quantità
     * @param qnt quantità da sottrarre
     */
    public static void diminuisciLotto(int id, int qnt)
    {
        LottoDAO.diminuisciLotto(id, qnt);
    }

    /**
     * Il metodo <code>getEspostiByProdotto</code> restituisce la quantità totale del prodotto esposto passato come parametro
     * @param p Prodotto del quale ci interessa la quantità
     * @return intero, quantità in esposizione
     */
    public static int getEspostiByProdotto(Prodotto p){
        return EsposizioneDAO.getEspostiByProdotto(p);
    }
}
