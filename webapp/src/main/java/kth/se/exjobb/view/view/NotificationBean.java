/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.integration.entities.SNMPMessage;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Managed bean managing the notification updates from the serer to the client.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar
 */
@Named(value = "notificationBean")
@ViewScoped
public class NotificationBean implements Serializable {
    @EJB
    Controller contr;
    SNMPMessage critical = null;
    List<SNMPMessage> criticalAlarms = null;
    int numberOfCriticalAlarms = 0;
    String alarmsLabel = "Critical Alarms (0)";
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will call the controller to fetch a list of all alarms and update the notifications.
     */
    @PostConstruct
    public void init(){
        criticalAlarms = (List) contr.getCriticalAlarms();
        if(criticalAlarms == null)
            criticalAlarms = new ArrayList();
        numberOfCriticalAlarms = criticalAlarms.size();
        alarmsLabel = "Critical Alarms (" + numberOfCriticalAlarms + ")";
        critical = null;
    }

    /**
     * Method that is used by the view and AJAX to poll the server for updates that then get presented as notifications
     * in the view.
     */
    public void checkForUpdates(){
        SNMPMessage recentCritical = contr.getRecentCritical();
        if(recentCritical != null){
            if(!recentCritical.equals(critical))
            {
                critical = recentCritical;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("New Critical Alarm",
                        "From: " + critical.getSysName() + "\n" +
                                "Severity: " + critical.getSeverity().getSeverity()));
            }
        }
        criticalAlarms = (List<SNMPMessage>) contr.getCriticalAlarms();
        Collections.sort(criticalAlarms);
        numberOfCriticalAlarms = criticalAlarms.size();
        alarmsLabel = "Critical Alarms (" + numberOfCriticalAlarms + ")";
    }

    /**
     * getCriticalAlarms
     *
     * @return list of critical alarms.
     */
    public List<SNMPMessage> getCriticalAlarms() {
        return criticalAlarms;
    }

    /**
     * getNumberOfCriticalAlarms.
     *
     * @return number of critical alarms.
     */
    public int getNumberOfCriticalAlarms() {
        return numberOfCriticalAlarms;
    }

    /**
     * getAlarmsLabel
     *
     * @return label for number of alarms, used in the navbar.
     */
    public String getAlarmsLabel() {
        return alarmsLabel;
    }
        
    /**
     * getFrequencyHour
     * 
     * @return integer, number of alarms the latest hour.
     */
    public int getFrequencyHour() {
        Date now = new Date();
        int count = 0;
        for (SNMPMessage message : criticalAlarms) {
            if(hoursDifference(now, message.getRawDate()) <= 1)
                count++;
        }
        return count;
    }
    private int hoursDifference(Date date1, Date date2) {

        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }
    
    public String getMostCriticalSystem(){
        HashMap<String, Integer> systems = new HashMap();
        for(SNMPMessage message : criticalAlarms){
            if (systems.containsKey(message.getSysName()))
                systems.put(message.getSysName(), systems.get(message.getSysName()) + 1);
            else
                systems.put(message.getSysName(), 1);
        }
        int max = 0;
        String criticalSystem = "";
        for(SNMPMessage message : criticalAlarms){
            if(systems.get(message.getSysName()) > max){
                criticalSystem = message.getSysName();
                max = systems.get(message.getSysName());
            }
        }
        return criticalSystem;
    }
}
