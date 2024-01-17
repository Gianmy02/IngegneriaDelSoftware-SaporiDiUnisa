package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Salvatore Ruocco
 * Servlet, invocata tramite AJAX, che ritorna l'oggetto fornitura in sessione
 */
@WebServlet(name = "GetFornituraSession", value = "/GetFornituraSession")
public class GetFornituraSession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final HttpSession session = req.getSession();
        val fornitura = (Fornitura) session.getAttribute("fornitura");

        JSONArray jsonArray = new JSONArray();
        for(var l : fornitura.getLotti()){
            JSONObject json = new JSONObject();
            json.put("nome", l.getProdotto().getNome());
            json.put("marchio", l.getProdotto().getMarchio());
            json.put("prezzo", l.getCosto() / l.getQuantita());
            json.put("quantita", l.getQuantita());
            json.put("dataScadenza", l.getDataScadenza().toString());
            jsonArray.put(json);
        }
        resp.setContentType("application/json");
        resp.getWriter().write(jsonArray.toString());
    }
}
