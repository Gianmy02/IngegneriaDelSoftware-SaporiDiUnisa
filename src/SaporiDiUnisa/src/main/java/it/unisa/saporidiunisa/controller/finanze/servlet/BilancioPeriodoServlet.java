package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "BilancioPeriodoServlet", value = "/bilancio-periodo-servlet")
public class BilancioPeriodoServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        val dipendente = (Dipendente)req.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }

        val inizio = req.getParameter("inizio");
        if (inizio == null) {
            Utils.dispatchError(Errors.INVALID_FIELD.formatted("data inizio"), req, resp);
            return;
        }

        val fine = req.getParameter("fine");
        if (fine == null) {
            Utils.dispatchError(Errors.INVALID_FIELD.formatted("data fine"), req, resp);
            return;
        }

        val inizioDate = Utils.parseAsLocalDate(inizio);
        if(inizioDate == null){
            Utils.dispatchError(Errors.INVALID_FORMAT.formatted("data inizio"), req, resp);
            return;
        }

        val fineDate = Utils.parseAsLocalDate(fine);
        if(fineDate == null){
            Utils.dispatchError(Errors.INVALID_FORMAT.formatted("data fine"), req, resp);
            return;
        }

        if (inizioDate.isAfter(fineDate)) {
            Utils.dispatchError(Errors.INVALID_FORMAT.formatted("intervallo di date"), req, resp);
            return;
        }

        if(inizioDate.isAfter(LocalDate.now().minusDays(1))){
            Utils.dispatchError("La data di inizio non può essere dopo quella di ieri", req, resp);
            return;
        }

        if(fineDate.isAfter(LocalDate.now().minusDays(1))){
            Utils.dispatchError("La data di fine non può essere dopo quella di ieri", req, resp);
            return;
        }
        req.setAttribute("dataInizio", inizioDate);
        req.setAttribute("dataFine", fineDate);
        req.setAttribute("bilancio", FinanzeController.visualizzaBilancioParziale(inizioDate, fineDate));
        req.getRequestDispatcher("/view/finanze/bilancio.jsp").forward(req, resp);
    }
}
