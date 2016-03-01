/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author kim
 */
@Stateless
public class SNMPManagerBean {

    @Resource
    private ManagedThreadFactory threadFactory;
    @EJB
    private Controller contr;
    private SNMPManager manager;
    
    public void listen(){
        manager = new SNMPManager(contr);
        Thread thread = threadFactory.newThread(manager);
        thread.start();
    }
    
    @PreDestroy
    public void terminate(){
        manager.terminate();
    }

}
