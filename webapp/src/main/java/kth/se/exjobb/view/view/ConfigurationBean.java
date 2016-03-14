/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import kth.se.exjobb.util.GenericLogger;

/**
 * Managed bean representing the interface between the configuration page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar
 */
@Named(value = "configurationBean")
@ViewScoped
public class ConfigurationBean implements Serializable {
        
    private List<String> savingPolicies;
    private String save;
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * Initializes a list of possible settings for saving-policy.
     */
    @PostConstruct
    public void init(){
        savingPolicies = new ArrayList();
        savingPolicies.add("dont save");
        savingPolicies.add("1 day");
        savingPolicies.add("3 days");
        savingPolicies.add("1 week");
        savingPolicies.add("2 weeks");
        savingPolicies.add("1 month");
        savingPolicies.add("6 months");
        savingPolicies.add("forever");
        save = "dont save";
    }
    
    /**
     * getSavings
     * @return list of saving-policies
     */
    public List<String> getSavingPolicies() {
        return savingPolicies;
    }
    
    /**
     * getSave
     * @return the current setting for saving policy
     */
    public String getSave() {
        return save;
    }
    
    /**
     * updated the saving policy
     * @param save saving policy
     */
    @GenericLogger
    public void setSave(String save) {
        this.save = save;
    }
    
}
