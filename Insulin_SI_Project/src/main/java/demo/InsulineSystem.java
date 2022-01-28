package demo;


public class InsulineSystem {

    private static final Clock clock = new Clock();


    public static void main(String[] args){
        Controller sis = new Controller();

        int sec1;
        int sec2;
        int now1 = clock.getAgeInSeconds();
        int now2 = clock.getAgeInSeconds();
        int result1 = 0;
        int result2 = 0;
        while(true){ //ciclo per il funzionamento del sistema
            sis.alarm.alarmRinging(false);
            sec1 = clock.getAgeInSeconds();
            result1= sec1-now1;
            sec2 = clock.getAgeInSeconds();
            result2= sec2-now2;
            if (result1 > 30){  //controllo dei valori glicemici e operazioni annesse
                sis.batteryLevel -= 0.001;
                sis.glucoseFromSensor();
                sis.sendDoseToPomp();
                sis.sendInfoToDisplays();
                now1= clock.getAgeInSeconds();
            }
            if (result2 > 3){ //controllo del funzionamento del sistema
                sis.checkSystemStatus();
                now2= clock.getAgeInSeconds();
            }
        }
    }
}
