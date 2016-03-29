/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.integration.DAO;

import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.integration.entities.Account;
import kth.se.exjobb.util.LogManager;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;
import kth.se.exjobb.integration.entities.Configuration;
import kth.se.exjobb.integration.entities.History;
import kth.se.exjobb.model.snmp.Severity;

/**
 * Class that encapsulates database interactions.
 *
 * @author Kim Hammar
 */
@Stateless
public class DataAccessObject {
    @PersistenceContext(unitName = "kth.se.exjobb_noc_prototype_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Method to persist a SNMPMessage in the database.
     *
     * @param message message to persist.
     */
    public void saveSNMPMessage(SNMPMessage message) {
        Configuration config = em.find(Configuration.class, 1);
        em.persist(message);
        LogManager.log("SNMPMessage from " + message.getSysName() + " with severity "
                + message.getSeverity() + " persisted successfully", Level.INFO);
    }

    /**
     * Method to query the database for all messages.
     *
     * @return list of messages.
     */
    public List<SNMPMessage> getAllMessages() {
        Query query = em.createQuery("SELECT e from SNMPMessage e");
        return (List<SNMPMessage>) query.getResultList();
    }

    /**
     * Method to query the database for a user account with a specified username (unique identifier)
     *
     * @param username username for the query
     * @return Account with the specified username
     */
    public Account getUserByName(String username){
        TypedQuery<Account> query = em.createNamedQuery("Account.findByUserName", Account.class);
        query.setParameter("username", username);
        try{
            return query.getSingleResult();
        }catch (NoResultException | NonUniqueResultException e) {
            LogManager.log("GET USER REQUEST FOR NON-EXISTING USERNAME", Level.WARNING);
            return null;
        }
    }
    
    /**
     * Method to remove a alarm from the database.
     * 
     * @param message alarm to remove
     */
    public void removeAlarm(SNMPMessage message){        
        em.remove(em.getReference(SNMPMessage.class, message.getMessageId()));
        LogManager.log("Alarm removed: "+ message.getSysName() + " " + message.getSeverity() + 
                " " + message.getRecievedDate(), Level.INFO);
    }
    
    /**
     * Method to remove a history entry from the database.
     * 
     * @param history entry to remove
     */
    public void removeHistory(History history){        
        em.remove(em.getReference(History.class, history.getId()));    
        LogManager.log("History removed: " + history.getSystemName() + " " + history.getSeverity() 
                + " " + history.getRemovedDate() , Level.INFO);
    }
    
    /**
     * Method to persist a history instance in the database
     * 
     * @param history instance to persist.
     */
    public void saveHistory(History history){
        em.persist(history);
        LogManager.log("History updated. History with system name: " + history.getSystemName() + 
                " severity: " + history.getSeverity() + " date:" + history.getRemovedDate() + 
                " persisted successfully",  Level.INFO);
    }
    
   /**
     * Method to query the database for history.
     *
     * @return list of messages.
     */
    public List<History> getAllHistories() {
        Query query = em.createQuery("SELECT e from History e");
        return (List<History>) query.getResultList();
    }
    
    /**
     * getConfiguration. Queries the database for the current configuration. If no configuration
     * exists a default configuration is set.
     * 
     * @return The current configuration.
     */
    public Configuration getConfiguration(){
        Query query = em.createQuery("SELECT e from Configuration e");
        try{
            Configuration config = (Configuration) query.getSingleResult();
            return config;
       }
        catch(NoResultException e){
            Configuration config = 
                    new Configuration("Forever", new Severity("Indeterminate"), "Forever", 
                            new Severity("Indeterminate"), new Severity("Indeterminate"));
            newConfiguration(config);
            return config;
        }   
    }

    /**
     * Method that creates as persist a new configuration.
     * 
     * @param config configuration to persist
     */
    public void newConfiguration(Configuration config){
        em.persist(config);
        LogManager.log("Configuration with alarm-saving-policy: " + config.getPolicy() + 
                " , Severity-policy: " + config.getSeverity() + " and History-policy: " + 
                config.getHistory() + " persisted successfully", Level.INFO);
    }

}
