/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import java.math.BigInteger;
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
    long cacheId = 999999;
    
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
        System.out.println("alarm ID: " + alarm.getMessageId());
        Configuration config = dao.getConfiguration();
        if(alarm.getSeverity().compareTo(config.getNotification()) >= 0)
            recentCritical = alarm;
        if(shouldSave(config, alarm)){
            dao.saveSNMPMessage(alarm);            
        }
        else{
            if(cacheId < 9000000)
                cacheId = 9999999;
            alarm.setMessageId(BigInteger.valueOf(cacheId));
            cacheId--;
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
     * getCriticalAlarms
     * @param alarms list of all alarms
     * @return a list of all critical alarms
     */
    public Collection <SNMPMessage> getCriticalAlarms(List<SNMPMessage> alarms){        
       List<SNMPMessage> criticalAlarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSeverity().getSeverity().equals("Critical"))
                criticalAlarms.add(message);
        }
        return criticalAlarms;
    }
    
    /**
     * getMajorAlarms
     * @param alarms list of all alarms
     * @return a list of all major alarms
     */
    public Collection <SNMPMessage> getMajorAlarms(List<SNMPMessage> alarms){        
       List<SNMPMessage> majorAlarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSeverity().getSeverity().equals("Major"))
                majorAlarms.add(message);
        }
        return majorAlarms;
    }
    
    /**
     * getMinorAlarms
     * @param alarms list of all alarms
     * @return a list of all minor alarms
     */
    public Collection <SNMPMessage> getMinorAlarms(List<SNMPMessage> alarms){      
       List<SNMPMessage> minorAlarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSeverity().getSeverity().equals("Minor"))
                minorAlarms.add(message);
        }
        return minorAlarms;
    }
    
    /**
     * getWarningAlarms
     * @param alarms list of all alarms
     * @return a list of all warning alarms
     */
    public Collection <SNMPMessage> getWarningAlarms(List<SNMPMessage> alarms){
        List<SNMPMessage> warningAlarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSeverity().getSeverity().equals("Warning"))
                warningAlarms.add(message);
        }
        return warningAlarms;
    }
    
    /**
     * getIndeterminateAlarms
     * @param alarms list of all alarms
     * @return a list of all indeterminate alarms
     */
    public Collection <SNMPMessage> getIndeterminateAlarms(List<SNMPMessage> alarms){        
        List<SNMPMessage> indeterminateAlarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSeverity().getSeverity().equals("Indeterminate"))
                indeterminateAlarms.add(message);
        }
        return indeterminateAlarms;
    }
    
    /**
     * getClearedAlarms
     * @param alarms list of all alarms
     * @return a list of all cleared alarms
     */
    public Collection <SNMPMessage> getClearedAlarms(List<SNMPMessage> alarms){        
        List<SNMPMessage> clearedAlarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSeverity().getSeverity().equals("Cleared"))
                clearedAlarms.add(message);
        }
        return clearedAlarms;
    }
   /**
     * getAgent1Alarms
     * @param alarms list of all alarms
     * @return a list of all alarms from agent 1
     */
    public Collection <SNMPMessage> getAgent1Alarms(List<SNMPMessage> alarms){        
       List<SNMPMessage> agent1Alarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSysName().equals("SIMULATED SNMP AGENT - NUMBER ONE"))
                agent1Alarms.add(message);
        }
        return agent1Alarms;
    }
    
    /**
     * getAgent2Alarms
     * @param alarms list of all alarms
     * @return a list of all alarms from agent 2
     */
    public Collection <SNMPMessage> getAgent2Alarms(List<SNMPMessage> alarms){        
       List<SNMPMessage> agent2Alarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSysName().equals("SIMULATED SNMP AGENT - NUMBER TWO"))
                agent2Alarms.add(message);
        }
        return agent2Alarms;
    }
    
    /**
     * getAgent3Alarms
     * @param alarms list of all alarms
     * @return a list of all alarms from agent 3
     */
    public Collection <SNMPMessage> getAgent3Alarms(List<SNMPMessage> alarms){      
       List<SNMPMessage> agent3Alarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSysName().equals("SIMULATED SNMP AGENT - NUMBER THREE"))
                agent3Alarms.add(message);
        }
        return agent3Alarms;
    }
    
    /**
     * getAgent4Alarms
     * @param alarms list of all alarms
     * @return a list of all alarms from agent 4
     */
    public Collection <SNMPMessage> getAgent4Alarms(List<SNMPMessage> alarms){
        List<SNMPMessage> agent4Alarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSysName().equals("SIMULATED SNMP AGENT - NUMBER FOUR"))
                agent4Alarms.add(message);
        }
        return agent4Alarms;
    }
    
    /**
     * getAgent5Alarms
     * @param alarms list of all alarms
     * @return a list of all alarms from agent 5
     */
    public Collection <SNMPMessage> getAgent5Alarms(List<SNMPMessage> alarms){        
        List<SNMPMessage> agent5alarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSysName().equals("SIMULATED SNMP AGENT - NUMBER FIVE"))
                agent5alarms.add(message);
        }
        return agent5alarms;
    }
    
    /**
     * getAgent6Alarms
     * @param alarms list of all alarms
     * @return a list of all alarms  from agent 6
     */
    public Collection <SNMPMessage> getAgent6Alarms(List<SNMPMessage> alarms){        
        List<SNMPMessage> agent6Alarms = new ArrayList();
        for(SNMPMessage message : alarms){
            if(message.getSysName().equals("SIMULATED SNMP AGENT - NUMBER SIX"))
                agent6Alarms.add(message);
        }
        return agent6Alarms;
    }
    /**
     * getSavedAlarms
     * 
     * @return list of all alarms that are persisted in the database.
     */
    public Collection <SNMPMessage> getSavedAlarms(){            
        return dao.getAllMessages();
    }
    
    /**
     * getNotificationAlarms
     * 
     * @return list of all alarms that should be a notification.
     */
    public Collection <SNMPMessage> getNotificationAlarms(){
        List<SNMPMessage> notificationAlarmsList = new ArrayList();
        for(SNMPMessage alarm : getAllAlarms()){
            if(alarm.getSeverity().compareTo(dao.getConfiguration().getNotification()) >= 0)
                notificationAlarmsList.add(alarm);
        }
        return notificationAlarmsList;
    }
    
    /**
     * getStatisticsAlarms
     * 
     * @return list of all alarms for statistical data.
     */
    public Collection <SNMPMessage> getStatisticsAlarms(){
        List<SNMPMessage> statisticsAlarmsList = new ArrayList();
        for(SNMPMessage alarm : getAllAlarms()){
            if(alarm.getSeverity().compareTo(dao.getConfiguration().getNotification()) >= 0)
                statisticsAlarmsList.add(alarm);
        }
        return statisticsAlarmsList;
    }
    
    /**
     * Removes a certain alarm
     * 
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
