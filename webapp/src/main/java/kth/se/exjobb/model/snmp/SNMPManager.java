/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.model.snmp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kth.se.exjobb.controller.Controller;

/**
 *
 * @author kim
 */
public class SNMPManager implements Runnable {
    Controller contr;
    public SNMPManager(Controller contr){
        this.contr = contr;
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
            contr.persistAlarm(msg);
            System.out.println("Received trap");
            System.out.println(msg.getCommunity());                        
        }
    }
    public void listen(){
        
    }
    
    
}
