package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.entity.Venduto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * La classe <code>VendutoDAO</code> gestisce le transazioni nel DB per la tabella Venduto
 */
public class VendutoDAO
{
    /**
     * Il metodo <code>getVendutiGiornalieroByProdotto</code> ricerca, per il prodotto passato nel DB, tutti i dati
     * di vendita della giornata odierna e li restituisce
     * @param p Prodotto
     * @return oggetto Venduto, oppure null
     */
    public static Venduto getVendutiGiornalieroByProdotto(Prodotto p)
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM venduto WHERE venduto.prodotto = ? AND venduto.giorno = CURDATE()");
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                val v = new Venduto();
                v.setCosto(rs.getFloat(1));
                v.setQuantita(rs.getInt(2));
                v.setGuadagno(rs.getFloat(3));
                v.setProdotto(p);
                v.setGiorno(LocalDate.parse(rs.getString(5)));
                return v;
            }
            return null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>doSaveVendita</code> salva una nuova Vendita nel giorno corrente di un prodotto ancora mai Venduto
     * @param v Oggetto Venduto, Prodotto con la propria quantità, costo e guadagno
     * @return booleano di conferma
     */
    public static boolean doSaveVendita(Venduto v)
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO venduto (costo, quantita, guadagno, prodotto, giorno) VALUES(?,?,?,?,CURDATE())");
            ps.setFloat(1, v.getCosto());
            ps.setInt(2, v.getQuantita());
            ps.setFloat(3, v.getGuadagno());
            ps.setInt(4, v.getProdotto().getId());

            return ps.executeUpdate() == 1;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>doSaveGiornoLavorativo</code> salva il giorno corrente nella tabella del db
     */
    public static void doSaveGiornoLavorativo()
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO vendita (giorno) VALUES(CURDATE())");
            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>searchGiornoLavorativo</code> cerca nel DB se il giorno odierno è già contenuto
     * @return booleano, se è contenuto ritorna false, in caso contrario true
     */
    public static boolean searchGiornoLavorativo()
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM vendita WHERE giorno = CURDATE();");
            ResultSet rs = ps.executeQuery();
            return !rs.next();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>doUpdateVendita</code> modifica nel DB il guadagno e la quantità venduta del giorno odierno aggiungendoli a quelli correnti
     * @param v Venduto, prodotto appena venduto
     * @return booleano di conferma
     */
    public static boolean doUpdateVendita(Venduto v)
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE venduto SET guadagno = guadagno + ?, quantita = quantita + ? WHERE venduto.prodotto = ? AND venduto.giorno = CURDATE()");
            ps.setFloat(1, v.getGuadagno());
            ps.setInt(2, v.getQuantita());
            ps.setInt(3, v.getProdotto().getId());

            return ps.executeUpdate() == 1;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * Il metodo <code>getStorico</code> prende la somma dal database nei giorni richiesti di tutti i prodotti
     * @param inizio delimitazione del periodo inferiore
     * @param fine delimitazione del periodo superiore
     * @return lista popolata, altrimenti null
     */
    public static ArrayList<Venduto> getStorico(LocalDate inizio, LocalDate fine)
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT p.id AS id_prodotto, p.nome AS nome_prodotto, p.marchio, p.foto, SUM(v.quantita) AS totale_quantita_venduta, SUM(v.guadagno) AS totale_guadagno, SUM(v.costo * v.quantita) AS costo_totale FROM prodotto p JOIN venduto v ON p.id = v.prodotto WHERE v.giorno BETWEEN ? AND ? GROUP BY p.id, p.nome, p.marchio, p.foto;");
            ps.setString(1, String.valueOf(inizio));
            ps.setString(2, String.valueOf(fine));
            ResultSet rs = ps.executeQuery();
            ArrayList<Venduto> venduti = new ArrayList<>();
            while (rs.next())
            {
                Venduto v = new Venduto();
                Prodotto p = new Prodotto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setMarchio(rs.getString(3));
                p.setFoto(rs.getBytes(4));
                v.setQuantita(rs.getInt(5));
                v.setGuadagno(rs.getFloat(6));
                v.setCosto(rs.getFloat(7));
                v.setProdotto(p);
                v.setGiorno(fine);
                venduti.add(v);
            }
            return venduti;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getIncassiTotali</code> restituisce gli incassi totali contenuti nel DB
     * @return float, nel caso di risultato vuoto 0
     */
    public static float getIncassiTotali()
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT SUM(costo * quantita) AS somma_costi_per_quantita FROM venduto;");
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getGuadagniTotali</code> restituisce i guadagni totali contenuti nel DB
     * @return float, nel caso di risultato vuoto 0
     */
    public static float getGuadagniTotali()
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT SUM(guadagno) FROM venduto;");
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getIncassi</code> restituisce gli incassi avuti in un periodo inserito
     * @param inizio delimitazione del periodo inferiore
     * @param fine delimitazione del periodo superiore
     * @return float, in caso di nessun riscontro tornerà 0.
     */
    public static float getIncassi(LocalDate inizio, LocalDate fine)
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT SUM(costo * quantita) AS somma_costi_per_quantita FROM venduto WHERE giorno BETWEEN ? AND ?;");
            ps.setString(1, String.valueOf(inizio));
            ps.setString(2, String.valueOf(fine));
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getGuadagni</code> restituisce i guadagni avuti in un periodo inserito
     * @param inizio delimitazione del periodo inferiore
     * @param fine delimitazione del periodo superiore
     * @return float, in caso di nessun riscontro tornerà 0.
     */
    public static float getGuadagni(LocalDate inizio, LocalDate fine)
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT SUM(guadagno) FROM venduto WHERE giorno BETWEEN ? AND ?;");
            ps.setString(1, String.valueOf(inizio));
            ps.setString(2, String.valueOf(fine));
            val rs = ps.executeQuery();
            return rs.next() ? rs.getFloat(1) : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
