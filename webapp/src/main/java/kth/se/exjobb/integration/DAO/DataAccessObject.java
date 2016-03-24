/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.integration.DAO;

import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.model.snmp.Severity;
import kth.se.exjobb.util.LogManager;

/**
 *
 * @author kim
 */
@Stateless
public class DataAccessObject {
    @PersistenceContext(unitName = "kth.se.exjobb_noc_prototype_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    private LogManager logManager;
    
    /**
     * Sets the logManager
     * @param logManager logmanager that is used to log application exceptions
     */
    public void setLogManager(LogManager logManager)
    {
        this.logManager = logManager;
    }
    public void saveSNMPMessage(SNMPMessage message){
        em.persist(message);
        logManager.log("SNMPMessage from " + message.getSysName() + " with severity " 
                + message.getSeverity() + " persisted successfully", Level.INFO);
    }
    
    public List<SNMPMessage> getAllMessages()
    {
        Query query = em.createQuery("SELECT e from SNMPMessage e");
        return (List<SNMPMessage>) query.getResultList();
    }
}
