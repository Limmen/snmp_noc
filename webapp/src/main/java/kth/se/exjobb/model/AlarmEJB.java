/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import kth.se.exjobb.model.snmp.SNMPMessage;

/**
 * This class handles incoming SNMP alarms.
 * @author kim
 */
@Stateless
public class AlarmEJB {
    ArrayList<SNMPMessage> alarms;
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * Initializes a list to hold the alarms.
     */
    @PostConstruct
    public void init(){
        alarms = new ArrayList();
    }
    
    /**
     * Method to add new alarm
     * @param alarm snmp message received
     */
    public void newAlarm(SNMPMessage alarm){
        if(alarms.size() >= 30)
            alarms.remove(0);
        alarms.add(alarm);
    }
    
    /**
     * getAllAlarms
     * @return a list of snmp messages
     */
    public Collection <SNMPMessage> getAllAlarms(){
        return alarms;
    }
    
    /**
     * Removes a certain alarm
     * @param alarm SNMPMessage to remove
     */
    public void removeSelectedAlarm(SNMPMessage alarm){
        alarms.remove(alarm);
    }
}
