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
    private String notification;
    private String statistics;
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
        notification = configuration.getNotification().getSeverity();
        statistics = configuration.getStatistics().getSeverity();        
        savingPolicies = SavingPolicies.getInstance().getSavingPolicies();
        severityPolicies = new ArrayList<String>(SeverityOrdering.severityOrdering.keySet());
        
    }
    
    /**
     * Method called when the user clicks "save". 
     * Updates the current configuration with the new values.
     */
    public void updateConfiguration(){
        configuration = contr.updateConfiguration(save, severity, history, notification, statistics);
    }
    
    /**
     * Boolean method that checks whether the change of configuration will cause extra deletions
     * in the database, if so, a warning sign is shown.
     * 
     * @return true if the warning should be visible, otherwise false.
     */
    public boolean deleteWarning(){
        if(SeverityOrdering.severityOrdering.get(severity) > 
                SeverityOrdering.severityOrdering.get(configuration.getSeverity().getSeverity()))
            return true;
        if((SavingPolicies.getInstance().getDate(save).compareTo(configuration.getConfigDate()) > 0))
            return true;
        if(SavingPolicies.getInstance().getDate(history).compareTo(configuration.getHistoryDate()) > 0)
            return true;
        else
            return false;
    }
    
    /**
     * getSavings
     * @return list of saving-policies
     */
    public List<String> getSavingPolicies() {
        return savingPolicies;
    }

    /**
     * getConfiguration
     * 
     * @return the current configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * getSeverityPolicies
     * 
     * @return list of possible severitypolicies
     */
    public List<String> getSeverityPolicies() {
        return severityPolicies;
    }

    /**
     * getSeverity
     * @return the new policy for severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Updates the new policy for severity
     * 
     * @param severity new policy
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * getSave
     * 
     * @return the new saving-policy for alarms
     */
    public String getSave() {
        return save;
    }

    /**
     * Updates the new saving-policy for alarms
     * 
     * @param save the new saving-policy
     */
    public void setSave(String save) {
        this.save = save;
    }
    
    /**
     * getHistory
     * 
     * @return new saving-policy for histories.
     */
    public String getHistory() {
        return history;
    }

    /**
     * Updates the new saving-policy for histories.
     * 
     * @param history new saving-policy
     */
    public void setHistory(String history) {
        this.history = history;
    }

    /**
     * getNotification
     * 
     * @return the new notification policy
     */
    public String getNotification() {
        return notification;
    }

    /**
     * Updates the new notification policy 
     * 
     * @param notification new value
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    /**
     * getStatistics
     * 
     * @return the new statistics policy
     */
    public String getStatistics() {
        return statistics;
    }

    /**
     * Updates the new statistics policy
     * 
     * @param statistics new value
     */
    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }
    
    
}
