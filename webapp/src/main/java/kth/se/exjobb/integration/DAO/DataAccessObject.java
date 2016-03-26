package kth.se.exjobb.integration.DAO;

import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.integration.entities.Account;
import kth.se.exjobb.util.LogManager;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;
import kth.se.exjobb.integration.entities.History;

/**
 * Class that encapsulates database interactions.
 *
 * @author kim
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
        System.out.println("ID::::::::::::::  " + message.getMessageId());
        em.remove(em.getReference(SNMPMessage.class, message.getMessageId()));        
    }
    /**
     * Method to persist a history instance in the database
     * 
     * @param history instance to persist.
     */
    public void saveHistory(History history){
        em.persist(history);
        LogManager.log("History updated", Level.INFO);
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

}
