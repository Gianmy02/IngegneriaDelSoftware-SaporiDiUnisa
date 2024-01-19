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

public class VendutoDAO {

    public ArrayList<Venduto> getVendutiGiornalieri() {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM prodotto, venduto WHERE venduto.prodotto = prodotto.id AND venduto.giorno = CURDATE()");
            ResultSet rs = ps.executeQuery();
            ArrayList<Venduto> venduti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                Venduto v = new Venduto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setMarchio(rs.getString(3));
                p.setPrezzo(rs.getFloat(4));
                p.setPrezzoScontato(rs.getFloat(5));
                p.setInizioSconto(LocalDate.parse(rs.getString(6)));
                p.setFineSconto(LocalDate.parse(rs.getString(7)));
                p.setFoto(rs.getBytes(8));
                v.setCosto(rs.getFloat(9));
                v.setQuantita(rs.getInt(10));
                v.setGuadagno(rs.getFloat(11));
                v.setProdotto(p);
                v.setGiorno(LocalDate.parse(rs.getString(13)));
                venduti.add(v);
            }
            return venduti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Venduto getVendutiGiornalieroByProdotto(Prodotto p) {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM venduto WHERE venduto.prodotto = ? AND venduto.giorno = CURDATE()");
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();
            Venduto v = new Venduto();
            while (rs.next()) {
                v.setCosto(rs.getFloat(1));
                v.setQuantita(rs.getInt(2));
                v.setGuadagno(rs.getFloat(3));
                v.setProdotto(p);
                v.setGiorno(LocalDate.parse(rs.getString(5)));
                return v;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doSaveVendita(Venduto v) {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO venduto (costo, quantita, guadagno, prodotto, giorno) VALUES(?,?,?,?,CURDATE())");
            ps.setFloat(1, v.getCosto());
            ps.setInt(2, v.getQuantita());
            ps.setFloat(3, v.getGuadagno());
            ps.setInt(4, v.getProdotto().getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSaveGiornoLavorativo() {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO vendita (giorno) VALUES(CURDATE())");
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean searchGiornoLavorativo() {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM vendita WHERE giorno = CURDATE()");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doUpdateVendita(Venduto v) {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE venduto SET guadagno = guadagno + ?, quantita = quantita + ? WHERE venduto.prodotto = ? AND venduto.giorno = CURDATE()");
            ps.setFloat(1, v.getGuadagno());
            ps.setInt(2, v.getQuantita());
            ps.setInt(3, v.getProdotto().getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*la funzione prende la somma dal db nei giorni richiesti di tutti i prodotti*/
    public ArrayList<Venduto> getStorico(LocalDate inizio, LocalDate fine) {
        try (val connection = Database.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT p.id AS id_prodotto, p.nome AS nome_prodotto, p.marchio, p.foto, SUM(v.quantita) AS totale_quantita_venduta, SUM(v.guadagno) AS totale_guadagno, SUM(v.costo * v.quantita) AS costo_totale FROM prodotto p JOIN venduto v ON p.id = v.prodotto WHERE v.giorno BETWEEN ? AND ? GROUP BY p.id, p.nome, p.marchio, p.foto;");
            ps.setString(1, String.valueOf(inizio));
            ps.setString(2, String.valueOf(fine));
            ResultSet rs = ps.executeQuery();
            ArrayList<Venduto> venduti = new ArrayList<>();
            while (rs.next()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static float getIncassiTotali() {
        try (val connection = Database.getConnection()) {
            val ps = connection.prepareStatement("SELECT SUM(costo * quantita) AS somma_costi_per_quantita FROM venduto;");
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static float getGuadagniTotali() {
        try (val connection = Database.getConnection()) {
            val ps = connection.prepareStatement("SELECT SUM(guadagno) FROM venduto;");
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
