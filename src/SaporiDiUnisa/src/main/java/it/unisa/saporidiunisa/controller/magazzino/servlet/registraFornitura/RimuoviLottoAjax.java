package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

import java.io.IOException;

/**
 * Servlet, invocata tramite AJAX, che gestisce la rimozione di un lotto dalla fornitura che si sta inserendo
 */
@WebServlet(name = "RimuoviLottoAjax", value = "/RimuoviLottoAjax")
public class RimuoviLottoAjax extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val keyLotto = Integer.parseInt(req.getParameter("key"));

        final HttpSession session = req.getSession();
        val fornitura = (Fornitura) session.getAttribute("fornitura");

        val lotti = fornitura.getLotti();
        for(val l : lotti){
            if(l.getId() == keyLotto){
                lotti.remove(l);
                break;
            }
        }

        fornitura.setLotti(lotti);
        session.setAttribute("fornitura", fornitura);

        resp.sendRedirect("/view/registraFornitura.jsp");
    }
}
