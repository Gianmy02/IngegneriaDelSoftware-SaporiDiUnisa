package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EsposizioneDAO
{

    public ArrayList<Esposizione> getEsposizione(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT esposizione.prodotto, esposizione.lotto, esposizione.quantita, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto from esposizione, prodotto, lotto WHERE lotto.id = esposizione.lotto AND esposizione.prodotto = prodotto.id;");
            ResultSet rs = ps.executeQuery();
            ArrayList<Esposizione> esposti = new ArrayList<>();
            while(rs.next()){
                Esposizione e = new Esposizione();
                Prodotto p = new Prodotto();
                Lotto l = new Lotto();
                p.setId(rs.getInt(1));
                l.setId(rs.getInt(2));
                e.setQuantita(rs.getInt(3));
                l.setCosto(rs.getFloat(4));
                l.setDataScadenza(LocalDate.parse(rs.getString(5)));
                l.setQuantita(rs.getInt(6));
                l.setQuantitaAttuale(rs.getInt(7));
                p.setNome(rs.getString(8));
                p.setMarchio(rs.getString(9));
                p.setPrezzo(rs.getFloat(10));
                p.setPrezzoScontato(rs.getFloat(11));
                p.setInizioSconto(LocalDate.parse(rs.getString(12)));
                p.setFineSconto(LocalDate.parse(rs.getString(13)));
                p.setFoto(rs.getBytes(14));
                e.setProdotto(p);
                e.setLotto(l);
                esposti.add(e);
            }
            return esposti;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Esposizione> getLottibyProdottoWithoutScaduti(Prodotto p){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT l.id, l.costo, l.data_scadenza, l.quantita, e.quantita, f.* FROM esposizione e JOIN lotto l ON e.lotto = l.id JOIN fornitura f ON l.fornitura = f.id WHERE l.data_scadenza >= CURRENT_DATE() AND e.prodotto = 1 GROUP BY e.lotto ORDER BY l.data_scadenza;");
            ResultSet rs = ps.executeQuery();
            ArrayList<Esposizione> esposizioni = new ArrayList<>();
            while(rs.next()){
                Lotto l = new Lotto();
                Esposizione e = new Esposizione();
                Fornitura f = new Fornitura();
                l.setId(rs.getInt(1));
                l.setCosto(rs.getFloat(2));
                l.setDataScadenza(LocalDate.parse(rs.getString(3)));
                l.setQuantita(rs.getInt(4));
                l.setQuantitaAttuale(rs.getInt(5));
                f.setId(rs.getInt(6));
                f.setGiorno(LocalDate.parse(rs.getString(7)));
                l.setProdotto(p);
                e.setProdotto(p);
                l.setFornitura(f);
                e.setLotto(l);
                esposizioni.add(e);
            }
            return esposizioni;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


}
