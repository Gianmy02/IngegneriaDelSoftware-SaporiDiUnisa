package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet, invocata tramite AJAX, che elimina l'oggetto fornitura in sessione
 * @author Salvatore Ruocco
 */
@WebServlet(name = "ResetFornituraSession", value = "/ResetFornituraSession")
public class ResetFornituraSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("fornitura");
    }
}
