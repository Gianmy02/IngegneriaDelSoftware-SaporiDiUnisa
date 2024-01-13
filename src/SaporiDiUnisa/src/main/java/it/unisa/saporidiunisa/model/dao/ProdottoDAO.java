package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO
{
    public static ArrayList<Prodotto> findProdotti()
    {
        try (val connection = Database.getConnection())
        {
            val prodotti = new ArrayList<Prodotto>();

            val preparedStatement = connection.prepareStatement("select id, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto from prodotto;");
            val resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                val prodotto = new Prodotto();
                prodotto.setId(resultSet.getInt(1));
                prodotto.setNome(resultSet.getString(2));
                prodotto.setMarchio(resultSet.getString(3));
                prodotto.setPrezzo(resultSet.getFloat(4));
                prodotto.setPrezzoScontato(resultSet.getFloat(5));
                prodotto.setInizioSconto(resultSet.getDate(6).toLocalDate());
                prodotto.setFineSconto(resultSet.getDate(7).toLocalDate());
                prodotto.setFoto(resultSet.getBlob(8).getBinaryStream().readAllBytes());
                prodotti.add(prodotto);
            }

            return prodotti;
        }
        catch (SQLException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
