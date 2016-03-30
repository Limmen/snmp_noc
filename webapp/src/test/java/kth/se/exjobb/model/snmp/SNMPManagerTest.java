/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.model.snmp;

import java.net.DatagramSocket;
import kth.se.exjobb.controller.Controller;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 * Unit-test suite for SNMPManager.java
 * 
 * @author Kim Hammar
 */
public class SNMPManagerTest {
    SNMPManager instance;
    
    /**
     * This method is ran before the test methods.
     */
    @Before
    public void setUp() {
        Controller mockContr = mock(Controller.class);
        instance = new SNMPManager(mockContr);
    }
    
    /**
     * This methods is ran after the test methods
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of listen method, of class SNMPManager.
     */
    @Test
    public void testListen() {
        final DatagramSocket mockSocket = mock(DatagramSocket.class);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                instance.listen(mockSocket);
            }
        });
        thread.start();
        instance.terminate();
    }    
}
