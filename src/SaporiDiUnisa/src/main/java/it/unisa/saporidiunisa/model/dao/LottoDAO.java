package it.unisa.saporidiunisa.model.dao;

import it.unisa.saporidiunisa.model.entity.Fornitura;
import it.unisa.saporidiunisa.model.entity.Lotto;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Database;
import lombok.val;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * La classe <code>LottoDAO</code> gestisce le transazioni nel DB con la tabella Lotto
 */
public class LottoDAO
{
    /**
     * Il metodo <code>insert</code> inserisce nel DB un nuovo Lotto
     * @param lotto Lotto da inserire
     */
    public static void insert(final Lotto lotto)
    {
        try (val con = Database.getConnection())
        {
            val ps = con.prepareStatement("insert into lotto(costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) values(?, ?, ?, ?, ?, ?)");
            ps.setFloat(1, lotto.getCosto());
            ps.setDate(2, Date.valueOf(lotto.getDataScadenza()));
            ps.setInt(3, lotto.getQuantita());
            ps.setInt(4, lotto.getQuantitaAttuale());
            ps.setInt(5, lotto.getFornitura().getId());
            ps.setInt(6, lotto.getProdotto().getId());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getSpeseTotali</code> restituisce la somma delle spese effettuate per tutti i lotti nel DB
     * @return float, nel caso di nessun riscontro 0
     */
    public static float getSpeseTotali()
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT SUM(costo) FROM lotto;");
            val rs = ps.executeQuery();

            return rs.next() ? rs.getFloat(1) : 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getIncassi</code> restituisce le spese avute in un periodo inserito
     * @param inizio delimitazione del periodo inferiore
     * @param fine delimitazione del periodo superiore
     * @return float, in caso di nessun riscontro tornerà 0.
     */
    public static float getSpese(LocalDate inizio, LocalDate fine)
    {
        try (val connection = Database.getConnection())
        {
            val ps = connection.prepareStatement("SELECT sum(l.costo) FROM lotto l JOIN fornitura f ON l.fornitura = f.id WHERE f.giorno BETWEEN ? AND ?;");
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
     * Restituisce tutti i lotti scaduti con solo gli attributi che servono
     * @return ArrayList di lotti
     */
    public static ArrayList<Lotto> getPerditeTotali()
    {
        try (val connection = Database.getConnection())
        {
            val lotti = new ArrayList<Lotto>();
            val ps = connection.prepareStatement("SELECT id, costo, quantita, quantita_attuale FROM lotto WHERE data_scadenza<CURDATE() AND quantita_attuale>0;");
            val rs = ps.executeQuery();

            while (rs.next())
            {
                val l = new Lotto();
                l.setId(rs.getInt(1));
                l.setCosto(rs.getFloat(2));
                l.setQuantita(rs.getInt(3));
                l.setQuantitaAttuale(rs.getInt(4));
                lotti.add(l);
            }

            return lotti;

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restituisce tutte le perdite date dai lotti scaduti in quel periodo
     * @param inizio data di inizio periodo
     * @param fine data di fine periodo
     * @return ArrayList di lotti scaduti in quei giorni
     */
    public static ArrayList<Lotto> getPerdite(LocalDate inizio, LocalDate fine)
    {
        try (val connection = Database.getConnection())
        {
            val lotti = new ArrayList<Lotto>();
            val ps = connection.prepareStatement("SELECT id, costo, quantita, quantita_attuale FROM lotto WHERE data_scadenza BETWEEN ? AND ? AND quantita_attuale>0;");
            ps.setString(1, String.valueOf(inizio));
            ps.setString(2, String.valueOf(fine));
            val rs = ps.executeQuery();

            while (rs.next())
            {
                val l = new Lotto();
                l.setId(rs.getInt(1));
                l.setCosto(rs.getFloat(2));
                l.setQuantita(rs.getInt(3));
                l.setQuantitaAttuale(rs.getInt(4));
                lotti.add(l);
            }

            return lotti;

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * Il metodo <code>getLottiWithoutEsposizione</code> restituisce tutti i lotti non scaduti nel DB che non hanno nessun prodotto in esposizione
     * @return ArrayList di lotti
     */
    public static ArrayList<Lotto> getLottiWithoutEsposizione()
    {
        try (val connection = Database.getConnection())
        {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT lotto.id, lotto.costo, lotto.data_scadenza, lotto.quantita, lotto.quantita_attuale, lotto.fornitura, lotto.prodotto, fornitura.giorno, prodotto.nome, prodotto.marchio, prodotto.prezzo, prodotto.prezzo_scontato, prodotto.inizio_sconto, prodotto.fine_sconto, prodotto.foto " +
                            "FROM lotto " +
                            "INNER JOIN fornitura ON lotto.fornitura = fornitura.id " +
                            "INNER JOIN prodotto ON lotto.prodotto = prodotto.id " +
                            "WHERE lotto.quantita_attuale > 0 AND lotto.data_scadenza > CURDATE() AND lotto.id NOT IN (SELECT esposizione.lotto FROM esposizione WHERE esposizione.lotto = lotto.id);");

            ResultSet rs = ps.executeQuery();
            ArrayList<Lotto> lottiMagazzino = new ArrayList<>();
            while (rs.next())
            {
                Lotto lotto = new Lotto();
                Fornitura fornitura = new Fornitura();
                Prodotto prodotto = new Prodotto();

                lotto.setId(rs.getInt(1));
                lotto.setCosto(rs.getFloat(2));
                lotto.setDataScadenza(LocalDate.parse(rs.getString(3)));
                lotto.setQuantita(rs.getInt(4));
                lotto.setQuantitaAttuale(rs.getInt(5));

                fornitura.setId(rs.getInt(6));
                fornitura.setGiorno(LocalDate.parse(rs.getString(8)));
                lotto.setFornitura(fornitura);

                prodotto.setId(rs.getInt(7));
                prodotto.setNome(rs.getString(9));
                prodotto.setMarchio(rs.getString(10));
                prodotto.setPrezzo(rs.getFloat(11));
                prodotto.setPrezzoScontato(rs.getFloat(12));
                if(rs.getDate(13) != null)
                    prodotto.setInizioSconto(rs.getDate(13).toLocalDate());
                if(rs.getDate(14) != null)
                    prodotto.setFineSconto(rs.getDate(14).toLocalDate());
                prodotto.setFoto(rs.getBytes(15));
                lotto.setProdotto(prodotto);

                lottiMagazzino.add(lotto);
            }
            return lottiMagazzino;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>diminuisciLotto</code> diminuisce la quantità attuale del lotto
     * @param id id del Lotto
     * @param qnt quantità da diminuire nel DB
     */
    public static void diminuisciLotto(int id, int qnt){

        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE lotto SET quantita_attuale = quantita_attuale - ? WHERE id = ?");
            ps.setInt(1, qnt);
            ps.setInt(2, id);

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
     * Il metodo <code>eliminaLotto</code> elimina un lotto scelto
     * @param l Lotto da eliminare
     */
    public static void eliminaLotto(Lotto l){

        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE lotto SET data_scadenza = DATE_SUB(CURDATE(), INTERVAL 1 DAY) WHERE id = ?");
            ps.setInt(1, l.getId());

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
     * Il metodo <code>getLottoById</code> restituisce un lotto per un id inserito
     * @param id id del lotto da ricercare
     * @return Lotto oppure null nel caso non esista un lotto con l'id passato
     */
    public static Lotto getLottoById(int id){
        try (val connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
            "SELECT l.id, l.costo, l.data_scadenza, l.quantita, l.quantita_attuale, f.id AS fornitura_id, f.giorno AS data_fornitura, p.id AS idProdotto, p.nome, p.marchio, p.prezzo, p.prezzo_scontato, p.inizio_sconto, p.fine_sconto, p.foto " +
                "FROM lotto l JOIN fornitura f ON l.fornitura = f.id JOIN prodotto p ON l.prodotto = p.id " +
                "WHERE l.id = ?"
            );
            ps.setInt(1, id);
            val rs = ps.executeQuery();
            if(rs.next()) {
                // Creare un oggetto Prodotto
                val prodotto = ProdottoDAO.buildBySQL(rs);

                // Creare oggetto Lotto con i dati ottenuti dalla query
                val lotto = new Lotto(
                    rs.getInt("id"),
                    rs.getFloat("costo"),
                    rs.getDate("data_scadenza").toLocalDate(),
                    rs.getInt("quantita"),
                    rs.getInt("quantita_attuale"),
                    null,
                    prodotto
                );

                // Creare oggetto Fornitura
                val fornitura = new Fornitura(
                    rs.getInt("fornitura_id"),
                    rs.getDate("data_fornitura").toLocalDate(),
                    null
                );
                lotto.setFornitura(fornitura);

                return lotto;
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>getMagazzino</code> restituisce tutto il magazzino corrente con tutti i lotti non scaduti per ogni prodotto
     * @return mappa con lotti non scaduti per ogni prodotto
     */
    public static HashMap<Prodotto, ArrayList<Lotto>> getMagazzino(){
        try (val connection = Database.getConnection()) {
            val ps = connection.prepareStatement(
            "SELECT l.id, l.costo, l.data_scadenza, l.quantita, l.quantita_attuale, f.id AS fornitura_id, f.giorno, p.id as idProdotto, nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto " +
                "FROM prodotto p " +
                "LEFT JOIN lotto l ON p.id = l.prodotto " +
                "LEFT JOIN fornitura f ON l.fornitura = f.id " +
                "WHERE l.data_scadenza >= CURDATE();"
            );
            val rs = ps.executeQuery();
            val prodottiMap = new HashMap<Prodotto, ArrayList<Lotto>>();
            while(rs.next()){
                val prodotto = ProdottoDAO.buildBySQL(rs);

                if(_productIsInHashMap(prodottiMap, prodotto) == null)
                    prodottiMap.put(prodotto, new ArrayList<>());

                if(rs.getInt("quantita_attuale") > 0) {
                    val lotto = new Lotto(
                        rs.getInt("id"),
                        rs.getFloat("costo"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getInt("quantita"),
                        rs.getInt("quantita_attuale"),
                        null,
                        prodotto
                    );

                    val fornitura = new Fornitura(
                        rs.getInt("fornitura_id"),
                        rs.getDate("giorno").toLocalDate(),
                        null
                    );
                    lotto.setFornitura(fornitura);

                    Objects.requireNonNull(_productIsInHashMap(prodottiMap, prodotto)).add(lotto);
                }
            }

            return prodottiMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>_productIsInHashMap</code> verifica se il prodotto passato è gia contenuto nella hashmap
     * @param hashMap del magazzino, con un array di lotti per ogni prodotto
     * @param prodotto Prodotto da ricercare
     * @return ArrayList di lotti o null
     */
    private static ArrayList<Lotto> _productIsInHashMap(final HashMap<Prodotto, ArrayList<Lotto>> hashMap, final Prodotto prodotto){
        for (val p : hashMap.keySet())
            if (p.getId() == prodotto.getId())
                return hashMap.get(p);
        return null;
    }

    /**
     * La funzione updateDataScadenza modifica in un lotto la sua data scadenza, utilizzata solo per i rollback nei unit test
     * @param lottoTmp lotto al quale modificare la data.
     * @param dataScadenza nuova data da impostare
     */
    public static void updateDataScadenza(Lotto lottoTmp, LocalDate dataScadenza) {
        try (val connection = Database.getConnection()) {
            val ps = connection.prepareStatement("UPDATE lotto SET data_scadenza = ? WHERE id = ?");
            ps.setDate(1, Date.valueOf(dataScadenza));
            ps.setInt(2, lottoTmp.getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
