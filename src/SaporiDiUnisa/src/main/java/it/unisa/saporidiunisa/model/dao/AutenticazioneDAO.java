package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutenticazioneDAO {
    public Dipendente login(int pin){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM dipendente WHERE pin = ?;");
            ps.setInt(1, pin);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Dipendente d = new Dipendente();
                d.setId(rs.getInt(1));
               // d.setRuolo(rs.getString(2));
                d.setPin(rs.getInt(3));
                return d;
            }
            return null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    public boolean updatePin(int pin, String ruolo){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE dipendente SET pin = ? WHERE ruolo = ? ");
            ps.setInt(1, pin);
            ps.setString(2, ruolo);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
            return true;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
