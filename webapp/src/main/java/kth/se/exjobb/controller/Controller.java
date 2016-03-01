/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import kth.se.exjobb.entities.Alarm;
import kth.se.exjobb.model.AlarmEJB;
import kth.se.exjobb.model.snmp.SNMPManager;
import kth.se.exjobb.model.snmp.SNMPMessage;

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
    ArrayList<SNMPMessage> alarms = new ArrayList();
    
    @PostConstruct
    public void init(){
        manager = new SNMPManager(this);
        new Thread(manager).start();      
    }
    @PreDestroy
    public void cleanUp(){
        //Stop thread
    }
    
    public void persistAlarm(SNMPMessage msg){
        alarms.add(msg);
    }
    
    public Collection <SNMPMessage> getAllAlarms(){
        return alarms;
    }
    
    public Alarm sendAlarm(String target) throws IOException{
        //return manager.sendDefaultRequest(new UdpAddress(target));
        return null;
    }
    
    
}
