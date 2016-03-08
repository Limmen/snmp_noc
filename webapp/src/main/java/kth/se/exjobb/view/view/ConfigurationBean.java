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
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author kim
 */
@Named(value = "configurationBean")
@ViewScoped
public class ConfigurationBean implements Serializable {

    
    private List<String> savings;
    private String save = "1 week";
    
    public ConfigurationBean() {
    }
    
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
        save = "1 week";
    }

    public List<String> getSavings() {
        return savings;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
    
}
