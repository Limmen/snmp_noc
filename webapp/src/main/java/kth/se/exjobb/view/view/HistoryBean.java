/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.integration.entities.History;

/**
 * Managed bean representing the interface between the history page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar
 */
@Named(value = "historyBean")
@ViewScoped
public class HistoryBean implements Serializable {
    @EJB
    Controller contr;
    private List<History> history;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private History selectedHistory;  
        
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will call the controller to fetch a list of all history.
     */
    @PostConstruct
    public void init(){
        history = (List) contr.getAllHistories();
        if(history == null)
            history = new ArrayList();
    }

    /**
     * getHistory
     * @return List of history
     */
    public List<History> getHistory() {
        return history;
    }
            
    /**
     * getSelectedHistory
     * @return the history row that is selected in the view
     */
    public History getSelectedHistory() {
        return selectedHistory;
    }
    
    /**
     * Updated the selected history
     * @param selectedHistory
     */
    public void setSelectedHistory(History selectedHistory) {
        this.selectedHistory = selectedHistory;
    }
    
    
}
