/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.view.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.model.snmp.SNMPMessage;

/**
 *
 * @author kim
 */
@Named(value = "requestBean")
@ViewScoped 
public class RequestBean implements Serializable {

    @EJB
    private Controller contr;
    private List<String> targets;
    private String target;
    private boolean result = false;
    private SNMPMessage alarm;
    public RequestBean() {
    }
    
    @PostConstruct
    public void init(){
        targets = new ArrayList();
        targets.add("Choose target");
        targets.add("Simulated SNMP Agent");
        targets.add("Localhost snmp service");
    }
    
    public void sendRequest() throws IOException{        
        switch(target){
            case "Simulated SNMP Agent":
                //alarm = contr.sendAlarm("127.0.0.1/9999");
                result = true;
                break;
            case "Localhost snmp service":
                //alarm = contr.sendAlarm("127.0.0.1/161");
                result = true;
        }
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public SNMPMessage getAlarm() {
        return alarm;
    }

    public void setAlarm(SNMPMessage alarm) {
        this.alarm = alarm;
    }
    
}
