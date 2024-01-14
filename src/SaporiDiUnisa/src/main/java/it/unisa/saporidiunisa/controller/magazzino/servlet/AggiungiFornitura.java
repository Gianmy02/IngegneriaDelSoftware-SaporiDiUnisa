package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.model.dao.FornituraDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

import java.io.IOException;

@WebServlet(name = "AggiungiFornitura", value = "/AggiungiFornitura")
public class AggiungiFornitura extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // prendo i dati dalla sessione
        final HttpSession session = req.getSession();
        val fornitura = (Fornitura) session.getAttribute("fornitura");

        val lotti = fornitura.getLotti();
        for(var l : lotti){
            val prodotto = l.getProdotto();
            if(prodotto.getId() == 0){  // prodotto nuovo
                ProdottoDAO.insert(prodotto);
            }
            else{
                // controllo se il prezzo inserito è almeno il doppio di quello attuale
                float prezzoAttuale = prodotto.getPrezzo();
                float prezzoInserito = l.getCosto() / l.getQuantita();
                if((prezzoInserito * 2) > prezzoAttuale){
                    ProdottoDAO.updatePrice(prezzoInserito * 2, prodotto.getId());
                }
            }
            LottoDAO.insert(l);
        }

        FornituraDAO.insert(fornitura);
        session.removeAttribute("fornitura");

        // TODO
        req.getRequestDispatcher(".").forward(req, resp);
    }
}
