package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.form.LottoForm;
import it.unisa.saporidiunisa.utils.Patterns;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.val;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Salvatore Ruocco
 * Servlet, invocata tramite AJAX, che gestisce l'aggiunta di un lotto alla fornitura che si sta inserendo
 */
@MultipartConfig
@WebServlet(name = "AggiungiLotto", value = "/AggiungiLotto")
public class AggiungiLotto extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val nome_str = Utils.readPart(req.getPart("nome"));
        val marchio_str = Utils.readPart(req.getPart("marchio"));
        val prezzo_str = Utils.readPart(req.getPart("prezzo"));
        val quantita_str = Utils.readPart(req.getPart("quantita"));
        val dataScadenza_str = Utils.readPart(req.getPart("dataScadenza"));

        val lottoForm = new LottoForm();
        val errorString = lottoForm.validate(nome_str, marchio_str, prezzo_str, quantita_str, dataScadenza_str);

        if(errorString != null){
            // TODO: ritornare errori con ajax
            return;
        }
        val nome = lottoForm.getNome();
        val marchio = lottoForm.getMarchio();
        val prezzo = lottoForm.getPrezzo();
        val quantita = lottoForm.getQuantita();
        val dataScadenza = lottoForm.getDataScadenza();

        var prodotto = MagazzinoController.checkProductExists(nome, marchio);
        // se il prodotto è nuovo, controllo che sia stata caricata una foto
        if(prodotto == null){
            val filePart = req.getPart("foto");
            if(filePart == null || filePart.getSize() <= 0 || !Utils.isImage(filePart)){
                // TODO: ritornare errori con ajax

                return;
            }
            final byte[] foto = filePart.getInputStream().readAllBytes();
            prodotto = new Prodotto(0, nome, marchio, prezzo, prezzo, null, null, foto);
        }

        // tengo l'id della fornitura in sessione per evitare ripetute select dal db
        final HttpSession session = req.getSession();
        var fornitura = (Fornitura) session.getAttribute("fornitura");
        if(fornitura == null)
            fornitura = new Fornitura();

        /* il lotto ha come id non quello che sarà presente una volta salvato sul database
           ma uno che indica l'i-esimo lotto della fornitura in sessione
           id = fornitura.getLotti().size() */
        val lotto = new Lotto(fornitura.getLotti().size(), prezzo * quantita, dataScadenza, quantita, quantita, fornitura, prodotto);
        fornitura.getLotti().add(lotto);
        session.setAttribute("fornitura", fornitura);

        // ritorno il lotto appena inserito in formato json
        val json = new JSONObject();
        json.put("keyId", lotto.getId());
        json.put("nome", nome);
        json.put("marchio", marchio);
        json.put("prezzo", prezzo);
        json.put("quantita", quantita);
        json.put("dataScadenza", dataScadenza.format(Patterns.DATE_TIME_FORMATTER));
        resp.setContentType("application/json");
        resp.getWriter().write(String.valueOf(json));
    }
}