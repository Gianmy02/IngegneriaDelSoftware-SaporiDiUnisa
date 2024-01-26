package it.unisa.saporidiunisa.controller.magazzino.servlet.registraFornitura;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
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
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val nome = Utils.readPart(req.getPart("nome"));
        val marchio = Utils.readPart(req.getPart("marchio"));
        val prezzo_str = Utils.readPart(req.getPart("prezzo"));
        val quantita_str = Utils.readPart(req.getPart("quantita"));
        val dataScadenza_str = Utils.readPart(req.getPart("dataScadenza"));

        val errorString = _validateLotto(nome, marchio, prezzo_str, quantita_str, dataScadenza_str);
        if(!errorString.isEmpty()){
            val json = new JSONObject();
            json.put("errors", errorString);
            resp.setContentType("application/json");
            resp.getWriter().write(String.valueOf(json));
            return;
        }

        val prezzo = Utils.parseAsFloat(prezzo_str);
        val quantita = Utils.parseAsInteger(quantita_str);
        val dataScadenza = Utils.parseAsLocalDate(dataScadenza_str);

        var prodotto = MagazzinoController.checkProductExists(nome, marchio);
        // se il prodotto è nuovo, controllo che sia stata caricata una foto
        if(prodotto == null){
            val fotoPart = req.getPart("foto");
            val errorPhotoString = _validatePhoto(fotoPart);
            if(errorPhotoString != null) {
                val json = new JSONObject();
                json.put("errors", errorPhotoString);
                resp.setContentType("application/json");
                resp.getWriter().write(String.valueOf(json));
                return;
            }
            // se non ci sono errori
            final byte[] foto = Utils.readPart(fotoPart).getBytes();
            prodotto = new Prodotto(0, nome, marchio, prezzo, 0, null, null, foto);
        }

        // tengo l'id della fornitura in sessione per evitare ripetute select dal db
        final HttpSession session = req.getSession();
        var fornitura = (Fornitura) session.getAttribute("fornitura");
        if(fornitura == null)
            fornitura = new Fornitura();

        /*
        il lotto ha come id non quello che sarà presente una volta salvato sul database
        ma uno che indica l'i-esimo lotto della fornitura in sessione
        id = fornitura.getLotti().size()
        */
        val lotto = new Lotto(fornitura.getLotti().size(), prezzo * quantita, dataScadenza, quantita, quantita, fornitura, prodotto);
        fornitura.getLotti().add(lotto);
        session.setAttribute("fornitura", fornitura);

        // ritorno il lotto appena inserito in formato json
        val json = new JSONObject();
        json.put("keyId", lotto.getId());
        json.put("nome", nome);
        json.put("marchio", marchio);
        json.put("prezzo", String.format("%.2f €", prezzo));
        json.put("quantita", quantita);
        json.put("dataScadenza", dataScadenza.format(Patterns.DATE_TIME_FORMATTER));
        resp.setContentType("application/json");
        resp.getWriter().write(String.valueOf(json));
    }

    private String _validateLotto(final String nome, final String marchio, final String prezzo, final String quantita, final String dataScadenza) {
        val s = new StringBuilder();

        if(nome.isEmpty() || nome.isBlank())
            s.append("Il nome non può essere vuoto\n");
        else if(nome.length() < 2 || nome.length() > 255)
            s.append("Il nome deve essere compreso tra 2 e 255 caratteri\n");
        else if(!Patterns.INPUT_STRING.matcher(nome).matches())
            s.append("Il nome non rispetta il formato\n");

        if(marchio.isEmpty() || marchio.isBlank())
            s.append("Il marchio non può essere vuoto\n");
        else if(marchio.length() < 2 || marchio.length() > 255)
            s.append("Il marchio deve essere compreso tra 2 e 255 caratteri\n");
        else if(!Patterns.INPUT_STRING.matcher(marchio).matches())
            s.append("Il marchio non rispetta il formato\n");

        Float _prezzo;
        if(prezzo.isEmpty() || prezzo.isBlank()) {
            s.append("Il prezzo non può essere vuoto\n");
        }
        else{
            _prezzo = Utils.parseAsFloat(prezzo);
            if(_prezzo == null)
                s.append("Il prezzo deve essere un numero\n");
            else if(!Patterns.PRICE.matcher(prezzo).matches())
                s.append("Il prezzo non rispetta il formato\n");
            else if(_prezzo <= 0)
                s.append("Il prezzo deve essere compreso tra 0 e 100000.00\n");
            else if(_prezzo >= 100000.00)
                s.append("Il prezzo deve essere compreso tra 0 e 100000.00\n");
        }

        Integer _quantita;
        if(quantita.isEmpty() || quantita.isBlank()) {
            s.append("La quantità non può essere vuota\n");
        }
        else{
            _quantita = Utils.parseAsInteger(quantita);
            if(_quantita == null)
                s.append("La quantità deve essere un numero\n");
            else if(!Patterns.QUANTITY.matcher(quantita).matches())
                s.append("La quantità non rispetta il formato\n");
            else if(_quantita <= 0)
                s.append("La quantità deve essere maggiore di 0\n");
            else if(_quantita >= 1000000)
                s.append("La quantità deve essere minore di 1000000\n");
        }

        LocalDate _dataScadenza;
        if(dataScadenza.isEmpty() || dataScadenza.isBlank()) {
            s.append("La data di scadenza non può essere vuota\n");
        }
        else{
            _dataScadenza = Utils.parseAsLocalDate(dataScadenza);
            if(_dataScadenza == null)
                s.append("La data di scadenza non è valida\n");
            else if(_dataScadenza.isBefore(LocalDate.now()) || _dataScadenza.isEqual(LocalDate.now()))
                s.append("La data di scadenza deve essere anteriore a quella odierno\n");
        }

        return s.toString();
    }

    private String _validatePhoto(final Part foto){
        if(foto == null || foto.getSize() <= 0)
            return "La foto non può essere vuota\n";
        else if(foto.getSize() > 1024 * 1024 * 2)
            return "La foto deve essere minore di 2MB\n";
        else if(!Utils.checkImageExtension(foto))
            return "La foto deve essere un'immagine con estensione: jpg, jpeg o png\n";
        else if(!Utils.assureSquareImage(foto))
            return "La foto deve avere dimensioni 1:1\n";
        else
            return null;
    }
}