/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.entities.Alarm;
import kth.se.exjobb.model.snmp.SNMPManager;

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
    private List<Alarm> alarms;
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
    }
    public List<Alarm> getAlarms() {
        return alarms;
    }
    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
