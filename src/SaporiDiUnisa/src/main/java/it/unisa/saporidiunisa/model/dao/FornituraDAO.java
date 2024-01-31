package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FornituraDAO
{
    /**
     * Il metodo <code>insert</code> inserisce una nuova fornitura nel DB
     * @param fornitura
     */
    public static void insert(final Fornitura fornitura)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("insert into fornitura(giorno) values(?)");
            ps.setDate(1, Date.valueOf(fornitura.getGiorno()));
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getLastId</code> cerca l'ultima fornitura inserita
     * @return id intero o, nel caso non venga trovato 0
     */
    public static int getLastId()
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("select id from fornitura order by id desc limit 1");
            val rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>selectAll</code> ritorna tutte le forniture arrivate, contenute nel DB
     * @return Lista di forniture
     */
    public static List<Fornitura> selectAll(){
        try (val connection = Database.getConnection()){
            val rs = connection.prepareStatement(
                    "select f.id, giorno, l.id, costo, data_scadenza, quantita, quantita_attuale, p.id as idProdotto, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto " +
                    "from fornitura f, lotto l, prodotto p " +
                    "where f.id = l.fornitura and l.prodotto = p.id " +
                    "order by giorno desc"
            ).executeQuery();

            val hashMap = new HashMap<Integer, Fornitura>();
            while (rs.next()){
                Fornitura fornitura;
                final int idFornitura = rs.getInt("f.id");
                if(hashMap.get(idFornitura) == null){
                    fornitura = new Fornitura(idFornitura, rs.getDate("giorno").toLocalDate(), new ArrayList<>());
                    hashMap.put(idFornitura, fornitura);
                }
                else{
                    fornitura = hashMap.get(rs.getInt("f.id"));
                }
                val lotto = new Lotto(
                    rs.getInt("l.id"),
                    rs.getFloat("costo"),
                    rs.getDate("data_scadenza").toLocalDate(),
                    rs.getInt("quantita"),
                    rs.getInt("quantita_attuale"),
                    fornitura,
                    null
                );
                val prodotto = ProdottoDAO.buildBySQL(rs);

                lotto.setProdotto(prodotto);
                fornitura.getLotti().add(lotto);
            }

            return hashMap.values().stream().toList();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}