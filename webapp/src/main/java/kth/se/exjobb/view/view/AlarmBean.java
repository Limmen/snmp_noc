/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
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
 *
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
    public AlarmBean() {
    }
    @PostConstruct
    public void init(){        
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
    }
    public void fetchAlarms(){        
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
        updated = new Date();
    }
    public void removeSelectedAlarm(){
        contr.removeSelectedAlarm(selectedAlarm);
    }
    public List<SNMPMessage> getAlarms() {
        return alarms;
    }
    public String getUpdated() {
        return dateFormat.format(updated);
    }

    public SNMPMessage getSelectedAlarm() {
        return selectedAlarm;
    }

    public void setSelectedAlarm(SNMPMessage selectedAlarm) {
        this.selectedAlarm = selectedAlarm;
    }
    
    
}
