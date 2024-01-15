package it.unisa.saporidiunisa.controller.magazzino;

import it.unisa.saporidiunisa.model.dao.LottoDAO;
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
        LottoDAO ldao= new LottoDAO();
        return ldao.getSpeseTotali();
    }
}
