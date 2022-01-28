package demo;

import java.util.*;

public class Controller {

    private String sensSerialN = "S-718395";
    private Integer sensControlV= 500;
    private String pompSerialN= "P-174362";
    private Integer pompControlV= 300;
    private String d1SerialN= "D1-48987";
    private Integer d1ControlV= 100;
    private String d2SerialN= "D2-39187";
    private Integer d2ControlV= 200;
    private String alrSerialN= "A-568743";
    private Integer alrControlV= 33;
    private int cicle;

    public InsulinPomp pomp = new InsulinPomp();
    public Sensor sensor = new Sensor();
    public Display1 display1 = new Display1();
    public Display2 display2 = new Display2();
    public Alarm alarm = new Alarm();

    public double batteryLevel = 100.0;
    public int currentInsQuantity= 350; //si assume che la pompa, all'accensione, sia piena.
    public double glucoseVariation = 0.0;
    public int dose = 0;
    public double glucose = 0.0;
    public boolean sisCheck= true;
    public int device=0;
    public double lastMeasurement=0.0;

    //richiede i valori glicemici al sensore
    public void glucoseFromSensor(){

        this.glucose = sensor.getGlucose();
        this.calculateDose();

    }

    //calcola la dose da iniettare in seguito alla misurazione del glucosio
    public void calculateDose(){
        if (cicle<1){
            glucoseVariation=lastMeasurement-glucose;
            glucoseVariation*=-1;}
        else{glucoseVariation=lastMeasurement-glucose;}
        cicle++;
        if (glucose >= 500.0 ){
            this.dose=16;
            display1.setEmergencyInsuline(false,true);
            display2.setEmergencyInsuline(false,true);
            alarm.alarmRinging(true);
            }
        if (glucose < 500.0 && glucose >= 450.0){this.dose=14;}
        if (glucose < 450.0 && glucose >= 400.0){ this.dose=12;}
        if (glucose < 400.0 && glucose >= 350.0){ this.dose=10;}
        if (glucose < 350.0 && glucose >= 300.0){ this.dose=8;}
        if (glucose < 300.0 && glucose >= 250.0){ this.dose=6;}
        if (glucose < 250.0 && glucose >= 200.0){ this.dose=4;}
        if (glucose < 200.0 && glucose >= 126.0){ this.dose=2;}
        if (glucose < 126 && glucose >= 76) { this.dose = 0;}
        if (glucose < 76.0){
            this.dose = 0;
            display1.setEmergencyInsuline(true,false);
            display2.setEmergencyInsuline(true,false);
            alarm.alarmRinging(true);
            }
        lastMeasurement = glucose;
    }

    //invia la dose alla pompa e controlla che il valore residuo di insulina nella pompa sia corretto
    public void sendDoseToPomp(){
        List<Integer> pompResponse=pomp.setDose(this.dose);
        this.currentInsQuantity-=this.dose;
        if ( pompResponse.size() ==2 || batteryLevel < 35){
            alarm.alarmRinging(true);
            display1.emergencyPrinting(true,true);
            display2.emergencyPrinting(true,true);

        }
    }

    //invia le indormazioni ai display
    public void sendInfoToDisplays(){
        List<String> list = new LinkedList<>();
        list.add(Double.toString(this.batteryLevel));
        list.add(Integer.toString(this.currentInsQuantity));
        display2.setPrintInfo(list);
        list.add(Double.toString(this.glucose));
        list.add(Double.toString(this.glucoseVariation));
        list.add(Integer.toString(this.dose));
        display1.setPrintInfo(list);
    }

    //controllo che tutti i componenti stiano funzionando correttamente
    public void checkSystemStatus(){
        if(batteryLevel < 35){sisCheck=false;device=0;}

        List<Object> result = sensor.getCheckValues();
        if((!sensSerialN.equals(result.get(0)))|| (!sensControlV.equals(result.get(1))) ||
                (Double.compare(lastMeasurement,(Double) result.get(2))!= 0)){sisCheck=false;device=1;}

        result = pomp.getCheckValues();
        if((!pompSerialN.equals(result.get(0)))||(!pompControlV.equals(result.get(1))) ||
            (currentInsQuantity != (int) result.get(2)) ||(dose != (int) result.get(3))){sisCheck=false;device=2;}

        result= display1.getCheckValues();
        if((!d1SerialN.equals(result.get(0))) || (!d1ControlV.equals(result.get(1)))){sisCheck=false;device=3;}

        result = display2.getCheckValues();
        if((!d2SerialN.equals(result.get(0))) || (!d2ControlV.equals(result.get(1)))){sisCheck=false;device=4;}

        result = alarm.getCheckValues();
        if((!alrSerialN.equals(result.get(0))) || (!alrControlV.equals(result.get(1)))){sisCheck=false;device=5;}


        if(!sisCheck && (device == 1 || device == 2)){
            alarm.alarmRinging(true);
            System.exit(1);
        }
        if(!sisCheck){
            alarm.alarmRinging(true);
        }

    }

}
