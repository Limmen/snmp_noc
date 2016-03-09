/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
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
 * Application controller. Encapsulates system functionality into an API.
 * @author kim
 */
@Startup
@Stateless
public class Controller {
    
    @EJB
            AlarmEJB alarmManager;
    @EJB
            SNMPManagerBean managerBean;
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * Tells the supervisor of SNMPManager to start the SNMPManager.
     */
    @PostConstruct
    public void init(){
        managerBean.listen();
    }
    
    /**
     * Method to add a new alarm
     * @param msg snmp message
     */
    public void newAlarm(SNMPMessage msg){
        alarmManager.newAlarm(msg);
    }
    
    /**
     * getAllAlarms
     * @return list of alarms
     */
    public Collection <SNMPMessage> getAllAlarms(){
        return alarmManager.getAllAlarms();
    }
    
    /**
     * Method to remove a certain alarm from the list
     * @param alarm snmp message
     */
    public void removeSelectedAlarm(SNMPMessage alarm){
        alarmManager.removeSelectedAlarm(alarm);
    }
}
