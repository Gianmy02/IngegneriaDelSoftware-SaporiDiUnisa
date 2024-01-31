package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

/**
 * @author Salvatore Ruocco
 * Servlet, invocata tramite AJAX, che gestisce la rimozione di un lotto dalla fornitura che si sta inserendo
 */
@WebServlet(name = "RimuoviLotto", value = "/RimuoviLotto")
public class RimuoviLotto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        val keyId = Integer.parseInt(req.getParameter("keyId"));

        final HttpSession session = req.getSession();
        val fornitura = (Fornitura) session.getAttribute("fornitura");

        val lotti = fornitura.getLotti();
        for(val l : lotti){
            if(l.getId() == keyId){
                lotti.remove(l);
                // Aggiorno la fornitura in sessione
                fornitura.setLotti(lotti);
                session.setAttribute("fornitura", fornitura);
                break;
            }
        }
    }
}
