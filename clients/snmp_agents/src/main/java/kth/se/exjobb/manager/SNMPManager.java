/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kim
 */
public class SNMPManager implements Runnable {
    public SNMPManager(){
    }
    
    @Override
    public void run() {   
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9888);
        } catch (SocketException ex) {
            Logger.getLogger(SNMPManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            byte[]buf = new byte[484];
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(SNMPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            SNMPMessage msg = SNMPParser.parse(buf);
            //contr.persistAlarm(msg);
            System.out.println("Received trap");
            System.out.println(bytesToHex(buf));
            System.out.println(msg.getCommunity());
            for(SNMPVariableBinding bind : msg.variableBindings){
                System.out.println("OID: " + bind.getOid());
                System.out.println("Value: "  + bind.getValue());
            }

        }
    }
    public void listen(){
        
    }
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
}
    
}
