package it.unisa.saporidiunisa.controller.vendite.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.controller.vendite.VenditaController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Venduto;
import it.unisa.saporidiunisa.utils.Messages;
import it.unisa.saporidiunisa.utils.Patterns;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gianmarco Riviello
 * La servlet <code>aggiungiVenditaServlet</code> funge con AJAX e aggiunge una vendita al db
 */
@WebServlet(name = "aggiungiVenditaServlet", value = "/aggiungi-vendita-servlet")
public class AggiungiVenditaServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        val dipendente = (Dipendente)request.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.CASSIERE)
        {
            Utils.dispatchError(Messages.NO_PERMISSIONS, request, response);
            return;
        }

        val reader = request.getReader();
        val sb = new StringBuilder();
        var line = (String)null;
        while ((line = reader.readLine()) != null)
            sb.append(line);

        val mapper = new ObjectMapper();
        List<Map<String, Object>> saleDataList;
        try {
            saleDataList = (mapper.readValue(sb.toString(), new TypeReference<>() {
            }));
        }catch (Exception e){
            Utils.sendMessage(Messages.INVALID_FIELD.formatted("quantita o prezzo"), response);
            return;
        }
        val selezionati = new ArrayList<Venduto>();
        for (val saleData : saleDataList)
        {
            val productId = (int)saleData.get("productId");
            val venduto = new Venduto();
            val p = MagazzinoController.getProdottoById(productId);
            if (p == null)
            {
                Utils.sendMessage(Messages.INVALID_FIELD.formatted("prodotto"), response);
                return;
            }

            venduto.setProdotto(p);
            venduto.setGiorno(LocalDate.now());
            val quantita = (int) saleData.get("quantity");
            if (quantita <= 0)
            {
                Utils.sendMessage("La quantità inserita è sotto il limite consentito", response);
                return;
            }

            if (quantita >= 1000000)
            {
                Utils.sendMessage("La quantità inserita è sopra il limite consentito", response);
                return;
            }

            if (ScaffaleController.getEspostiByProdotto(p) - quantita < 0)
            {
                Utils.sendMessage("La quantità inserita è maggiore della quantità esposta", response);
                return;
            }

            venduto.setQuantita(quantita);

            if (saleData.get("price") instanceof Number)
            {
                val prezzo =((Number)saleData.get("price")).floatValue();
                if (prezzo < 0)
                {
                    Utils.sendMessage("Il prezzo è sotto il limite consentito", response);
                    return;
                }

                if (prezzo >= 100000)
                {
                    Utils.sendMessage("Il prezzo è sopra il limite consentito", response);
                    return;
                }

                if((!Patterns.PRICE.matcher(Float.toString(prezzo)).matches())){
                    Utils.sendMessage(Messages.INVALID_FORMAT.formatted("prezzo"), response);
                    return;
                }

                venduto.setCosto(((Number)saleData.get("price")).floatValue());
            }
            else if (saleData.get("price") instanceof String)
            {
                try
                {
                    if (Float.parseFloat((String)saleData.get("price")) < 0)
                    {
                        Utils.sendMessage("Il prezzo è sotto il limite consentito", response);
                        return;
                    }

                    if (Float.parseFloat((String)saleData.get("price")) >= 100000)
                    {
                        Utils.sendMessage("Il prezzo è sopra il limite consentito", response);
                        return;
                    }
                    if((!Patterns.PRICE.matcher((String) saleData.get("price")).matches())){
                        Utils.sendMessage(Messages.INVALID_FORMAT.formatted("prezzo"), response);
                        return;
                    }

                    venduto.setCosto(Float.parseFloat((String)saleData.get("price")));
                }
                catch (NumberFormatException e)
                {
                    Utils.sendMessage(Messages.INVALID_FORMAT.formatted("prezzo"), response);
                    return;
                }
            }
            else
            {
                Utils.sendMessage(Messages.INVALID_FORMAT.formatted("prezzo"), response);
                return;
            }

            selezionati.add(venduto);
        }

        VenditaController.addGiornoVendite();

        if (!VenditaController.venditaProdotti(selezionati))
        {
            Utils.sendMessage(Messages.FAIL, response);
            return;
        }

        request.getRequestDispatcher("view/cassiere/vendita.jsp").forward(request, response);
    }
}