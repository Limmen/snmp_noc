/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.model.snmp.SNMPManager;
import kth.se.exjobb.model.snmp.SNMPMessage;

/**
 * Managed bean representing the interface between the index page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author kim
 */
@Named(value = "alarmBean")
@ViewScoped
public class AlarmBean implements Serializable {
    @EJB
    Controller contr;
    private SNMPManager manager;
    private List<SNMPMessage> alarms;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date updated = new Date();
    private SNMPMessage selectedAlarm;
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will call the controller to fetch a list of all alarms.
     */
    @PostConstruct
    public void init(){
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
    }
    
    /**
     * A method that is called on time-periods to partially upadte the view with new alarms.
     */
    public void fetchAlarms(){
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
        updated = new Date();
    }
    
    /**
     * Removes a selectedAlarm from the list of alarms
     */
    public void removeSelectedAlarm(){
        contr.removeSelectedAlarm(selectedAlarm);
    }
    
    /**
     * getAlarms
     * @return List of alarms
     */
    public List<SNMPMessage> getAlarms() {
        return alarms;
    }
    
    /**
     * getUpdated
     * @return Date when the list of alarms was latest updated
     */
    public String getUpdated() {
        return dateFormat.format(updated);
    }
    
    /**
     * getSelectedAlarm
     * @return the alarm that is selected in the view
     */
    public SNMPMessage getSelectedAlarm() {
        return selectedAlarm;
    }
    
    /**
     * Updated the selected alarm
     * @param selectedAlarm
     */
    public void setSelectedAlarm(SNMPMessage selectedAlarm) {
        this.selectedAlarm = selectedAlarm;
    }
    
    
}
