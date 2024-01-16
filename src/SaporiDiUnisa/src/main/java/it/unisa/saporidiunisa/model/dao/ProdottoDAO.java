package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.Date;
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
            val resultSet = connection.prepareStatement("select id, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto from prodotto;").executeQuery();

            while (resultSet.next())
                prodotti.add(_build(resultSet));

            return prodotti;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Prodotto selectByNameAndBrand(final String nome, final String marchio)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select * from prodotto where nome = ? and marchio = ?;");
            ps.setString(1, nome);
            ps.setString(2, marchio);

            val rs = ps.executeQuery();
            return rs.next() ? _build(rs) : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void insert(final Prodotto prodotto)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("insert into prodotto (nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto) values (?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getMarchio());
            ps.setFloat(3, prodotto.getPrezzo());
            ps.setFloat(4, prodotto.getPrezzoScontato());
            ps.setDate(5, Date.valueOf(prodotto.getInizioSconto()));
            ps.setDate(6, Date.valueOf(prodotto.getFineSconto()));
            ps.setBytes(7, prodotto.getFoto());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void updatePrice(final float prezzo, final int id)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("update prodotto set prezzo=(?) where id=(?)");
            ps.setFloat(1, prezzo);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateSconto(Prodotto p)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("update prodotto set prezzo_scontato = ?, inizio_sconto = ?, fine_sconto = ? where id = ?;");
            ps.setFloat(1, p.getPrezzoScontato());
            ps.setDate(2, Date.valueOf(p.getInizioSconto()));
            ps.setDate(3, Date.valueOf(p.getFineSconto()));
            ps.setInt(4, p.getId());
            return ps.executeUpdate() == 1;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Prodotto findProdottoById(int id)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select * from prodotto where id = ?;");
            ps.setInt(1, id);
            val rs = ps.executeQuery();
            return rs.next() ? _build(rs) : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static Prodotto _build(final ResultSet rs) throws SQLException
    {
        val prodotto = new Prodotto();
        prodotto.setId(rs.getInt("id"));
        prodotto.setNome(rs.getString("nome"));
        prodotto.setMarchio(rs.getString("marchio"));
        prodotto.setPrezzo(rs.getFloat("prezzo"));
        prodotto.setPrezzoScontato(rs.getFloat("prezzo_scontato"));
        if(rs.getDate("inizio_sconto")!=null)
            prodotto.setInizioSconto(rs.getDate("inizio_sconto").toLocalDate());
        if(rs.getDate("fine_sconto")!=null)
            prodotto.setFineSconto(rs.getDate("fine_sconto").toLocalDate());
        prodotto.setFoto(rs.getBytes("foto"));
        return prodotto;
    }
}
