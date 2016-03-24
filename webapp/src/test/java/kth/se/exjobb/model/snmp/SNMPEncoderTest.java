package kth.se.exjobb.model.snmp;

import kth.se.exjobb.integration.entities.SNMPMessage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;

/**
 * @author Kim Hammar on 2016-03-23.
 */
public class SNMPEncoderTest {
    
    @Before
    public void setUp() throws Exception {
        
    }
    
    @Test
    @Ignore
    public void testEncode() throws Exception {
        int version = 1;
        String community = "public";
        int requestId = 814753557;
        int error = 0;
        int errorIndex = 0;
        int pduType = 160;
        List<SNMPVariableBinding> variableBindings = new ArrayList();
        variableBindings.add(new SNMPVariableBinding("1.3.6.1.2.1.1.5.0.", 5));
        variableBindings.add(new SNMPVariableBinding("1.3.6.1.2.1.1.1.0.", 5));
        variableBindings.add(new SNMPVariableBinding("1.3.6.1.2.1.1.4.0.", 5));
        variableBindings.add(new SNMPVariableBinding("1.3.6.1.2.1.1.6.0.", 5));
        SNMPMessage testMessage = new SNMPMessage(version, community, requestId, error, errorIndex, pduType, variableBindings);
        byte[] result = SNMPEncoder.encode(testMessage);
        System.out.println(bytesToHex(result));
        Assert.assertEquals("305302010104067075626C6963A0460204309027150201000201003038300C06082B060102010105000500300C06082B060102010101000500300C06082B060102010104000500300C06082B060102010106000500",
                bytesToHex(result));
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}