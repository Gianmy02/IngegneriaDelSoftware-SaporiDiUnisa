package it.unisa.saporidiunisa.controller.magazzino.servlet;

import it.unisa.saporidiunisa.model.dao.ProdottoDAO;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet che gestisce l'aggiunta di un lotto alla fornitura che si sta inserendo
 */
@MultipartConfig
@WebServlet(name = "AggiungiLotto", value = "/AggiungiLotto")
public class AggiungiLotto extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val nome = req.getParameter("nome");
        val marchio = req.getParameter("marchio");
        // la verifica dei prezzi inseriti solo a registrazione avvenuta
        val prezzo = Float.parseFloat(req.getParameter("prezzo"));

        var prodotto = ProdottoDAO.selectByNameAndBrand(nome, marchio);
        if(prodotto == null){
            byte[] foto = req.getPart("foto").getInputStream().readAllBytes();

            prodotto = new Prodotto();
            prodotto.setId(0);
            prodotto.setNome(nome);
            prodotto.setMarchio(marchio);
            prodotto.setPrezzo(prezzo);
            prodotto.setFoto(foto);
        }

        val quantita = Integer.parseInt(req.getParameter("quantita"));
        val dataScadenza = LocalDate.parse(req.getParameter("dataScadenza"));

        // tengo l'id della fornitura in sessione per evitare ripetute select dal db
        final HttpSession session = req.getSession();
        var fornitura = (Fornitura) session.getAttribute("fornitura");
        if(fornitura == null){
            fornitura = new Fornitura();
            session.setAttribute("fornitura", fornitura);
        }

        val lotto = new Lotto(0, prezzo * quantita, dataScadenza, quantita, quantita, fornitura, prodotto);
        fornitura.getLotti().add(lotto);
    }
}