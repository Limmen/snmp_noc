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

/**
 * Managed bean representing the interface between the configuration page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author kim
 */
@Named(value = "configurationBean")
@ViewScoped
public class ConfigurationBean implements Serializable {
    
    
    private List<String> savings;
    private String save;
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * Initializes a list of possible settings for saving-policy.
     */
    @PostConstruct
    public void init(){
        savings = new ArrayList();
        savings.add("dont save");
        savings.add("1 day");
        savings.add("3 days");
        savings.add("1 week");
        savings.add("2 weeks");
        savings.add("1 month");
        savings.add("6 months");
        savings.add("forever");
        save = "dont save";
    }
    
    /**
     * getSavings
     * @return list of saving-policies
     */
    public List<String> getSavings() {
        return savings;
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
    public void setSave(String save) {
        this.save = save;
    }
    
}
