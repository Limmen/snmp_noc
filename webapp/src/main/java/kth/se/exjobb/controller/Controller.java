/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import kth.se.exjobb.entities.Alarm;
import kth.se.exjobb.model.AlarmEJB;
import kth.se.exjobb.model.snmp.SNMPManager;
import kth.se.exjobb.view.view.AlarmBean;

/**
 *
 * @author kim
 */
@Startup
@Stateless
public class Controller {
    
    @EJB
    AlarmEJB alarmManager;
    private SNMPManager manager;
    
    @PostConstruct
    public void init(){
        manager = new SNMPManager(this);
        try {
            manager.start();
        } catch (IOException ex) {
            Logger.getLogger(AlarmBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AlarmBean.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    @PreDestroy
    public void cleanUp(){
        try {
            manager.stop();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void persistAlarm(Alarm a){
         alarmManager.persistAlarm(a);
     }
     
    public Collection <Alarm> getAllAlarms(){
        return alarmManager.getAllAlarms();
     }
     
     
}
