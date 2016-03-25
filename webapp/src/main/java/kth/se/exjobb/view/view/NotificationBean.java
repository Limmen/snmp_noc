/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.integration.entities.SNMPMessage;

/**
 * @author Kim Hammar
 */
@Named(value = "notificationBean")
@ViewScoped
public class NotificationBean implements Serializable {
    @EJB
    Controller contr;
    SNMPMessage critical = null;
    List<SNMPMessage> criticalAlarms = null;
    List<SNMPMessage> mostCritical = null;
    int numberOfCriticalAlarms = 0;
    String alarmsLabel = "Critical Alarms (0)";
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will call the controller to fetch a list of all alarms.
     */
    @PostConstruct
    public void init(){
        criticalAlarms = (List) contr.getAllAlarms();
        if(criticalAlarms == null)
            criticalAlarms = new ArrayList();
        numberOfCriticalAlarms = criticalAlarms.size();
        alarmsLabel = "Critical Alarms (" + numberOfCriticalAlarms + ")";
        critical = null;
    }
    
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

    public List<SNMPMessage> getCriticalAlarms() {
        return criticalAlarms;
    }

    public List<SNMPMessage> getMostCritical() {
        return mostCritical;
    }

    public int getNumberOfCriticalAlarms() {
        return numberOfCriticalAlarms;
    }

    public void setNumberOfCriticalAlarms(int numberOfCriticalAlarms) {
        this.numberOfCriticalAlarms = numberOfCriticalAlarms;
    }

    public String getAlarmsLabel() {
        return alarmsLabel;
    }
        
}
