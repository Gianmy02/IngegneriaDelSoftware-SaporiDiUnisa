package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.controller.finanze.FinanzeController;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;


import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "ImpostaScontoServlet", value = "/imposta-sconto-servlet")
public class ImpostaScontoServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if(d!= null && (d.getRuolo() == Dipendente.Ruolo.FINANZE)){
            LocalDate dataInizioSconto = LocalDate.parse(req.getParameter("dataInizioSconto"));
            LocalDate dataFineSconto = LocalDate.parse(req.getParameter("dataFineSconto"));
            if(!dataFineSconto.isAfter(dataInizioSconto)){
                //TODO: dispatch alla pagina di errore
            }
            int sconto = Integer.parseInt(req.getParameter("sconto"));
            if(sconto <1 || sconto > 100){
                //TODO: dispatch alla pagina di errore
            }
            int id = Integer.parseInt(req.getParameter("prodotto"));
            Prodotto p = MagazzinoController.getProdottoById(id);
            if(FinanzeController.impostaSconto(p, sconto, dataInizioSconto, dataFineSconto))
            {
                //TODO: dispatch alla pagina di conferma
            }else{
                //TODO: dispatch alla pagina di errore
            }
        }else {
            //TODO: dispatch alla pagina di errore
        }
    }
}
