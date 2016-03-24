/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.model.util.SeverityOrdering;

/**
 * This class handles incoming SNMP alarms.
 * @author kim
 */
@Stateless
public class AlarmEJB {
    
    @EJB
    DataAccessObject dao;
    ArrayList<SNMPMessage> alarms;
    SNMPMessage recentCritical = null;
    
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
        if(SeverityOrdering.severityOrdering.get(alarm.getSeverity().getSeverity()) > 1){
            dao.saveSNMPMessage(alarm);
            recentCritical = alarm;
        }
        else{
            if(alarms.size() >= 30)
                alarms.remove(0);
            alarms.add(alarm);
        }
    }
    
    /**
     * getAllAlarms
     * @return a list of snmp messages
     */
    public Collection <SNMPMessage> getAllAlarms(){
        List<SNMPMessage> persistedMessages = dao.getAllMessages();
        for(SNMPMessage message : alarms){
            persistedMessages.add(message);
        }
        return persistedMessages;
    }
    
    public Collection <SNMPMessage> getCriticalAlarms(){            
        return dao.getAllMessages();
    }
    
    /**
     * Removes a certain alarm
     * @param alarm SNMPMessage to remove
     */
    public void removeSelectedAlarm(SNMPMessage alarm){
        alarms.remove(alarm);
    }

    public SNMPMessage getRecentCritical() {
        return recentCritical;
    }

    
}
