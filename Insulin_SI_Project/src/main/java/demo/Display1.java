package demo;

import java.util.*;

public class Display1 {

    static final String serialNumber = "D1-48987";
    static final Integer controlValue = 100;

    public String batteryLevel;
    public String insulineResidueQuantity;
    public String glucose;
    public String glucoseVariation;
    public String dose;

    public boolean pomp;
    public boolean battery;
    public boolean lowInsuline;
    public boolean highInsuline;

    //tre funzioni che impostano i parametri per una futura stampa
    public void setEmergencyInsuline(boolean low, boolean high){
        this.lowInsuline = low;
        this.highInsuline = high;
    }

    public void emergencyPrinting(boolean pomp, boolean battery){
        this.pomp=pomp;
        this.battery=battery;
    }

    public void setPrintInfo(List<String> param){
        this.batteryLevel=param.get(0);
        this.insulineResidueQuantity=param.get(1);
        this.glucose= param.get(2);
        this.glucoseVariation=param.get(3);
        this.dose=param.get(4);
    }

    //ritorna i valori di controllo
    public List<Object> getCheckValues(){
        List<Object> result = new LinkedList<>();
        result.add(serialNumber);
        result.add(controlValue);
        return result;
    }

}
