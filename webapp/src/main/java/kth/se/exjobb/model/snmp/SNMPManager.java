/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
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
 * SNMP-Manager that listens on port 9888 for incoming UDP-messages (SNMP-messages).
 * When a message is received it is parsed and then the application controller is notified.
 * @author kim
 */
public class SNMPManager implements Runnable {
    private final Controller contr;
    private boolean running = true;
    
    /**
     *
     * @param contr
     */
    public SNMPManager(Controller contr){
        this.contr = contr;
    }
    
    /**
     * This is where the work is done. This method will create a UDP-socket and then
     * listens for incoming requests.
     */
    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9888);
            listen(socket);
        } catch (SocketException ex) {
            Logger.getLogger(SNMPManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void listen(DatagramSocket socket){
        while(running){
            byte[]buf = new byte[484];
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(SNMPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            SNMPMessage msg = SNMPParser.parse(buf);
            contr.newAlarm(msg);
        }
    }
    
    /**
     * This method will terminate the thread.
     */
    public void terminate(){
        running = false;
    }
    
    
}
