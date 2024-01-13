package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.model.entity.Venduto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestioneVenditeDAO {
    public ArrayList<Esposizione> visualizzaProdottiEsposti(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT e.prodotto, p.nome, p.marchio, p.foto, CASE WHEN p.inizio_sconto <= CURRENT_DATE() AND p.fine_sconto >= CURRENT_DATE() THEN p.prezzo_scontato ELSE p.prezzo END AS prezzo, SUM(e.quantita) AS quantita_totale FROM esposizione e JOIN lotto l ON e.lotto = l.id JOIN prodotto p ON e.prodotto = p.id WHERE l.data_scadenza >= CURRENT_DATE() GROUP BY e.prodotto, p.nome, p.marchio, prezzo;");
            ResultSet rs = ps.executeQuery();
            ArrayList<Esposizione> esposti = new ArrayList<>();
            while(rs.next()){
                Esposizione e = new Esposizione();
                Prodotto p = new Prodotto();
                Lotto l = new Lotto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setMarchio(rs.getString(3));
                p.setFoto(rs.getBytes(4));
                p.setPrezzo(rs.getFloat(5));
                e.setQuantita(rs.getInt(6));
                e.setProdotto(p);
                e.setLotto(l);
                esposti.add(e);
            }
            return esposti;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Venduto> getVendutiGiornalieri(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM prodotto, venduto WHERE venduto.prodotto = prodotto.id AND venduto.giorno = CURDATE()");
            ResultSet rs = ps.executeQuery();
            ArrayList<Venduto> venduti = new ArrayList<>();
            while(rs.next()){
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
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Venduto getVendutiGiornalieroByProdotto(Prodotto p){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM venduto WHERE venduto.prodotto = ? AND venduto.giorno = CURDATE()");
            ps.setInt(p.getId(), 1);
            ResultSet rs = ps.executeQuery();
            Venduto v = new Venduto();
            while(rs.next()){

                v.setCosto(rs.getFloat(1));
                v.setQuantita(rs.getInt(2));
                v.setGuadagno(rs.getFloat(3));
                v.setProdotto(p);
                v.setGiorno(LocalDate.parse(rs.getString(5)));
            }
            return v;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean doSaveVendita(Venduto v){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO venduto (costo, quantita, guadagno, prodotto, giorno) VALUES(?,?,?,?,CURDATE())");
            ps.setFloat(1,v.getCosto());
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

    public void doSaveGiornoLavorativo(){
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

    public boolean doUpdateVendita(Venduto v){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE venduto SET guadagno = guadagno + ?, quantita = quantita + ? WHERE venduto.prodotto = ? AND venduto.giorno = ?");
            ps.setFloat(1, v.getGuadagno());
            ps.setInt(2, v.getQuantita());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void diminuisciEsposizione(int quantita, Esposizione es){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE esposizione SET quantita = quantita - ? WHERE esposizione.prodotto = ? AND esposizione.lotto = ?");
            ps.setInt(1, quantita);
            ps.setInt(2, es.getProdotto().getId());
            ps.setInt(3, es.getLotto().getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
