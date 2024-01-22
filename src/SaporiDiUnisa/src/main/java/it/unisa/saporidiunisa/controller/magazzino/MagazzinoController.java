package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.FornituraDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.form.LottoForm;
import lombok.val;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Salvatore Ruocco
 * La classe <code>MagazzinoController</code> funge da tramite fra il database e le servlet per le operazioni sul magazzino
 */
public class MagazzinoController {

    /**
     * Il metodo <code>aggiungiLotto</code> aggiunge un nuovo lotto alla fornitura che si sta inserendo
     * */
    public static String aggiungiLotto(final LottoForm lottoForm) {
        return null;
    }

    /**
     * Il metodo <code>registraFornitura</code> registra una nuova fornitura del giorno nel db
     * @param fornitura fornitura inserita in input
     * @return booleano di conferma
     */
    public static boolean registraFornitura(final Fornitura fornitura) {
        if(fornitura == null || fornitura.getLotti().isEmpty())
            return false;

        FornituraDAO.insert(fornitura);
        val idFornitura = FornituraDAO.getLastId();

        val lotti = fornitura.getLotti();
        for(var l : lotti){
            l.getFornitura().setId(idFornitura);

            // Prezzo cad. inserito nel form
            float prezzoInserito = l.getCosto() / l.getQuantita();

            val prodotto = l.getProdotto();
            if(ProdottoDAO.selectByNameAndBrand(prodotto.getNome(), prodotto.getMarchio()) == null){  // prodotto nuovo
                // Imposto il prezzo del prodotto al doppio di quello inserito
                prodotto.setPrezzo(prezzoInserito * 2);
                ProdottoDAO.insert(prodotto);
                prodotto.setId(ProdottoDAO.getLastId());
            }
            else{
                // controllo se il prezzo inserito è almeno il doppio di quello attuale
                float prezzoAttuale = prodotto.getPrezzo();
                if((prezzoInserito * 2) > prezzoAttuale)
                    ProdottoDAO.updatePrice(prezzoInserito * 2, prodotto.getId());
            }
            LottoDAO.insert(l);
        }
        return true;
    }

    /**
     * Il metodo <code>eliminaLotto</code> permette di eliminare un lotto per emergenze alimentari da parte di admin
     * @param l id int del lotto
     * @return booleano di conferma
     */
    public static boolean eliminaLotto(int l) {
        Lotto lotto = LottoDAO.getLottoById(l);
        if(lotto!=null){
            if(EsposizioneDAO.getEsposizioneByLotto(lotto)!=null){
                LottoDAO.eliminaLotto(lotto);
                EsposizioneDAO.rimuoviScaduto(Objects.requireNonNull(EsposizioneDAO.getEsposizioneByLotto(lotto)));
                return true;
            }
            else {
                LottoDAO.eliminaLotto(lotto);
                return true;
            }
        }
        return false;
    }

    public static boolean modificaFornitura(Fornitura fornitura) {
        return false;
    }

    /**
     * Il metodo <code>visualizzaProdottiMagazzino</code> permette di avere per ogni prodotto tutti i lotti di quel prodotto
     * @return HashMap<Prodotto, ArrayList<Lotto>>, per ogni prodotto si hanno tutti i lotti
     */
    public static HashMap<Prodotto, ArrayList<Lotto>> visualizzaProdottiMagazzino() {
        return LottoDAO.getMagazzino();
    }

    /**
     * Il metodo <code>visualizzaForniture</code> fa visualizzare tutte le forniture avvenute dall'apertura a oggi
     * @return lista di forniture
     */
    public static List<Fornitura> visualizzaForniture() {
        return FornituraDAO.selectAll();
    }

    public static float getSpese(LocalDate dataInizio, LocalDate dataFine) {
        return LottoDAO.getSpese(dataInizio, dataFine);
    }

    /**
     * Il metodo <code>getSpeseTotali</code> calcola tutti i soldi spesi per tutti i lotti dal giorno di apertura del supermercato
     * @return totale
     */
    public static float getSpeseTotali() {
        return LottoDAO.getSpeseTotali();
    }

    /**
     * La funzione <code>getProdottoById</code> cerca il prodotto corrispondente all'id richiesto
     * @param id prodotto da ricercare
     * @return Prodotto trovato nel caso
     */
    public static Prodotto getProdottoById(int id){
        return ProdottoDAO.findProdottoById(id);
    }

    /**
     * Il metodo <code>checkProductExists</code> controlla se e presente già un prodotto nella base dati
     * @param nome nome del prodotto
     * @param marchio marchio del prodotto
     * @return Prodotto oppure null
     */
    public static Prodotto checkProductExists(final String nome, final String marchio){
        return ProdottoDAO.selectByNameAndBrand(nome, marchio);
    }

    public static List<Prodotto> getAllProducts() {
        return ProdottoDAO.selectAll();
    }
}
