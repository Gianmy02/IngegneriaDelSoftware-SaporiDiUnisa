package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "MostraProdottoFinanzeServlet", value = "/mostra-prodotto-finanze-servlet")
public class MostraProdottoFinanzeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        val id = request.getParameter("prodotto");
        if(id == null){
            request.setAttribute("message", "Errore sul prodotto richiesto");
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
            return;
        }

        session.setAttribute("prodottoSelezionato", MagazzinoController.getProdottoById(Integer.parseInt(id)));
        request.getRequestDispatcher("view/finanze/impostaSconto.jsp").forward(request, response);
    }
}
