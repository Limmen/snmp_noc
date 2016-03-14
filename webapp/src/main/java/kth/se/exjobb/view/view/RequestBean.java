/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.view.DTO.SetRequestDTO;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.model.snmp.SNMPMessage;
import kth.se.exjobb.util.GenericLogger;

/**
 * Managed bean representing the interface between the request page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar
 */
@Named(value = "requestBean")
@ViewScoped
public class RequestBean implements Serializable {
    
    @EJB
    private Controller contr;
    private boolean availableResult = false;
    private SNMPMessage resultAlarm;
    private SetRequestDTO setDTO = new SetRequestDTO();
    private String ip;
    private String[] selectedParameters;
    private List<String> parameters;
    
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will set the parameter list
     */
    @PostConstruct
    public void init(){
        parameters = new ArrayList();
        parameters.add("System name");
        parameters.add("System description");
        parameters.add("System contact");
        parameters.add("System location");
    }
    
    /**
     * Method called when the user presses the Send-button.
     * Will availableResult in a call to the controller to send a SNMP-request to a
     * specified IP-address.
     */
    @GenericLogger
    public void sendRequest(){
        
    }
    
    /**
     * Returns true or false depending on if there is a availableResult to show the user or not.
     * @return
     */
    public boolean isResult() {
        return availableResult;
    }
    
    /**
     * Updates the boolean that decides wether there is a availableResult to show or not.
     * @param availableResult
     */
    public void setAvailableResult(boolean availableResult) {
        this.availableResult = availableResult;
    }
    
    /**
     * getResultAlarm
     * @return The availableResult of the latest request
     */
    public SNMPMessage getResultAlarm() {
        return resultAlarm;
    }
    
    /**
     * Updates the availableResult of the latest request
     * @param alarm
     */
    public void setResultAlarm(SNMPMessage alarm) {
        this.resultAlarm = alarm;
    }
    
    /**
     * getSetDTO
     * @return DTO of set-request parameters
     */
    public SetRequestDTO getSetDTO() {
        return setDTO;
    }
    
    /**
     * Updates set-request parameters
     * @param setDTO
     */
    public void setSetDTO(SetRequestDTO setDTO) {
        this.setDTO = setDTO;
    }
    
    /**
     * getIP
     * @return IP-address to send request to
     */
    public String getIp() {
        return ip;
    }
    
    /**
     * Updates the IP-address.
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * getSelectedParameters
     * @return array of selected parameters
     */
    public String[] getSelectedParameters() {
        return selectedParameters;
    }

    /**
     * Update selected parameters
     * @param selectedParameters array of parameters
     */
    public void setSelectedParameters(String[] selectedParameters) {
        this.selectedParameters = selectedParameters;
    }

    /**
     * getParameters
     * @return list of parameters
     */
    public List<String> getParameters() {
        return parameters;
    }
            
}
