/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.model;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import kth.se.exjobb.model.snmp.SNMPMessage;

/**
 *
 * @author kim
 */
@Stateless
public class AlarmEJB {
    ArrayList<SNMPMessage> alarms;
    
    @PostConstruct
    public void init(){
        alarms = new ArrayList();    
    }
    
    public void newAlarm(SNMPMessage alarm){
        if(alarms.size() >= 30)
            alarms.remove(0);
        alarms.add(alarm);
    }
    public Collection <SNMPMessage> getAllAlarms(){
        return alarms;
    } 
    public void removeSelectedAlarm(SNMPMessage alarm){
        alarms.remove(alarm);
    }
}
