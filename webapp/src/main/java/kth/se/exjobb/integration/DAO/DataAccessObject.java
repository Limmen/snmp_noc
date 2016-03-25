package kth.se.exjobb.integration.DAO;

import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.integration.entities.Account;
import kth.se.exjobb.util.LogManager;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;

/**
 * @author kim
 */
@Stateless
public class DataAccessObject {
    @PersistenceContext(unitName = "kth.se.exjobb_noc_prototype_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public void saveSNMPMessage(SNMPMessage message) {
        em.persist(message);
        LogManager.log("SNMPMessage from " + message.getSysName() + " with severity "
                + message.getSeverity() + " persisted successfully", Level.INFO);
    }

    public List<SNMPMessage> getAllMessages() {
        Query query = em.createQuery("SELECT e from SNMPMessage e");
        return (List<SNMPMessage>) query.getResultList();
    }

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

}
