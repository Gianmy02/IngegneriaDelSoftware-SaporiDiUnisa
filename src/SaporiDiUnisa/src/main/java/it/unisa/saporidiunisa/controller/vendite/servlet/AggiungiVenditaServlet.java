package it.unisa.saporidiunisa.controller.vendite.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Venduto;
import it.unisa.saporidiunisa.utils.Errors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AggiungiVenditaServlet", value = "/AggiungiVenditaServlet")
public class AggiungiVenditaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if (d != null && d.getRuolo() == Dipendente.Ruolo.CASSIERE) {
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> saleDataList = mapper.readValue(sb.toString(), new TypeReference<>() {});
            ArrayList<Venduto> selezionati = new ArrayList<>();


            for (Map<String, Object> saleData : saleDataList) {
                int productId = (int) saleData.get("productId"); // Modifica il tipo a int
                Venduto v = new Venduto();
                v.setProdotto(MagazzinoController.getProdottoById(productId));
                v.setGiorno(LocalDate.now());
                v.setQuantita((int) saleData.get("quantity"));
                if (saleData.get("price") instanceof Number) {
                    v.setCosto(((Number) saleData.get("price")).floatValue());
                } else if (saleData.get("price") instanceof String) {
                    try {
                        v.setCosto(Float.parseFloat((String) saleData.get("price")));
                    } catch (NumberFormatException e) {
                        // Gestione dell'eccezione in caso di errore di conversione
                        System.err.println("Errore durante la conversione del prezzo a float: " + e.getMessage());
                        // Assegna un valore di default o gestisci l'errore a seconda delle tue esigenze
                        v.setCosto(0);
                    }
                } else {
                    // Gestione di altri casi in cui il valore "price" non può essere convertito a float
                    System.err.println("Errore: il valore 'price' non è di tipo numerico o stringa.");
                    // Assegna un valore di default o gestisci l'errore a seconda delle tue esigenze
                    v.setCosto(0);
                }
                selezionati.add(v);
            }
            VenditaController.addGiornoVendite();
            if (VenditaController.venditaProdotti(selezionati)) {
                req.getRequestDispatcher("view/cassiere/vendita.jsp").forward(req, resp);
            } else {
                val json = new JSONObject();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                json.put("errors", String.format(Errors.GENERIC, "venduto"));
                resp.setContentType("application/json");
                resp.getWriter().write(String.valueOf(json));
                /*
                req.setAttribute("message", "Vendita non riuscita");
                req.getRequestDispatcher("view/error.jsp").forward(req, resp);*/
            }
        } else {
            req.setAttribute("message", "Permessi non concessi per questa pagina");
            req.getRequestDispatcher("view/error.jsp").forward(req, resp);
        }

    }
}