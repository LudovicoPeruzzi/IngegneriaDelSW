package demo;

import java.util.*;

public class InsulinPomp {

    static final String serialNumber = "P-174362";
    static final Integer controlValue = 300;

    public int dose;
    public int quantity = 350;

    //ritona una lista con il valore della quantità residua di insulina e, quando serve, il valore di "allarme"
    public List<Integer> getQuantity(){
        List<Integer> result = new LinkedList<>();
        this.quantity=this.quantity-dose;
        result.add(this.quantity);
        if (this.quantity < 60){
               result.add(700);
        }

        return result;
    }

    //imposta la dose da somministrare e ritorna il valore ritornato da getQuantity()
    public List<Integer> setDose(Integer dose){
        this.dose=dose;
        if( this.dose != 0){injection();}
        return getQuantity();
    }

    public void injection(){}; //funzione, non implementata, per comandare all'hw della pompa di iniettare la dose di insulina

    //ritorna i valori di controllo
    public List<Object> getCheckValues(){
        List<Object> result = new LinkedList<>();
        result.add(serialNumber);
        result.add(controlValue);
        result.add(this.quantity);
        result.add(this.dose);
        return result;
    }

}
