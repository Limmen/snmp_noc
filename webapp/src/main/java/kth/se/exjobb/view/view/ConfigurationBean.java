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
import kth.se.exjobb.model.util.SavingPolicies;
import kth.se.exjobb.model.util.SeverityOrdering;

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
    private String history;
    
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
        severity = configuration.getSeverity().getSeverity();   
        history = configuration.getHistory();
        savingPolicies = SavingPolicies.getInstance().getSavingPolicies();
        severityPolicies = new ArrayList<String>(SeverityOrdering.severityOrdering.keySet());
        
    }
    
    
    public void updateConfiguration(){
        configuration = contr.updateConfiguration(save, severity, history);
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
    
    public boolean deleteWarning(){
        if(SeverityOrdering.severityOrdering.get(severity) > 
                SeverityOrdering.severityOrdering.get(configuration.getSeverity().getSeverity()))
            return true;
        if(SavingPolicies.getInstance().getDate(save).compareTo(configuration.getConfigDate()) > 0)
            return true;
        
        return false;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
    
    
}
