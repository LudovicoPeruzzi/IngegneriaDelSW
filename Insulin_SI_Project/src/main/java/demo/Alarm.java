package demo;

import java.util.LinkedList;
import java.util.List;

public class Alarm {

    static final String serialNumber = "A-568743" ;
    static final Integer controlValue = 33;

    public boolean ring=false;

    public void alarmRinging(boolean ring){
        this.ring= ring;
    }

    public List<Object> getCheckValues(){
        List<Object> result = new LinkedList<>();
        result.add(serialNumber);
        result.add(controlValue);
        return result;
    }


}
