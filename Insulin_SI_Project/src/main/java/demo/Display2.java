package demo;

import java.util.LinkedList;
import java.util.List;

public class Display2 {

    static final String serialNumber = "D2-39187";
    static final Integer controlValue = 200;

    public String batteryLevel;
    public String insulineResidueQuantity;

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
    }

    //ritorna i valori di controllo
    public List<Object> getCheckValues(){
        List<Object> result = new LinkedList<>();
        result.add(serialNumber);
        result.add(controlValue);
        return result;
    }
}
