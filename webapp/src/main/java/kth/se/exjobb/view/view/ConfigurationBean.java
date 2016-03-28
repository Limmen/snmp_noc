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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.integration.entities.Configuration;

/**
 * Managed bean representing the interface between the configuration page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar
 */
@Named(value = "configurationBean")
@ViewScoped
public class ConfigurationBean implements Serializable {
        
    @EJB
    private Controller contr;
    private List<String> savingPolicies;
    private List<String> severityPolicies;
    private Configuration configuration;
    private String severity;
    private String save;
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * Initializes a list of possible settings for saving-policy.
     */
    @PostConstruct
    public void init(){
        configuration = contr.getConfiguration();
        save = configuration.getPolicy();
        severity = configuration.getSeverity();
        
        savingPolicies = new ArrayList();
        savingPolicies.add("Dont save");
        savingPolicies.add("1 day");
        savingPolicies.add("3 days");
        savingPolicies.add("1 week");
        savingPolicies.add("2 weeks");
        savingPolicies.add("1 month");
        savingPolicies.add("6 months");
        savingPolicies.add("Forever");
        
        severityPolicies = new ArrayList();
        severityPolicies.add("Cleared");
        severityPolicies.add("Indeterminate");
        severityPolicies.add("Warning");
        severityPolicies.add("Minor");
        severityPolicies.add("Major");
        severityPolicies.add("Critical");   
        
    }
    
    
    public void updateConfiguration(){
        configuration = contr.updateConfiguration(save, severity);
    }
    
    /**
     * getSavings
     * @return list of saving-policies
     */
    public List<String> getSavingPolicies() {
        return savingPolicies;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public List<String> getSeverityPolicies() {
        return severityPolicies;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
    

    
}
