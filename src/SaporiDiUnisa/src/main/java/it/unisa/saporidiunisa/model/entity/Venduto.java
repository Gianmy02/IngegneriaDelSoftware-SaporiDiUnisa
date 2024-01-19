package it.unisa.saporidiunisa.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Venduto
{
    private Prodotto prodotto;
    private int quantita;
    private float guadagno;
    private float costo;
    private LocalDate giorno;

    public boolean validate(int quantitaEsposta){
        if(this.prodotto!=null && this.prodotto.getId()!=0) {
            if(this.quantita>0 && this.quantita<100000){
                if(quantitaEsposta - this.quantita>=0){
                    if(this.costo>=0 && this.costo<100000)
                     return true;
                }
            }
        }
        return false;
    }
}