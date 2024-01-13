package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.SQLException;

public class DipendenteDAO
{
    public Dipendente findDipendenteByPin(int pin)
    {
        try (val connection = Database.getConnection())
        {
            val preparedStatement = connection.prepareStatement("select id, ruolo, pin from dipendente where pin = ?;");
            preparedStatement.setInt(1, pin);
            val resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                val dipendente = new Dipendente();
                dipendente.setId(resultSet.getInt(1));
                dipendente.setRuolo(Dipendente.Ruolo.valueOf(resultSet.getString(2)));
                dipendente.setPin(resultSet.getString(3).toCharArray());
                return dipendente;
            }

            return null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePin(int pin, Dipendente.Ruolo ruolo)
    {
        try (val connection = Database.getConnection())
        {
            var preparedStatement = connection.prepareStatement("select pin from dipendente where pin = ? and ruolo = ?;");
            preparedStatement.setInt(1, pin);
            preparedStatement.setInt(2, ruolo.ordinal());
            val resultSet = preparedStatement.executeQuery();

            // Controllo che il nuovo pin non sia uguale a quello in uso
            if (!resultSet.next())
            {
                preparedStatement = connection.prepareStatement("update dipendente set pin = ? where ruolo = ?;");
                preparedStatement.setInt(1, pin);
                preparedStatement.setInt(2, ruolo.ordinal());

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
