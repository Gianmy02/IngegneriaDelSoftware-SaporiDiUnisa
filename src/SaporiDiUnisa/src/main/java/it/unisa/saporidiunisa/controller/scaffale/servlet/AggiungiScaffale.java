package it.unisa.saporidiunisa.controller.scaffale.servlet;

import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.util.ArrayList;


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

        int qntScaffale = 0;

        for(Esposizione e : lottiScaffale) {
            try {
                if (Integer.parseInt(req.getParameter("qntScaffale" + e.getLotto().getId())) != 0) {
                    qntScaffale = Integer.parseInt(req.getParameter("qntScaffale" + e.getLotto().getId()));
                    if (qntScaffale <= e.getLotto().getQuantitaAttuale() && qntScaffale > 0) {
                        ScaffaleController.aumentaEsposizione(qntScaffale, e);
                        ScaffaleController.diminuisciLotto(e.getLotto().getId(), qntScaffale);
                    }
                }
            }
            catch (NumberFormatException ex)
            {
                Utils.dispatchError(Messages.INVALID_FORMAT.formatted("qnt Scaffale"), req, resp);
                return;
            }
        }

        for(Lotto l : lottiMagazzino) {
            try {
                if (Integer.parseInt(req.getParameter("qntMagazzino" + l.getId())) != 0) {
                    qntScaffale = Integer.parseInt(req.getParameter("qntMagazzino" + l.getId()));
                    if (qntScaffale <= l.getQuantitaAttuale() && qntScaffale > 0) {
                        ScaffaleController.inserisciEsposizione(qntScaffale, l);
                        ScaffaleController.diminuisciLotto(l.getId(), qntScaffale);
                    }
                }
            }
            catch (NumberFormatException ex)
            {
                Utils.dispatchError(Messages.INVALID_FORMAT.formatted("qnt Magazzino"), req, resp);
                return;
            }
        }

        lottiMagazzino = ScaffaleController.visualizzaProdottiMagazzino();
        lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();
        req.setAttribute("lottiMagazzino", lottiMagazzino);
        req.setAttribute("lottiScaffale", lottiScaffale);

        RequestDispatcher dispatcher = req.getRequestDispatcher("view/scaffale/aggiunta_scaffale.jsp");
        dispatcher.forward(req, resp);
    }

}
