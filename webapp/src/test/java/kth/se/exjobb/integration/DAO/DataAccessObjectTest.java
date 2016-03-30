/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.integration.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import kth.se.exjobb.integration.entities.Account;
import kth.se.exjobb.integration.entities.Configuration;
import kth.se.exjobb.integration.entities.History;
import kth.se.exjobb.integration.entities.SNMPMessage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit-test suite for DataAccessObject.java
 * 
 * @author Kim Hammar
 */
public class DataAccessObjectTest {
    
    DataAccessObject instance;
    public DataAccessObjectTest() {
    }

    @Before
    public void setUp() {
        instance = new DataAccessObject();
    }

    /**
     * Test of getAllMessages method, of class DataAccessObject.
     */
    @Test
    public void testGetAllMessages() throws Exception {
        EntityManager mockManager = mock(EntityManager.class);
        Query mockQuery = mock(Query.class);
        List<SNMPMessage> mockList = mock(List.class);
        when((mockManager.createQuery("SELECT e from SNMPMessage e"))).thenReturn((mockQuery));
        when((mockQuery.getResultList())).thenReturn((mockList));       
        instance.setEm(mockManager);
        assertEquals(mockList, instance.getAllMessages());
    }
 
    /**
     * Test of getUserByName method, of class DataAccessObject.
     */
    @Test
    public void testGetUserByName() throws Exception {
        EntityManager mockManager = mock(EntityManager.class);
        TypedQuery<Account> mockQuery = mock(TypedQuery.class);        
        Account mockAccount = mock(Account.class);
        when((mockManager.createNamedQuery("Account.findByUserName", Account.class))).thenReturn((mockQuery));
        when((mockQuery.getSingleResult())).thenReturn((mockAccount));
        when((mockQuery.setParameter("username", "test"))).thenReturn(mockQuery);
        instance.setEm(mockManager);
        assertEquals(mockAccount, instance.getUserByName("test"));
        when((mockQuery.getSingleResult())).thenThrow(new NoResultException());
        assertEquals(null, instance.getUserByName("test"));
    }

    /**
     * Test of getAllHistories method, of class DataAccessObject.
     */
    @Test
    public void testGetAllHistories() throws Exception {
        EntityManager mockManager = mock(EntityManager.class);
        Query mockQuery = mock(Query.class);
        List<History> mockList = mock(List.class);
        when((mockManager.createQuery("SELECT e from History e"))).thenReturn((mockQuery));
        when((mockQuery.getResultList())).thenReturn((mockList));       
        instance.setEm(mockManager);
        assertEquals(mockList, instance.getAllHistories());       
    }

    /**
     * Test of getConfiguration method, of class DataAccessObject.
     */
    @Test
    public void testGetConfiguration() throws Exception {
        EntityManager mockManager = mock(EntityManager.class);
        Query mockQuery = mock(Query.class);
        Configuration mockConfiguration = mock(Configuration.class);
        when((mockManager.createQuery("SELECT e from Configuration e"))).thenReturn((mockQuery));
        when((mockQuery.getSingleResult())).thenReturn((mockConfiguration));       
        instance.setEm(mockManager);
        assertEquals(mockConfiguration, instance.getConfiguration());  
    }

    
}
