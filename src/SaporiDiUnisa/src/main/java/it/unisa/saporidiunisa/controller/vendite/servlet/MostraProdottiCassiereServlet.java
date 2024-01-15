package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MostraProdottiCassiereServlet", value = "/MostraProdottiCassiereServlet")
public class MostraProdottiCassiereServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if(d != null && d.getRuolo() == Dipendente.Ruolo.CASSIERE){
            VenditaController vc = new VenditaController();
            ArrayList<Esposizione> esposti = vc.visualizzaProdottiEsposti();
            session.setAttribute("prodottiEsposti", esposti);
        }
        else{
            //errore da gestire, mandare ad una pagina di errore TODO
        }

        req.getRequestDispatcher("view/vendita.jsp").forward(req, resp);
    }
}
