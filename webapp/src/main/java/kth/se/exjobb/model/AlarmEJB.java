/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.model;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kth.se.exjobb.entities.Alarm;

/**
 *
 * @author kim
 */
@Stateless
public class AlarmEJB {

    @PersistenceContext(unitName = "exjobbPU")
    private EntityManager em;
    
    
    public Collection<Alarm> getAllAlarms(){
        Query query = em.createQuery("SELECT e FROM Alarm e");
        return (Collection<Alarm>) query.getResultList();
    }
    
    public void persistAlarm(Alarm a){
        em.persist(a);
    }
}
