/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.SNMPMessage;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kth.se.exjobb.integration.entities.Configuration;
import kth.se.exjobb.integration.entities.History;

/**
 * This class handles incoming SNMP alarms.
 *
 * @author Kim Hammar
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
        if(shouldSave(dao.getConfiguration(), alarm)){
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
    
    /**
     *
     * @return
     */
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
            if(shouldSave(dao.getConfiguration(), history))
                dao.saveHistory(history);
        }
    }

    /**
     * getRecentCritical
     * 
     * @return the most recent critical alarm.
     */
    public SNMPMessage getRecentCritical() {
        return recentCritical;
    }

    private boolean shouldSave(Configuration config, SNMPMessage alarm){
        if(config.getSeverity().compareTo(alarm.getSeverity()) > 0)
            return false;
        if(config.getConfigDate().compareTo(alarm.getRawDate()) > 0)
            return false;
        else
            return true;        
    }
    
    private boolean shouldSave(Configuration config, History history){
        if(config.getConfigDate().compareTo(history.getRemovedDate()) > 0)
            return false;
        else
            return true; 
    }
    
}
