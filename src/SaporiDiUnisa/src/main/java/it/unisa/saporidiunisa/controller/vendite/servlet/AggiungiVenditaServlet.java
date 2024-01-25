package it.unisa.saporidiunisa.controller.vendite.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Venduto;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

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
        if (d == null || d.getRuolo() != Dipendente.Ruolo.CASSIERE) {
            Utils.dispatchError(Messages.NO_PERMISSIONS, req, resp);
            return;
        }
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> saleDataList = mapper.readValue(sb.toString(), new TypeReference<>() {
        });
        ArrayList<Venduto> selezionati = new ArrayList<>();
        for (Map<String, Object> saleData : saleDataList) {
            int productId = (int) saleData.get("productId");
            Venduto v = new Venduto();
            val p = MagazzinoController.getProdottoById(productId);
            if (p == null) {
                Utils.sendMessage(Messages.INVALID_FIELD.formatted("prodotto"), resp);
                return;
            }
            v.setProdotto(MagazzinoController.getProdottoById(productId));
            v.setGiorno(LocalDate.now());
            int quantita = (int) saleData.get("quantity");
            if (quantita <= 0) {
                Utils.sendMessage("La quantità inserita è sotto il limite consentito", resp);
                return;
            }
            if (quantita >= 100000) {
                Utils.sendMessage("La quantità inserita è sopra il limite consentito", resp);
                return;
            }

            if (ScaffaleController.getEspostiByProdotto(p) - quantita < 0) {
                Utils.sendMessage("La quantità inserita è maggiore della quantità esposta", resp);
                return;
            }
            v.setQuantita(quantita);
            if (saleData.get("price") instanceof Number) {
                if (((Number) saleData.get("price")).floatValue() < 0) {
                    Utils.sendMessage("Il prezzo è sotto il limite consentito", resp);
                    return;
                }

                if (((Number) saleData.get("price")).floatValue() >= 100000) {
                    Utils.sendMessage("Il prezzo è sopra il limite consentito", resp);
                    return;
                }
                v.setCosto(((Number) saleData.get("price")).floatValue());
            } else if (saleData.get("price") instanceof String) {
                try {
                    if (Float.parseFloat((String) saleData.get("price")) < 0) {
                        Utils.sendMessage("Il prezzo è sotto il limite consentito", resp);
                        return;
                    }

                    if (Float.parseFloat((String) saleData.get("price")) >= 100000) {
                        Utils.sendMessage("Il prezzo è sopra il limite consentito", resp);
                        return;
                    }
                    v.setCosto(Float.parseFloat((String) saleData.get("price")));

                } catch (NumberFormatException e) {
                    Utils.sendMessage(Messages.INVALID_FORMAT.formatted("prezzo"), resp);
                    return;
                }
            } else {
                Utils.sendMessage(Messages.INVALID_FORMAT.formatted("prezzo"), resp);
                return;
            }
            selezionati.add(v);
        }
        VenditaController.addGiornoVendite();
        if (VenditaController.venditaProdotti(selezionati)) {
            req.getRequestDispatcher("view/cassiere/vendita.jsp").forward(req, resp);
        } else {
            Utils.sendMessage(Messages.FAIL, resp);
        }
    }
}