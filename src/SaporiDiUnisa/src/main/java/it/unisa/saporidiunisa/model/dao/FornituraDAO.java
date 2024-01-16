package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.Date;
import java.sql.SQLException;

public class FornituraDAO
{
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
}