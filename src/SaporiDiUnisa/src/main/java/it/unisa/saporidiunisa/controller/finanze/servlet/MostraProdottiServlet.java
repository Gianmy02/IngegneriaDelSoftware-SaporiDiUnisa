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

@WebServlet(name = "MostraProdottiServlet", value = "/mostra-prodotti-servlet")
public class MostraProdottiServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val session = request.getSession();

        val dipendente = (Dipendente)session.getAttribute("dipendente");
        if (dipendente == null)
        {
            request.setAttribute("message", "Permessi non concessi per questa pagina");
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
            return;
        }

        if (dipendente.getRuolo() != Dipendente.Ruolo.FINANZE)
        {
            request.setAttribute("message", "Permessi non concessi per questa pagina");
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
            return;
        }

        session.setAttribute("prodotti", FinanzeController.visualizzaProdotti());
        request.getRequestDispatcher("view/finanze/visualizzaProdotti.jsp").forward(request, response);

    }
}
