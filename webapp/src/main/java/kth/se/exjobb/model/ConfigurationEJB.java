/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.Configuration;
import kth.se.exjobb.integration.entities.History;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.model.snmp.Severity;

/**
 * Business component that handles configuration updates
 *
 * @author Kim Hammar
 */
@Stateless
public class ConfigurationEJB {
    
    @EJB
            DataAccessObject dao;
    
    /**
     * Updates the current configuration
     *
     * @param policy new saving-policy for alarms
     * @param severity new severity-policy
     * @param history new history-policy
     * @param notification
     * @param statistics
     * @return The new configuration
     */
    public Configuration updateConfiguration(String policy, String severity, String history,
            String notification, String statistics){
        Configuration config = dao.getConfiguration();
        config.setPolicy(policy);
        config.setSeverity(new Severity(severity));
        config.setHistory(history);
        config.setNotification(new Severity(notification));
        config.setStatistics(new Severity(statistics));
        return config;
    }
    
    /**
     * Updates the database according to the new configuration.
     */
    public void updateDatabaseWithNewConfig(){
        Configuration config = dao.getConfiguration();
        for(SNMPMessage message : dao.getAllMessages()){
            if(shouldRemove(config, message))
                dao.removeAlarm(message);
        }
        for(History history : dao.getAllHistories()){
            if(shouldRemove(config, history))
                dao.removeHistory(history);
        }
    }
    
    private boolean shouldRemove(Configuration config, SNMPMessage alarm){
        if(config.getSeverity().compareTo(alarm.getSeverity()) < 0)
            return true;
        if(!(config.getConfigDate().compareTo(alarm.getRawDate()) < 0))
            return true;
        else
            return false;
        
    }
    
    private boolean shouldRemove(Configuration config, History history){
        if(!(config.getConfigDate().compareTo(history.getRemovedDate()) < 0))
            return true;
        else
            return false;
        
    }
}
