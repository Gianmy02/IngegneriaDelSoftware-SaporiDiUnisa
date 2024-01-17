package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Patterns;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.val;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * @author Salvatore Ruocco
 * Servlet, invocata tramite AJAX, che gestisce l'aggiunta di un lotto alla fornitura che si sta inserendo
 */
@MultipartConfig
@WebServlet(name = "AggiungiLotto", value = "/AggiungiLotto")
public class AggiungiLotto extends HttpServlet {
    final MagazzinoController magazzinoController = new MagazzinoController();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val nome = _readPart(req.getPart("nome"));
        val marchio = _readPart(req.getPart("marchio"));
        // la verifica dei prezzi inseriti rispetto a quelli attuali solo a registrazione avvenuta
        val prezzo = Float.parseFloat(_readPart(req.getPart("prezzo")));
        val quantita = Integer.parseInt(_readPart(req.getPart("quantita")));
        val dataScadenza = LocalDate.parse(_readPart(req.getPart("dataScadenza")));

        val errorString = magazzinoController.lottoValidation(nome, marchio, prezzo, quantita, dataScadenza);
        if(errorString != null){
            req.setAttribute("error", errorString);
            req.getRequestDispatcher("/view/registraFornitura.jsp").forward(req, resp);
            return;
        }

        Prodotto prodotto = magazzinoController.checkProductExists(nome, marchio);
        if(prodotto == null){
            byte[] foto = req.getPart("foto").getInputStream().readAllBytes();
            prodotto = new Prodotto(0, nome, marchio, prezzo, prezzo, null, null, foto);
        }

        // tengo l'id della fornitura in sessione per evitare ripetute select dal db
        final HttpSession session = req.getSession();
        var fornitura = (Fornitura) session.getAttribute("fornitura");
        if(fornitura == null)
            fornitura = new Fornitura();

        val lotto = new Lotto(0, prezzo * quantita, dataScadenza, quantita, quantita, fornitura, prodotto);
        fornitura.getLotti().add(lotto);
        session.setAttribute("fornitura", fornitura);

        // ritorno il lotto appena inserito in formato json
        val json = new JSONObject();
        json.put("nome", nome);
        json.put("marchio", marchio);
        json.put("prezzo", prezzo);
        json.put("quantita", quantita);
        json.put("dataScadenza", dataScadenza.format(Patterns.DATE_TIME_FORMATTER));
        resp.setContentType("application/json");
        resp.getWriter().write(String.valueOf(json));
    }

    private String _readPart(Part part) throws IOException {
        try (InputStream is = part.getInputStream();
            val reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}