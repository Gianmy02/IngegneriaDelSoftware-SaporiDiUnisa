package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * La classe <code>EsposizioneDAO</code> gestisce le transazioni nel DB per la tabella esposizione
 */
public class EsposizioneDAO
{

    /**
     * Il metodo <code>getEsposizione</code> restituisce tutti i prodotti in esposizione
     * @return lista di prodotti in esposizione
     */
    public static ArrayList<Esposizione> getEsposizione(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT esposizione.prodotto, esposizione.lotto, esposizione.quantita, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto from esposizione, prodotto, lotto WHERE lotto.id = esposizione.lotto AND esposizione.prodotto = prodotto.id ORDER BY lotto.data_scadenza;");
            ResultSet rs = ps.executeQuery();
            ArrayList<Esposizione> esposti = new ArrayList<>();
            while(rs.next()){
                Esposizione e = new Esposizione();
                Prodotto p = new Prodotto();
                Lotto l = new Lotto();
                p.setId(rs.getInt(1));
                l.setId(rs.getInt(2));
                e.setQuantita(rs.getInt(3));
                l.setCosto(rs.getFloat(4));
                l.setDataScadenza(LocalDate.parse(rs.getString(5)));
                l.setQuantita(rs.getInt(6));
                l.setQuantitaAttuale(rs.getInt(7));
                p.setNome(rs.getString(8));
                p.setMarchio(rs.getString(9));
                p.setPrezzo(rs.getFloat(10));
                p.setPrezzoScontato(rs.getFloat(11));
                if((rs.getString(12)) != null)
                    p.setInizioSconto(LocalDate.parse(rs.getString(12)));
                if((rs.getString(13)) != null)
                    p.setFineSconto(LocalDate.parse(rs.getString(13)));
                p.setFoto(rs.getBytes(14));
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

    /**
     * Il metodo <code>getLottibyProdottoWithoutScaduti</code> restituisce i lotti che sono in esposizione e che non sono scaduti, relativi al prodotto passato come parametro
     * @param p prodotto del quale si vogliono ottenere i lotti in esposizione
     * @return lista di lotti in esposizione relativi al prodotto senza scaduti
     */
    public static ArrayList<Esposizione> getLottibyProdottoWithoutScaduti(Prodotto p){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT l.id, l.costo, l.data_scadenza, l.quantita, l.quantita_attuale, e.quantita, f.* FROM esposizione e JOIN lotto l ON e.lotto = l.id JOIN fornitura f ON l.fornitura = f.id WHERE l.data_scadenza >= CURRENT_DATE() AND e.prodotto = ? GROUP BY e.lotto, l.data_scadenza ORDER BY l.data_scadenza;");
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Esposizione> esposizioni = new ArrayList<>();
            while(rs.next()){
                Lotto l = new Lotto();
                Esposizione e = new Esposizione();
                Fornitura f = new Fornitura();
                l.setId(rs.getInt(1));
                l.setCosto(rs.getFloat(2));
                l.setDataScadenza(LocalDate.parse(rs.getString(3)));
                l.setQuantita(rs.getInt(4));
                l.setQuantitaAttuale(rs.getInt(5));
                e.setQuantita(rs.getInt(6));
                f.setId(rs.getInt(7));
                f.setGiorno(LocalDate.parse(rs.getString(8)));
                l.setProdotto(p);
                e.setProdotto(p);
                l.setFornitura(f);
                e.setLotto(l);
                esposizioni.add(e);
            }
            return esposizioni;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>diminuisciEsposizione</code> diminuisce la quantità dell'esposizione passate e, se il parametro elimina è true, elimina l'esposizione
     * @param quantita è la quantita da sottrarre all'esposizione
     * @param es è l'esposizione che si intende diminuire
     * @param elimina, se true vuol dire che la quantità rimasta in esposizione è 0, dunque elimina anche l'esposizione; se false non la elimina
     */
    public static void diminuisciEsposizione(int quantita, Esposizione es, boolean elimina){
        try (val connection = Database.getConnection()) {
            if(elimina){
                PreparedStatement ps = connection.prepareStatement(
                        "DELETE FROM esposizione WHERE prodotto = ? AND lotto = ?");
                ps.setInt(1, es.getProdotto().getId());
                ps.setInt(2, es.getLotto().getId());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("DELETE error.");
                }
            }else{
                PreparedStatement ps = connection.prepareStatement(
                        "UPDATE esposizione SET quantita = quantita - ? WHERE esposizione.prodotto = ? AND esposizione.lotto = ?");
                ps.setInt(1, quantita);
                ps.setInt(2, es.getProdotto().getId());
                ps.setInt(3, es.getLotto().getId());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("UPDATE error.");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>aumentaEsposizione</code> aumenta la quantità di un prodotto gia in esposizione per un determinato lotto
     * @param quantita è la quantità da aggiungere
     * @param es contiene il prodotto e il lotto da aumentare
     */
    public static void aumentaEsposizione(int quantita, Esposizione es){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE esposizione SET quantita = quantita + ? WHERE esposizione.prodotto = ? AND esposizione.lotto = ?");
            ps.setInt(1, quantita);
            ps.setInt(2, es.getProdotto().getId());
            ps.setInt(3, es.getLotto().getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>inserisciEsposizione</code> mette in esposizione un prodotto appartenente a un lotto che non si trovava in esposizione
     * @param quantita quantità da mettere in esposizione
     * @param l lotto contenente i prodotti
     */
    public static void inserisciEsposizione(int quantita, Lotto l){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO esposizione(prodotto, lotto, quantita) VALUES (?, ?, ?)");
            ps.setInt(1, l.getProdotto().getId());
            ps.setInt(2, l.getId());
            ps.setInt(3, quantita);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>visualizzaProdottiEspostiCassiere</code> restituisce i prodotti che sono in esposizione utile per la vista del cassiere sui prodotti
     * @return lista di prodotti in esposizione
     */
    public static ArrayList<Esposizione> visualizzaProdottiEspostiCassiere(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT e.prodotto, p.nome, p.marchio, p.foto, CASE WHEN p.inizio_sconto <= CURRENT_DATE() AND p.fine_sconto >= CURRENT_DATE() THEN p.prezzo_scontato ELSE p.prezzo END AS prezzo, SUM(e.quantita) AS quantita_totale FROM esposizione e JOIN lotto l ON e.lotto = l.id JOIN prodotto p ON e.prodotto = p.id WHERE l.data_scadenza >= CURRENT_DATE() GROUP BY e.prodotto, p.nome, p.marchio, prezzo HAVING quantita_totale > 0;");
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

    /**
     * Il metodo <code>getEsposizioneScaduti</code> restituisce tutti i prodotti scaduti in esposizione
     * @return lista di prodotti scaduti in esposizione
     */
    public static ArrayList<Esposizione> getEsposizioneScaduti(){
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT esposizione.prodotto, esposizione.lotto, esposizione.quantita, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto from esposizione, prodotto, lotto WHERE lotto.id = esposizione.lotto AND esposizione.prodotto = prodotto.id AND lotto.data_scadenza < CURDATE();");
            ResultSet rs = ps.executeQuery();
            ArrayList<Esposizione> esposti = new ArrayList<>();
            while(rs.next()){
                Esposizione e = new Esposizione();
                Prodotto p = new Prodotto();
                Lotto l = new Lotto();
                p.setId(rs.getInt(1));
                l.setId(rs.getInt(2));
                e.setQuantita(rs.getInt(3));
                l.setCosto(rs.getFloat(4));
                l.setDataScadenza(LocalDate.parse(rs.getString(5)));
                l.setQuantita(rs.getInt(6));
                l.setQuantitaAttuale(rs.getInt(7));
                p.setNome(rs.getString(8));
                p.setMarchio(rs.getString(9));
                p.setPrezzo(rs.getFloat(10));
                p.setPrezzoScontato(rs.getFloat(11));
                if((rs.getString(12)) != null)
                    p.setInizioSconto(LocalDate.parse(rs.getString(12)));
                if((rs.getString(13)) != null)
                    p.setFineSconto(LocalDate.parse(rs.getString(13)));
                p.setFoto(rs.getBytes(14));
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

    /**
     * Il metodo <code>rimuoviScaduto</code> elimina l'esposizione passata (scaduta) e azzera a quantità del lotto
     * @param es esposizione scadura da rimuovere
     */
    public static void rimuoviScaduto(Esposizione es){

        //se la quantita è 0, inutile la query
        try (val connection = Database.getConnection()) {
            if(es.getQuantita()>0) {
                PreparedStatement ps = connection.prepareStatement(
                        "UPDATE lotto SET quantita_attuale = quantita_attuale + ? WHERE id = ?");
                ps.setInt(1, es.getQuantita());
                ps.setInt(2, es.getLotto().getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("UPDATE error.");
                }
            }
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM esposizione WHERE prodotto = ? AND lotto = ?");
            ps.setInt(1, es.getProdotto().getId());
            ps.setInt(2, es.getLotto().getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Il metodo <code>getEsposizioneByLotto</code> restituisce l'esposizione relativa al lotto fornito
     * @param l lotto di cui interessa l'esposizione
     */
    public static Esposizione getEsposizioneByLotto(Lotto l){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT prodotto, quantita FROM esposizione WHERE lotto = ?");
            ps.setInt(1, l.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Esposizione e = new Esposizione();
                Prodotto p = new Prodotto();
                e.setLotto(l);
                p.setId(rs.getInt(1));
                e.setProdotto(p);
                e.setQuantita(rs.getInt(2));
                return e;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getEspostiByProdotto</code> restituisce la quantità totale del prodotto esposto passato come parametro
     * @param p Prodotto del quale ci interessa la quantità
     * @return intero, quantità in esposizione
     */
    public static int getEspostiByProdotto(Prodotto p){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT SUM(e.quantita) AS quantita_totale FROM esposizione e JOIN lotto l ON e.lotto = l.id JOIN prodotto p ON e.prodotto = p.id WHERE l.data_scadenza >= CURRENT_DATE() AND e.prodotto = ? GROUP BY e.prodotto, p.nome, p.marchio, prezzo HAVING quantita_totale > 0;");
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
