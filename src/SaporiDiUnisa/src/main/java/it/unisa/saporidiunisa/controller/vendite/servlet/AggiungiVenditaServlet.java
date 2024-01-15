package it.unisa.saporidiunisa.controller.vendite.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.entity.Venduto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AggiungiVenditaServlet", value = "/AggiungiVenditaServlet")
public class AggiungiVenditaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Dipendente d = (Dipendente) session.getAttribute("dipendente");
        if(d != null && d.getRuolo() == Dipendente.Ruolo.CASSIERE){
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> saleDataList = mapper.readValue(sb.toString(), new TypeReference<List<Map<String, Object>>>(){});
            ArrayList<Venduto> selezionati = new ArrayList<>();

            for (Map<String, Object> saleData : saleDataList) {
                int productId = (int) saleData.get("productId"); // Modifica il tipo a int
                Venduto v = new Venduto();
                v.setProdotto(MagazzinoController.getProdottoById(productId));
                v.setQuantita((int) saleData.get("quantity"));
                v.setCosto((float) saleData.get("price"));
                System.out.println(v.toString());
                selezionati.add(v);
            }
            VenditaController.addGiornoVendite();
            if(VenditaController.venditaProdotti(selezionati)){
                req.getRequestDispatcher("view/vendita.jsp").forward(req, resp);
            }else {
                //TODO: errore nella vendita
            }
        }
        else{
            //TODO: errore da gestire, mandare ad una pagina di errore
        }

    }
}