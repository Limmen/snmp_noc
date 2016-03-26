/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.model.util.SeverityOrdering;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kth.se.exjobb.integration.entities.History;

/**
 * This class handles incoming SNMP alarms.
 *
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
        recentCritical = null;
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
        if(alarms.contains(alarm))
            alarms.remove(alarm);
        else{
            History history = new History(alarm.getRequestID(), alarm.getSysName(), alarm.getSeverity());
            dao.removeAlarm(alarm);
            dao.saveHistory(history);
        }        
    }

    public SNMPMessage getRecentCritical() {
        return recentCritical;
    }

    
}
