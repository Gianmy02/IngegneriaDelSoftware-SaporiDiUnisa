package it.unisa.saporidiunisa.controller.finanze;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Bilancio;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;

import java.time.LocalDate;
import java.util.ArrayList;

public class FinanzeController
{
    public Bilancio visualizzaBilancio()
    {
        LottoDAO ldao = new LottoDAO();
        ArrayList<Lotto> lotti = ldao.getPerditeTotali();
        float perdite = 0;
        for(Lotto l : lotti){
            perdite += l.getCostoProdotto()*l.getQuantitaAttuale();
        }
        Bilancio b = new Bilancio();
        b.setPerdite(perdite);
        VenditaController vc = new VenditaController();
        b.setGuadagno(vc.getGuadagniTotali());
        b.setIncasso(vc.getIncassiTotali());
        MagazzinoController mc =new MagazzinoController();
        b.setSpese(mc.getSpeseTotali());
        return b;
    }

    public ArrayList<Integer> visualizzaAndamentoProdotto(LocalDate dataInizio, LocalDate dataFine, Prodotto prodotto)
    {
        if (dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine))
        {

        }

        return null;
    }

    public boolean impostaSconto(Prodotto prodotto, int sconto, LocalDate dataInizio, LocalDate dataFine)
    {
        if (!prodotto.isSconto())
        {

        }

        return false;
    }

    public static ArrayList<Prodotto> visualizzaProdotti()
    {
        return ProdottoDAO.selectAll();
    }
}
