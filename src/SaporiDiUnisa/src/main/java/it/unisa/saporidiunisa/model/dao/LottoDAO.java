package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class LottoDAO
{
    public static int selectLastId()
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select id from lotto order by id desc limit 1");
            val rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void insert(final Lotto lotto)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("insert into lotto(costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) values(?, ?, ?, ?, ?, ?)");
            ps.setFloat(1, lotto.getCosto());
            ps.setDate(2, Date.valueOf(lotto.getDataScadenza()));
            ps.setInt(3, lotto.getQuantita());
            ps.setInt(4, lotto.getQuantitaAttuale());
            ps.setInt(5, lotto.getFornitura().getId());
            ps.setInt(6, lotto.getProdotto().getId());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static float getSpeseTotali()
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT SUM(costo) FROM lotto;");
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restituisce tutti i lotti scaduti con solo gli attributi che servono
     */
    public static ArrayList<Lotto> getPerditeTotali()
    {
        try (val connection = Database.getConnection())
        {
            val lotti = new ArrayList<Lotto>();
            val ps = connection.prepareStatement("SELECT id, costo, quantita, quantita_attuale FROM lotto WHERE data_scadenza<=CURDATE() AND quantita_attuale>0;");
            val rs = ps.executeQuery();

            while (rs.next())
            {
                val l = new Lotto();
                l.setId(rs.getInt(1));
                l.setCosto(rs.getFloat(2));
                l.setQuantita(rs.getInt(3));
                l.setQuantitaAttuale(rs.getInt(4));
                lotti.add(l);
            }

            return lotti;

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Lotto> getLottiWithoutEsposizione()
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT lotto.id, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, lotto.fornitura, lotto.prodotto, fornitura.giorno, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto " +
                            "FROM lotto " +
                            "INNER JOIN fornitura ON lotto.fornitura = fornitura.id " +
                            "INNER JOIN prodotto ON lotto.prodotto = prodotto.id " +
                            "WHERE lotto.quantita_attuale > 0 AND lotto.data_scadenza > CURDATE() AND lotto.id NOT IN (SELECT esposizione.lotto FROM esposizione WHERE esposizione.lotto = lotto.id);");

            ResultSet rs = ps.executeQuery();
            ArrayList<Lotto> lottiMagazzino = new ArrayList<>();
            while (rs.next())
            {
                Lotto lotto = new Lotto();
                Fornitura fornitura = new Fornitura();
                Prodotto prodotto = new Prodotto();

                lotto.setId(rs.getInt(1));
                lotto.setCosto(rs.getFloat(2));
                lotto.setDataScadenza(LocalDate.parse(rs.getString(3)));
                lotto.setQuantita(rs.getInt(4));
                lotto.setQuantitaAttuale(rs.getInt(5));

                fornitura.setId(rs.getInt(6));
                fornitura.setGiorno(LocalDate.parse(rs.getString(8)));
                lotto.setFornitura(fornitura);

                prodotto.setId(rs.getInt(7));
                prodotto.setNome(rs.getString(9));
                prodotto.setMarchio(rs.getString(10));
                prodotto.setPrezzo(rs.getFloat(11));
                prodotto.setPrezzoScontato(rs.getFloat(12));
                if(rs.getDate(13) != null)
                    prodotto.setInizioSconto(rs.getDate(13).toLocalDate());
                if(rs.getDate(14) != null)
                    prodotto.setFineSconto(rs.getDate(14).toLocalDate());
                prodotto.setFoto(rs.getBytes(15));
                lotto.setProdotto(prodotto);

                lottiMagazzino.add(lotto);
            }
            return lottiMagazzino;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void diminuisciLotto(int id, int qnt){

        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE lotto SET quantita_attuale = quantita_attuale - ? WHERE id = ?");
            ps.setInt(1, qnt);
            ps.setInt(2, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void eliminaLotto(Lotto l){

        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE lotto SET data_scadenza = DATE_SUB(CURDATE(), INTERVAL 1 DAY) WHERE id = ?");
            ps.setInt(1, l.getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Lotto getLottoById(int id){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT lotto.id, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, fornitura.id AS fornitura_id, fornitura.giorno AS data_fornitura, prodotto.id AS prodotto_id, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto FROM lotto JOIN fornitura ON lotto.fornitura = fornitura.id JOIN prodotto ON lotto.prodotto = prodotto.id WHERE lotto.id = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                // Creare un oggetto Lotto con i dati ottenuti dalla query
                Lotto lotto = new Lotto();
                lotto.setId(resultSet.getInt("id"));
                lotto.setCosto(resultSet.getFloat("costo"));
                lotto.setDataScadenza(resultSet.getDate("data_scadenza").toLocalDate());
                lotto.setQuantita(resultSet.getInt("quantita"));
                lotto.setQuantitaAttuale(resultSet.getInt("quantita_attuale"));

                // Creare un oggetto Fornitura
                Fornitura fornitura = new Fornitura();
                fornitura.setId(resultSet.getInt("fornitura_id"));
                fornitura.setGiorno(resultSet.getDate("data_fornitura").toLocalDate());
                lotto.setFornitura(fornitura);

                // Creare un oggetto Prodotto
                Prodotto prodotto = new Prodotto();
                prodotto.setId(resultSet.getInt("prodotto_id"));
                prodotto.setNome(resultSet.getString("nome"));
                prodotto.setMarchio(resultSet.getString("marchio"));
                prodotto.setPrezzo(resultSet.getFloat("prezzo"));
                prodotto.setPrezzoScontato(resultSet.getFloat("prezzo_scontato"));
                if(resultSet.getDate("inizio_sconto")!=null)
                    prodotto.setInizioSconto(resultSet.getDate("inizio_sconto").toLocalDate());
                if(resultSet.getDate("fine_sconto")!=null)
                    prodotto.setFineSconto(resultSet.getDate("fine_sconto").toLocalDate());
                prodotto.setFoto(resultSet.getBytes("foto"));
                lotto.setProdotto(prodotto);

                return lotto;
            }
            return null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Prodotto, ArrayList<Lotto>> getMagazzino(){

        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM prodotto;");
                    ResultSet resultSet = ps.executeQuery();
                    ArrayList<Prodotto> prodotti = new ArrayList<>();
                    HashMap<Prodotto, ArrayList<Lotto>> prodottiMap = new HashMap<>();
                    while(resultSet.next()){
                        Prodotto prodotto = new Prodotto();
                        prodotto.setId(resultSet.getInt("id"));
                        prodotto.setNome(resultSet.getString("nome"));
                        prodotto.setMarchio(resultSet.getString("marchio"));
                        prodotto.setPrezzo(resultSet.getFloat("prezzo"));
                        prodotto.setPrezzoScontato(resultSet.getFloat("prezzo_scontato"));
                        if(resultSet.getDate("inizio_sconto")!=null)
                            prodotto.setInizioSconto(resultSet.getDate("inizio_sconto").toLocalDate());
                        if(resultSet.getDate("fine_sconto")!=null)
                            prodotto.setFineSconto(resultSet.getDate("fine_sconto").toLocalDate());
                        prodotto.setFoto(resultSet.getBytes("foto"));
                        prodotti.add(prodotto);
                    }
            for(Prodotto p: prodotti){
                ps = connection.prepareStatement("SELECT lotto.id, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, fornitura.id AS fornitura_id, fornitura.giorno FROM lotto, fornitura WHERE lotto.fornitura = fornitura.id AND lotto.prodotto = ? AND data_scadenza>=CURDATE() AND quantita_attuale >0;");
                ps.setInt(1, p.getId());
                resultSet = ps.executeQuery();
                ArrayList<Lotto> lotti = new ArrayList<>();
                while(resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setId(resultSet.getInt("id"));
                    lotto.setCosto(resultSet.getFloat("costo"));
                    lotto.setDataScadenza(resultSet.getDate("data_scadenza").toLocalDate());
                    lotto.setQuantita(resultSet.getInt("quantita"));
                    lotto.setQuantitaAttuale(resultSet.getInt("quantita_attuale"));

                    // Creare oggetto Fornitura
                    Fornitura fornitura = new Fornitura();
                    fornitura.setId(resultSet.getInt("fornitura_id"));
                    fornitura.setGiorno(resultSet.getDate("giorno").toLocalDate());
                    lotto.setFornitura(fornitura);
                    lotti.add(lotto);
                }
                prodottiMap.put(p, lotti);
            }
            return prodottiMap;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
