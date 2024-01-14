package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.SQLException;

public class LottoDAO {
    public static int selectLastId() {
        try {
            val con = Database.getConnection();
            val ps = con.prepareStatement("select id from lotto order by id desc limit 1");
            val rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(final Lotto lotto){
        try {
            val con = Database.getConnection();
            val ps = con.prepareStatement("insert into lotto(costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) values(?, ?, ?, ?, ?, ?)");
            ps.setFloat(1, lotto.getCosto());
            ps.setDate(2, java.sql.Date.valueOf(lotto.getDataScadenza()));
            ps.setInt(3, lotto.getQuantita());
            ps.setInt(4, lotto.getQuantitaAttuale());
            ps.setInt(5, lotto.getFornitura().getId());
            ps.setInt(6, lotto.getProdotto().getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
