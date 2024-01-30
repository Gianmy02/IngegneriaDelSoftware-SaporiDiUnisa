package it.unisa.saporidiunisa.controller.scaffale.servlet;

import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


@WebServlet(name = "AggiungiScaffale", value = "/AggiungiScaffale")
public class AggiungiScaffale extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        val session = req.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, req, resp);
            return;
        }

        ArrayList<Lotto> lottiMagazzino = ScaffaleController.visualizzaProdottiMagazzino();
        ArrayList<Esposizione> lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();

        HashMap<Lotto, Integer> lottiMagazzinoValid = new HashMap<>();
        HashMap<Esposizione, Integer> lottiScaffaleValid = new HashMap<>();


        int qntScaffale;

        for(Esposizione e : lottiScaffale) {
            if(e.getLotto().getDataScadenza().isAfter(LocalDate.now()))
            {
                try {
                    if (Integer.parseInt(req.getParameter("qntScaffale" + e.getLotto().getId())) != 0) {
                        qntScaffale = Integer.parseInt(req.getParameter("qntScaffale" + e.getLotto().getId()));
                        if (qntScaffale < 0) {
                            Utils.dispatchError("la quantità scaffale è minore di 0", req, resp);
                            return;
                        } else if (qntScaffale >= 1000000) {
                            Utils.dispatchError("la quantità scaffale è maggiore della massima consentita", req, resp);
                            return;
                        } else if (qntScaffale > e.getLotto().getQuantitaAttuale()) {
                            Utils.dispatchError("la quantità scaffale è maggiore della quantità presente in magazzino", req, resp);
                            return;
                        } else if (qntScaffale > 0) {
                            lottiScaffaleValid.put(e, qntScaffale);
                        }
                    }
                } catch (NumberFormatException ex) {
                    Utils.dispatchError(Messages.INVALID_FORMAT.formatted("quantità scaffale"), req, resp);
                    return;
                }
            }
        }

        for(Lotto l : lottiMagazzino) {
            try
            {
                if (Integer.parseInt(req.getParameter("qntMagazzino" + l.getId())) != 0) {
                    qntScaffale = Integer.parseInt(req.getParameter("qntMagazzino" + l.getId()));
                    if(qntScaffale < 0)
                    {
                        Utils.dispatchError("la quantità del prodotto non esposto è minore di 0", req, resp);
                        return;
                    }
                    else if(qntScaffale >= 1000000)
                    {
                        Utils.dispatchError("la quantità del prodotto non esposto è maggiore della massima consentita", req, resp);
                        return;
                    }
                    else if(qntScaffale > l.getQuantitaAttuale())
                    {
                        Utils.dispatchError("la quantità del prodotto non esposto è maggiore della quantità presente in magazzino", req, resp);
                        return;
                    }
                    else if (qntScaffale > 0) {
                        lottiMagazzinoValid.put(l, qntScaffale);
                    }
                }
            }
            catch (NumberFormatException ex)
            {
                Utils.dispatchError(Messages.INVALID_FORMAT.formatted("quantità magazzino"), req, resp);
                return;
            }
        }


        for (HashMap.Entry<Esposizione, Integer> entry : lottiScaffaleValid.entrySet()) {
            Esposizione esposizione = entry.getKey();
            Integer quantita = entry.getValue();

            ScaffaleController.aumentaEsposizione(quantita, esposizione);
            ScaffaleController.diminuisciLotto(esposizione.getLotto().getId(), quantita);
        }


        for (HashMap.Entry<Lotto, Integer> entry : lottiMagazzinoValid.entrySet()) {
            Lotto lotto = entry.getKey();
            Integer quantita = entry.getValue();

            ScaffaleController.inserisciEsposizione(quantita, lotto);
            ScaffaleController.diminuisciLotto(lotto.getId(), quantita);
        }


        Utils.dispatchSuccess(Messages.SUCCESS, req, resp);

    }

}
