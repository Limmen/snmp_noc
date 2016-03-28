/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author kim
 */
@Stateless
public class ConfigurationEJB {
    
    @EJB
    DataAccessObject dao;
    
    public Configuration updateConfiguration(String policy, String severity, String history){
        Configuration config = dao.getConfiguration();
        config.setPolicy(policy);
        config.setSeverity(new Severity(severity));
        config.setHistory(history);
        return config;
    }
    
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
        if(config.getSeverity().compareTo(alarm.getSeverity()) < 0 
                && !(config.getConfigDate().compareTo(alarm.getRawDate()) > 0))
            return true;
        else
            return false;
        
    }
    
    private boolean shouldRemove(Configuration config, History history){
        if(!(config.getConfigDate().compareTo(history.getRemovedDate()) > 0))
            return true;
        else
            return false;
        
    }
}
