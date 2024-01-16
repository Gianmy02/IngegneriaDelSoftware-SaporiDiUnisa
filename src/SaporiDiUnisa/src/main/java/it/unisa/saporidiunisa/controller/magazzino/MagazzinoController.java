package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MagazzinoController
{
    public boolean registraFornitura(Fornitura fornitura)
    {
        return false;
    }

    public boolean eliminaLotto(Lotto lotto)
    {
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
        if(nome == null || nome.isEmpty() || nome.isBlank() || nome.length() > 255)
            return "Nome non valido";
        if(marchio == null || marchio.isEmpty() || marchio.isBlank() || marchio.length() > 255)
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
