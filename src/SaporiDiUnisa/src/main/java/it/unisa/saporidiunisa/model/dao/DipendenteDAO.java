package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.SQLException;

/**
 * La classe <code>DipendenteDAO</code> gestisce le transazioni della tabella Dipendente nel DB
 */
public class DipendenteDAO
{
    /**
     * Il metodo <code>findDipendenteByPin</code> cerca nel database nella tabella dipendente uno che abbia un pin uguale a quello inviato
     * @param pin stringa per trovare un determinato dipendente
     * @return Dipendente trovato oppure null
     */
    public static Dipendente findDipendenteByPin(String pin)
    {
        try (val connection = Database.getConnection())
        {
            val preparedStatement = connection.prepareStatement("select id, ruolo, pin from dipendente where pin = ?;");
            preparedStatement.setString(1, pin);
            val resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                val dipendente = new Dipendente();
                dipendente.setId(resultSet.getInt(1));
                dipendente.setRuolo(Dipendente.Ruolo.valueOf(resultSet.getString(2).toUpperCase()));
                dipendente.setPin(resultSet.getString(3));
                return dipendente;
            }

            return null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>updatePin</code> cerca nella tabella dipendente del DB se un dipendente ha già il pin passato
     * nel caso non sia così effettua una modifica del pin del ruolo passato
     * @param pin Stringa, nuovo pin
     * @param ruolo Ruolo al quale modificare il pin
     * @return booleano di conferma
     */
    public static boolean updatePin(String pin, Dipendente.Ruolo ruolo)
    {
        try (val connection = Database.getConnection())
        {
            var preparedStatement = connection.prepareStatement("select pin from dipendente where pin = ?;");
            preparedStatement.setString(1, pin);
            val resultSet = preparedStatement.executeQuery();

            // Controllo che il nuovo pin non sia uguale a quelli in uso
            if (!resultSet.next())
            {
                preparedStatement = connection.prepareStatement("update dipendente set pin = ? where ruolo = ?;");
                preparedStatement.setString(1, pin);
                preparedStatement.setString(2, String.valueOf(ruolo));

                if (preparedStatement.executeUpdate() != 1)
                {
                    throw new RuntimeException("UPDATE error.");
                }

                return true;
            }

            // Il pin è già impostato
            return false;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
