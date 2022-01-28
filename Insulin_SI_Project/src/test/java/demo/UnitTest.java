package demo;

import static org.junit.Assert.*;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.Test;
import java.util.*;

public class UnitTest {

    @Test
    public void testSystem() {
        Controller c3 = new Controller();
        c3.checkSystemStatus();
        assertTrue(c3.sisCheck);

    }

    @Test
    public void testPomp(){
        List<Integer> expectedValue =new LinkedList<>();
        InsulinPomp p = new InsulinPomp();
        p.dose = 8;
        expectedValue.add(342);
        assertEquals(expectedValue,p.getQuantity());
        assertEquals(342,p.quantity);

        assertEquals( expectedValue,p.setDose(0));
        assertEquals(0,p.dose);

        expectedValue.clear();
        for (int i=0;i<17;i++){
            p.setDose(16);
        }
        expectedValue.add(54);
        expectedValue.add(700);
        List<Integer> result = p.setDose(16);
        assertEquals(2,result.size());
        assertEquals(expectedValue,result);

    }

    @Test
    public void testSensorController(){
        Controller c1 = new Controller();
        c1.glucose = 250.0;
        c1.calculateDose();
        assertEquals(6,c1.dose);
        c1.sendDoseToPomp();
        assertEquals(344,c1.currentInsQuantity);

    }

    @Test
    public void testDisplays() {
        Controller c2 = new Controller();
        c2.sendInfoToDisplays();
        assertEquals(String.valueOf(100.0),c2.display1.batteryLevel);
        assertEquals(String.valueOf(350),c2.display1.insulineResidueQuantity);
        assertEquals(String.valueOf(0.0),c2.display1.glucose);
        assertEquals(String.valueOf(0.0),c2.display1.glucoseVariation);
        assertEquals(String.valueOf(0),c2.display1.dose);

        Display1 d1 = new Display1();
        Display2 d2= new Display2();

        d1.emergencyPrinting( true, false);
        d2.emergencyPrinting(false, true);
        assertFalse( d1.battery);
        assertTrue( d1.pomp);
        assertTrue( d2.battery);
        assertFalse( d2.pomp);
    }


    @Test
    public void testSensorIsInRange(){
        Sensor s = new Sensor();
        double glucose = s.getGlucose();
        for(int i= 0; i<100000; i++) {
            assertTrue(0 <= glucose && glucose <= 600);
        }


    }

    //Testing dei casi di errore per i singoli metodi



    @Test
    public void testAlarm(){
        Alarm a = new Alarm();
        a.alarmRinging(true);
        assertTrue(a.ring);
    }

    @Test
    public void testLowBattery(){
        Controller c= new Controller();
        c.batteryLevel =32;
        c.sendDoseToPomp();
        assertTrue(c.alarm.ring);
        assertTrue(c.display1.battery);
        assertTrue(c.display2.battery);

    }

    @Test
    public void testLowInsuline(){
        Controller c= new Controller();
        c.dose =320;
        c.sendDoseToPomp();
        assertTrue(c.alarm.ring);
        assertTrue(c.display1.pomp);
        assertTrue(c.display2.pomp);
    }

    @Test

    public void testSysError() throws Exception {

        int status = SystemLambda.catchSystemExit(() -> {
            Controller c = new Controller();
            c.sensor.lastMeasurement = 10.0;
            c.checkSystemStatus();
            assertFalse(c.sisCheck);
            assertTrue(c.alarm.ring);
        });

        assertEquals(1, status);
    }

    @Test
    public void testGlucoseTooLowOrTooHigh(){
        Controller c = new Controller();
        c.glucose= 66;
        c.calculateDose();
        assertEquals(0,c.dose);
        assertTrue(c.display1.lowInsuline);
        assertFalse(c.display2.highInsuline);
        assertTrue(c.alarm.ring);

        c.alarm.ring=false;
        c.glucose=600;
        c.calculateDose();
        assertEquals(16,c.dose);
        assertTrue(c.display1.highInsuline);
        assertFalse(c.display2.lowInsuline);
        assertTrue(c.alarm.ring);
    }

    @Test
    public void testEmergencyInsuline(){
        Display1 d1 = new Display1();
        Display2 d2 = new Display2();

        d1.setEmergencyInsuline(true,true);
        d2.setEmergencyInsuline(false,true);

        assertTrue(d1.highInsuline);
        assertTrue(d1.lowInsuline);
        assertFalse(d2.lowInsuline);
        assertTrue(d2.highInsuline);
    }

}
