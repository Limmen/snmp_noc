/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedThreadFactory;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.model.snmp.SNMPManager;

/**
 * This class acts as a supervisor for the SNMPManager-thread.
 * @author kim
 */
@Stateless
public class SNMPManagerBean {
    
    @Resource
    private ManagedThreadFactory threadFactory;
    @EJB
    private Controller contr;
    private SNMPManager manager;
    
    /**
     * Initializes and starts the SNMPManager thread.
     */
    public void listen(){
        manager = new SNMPManager(contr);
        Thread thread = threadFactory.newThread(manager);
        thread.start();
    }
    
    /**
     * Stops the SNMPManager thread.
     */
    @PreDestroy
    public void terminate(){
        manager.terminate();
    }
    
}
