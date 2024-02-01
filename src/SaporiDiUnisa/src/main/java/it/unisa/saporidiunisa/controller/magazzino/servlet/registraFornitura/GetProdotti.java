package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

/**
 * Servlet, invocata tramite AJAX, che ritorna tutti i prodotti presenti nel db
 * @author Salvatore Ruocco
 */
@WebServlet(name = "GetProdotti", value = "/GetProdotti")
public class GetProdotti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        val prodotti = MagazzinoController.getAllProducts();

        val jsonArray = new JSONArray();
        for(val p : prodotti){
            val jsonObject = new JSONObject();
            jsonObject.put("nome", p.getNome());
            jsonObject.put("marchio", p.getMarchio());
            jsonArray.put(jsonObject);
        }
        resp.setContentType("application/json");
        resp.getWriter().write(jsonArray.toString());
    }
}
