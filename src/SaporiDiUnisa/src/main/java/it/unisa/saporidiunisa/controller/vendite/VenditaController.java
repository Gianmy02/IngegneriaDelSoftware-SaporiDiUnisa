package it.unisa.saporidiunisa.controller.vendite;

import it.unisa.saporidiunisa.model.dao.GestioneScaffaliDAO;
import it.unisa.saporidiunisa.model.dao.GestioneVenditeDAO;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.entity.Venduto;

import java.time.LocalDate;
import java.util.ArrayList;

public class VenditaController
{
    public boolean venditaProdotti(ArrayList<Venduto> venduti)
    {
        GestioneVenditeDAO vdao = new GestioneVenditeDAO();
        GestioneScaffaliDAO gdao = new GestioneScaffaliDAO();
        boolean b = false;
        /*itero sulla lista di prodotti che si vogliono acquistare*/
        for(Venduto v: venduti){
            float guadagno = 0;
                ArrayList<Esposizione> e = gdao.getLottibyProdottoWithoutScaduti(v.getProdotto()); //prendo tutti i prodotti in esposizione del prodotto specificato
                int quantitaCount = v.getQuantita();
                //itero per la quantita di prodotti da comprare
                for(Esposizione es: e) {
                    //se la quantita richiesta e maggiore di quella esposta del lotto preso in questione si continua
                    if(quantitaCount>es.getQuantita()) {
                        guadagno += (v.getCosto() - es.getLotto().getCostoProdotto()) * es.getQuantita();
                        quantitaCount = quantitaCount- es.getQuantita();
                        vdao.diminuisciEsposizione(es.getQuantita(), es);
                    }
                    //se e minore o ugualela quantita esposta di quel lotto va bene e quindi sarà l'ultima
                    else {
                        guadagno += (v.getCosto() - es.getLotto().getCostoProdotto()) * v.getQuantita();
                        vdao.diminuisciEsposizione(v.getQuantita(), es);
                        break;
                    }
                }
                v.setGuadagno(guadagno);
                /*salva nel db i nuovi venduti del prodotto,
            vedo se al giorno d'oggi ci sono state altre vendite del prodotto*/
                Venduto attuale = vdao.getVendutiGiornalieroByProdotto(v.getProdotto());
                if(attuale!=null){
                    b = vdao.doUpdateVendita(v);
                }else {
                    b = vdao.doSaveVendita(v);
                }
        }
        return b;
    }

    public ArrayList<Venduto> visualizzaStoricoVendite(LocalDate dataInizio, LocalDate dataFine)
    {
        return null;
    }

    public float getIncassi(LocalDate dataInizio, LocalDate dataFine)
    {
        return 0;
    }

    public float getIncassiTotali()
    {
        return 0;
    }
}
