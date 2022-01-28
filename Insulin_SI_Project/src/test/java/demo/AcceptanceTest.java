package demo;

import static org.junit.Assert.*;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.Test;
import java.util.*;

public class AcceptanceTest {

    @Test
    public void testInsuline(){
        Controller c = new Controller();
        c.glucose = 236.7;
        c.calculateDose();
        assertEquals(4,c.dose);

        c.sendDoseToPomp();
        assertEquals(4,c.pomp.dose);
        assertEquals(346,c.pomp.quantity);

        c.glucose= 119.3;
        c.sensor.lastMeasurement=119.3;
        c.calculateDose();
        c.sendDoseToPomp();
        assertEquals(0,c.dose);
        assertEquals(0,c.pomp.dose);
        assertEquals(117.4,c.glucoseVariation,0.2);

        c.sendInfoToDisplays();
        assertEquals(String.valueOf(346),c.display2.insulineResidueQuantity);
        assertEquals(String.valueOf(119.3),c.display1.glucose);
        assertFalse(c.display1.lowInsuline);
        assertFalse(c.display2.pomp);

        c.checkSystemStatus();
        assertTrue(c.sisCheck);

        c.glucose= 56;
        c.calculateDose();
        c.sendDoseToPomp();
        assertEquals(0,c.pomp.dose);
        assertTrue(c.alarm.ring);
        assertEquals(346,c.currentInsQuantity);
        assertEquals(56,c.lastMeasurement,0.2);
        assertEquals(63.3,c.glucoseVariation,0.2);

        c.glucose= 554.5;
        c.calculateDose();
        c.sendDoseToPomp();
        assertEquals(16,c.dose);
        assertEquals(-498.5,c.glucoseVariation,0.2);
        assertTrue(c.display2.highInsuline);
        assertTrue(c.alarm.ring);

    }
    @Test
    public void testErrorOrWarningCases() throws Exception{
        Controller c = new Controller();
        c.glucose=498.8;
        c.sensor.lastMeasurement = 498.8;
        List<Integer> pompResponse = new LinkedList<>();
        for(int i = 0; i<22; i++){
            c.calculateDose();
            c.sendDoseToPomp();
        }
        assertEquals(42,c.currentInsQuantity);
        assertEquals(42,c.pomp.quantity);
        assertEquals(0.0,c.glucoseVariation,0.2);

        assertTrue(c.display1.pomp);
        assertTrue(c.display2.pomp);

        c.sendInfoToDisplays();
        assertEquals(String.valueOf(42),c.display2.insulineResidueQuantity);
        assertEquals(String.valueOf(14),c.display1.dose);
        assertTrue(c.alarm.ring);

        c.alarm.ring=false;
        c.batteryLevel = 25.4;

        c.checkSystemStatus();
        assertTrue(c.alarm.ring);

        int status = SystemLambda.catchSystemExit(() -> {
            c.pomp.dose = 8;
            c.checkSystemStatus();
            assertFalse(c.sisCheck);
            assertTrue(c.alarm.ring);
        });

        assertEquals(1, status);


    }
}
