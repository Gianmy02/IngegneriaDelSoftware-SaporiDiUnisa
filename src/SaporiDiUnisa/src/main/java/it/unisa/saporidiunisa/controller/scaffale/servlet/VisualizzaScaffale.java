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
import java.util.ArrayList;

/**
 * La servlet <code>VisualizzaScaffale</code> indirizza l'utente alla aggiunta_scaffale.jsp o alla visualizza_scaffale.jsp a seconda della richiesta.
 * Prima di effettuare il dispatch, interroga il DB per recuperare i prodotti dallo scaffale ed eventualmente dal magazzino
 * @author Simone Vittoria
 */
@WebServlet(name = "VisualizzaScaffale", value = "/VisualizzaScaffale")
public class VisualizzaScaffale extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        val session = req.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, req, resp);
            return;
        }

        String address = req.getParameter("address");
        if (address == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("indirizzo"), req, resp);
            return;
        }

        ArrayList<Esposizione> lottiScaffale = ScaffaleController.visualizzaProdottiScaffale();
        req.setAttribute("lottiScaffale", lottiScaffale);

        if(address.equalsIgnoreCase("aggiunta_scaffale")){
            ArrayList<Lotto> lottiMagazzino = ScaffaleController.visualizzaProdottiMagazzino();
            req.setAttribute("lottiMagazzino", lottiMagazzino);
        }
        else if(address.equalsIgnoreCase("visualizza_scaffale"))
        {
            ArrayList<Esposizione> lottiScaffaleScaduti = ScaffaleController.visualizzaProdottiScaffaleScaduti();
            req.setAttribute("lottiScaffaleScaduti", lottiScaffaleScaduti);
        }
        else
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("indirizzo"), req, resp);
            return;
        }

        req.getRequestDispatcher("view/scaffale/" + address + ".jsp").forward(req, resp);
    }
}
