package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MagazzinoController
{
    public boolean registraFornitura(Fornitura fornitura)
    {
        return false;
    }

    public static boolean eliminaLotto(int l)
    {
        Lotto lotto = LottoDAO.getLottoById(l);
        if(lotto!=null){
            if(EsposizioneDAO.getEsposizioneByLotto(lotto)!=null){
                LottoDAO.eliminaLotto(lotto);
                EsposizioneDAO.rimuoviScaduto(Objects.requireNonNull(EsposizioneDAO.getEsposizioneByLotto(lotto)));
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean modificaFornitura(Fornitura fornitura)
    {
        return false;
    }

    public HashMap<Prodotto, ArrayList<Lotto>> visualizzaProdottiMagazzino()
    {
        return null;
    }

    public List<Fornitura> visualizzaForniture()
    {
        return null;
    }

    public float getSpese(LocalDate dataInizio, LocalDate dataFine)
    {
        return 0;
    }

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

    public Prodotto checkProductExists(final String nome, final String marchio){
        return ProdottoDAO.selectByNameAndBrand(nome, marchio);
    }

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
