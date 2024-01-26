package it.unisa.saporidiunisa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lotto
{
    private int id;
    private float costo;
    private LocalDate dataScadenza;
    private int quantita;
    private int quantitaAttuale;
    private Fornitura fornitura;
    private Prodotto prodotto;

    public float getCostoProdotto(){
        return this.costo/this.quantita;
    }

    public String getInfo() {
        return "Id lotto:" + id +
                ", costo totale: " + costo +
                ", dataScadenza: " + dataScadenza +
                ", quantità: " + quantita +
                ", " + prodotto.getInfo();
    }
}