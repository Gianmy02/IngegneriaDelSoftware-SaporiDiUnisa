package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO
{
    /**
     * Il metodo <code>selectAll</code> restituisce una lista di tutti i prodotti presenti nel database.
     * @return ArrayList<Prodotto>
     */
    public static ArrayList<Prodotto> selectAll()
    {
        try (val connection = Database.getConnection())
        {
            val prodotti = new ArrayList<Prodotto>();
            val resultSet = connection.prepareStatement("select id as idProdotto, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto from prodotto;").executeQuery();

            while (resultSet.next())
                prodotti.add(buildBySQL(resultSet));

            return prodotti;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>selectByNameAndBrand</code> restituisce un prodotto con un determinato nome e marchio.
     * @param nome Nome del prodotto
     * @param marchio Marchio del prodotto
     * @return Prodotto
     */
    public static Prodotto selectByNameAndBrand(final String nome, final String marchio)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select id as idProdotto, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto from prodotto where nome = ? and marchio = ?;");
            ps.setString(1, nome);
            ps.setString(2, marchio);

            val rs = ps.executeQuery();
            return rs.next() ? buildBySQL(rs) : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>insert</code> inserisce un prodotto nel database.
     * @param prodotto Prodotto da inserire
     */
    public static void insert(final Prodotto prodotto)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("insert into prodotto (nome, marchio, prezzo, foto) values (?, ?, ?, ?);");
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getMarchio());
            ps.setFloat(3, prodotto.getPrezzo());
            ps.setBytes(4, prodotto.getFoto());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getLastId</code> restituisce l'id dell'ultimo prodotto inserito nel database.
     * @return int
     */
    public static int getLastId()
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select id from prodotto order by id desc limit 1");
            val rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>updatePrice</code> aggiorna il prezzo di un prodotto.
     * @param prezzo Prezzo del prodotto
     * @param id Id del prodotto
     */
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

    /**
     * Il metodo <code>updateSconto</code> aggiorna lo sconto di un prodotto.
     * @param p Prodotto
     * @return boolean true se l'aggiornamento è andato a buon fine, false altrimenti
     */
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

    /**
     * Il metodo <code>findProdottoById</code> trova un prodotto nel database tramite il suo id.
     * @param id Id del prodotto da trovare
     */
    public static Prodotto findProdottoById(int id)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select id as idProdotto, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto from prodotto where id = ?;");
            ps.setInt(1, id);
            val rs = ps.executeQuery();
            return rs.next() ? buildBySQL(rs) : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>buildBySQL</code> costruisce un prodotto tramite una query SQL.
     * @param rs ResultSet
     */
    public static Prodotto buildBySQL(final ResultSet rs) throws SQLException
    {
        try {
            return new Prodotto(
                rs.getInt("idProdotto"),
                rs.getString("nome"),
                rs.getString("marchio"),
                rs.getFloat("prezzo"),
                rs.getFloat("prezzo_scontato"),
                rs.getDate("inizio_sconto")!=null ? rs.getDate("inizio_sconto").toLocalDate() : null,
                rs.getDate("fine_sconto")!=null ? rs.getDate("fine_sconto").toLocalDate() : null,
                rs.getBlob("foto") != null ? rs.getBlob("foto").getBinaryStream().readAllBytes() : null
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
