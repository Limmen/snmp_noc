/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.agents;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author kim
 */
public class Startup {
    
    public static void main(String[] args){
        try {
            SNMPAgent1 agent1 = new SNMPAgent1();
            SNMPAgent2 agent2 = new SNMPAgent2();
            SNMPAgent3 agent3 = new SNMPAgent3();
            SNMPAgent4 agent4 = new SNMPAgent4();
            SNMPAgent5 agent5 = new SNMPAgent5();
            SNMPAgent6 agent6 = new SNMPAgent6();
            agent1.start();
            agent2.start();
            agent3.start();
            agent4.start();
            agent5.start();
            agent6.start();
            int turn;
            while(true) {
                turn = ThreadLocalRandom.current().nextInt(1, 7);
                switch(turn){
                    case 1:
                        agent1.sendSnmpV2Trap();
                        break;
                    case 2:
                        agent2.sendSnmpV2Trap();
                        break;
                    case 3:
                        agent3.sendSnmpV2Trap();
                        break;
                    case 4:
                        agent4.sendSnmpV2Trap();
                        break;
                    case 5:
                        agent5.sendSnmpV2Trap();
                        break;
                    case 6:
                        agent6.sendSnmpV2Trap();
                        break;
                }
                turn++;
                Thread.sleep(5000);               
            }
        } catch (IOException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
