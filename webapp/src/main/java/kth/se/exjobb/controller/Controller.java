/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.controller;

import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import kth.se.exjobb.model.AlarmEJB;
import kth.se.exjobb.model.SNMPManagerBean;
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
    @EJB
    SNMPManagerBean managerBean;
    
    @PostConstruct
    public void init(){
       managerBean.listen();
    }
    public void newAlarm(SNMPMessage msg){
        alarmManager.newAlarm(msg);
    }
    
    public Collection <SNMPMessage> getAllAlarms(){
        return alarmManager.getAllAlarms();
    }        
}
