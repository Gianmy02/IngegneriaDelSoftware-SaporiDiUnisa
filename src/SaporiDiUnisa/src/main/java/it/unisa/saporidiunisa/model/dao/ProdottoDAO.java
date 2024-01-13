package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO
{
    public static ArrayList<Prodotto> selectAll()
    {
        try (val connection = Database.getConnection())
        {
            val prodotti = new ArrayList<Prodotto>();

            val preparedStatement = connection.prepareStatement("select id, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto from prodotto;");
            val resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                val prodotto = _build(resultSet);
                prodotti.add(prodotto);
            }

            return prodotti;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Prodotto selectByNameAndBrand(final String nome, final String marchio) {
        try {
            val con = Database.getConnection();
            val ps = con.prepareStatement("select * from prodotto where nome=(?) and marchio=(?)");
            ps.setString(1, nome);
            ps.setString(2, marchio);
            val rs = ps.executeQuery();
            val prodotto = rs.next() ? _build(rs) : null;
            rs.close();
            ps.close();
            con.close();
            return prodotto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Prodotto _build(final ResultSet rs) throws SQLException {
        val prodotto = new Prodotto();
        prodotto.setId(rs.getInt("id"));
        prodotto.setNome(rs.getString("nome"));
        prodotto.setMarchio(rs.getString("marchio"));
        prodotto.setPrezzo(rs.getFloat("prezzo"));
        prodotto.setPrezzoScontato(rs.getFloat("prezzoScontato"));
        prodotto.setInizioSconto(rs.getDate("inizioSconto").toLocalDate());
        prodotto.setFineSconto(rs.getDate("fineSconto").toLocalDate());
        prodotto.setFoto(rs.getBytes("foto"));
        return prodotto;
    }
}
