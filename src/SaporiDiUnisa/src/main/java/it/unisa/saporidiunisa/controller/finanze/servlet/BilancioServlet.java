package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
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
        val session = request.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null)
        {
            // TODO: errore
            return;
        }

        if (dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            // TODO: errore
            return;
        }

        val bilancio = FinanzeController.visualizzaBilancio();
        request.setAttribute("bilancio", bilancio);

        request.getRequestDispatcher("/view/finanze/bilancio.jsp").forward(request, response);
    }
}
