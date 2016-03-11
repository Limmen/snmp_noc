/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model.snmp;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Test suite for SNMPParser
 * @author kim
 */
public class SNMPParserTest {
    
    /**
     * Test of parse method, of class SNMPParser.
     */
    @Test
    public void testParse() {
        byte[] bytes = hexStringToByteArray("3082011202010104067075626C6963A7820103020452C1EEFF0201000201003081F4300F06082B06010201010300430301B2073015060A2B06010603010104010006072B060102010106301106092B060106031201030040047F000001303906082B06010201010500042D53494D554C4154454420534E4D50204147454E54202D2045584A4F4242204D4943204E4F524449432032303136302C06082B060102010101000420534E4D50204147454E5420646576656C6F706564207769746820534E4D50344A302306082B0601020101040004174B696D2048616D6D61722C204D617263757320426C6F6D302906082B06010201010600041D526F79616C20496E73746974757465206F6620546563686E6F6C6F67790000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        SNMPMessage result = SNMPParser.parse(bytes);
        System.out.println(result.getCommunity() + " " + result.getRequestID());
        Assert.assertEquals("public", result.getCommunity());
        Assert.assertEquals(1, result.getVersionNumber());
        Assert.assertEquals(0, result.getError());
        Assert.assertEquals(0, result.getErrorIndex());
        //Assert.assertEquals("Fine", result.getSeverity());
        Assert.assertEquals(1388441343, result.getRequestID());
        
        for(SNMPVariableBinding binding : result.getVariableBindings()){
            if(binding.getOid().equals("sysUpTimeInstance"))
                Assert.assertEquals("111111", binding.getValue());
            if(binding.getOid().equals("sysName.0"))
                Assert.assertEquals("SIMULATED SNMP AGENT - EXJOBB MIC NORDIC 2016", binding.getValue());
            if(binding.getOid().equals("sysDescr.0"))
                Assert.assertEquals("SNMP AGENT developed with SNMP4J", binding.getValue());
            if(binding.getOid().equals("sysContact.0"))
                Assert.assertEquals("Kim Hammar, Marcus Blom", binding.getValue());
            if(binding.getOid().equals("sysLocation.0"))
                Assert.assertEquals("Royal Institute of Technology", binding.getValue());
            
        }
        
    }
    
    /**
     * Test of translateOID method, of class SNMPParser.
     */
    @Test
    public void testTranslateOID() {
        Assert.assertEquals("sysUpTimeInstance", SNMPParser.translateOID("1.3.6.1.2.1.1.3.0."));
        Assert.assertEquals("snmpTrapOID.0", SNMPParser.translateOID("1.3.6.1.6.3.1.1.4.1.0."));
        Assert.assertEquals("snmpMIBObjects.3.0", SNMPParser.translateOID("1.3.6.1.6.3.1.1.3.0."));
        Assert.assertEquals("sysName.0", SNMPParser.translateOID("1.3.6.1.2.1.1.5.0."));
        Assert.assertEquals("sysDescr.0", SNMPParser.translateOID("1.3.6.1.2.1.1.1.0."));
        Assert.assertEquals("sysContact.0", SNMPParser.translateOID("1.3.6.1.2.1.1.4.0."));
        Assert.assertEquals("sysLocation.0", SNMPParser.translateOID("1.3.6.1.2.1.1.6.0."));
    }
    
    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
