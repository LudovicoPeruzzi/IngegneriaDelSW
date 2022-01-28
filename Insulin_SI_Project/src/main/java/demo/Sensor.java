package demo;

import java.util.*;
import java.lang.Math;

public class Sensor {

    static final String serialNumber = "S-718395";
    static final int controlValue = 500;

    public double glucoseLevel;
    public double lastMeasurement;

    //funzione che ritorna il valore attuale di glucosio del sangue
    public double getGlucose(){
        this.glucoseLevel = (Math.random()*600);
        this.lastMeasurement= this.glucoseLevel;
        return this.glucoseLevel;
    }

    //funzione che ritorna i valori di controllo
    public List<Object> getCheckValues(){
        List<Object> result = new LinkedList<>();
        result.add(serialNumber);
        result.add(controlValue);
        result.add(this.lastMeasurement);
        return result;
    }
}
