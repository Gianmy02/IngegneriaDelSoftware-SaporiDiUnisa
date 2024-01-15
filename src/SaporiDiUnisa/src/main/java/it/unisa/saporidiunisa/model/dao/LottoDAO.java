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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class LottoDAO {
    public static int selectLastId() {
        try(val con = Database.getConnection()) {
            val ps = con.prepareStatement("select id from lotto order by id desc limit 1");
            val rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(final Lotto lotto){
        try(val con = Database.getConnection()) {
            val ps = con.prepareStatement("insert into lotto(costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) values(?, ?, ?, ?, ?, ?)");
            ps.setFloat(1, lotto.getCosto());
            ps.setDate(2, java.sql.Date.valueOf(lotto.getDataScadenza()));
            ps.setInt(3, lotto.getQuantita());
            ps.setInt(4, lotto.getQuantitaAttuale());
            ps.setInt(5, lotto.getFornitura().getId());
            ps.setInt(6, lotto.getProdotto().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float getSpeseTotali(){
            try (val connection = Database.getConnection())
            {
                PreparedStatement ps =
                        connection.prepareStatement("SELECT SUM(costo) FROM lotto;");
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getFloat(1);
                }
                return 0;

            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
    }

    /**
     * Restituisce tutti i lotti scaduti con solo gli attributi che servono
     */
    public static ArrayList<Lotto> getPerditeTotali(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT id, costo, quantita, quantita_attuale FROM lotto WHERE data_scadenza<=CURDATE() AND quantita_attuale>0;");
            ResultSet rs = ps.executeQuery();
            ArrayList<Lotto> lotti = new ArrayList<>();
            while(rs.next()){
                Lotto l = new Lotto();
                l.setId(rs.getInt(1));
                l.setCosto(rs.getFloat(2));
                l.setQuantita(rs.getInt(3));
                l.setQuantitaAttuale(rs.getInt(4));
            }
            return lotti;

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Lotto> getLottiWithoutEsposizione(){
        try (val connection = Database.getConnection()) {
            Statement st = connection.createStatement();
            String query = "DROP VIEW IF EXISTS vista;";
            st.executeUpdate(query);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        try (val connection = Database.getConnection()) {
            Statement st = connection.createStatement();
            String query = "CREATE VIEW vista AS SELECT esposizione.lotto FROM esposizione, lotto, prodotto WHERE esposizione.lotto = lotto.id AND esposizione.prodotto = prodotto.id;";
            st.executeUpdate(query);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        try (val connection = Database.getConnection()) {

            PreparedStatement ps =
                    connection.prepareStatement("SELECT lotto.id, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, lotto.fornitura, lotto.prodotto, fornitura.giorno, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto FROM lotto, fornitura, prodotto WHERE lotto.fornitura = fornitura.id AND lotto.prodotto = prodotto.id AND lotto.id NOT IN (SELECT lotto FROM vista);");
            ResultSet rs = ps.executeQuery();
            ArrayList<Lotto> lottiMagazzino = new ArrayList<>();
            while (rs.next()) {
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
                prodotto.setInizioSconto(rs.getDate(13).toLocalDate());
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
}
