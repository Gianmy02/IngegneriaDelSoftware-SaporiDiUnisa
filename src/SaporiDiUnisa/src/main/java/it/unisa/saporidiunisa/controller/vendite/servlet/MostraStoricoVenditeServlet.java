package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Venduto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "MostraStoricoVenditeServlet", value = "/MostraStoricoVenditeServlet")
public class MostraStoricoVenditeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if(d != null && d.getRuolo() == Dipendente.Ruolo.CASSIERE) {
            val inizio = req.getParameter("inizio");
            if (inizio == null)
            {
                req.setAttribute("message", "Data Inizio non inserita");
                req.getRequestDispatcher("view/error.jsp").forward(req, resp);
                return;
            }

            val fine = req.getParameter("fine");
            if (fine == null)
            {
                req.setAttribute("message", "Data Fine Sconto non inserita");
                req.getRequestDispatcher("view/error.jsp").forward(req, resp);
                return;
            }

            val inizioDate = LocalDate.parse(inizio);
            val fineDate = LocalDate.parse(fine);
            if (inizioDate.isAfter(fineDate))
            {
                req.setAttribute("message", "Le 2 date non sono giuste");
                req.getRequestDispatcher("view/error.jsp").forward(req, resp);
                return;
            }

            ArrayList<Venduto> v = VenditaController.visualizzaStoricoVendite(inizioDate, fineDate);
            session.setAttribute("prodotti", v);
            req.setAttribute("dataInizio", inizioDate);
            req.setAttribute("dataFine", fineDate);
            req.getRequestDispatcher("view/cassiere/mostraStoricoVendite.jsp").forward(req, resp);
        }else{
                req.setAttribute("message", "Permessi non concessi per questa pagina");
                req.getRequestDispatcher("view/error.jsp").forward(req, resp);
        }
    }
}

