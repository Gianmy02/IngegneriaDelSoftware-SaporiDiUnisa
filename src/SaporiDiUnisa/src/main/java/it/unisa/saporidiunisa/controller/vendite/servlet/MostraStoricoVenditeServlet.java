package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Venduto;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.val;

/**
 * La servlet <code>MostraStoricoVenditeServlet</code> restituisce lo storico delle vendite in un periodo scelto settando la sessione
 * @author Gianmarco Riviello
 */

@WebServlet(name = "MostraStoricoVenditeServlet", value = "/MostraStoricoVenditeServlet")
public class MostraStoricoVenditeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if (d == null || d.getRuolo() != Dipendente.Ruolo.CASSIERE) {
            Utils.dispatchError(Messages.NO_PERMISSIONS, req, resp);
            return;
        }
        val inizio = req.getParameter("inizio");
        if (inizio == null) {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("data inizio"), req, resp);
            return;
        }

        val fine = req.getParameter("fine");
        if (fine == null) {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("data fine"), req, resp);
            return;
        }

        val inizioDate = Utils.parseAsLocalDate(inizio);
        if (inizioDate == null) {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("data inizio"), req, resp);
            return;
        }

        val fineDate = Utils.parseAsLocalDate(fine);
        if (fineDate == null) {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("data fine"), req, resp);
            return;
        }

        if (inizioDate.isAfter(fineDate)) {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("intervallo di date"), req, resp);
            return;
        }

        if (inizioDate.isAfter(LocalDate.now().minusDays(1))) {
            Utils.dispatchError("La data di inizio non può essere dopo quella di ieri", req, resp);
            return;
        }

        if (fineDate.isAfter(LocalDate.now().minusDays(1))) {
            Utils.dispatchError("La data di fine non può essere dopo quella di ieri", req, resp);
            return;
        }


        ArrayList<Venduto> v = VenditaController.visualizzaStoricoVendite(inizioDate, fineDate);
        session.setAttribute("prodotti", v);
        req.setAttribute("dataInizio", inizioDate);
        req.setAttribute("dataFine", fineDate);
        req.getRequestDispatcher("view/cassiere/mostraStoricoVendite.jsp").forward(req, resp);
    }
}

