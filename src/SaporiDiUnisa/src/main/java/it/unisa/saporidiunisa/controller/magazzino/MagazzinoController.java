package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.FornituraDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Salvatore Ruocco
 * La classe <code>MagazzinoController</code> funge da tramite fra il database e le servlet per le operazioni sul magazzino
 */
public class MagazzinoController
{
    /**
     * Il metodo <code>registraFornitura</code> registra una nuova fornitura del giorno nel db
     * @param fornitura fornitura inserita in input
     * @return booleano di conferma
     */
    public boolean registraFornitura(final Fornitura fornitura)
    {
        FornituraDAO.insert(fornitura);
        val idFornitura = FornituraDAO.getLastId();

        val lotti = fornitura.getLotti();
        for(var l : lotti){
            l.getFornitura().setId(idFornitura);

            val prodotto = l.getProdotto();
            if(ProdottoDAO.selectByNameAndBrand(prodotto.getNome(), prodotto.getMarchio()) == null){  // prodotto nuovo
                ProdottoDAO.insert(prodotto);
                prodotto.setId(ProdottoDAO.getLastId());
            }
            else{
                // controllo se il prezzo inserito è almeno il doppio di quello attuale
                float prezzoAttuale = prodotto.getPrezzo();
                float prezzoInserito = l.getCosto() / l.getQuantita();
                if((prezzoInserito * 2) > prezzoAttuale){
                    ProdottoDAO.updatePrice(prezzoInserito * 2, prodotto.getId());
                }
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
    public static boolean eliminaLotto(int l)
    {
        Lotto lotto = LottoDAO.getLottoById(l);
        if(lotto!=null){
            if(EsposizioneDAO.getEsposizioneByLotto(lotto)!=null){
                LottoDAO.eliminaLotto(lotto);
                EsposizioneDAO.rimuoviScaduto(Objects.requireNonNull(EsposizioneDAO.getEsposizioneByLotto(lotto)));
                return true;
            }
            else
            {
                LottoDAO.eliminaLotto(lotto);
                return true;
            }
        }
        return false;
    }

    public boolean modificaFornitura(Fornitura fornitura)
    {
        return false;
    }

    /**
     * Il metodo <code>visualizzaProdottiMagazzino</code> permette di avere per ogni prodotto tutti i lotti di quel prodotto
     * @return HashMap<Prodotto, ArrayList<Lotto>>, per ogni prodotto si hanno tutti i lotti
     */
    public static HashMap<Prodotto, ArrayList<Lotto>> visualizzaProdottiMagazzino()
    {
        return LottoDAO.getMagazzino();
    }

    /**
     * Il metodo <code>visualizzaForniture</code> fa visualizzare tutte le forniture avvenute dall'apertura a oggi
     * @return lista di forniture
     */
    public List<Fornitura> visualizzaForniture()
    {
        return null;
    }

    public float getSpese(LocalDate dataInizio, LocalDate dataFine)
    {
        return 0;
    }

    /**
     * Il metodo <code>getSpeseTotali</code> calcola tutti i soldi spesi per tutti i lotti dal giorno di apertura del supermercato
     * @return totale
     */
    public static float getSpeseTotali()
    {
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
    public Prodotto checkProductExists(final String nome, final String marchio){
        return ProdottoDAO.selectByNameAndBrand(nome, marchio);
    }

    /**
     * Il metodo <code>lottoValidation</code> controlla se per tutti campi inseriti di un lotto non ci siano errori
     * @param nome stringa, nome del prodotto
     * @param marchio stringa, marchio del prodotto
     * @param prezzo costo del lotto, float
     * @param quantita quantita del lotto, int
     * @param dataScadenza data di scadenza del lotto, LocalDate
     * @return stringa di errore nel caso
     */
    public String lottoValidation(final String nome, final String marchio, final float prezzo, final int quantita, final LocalDate dataScadenza){
        if(nome == null || nome.isBlank() || nome.length() > 255 || nome.length() < 2)
            return "Nome non valido";
        if(marchio == null || marchio.isBlank() || marchio.length() > 255 || marchio.length() < 2)
            return "Marchio non valido";
        if(prezzo <= 0)
            return "Prezzo non valido";
        if(quantita <= 0)
            return "Quantità non valida";
        if(dataScadenza == null || dataScadenza.isBefore(LocalDate.now()))
            return "Data scadenza non valida";

        // non controllo la foto inserita

        return null;
    }


}
