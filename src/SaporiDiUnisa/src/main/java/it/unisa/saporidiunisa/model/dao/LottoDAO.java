package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
