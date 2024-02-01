package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
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

/**
 * La servlet <code>bilancioPeriodoServlet</code> visualizza il bilancio tra due date
 * @author Antonio Facchiano
 */
@WebServlet(name = "bilancioPeriodoServlet", value = "/bilancio-periodo-servlet")
public class BilancioPeriodoServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        val dipendente = (Dipendente)request.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }

        val inizio = request.getParameter("inizio");
        if (inizio == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("data inizio"), request, response);
            return;
        }

        val fine = request.getParameter("fine");
        if (fine == null)
        {
            Utils.dispatchError(Messages.INVALID_FIELD.formatted("data fine"), request, response);
            return;
        }

        val inizioDate = Utils.parseAsLocalDate(inizio);
        if (inizioDate == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("data inizio"), request, response);
            return;
        }

        val fineDate = Utils.parseAsLocalDate(fine);
        if (fineDate == null)
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("data fine"), request, response);
            return;
        }

        if (inizioDate.isAfter(fineDate))
        {
            Utils.dispatchError(Messages.INVALID_FORMAT.formatted("intervallo di date"), request, response);
            return;
        }

        val yesterday = LocalDate.now().minusDays(1);
        if (inizioDate.isAfter(yesterday))
        {
            Utils.dispatchError("La data di inizio non può essere dopo quella di ieri", request, response);
            return;
        }
        if (fineDate.isAfter(yesterday))
        {
            Utils.dispatchError("La data di fine non può essere dopo quella di ieri", request, response);
            return;
        }

        request.setAttribute("dataInizio", inizioDate);
        request.setAttribute("dataFine", fineDate);
        request.setAttribute("bilancio", FinanzeController.visualizzaBilancioParziale(inizioDate, fineDate));
        request.getRequestDispatcher("/view/finanze/bilancio.jsp").forward(request, response);
    }
}
