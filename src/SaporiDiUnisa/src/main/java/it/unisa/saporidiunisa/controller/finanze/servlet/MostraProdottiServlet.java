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

@WebServlet(name = "mostraProdottiServlet", value = "/mostra-prodotti-servlet")
public class MostraProdottiServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        val session = request.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, request, response);
            return;
        }

        session.setAttribute("prodotti", FinanzeController.visualizzaProdotti());
        request.getRequestDispatcher("view/finanze/visualizzaProdotti.jsp").forward(request, response);
    }
}