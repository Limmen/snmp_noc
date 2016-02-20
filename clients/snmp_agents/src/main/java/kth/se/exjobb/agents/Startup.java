/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.agents;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kim
 */
public class Startup {
    
    public static void main(String[] args){
        try {
            SNMPAgent agent = new SNMPAgent();
            agent.start();            
            while(true) {
			System.out.println("Agent running");
			Thread.sleep(5000);      
                        agent.sendSnmpV2Trap();
		}
        } catch (IOException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
