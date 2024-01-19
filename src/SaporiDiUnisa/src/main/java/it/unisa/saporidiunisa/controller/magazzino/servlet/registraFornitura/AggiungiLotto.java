package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.form.LottoForm;
import it.unisa.saporidiunisa.utils.Errors;
import it.unisa.saporidiunisa.utils.Patterns;
import it.unisa.saporidiunisa.utils.Utils;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val dipendente = (Dipendente)req.getSession().getAttribute("dipendente");
        if (dipendente == null || dipendente.getRuolo() != Dipendente.Ruolo.MAGAZZINIERE) {
            Utils.dispatchError(Errors.NO_PERMISSIONS, req, resp);
            return;
        }

        val nome = _readPart(req.getPart("nome"));
        val marchio = _readPart(req.getPart("marchio"));
        // la verifica dei prezzi inseriti rispetto a quelli attuali solo a registrazione avvenuta
        val prezzo = Float.parseFloat(_readPart(req.getPart("prezzo")));
        val quantita = Integer.parseInt(_readPart(req.getPart("quantita")));
        val dataScadenza = LocalDate.parse(_readPart(req.getPart("dataScadenza")));

        val lottoForm = new LottoForm(nome, marchio, prezzo, quantita, dataScadenza);
        val errorString = lottoForm.validate();
        if(errorString != null){
            // TODO: ritornare errori con ajax
            req.setAttribute("error", errorString);
            req.getRequestDispatcher("/view/registraFornitura.jsp").forward(req, resp);
            return;
        }

        var prodotto = MagazzinoController.checkProductExists(nome, marchio);
        // se il prodotto è nuovo, controllo che sia stata caricata una foto
        if(prodotto == null){
            val filePart = req.getPart("foto");
            if(filePart == null || filePart.getSize() <= 0 || !_isImage(filePart)){
                // TODO: ritornare errori con ajax
                req.setAttribute("error", "Il file caricato non è supportato");
                req.getRequestDispatcher("/view/registraFornitura.jsp").forward(req, resp);
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
        json.put("nome", nome);
        json.put("marchio", marchio);
        json.put("prezzo", prezzo);
        json.put("quantita", quantita);
        json.put("dataScadenza", dataScadenza.format(Patterns.DATE_TIME_FORMATTER));
        resp.setContentType("application/json");
        resp.getWriter().write(String.valueOf(json));
    }

    // metodo per leggere campi provenienti un oggetto JavaScript FormData
    private String _readPart(final Part part) {
        try (InputStream is = part.getInputStream();
            val reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean _isImage(final Part part) {
        val contentDisposition = part.getHeader("content-disposition");
        val tokens = contentDisposition.split(";");

        for (val token : tokens) {
            if (token.trim().startsWith("filename")) {
                val fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                int lastDotIndex = fileName.lastIndexOf('.');
                if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
                    val extension = fileName.substring(lastDotIndex + 1);
                    return switch (extension) {
                        case "jpg", "jpeg", "png" -> true;
                        default -> false;
                    };
                }
            }
        }
        return false;
    }
}