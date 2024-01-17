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

@WebServlet(name = "bilancioServet", value = "/bilancio-servlet")
public class BilancioServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        val dipendente = (Dipendente)request.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, request, response);
            return;
        }

        request.setAttribute("bilancio", FinanzeController.visualizzaBilancio());
        request.getRequestDispatcher("/view/finanze/bilancio.jsp").forward(request, response);
    }
}
