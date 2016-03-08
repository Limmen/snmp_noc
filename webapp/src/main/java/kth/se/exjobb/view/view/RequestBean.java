/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.view.view;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.DTO.SetRequestDTO;
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
    private boolean result = false;
    private SNMPMessage resultAlarm;
    private SetRequestDTO setDTO = new SetRequestDTO();
    private String ip;
    
    public RequestBean() {
    }
    
    @PostConstruct
    public void init(){
    }

    public void sendRequest() throws IOException{
        
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public SNMPMessage getResultAlarm() {
        return resultAlarm;
    }

    public void setResultAlarm(SNMPMessage alarm) {
        this.resultAlarm = alarm;
    }

    public SetRequestDTO getSetDTO() {
        return setDTO;
    }

    public void setSetDTO(SetRequestDTO setDTO) {
        this.setDTO = setDTO;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

 
    
}
