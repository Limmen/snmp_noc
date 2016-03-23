/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.model.snmp.SNMPSender;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedThreadFactory;

/**
 * This class acts as a supervisor for the SNMPSender-thread.
 * @author kim
 */
@Stateless
public class SNMPSenderBean {
    
    @Resource
    private ManagedThreadFactory threadFactory;
    @EJB
    private Controller contr;
    private SNMPSender sender;
    
    /**
     * Initializes and starts the SNMPSender thread.
     */
    public void listen(String[] oids, String ip){
        //byte[] data = SNMPEncoder
        //sender = new SNMPSender();
        Thread thread = threadFactory.newThread(sender);
        thread.start();
    }

    
}
