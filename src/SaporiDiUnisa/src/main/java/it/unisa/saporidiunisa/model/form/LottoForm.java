package it.unisa.saporidiunisa.model.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LottoForm {

    public String nome;
    public String marchio;
    public float prezzo;
    public int quantita;
    public LocalDate dataScadenza;

    private void _privateConstructor(final String nome, final String marchio, final float prezzo, final int quantita, final LocalDate dataScadenza) {
        this.nome = nome;
        this.marchio = marchio;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.dataScadenza = dataScadenza;
    }

    public String validate(final String nome, final String marchio, final String prezzo, final String quantita, final String dataScadenza) {
        val s = new StringBuilder();

        if(nome.isEmpty() || nome.isBlank())
            s.append("Il nome non può essere vuoto\n");
        else if(nome.length() < 2 || nome.length() > 255)
            s.append("Il nome deve essere compreso tra 2 e 255 caratteri\n");

        if(marchio.isEmpty() || marchio.isBlank())
            s.append("Il marchio non può essere vuoto\n");
        else if(marchio.length() < 2 || marchio.length() > 255)
            s.append("Il marchio deve essere compreso tra 2 e 255 caratteri\n");

        float _prezzo = 0;
        if(prezzo.isEmpty() || prezzo.isBlank()) {
            s.append("Il prezzo non può essere vuoto\n");
        }
        else{
            boolean flag = false;
            try {
                _prezzo = Float.parseFloat(prezzo);
            } catch (NumberFormatException e) {
                s.append("Il prezzo deve essere un numero\n");
                flag = true;
            }
            if(!flag) {
                if(_prezzo <= 0)
                    s.append("Il prezzo deve essere compreso tra 0 e 100000.00\n");
                else if(_prezzo >= 100000.00)
                    s.append("Il prezzo deve essere compreso tra 0 e 100000.00\n");
            }
        }

        int _quantita = 0;
        if(quantita.isEmpty() || quantita.isBlank()) {
            s.append("La quantità non può essere vuota\n");
        }
        else{
            boolean flag = false;
            try {
                _quantita = Integer.parseInt(quantita);
            } catch (NumberFormatException e) {
                s.append("La quantità deve essere un numero\n");
                flag = true;
            }
            if(!flag) {
                if(_quantita <= 0)
                    s.append("La quantità deve essere maggiore di 0\n");
                else if(_quantita >= 1000000)
                    s.append("La quantità deve essere minore di 1000000\n");
            }
        }

        LocalDate _dataScadenza = null;
        if(dataScadenza.isEmpty() || dataScadenza.isBlank()) {
            s.append("La data di scadenza non può essere vuota\n");
        }
        else{
            boolean flag = false;
            try {
                _dataScadenza = LocalDate.parse(dataScadenza);
            } catch (Exception e) {
                s.append("La data di scadenza non valida\n");
                flag = true;
            }
            if(!flag){
                if(_dataScadenza.isBefore(LocalDate.now()) || _dataScadenza.isEqual(LocalDate.now()))
                    s.append("La data di scadenza deve essere anteriore a quella odierno\n");
            }
        }

        // Se non ci sono errori di validazione, costruisco l'oggetto
        if(s.isEmpty())
            this._privateConstructor(nome, marchio, _prezzo, _quantita, _dataScadenza);

        return s.toString();
    }
}
