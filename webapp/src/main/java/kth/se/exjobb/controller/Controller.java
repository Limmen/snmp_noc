/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.controller;

import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.model.AlarmEJB;
import kth.se.exjobb.model.HttpSessionEJB;
import kth.se.exjobb.model.LoginEJB;
import kth.se.exjobb.model.SNMPManagerBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import kth.se.exjobb.integration.entities.Configuration;
import kth.se.exjobb.integration.entities.History;
import kth.se.exjobb.model.ConfigurationEJB;

/**
 * Application controller. Encapsulates system functionality into an API.
 *
 * @author Kim Hammar
 */
@Startup
@Stateless
public class Controller {

    @EJB
    AlarmEJB alarmManager;
    @EJB
    SNMPManagerBean managerBean;
    @EJB
    DataAccessObject dao;
    @EJB
    LoginEJB login;
    @EJB
    HttpSessionEJB session;
    @EJB
    ConfigurationEJB config;

    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     * <p>
     * Tells the supervisor of SNMPManager to start the SNMPManager.
     */
    @PostConstruct
    public void init() {
        managerBean.listen();
        
    }

    /**
     * Method to add a new alarm
     *
     * @param msg snmp message
     */
    public void newAlarm(SNMPMessage msg) {
        alarmManager.newAlarm(msg);
    }

    /**
     * getAllAlarms
     *
     * @return list of alarms
     */
    public Collection<SNMPMessage> getAllAlarms() {
        return alarmManager.getAllAlarms();
    }

   /**
     * getAllHistories
     *
     * @return list of history
     */
    public Collection<History> getAllHistories() {
        return dao.getAllHistories();
    }
    /**
     * Method to remove a certain alarm from the list
     *
     * @param alarm snmp message
     */
    public void removeSelectedAlarm(SNMPMessage alarm) {
        alarmManager.removeSelectedAlarm(alarm);
    }

    /**
     * Method to send a SNMP request to a specified ip adress.
     *
     * @param oids oids to be in the request.
     * @param ip ip adress of the target for the request
     */
    public void sendGetRequest(String[] oids, String ip) {
    }

    /**
     * Method that gets the most recent critical alarm. (Used for notifications)
     *
     * @return most recent critical alarm.
     */
    public SNMPMessage getRecentCritical() {
        return alarmManager.getRecentCritical();
    }

    /**
     * Method to get all saved alarms.
     *
     * @return list of saved alarms
     */
    public Collection<SNMPMessage> getSavedAlarms() {
        return alarmManager.getSavedAlarms();
    }

    /**
     * Method to get all alarms that fulfill the severity to do a notification.
     *
     * @return list of alarms
     */
    public Collection<SNMPMessage> getNotificationAlarms() {
        return alarmManager.getNotificationAlarms();
    }

    /**
     * Method to validate user credentials for login.
     *
     * @param username username to validate
     * @param password password to validate
     * @return true if the validation was successful otherwise false.
     * @throws NoSuchAlgorithmException if the encryption step fails.
     */
    public boolean validateLogin(String username, String password) throws NoSuchAlgorithmException {
        return login.validateLogin(username, password);
    }

    /**
     * Returns the HTTP-session
     * 
     * @return http-session
     */
    public HttpSession getSession()
    {
        return session.getSession();
    }
    
    /**
     * getConfiguration
     * 
     * @return Configuration entity
     */
    public Configuration getConfiguration(){
        return dao.getConfiguration();
    }
    
    /**
     * Method that updates the current configuration and also updates the database according to
     * the specified configuration.
     * 
     * @param save new savingpolicy for alarms
     * @param severity new severitypolicy
     * @param history new savingpolicy for history
     * @param notification
     * @param statistics
     * @return The newly set configuration
     */
    public Configuration updateConfiguration(String save, String severity, String history,
            String notification, String statistics){
        Configuration configuration = config.updateConfiguration(save, severity, history,
                notification, statistics);
        config.updateDatabaseWithNewConfig();
        return configuration;
    }
}
