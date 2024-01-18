package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Patterns;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

@WebServlet(name = "GetListForniture", value = "/GetListForniture")
public class GetListForniture extends HttpServlet {
    final MagazzinoController magazzinoController = new MagazzinoController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        val dipendente = (Dipendente)req.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE)
        {
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }
        val forniture = magazzinoController.visualizzaForniture();

        val jsonArray = new JSONArray();
        for(val f : forniture){
            val json = new JSONObject();
            json.put("_id", f.getId());
            json.put("giorno", f.getGiorno().format(Patterns.DATE_TIME_FORMATTER));
            val s = new StringBuilder();
            for(val l : f.getLotti())
                s.append(l.toString()).append("\n");
            json.put("lotti", s.toString());
            jsonArray.put(json);
        }
        resp.setContentType("application/json");
        resp.getWriter().write(jsonArray.toString());
    }
}