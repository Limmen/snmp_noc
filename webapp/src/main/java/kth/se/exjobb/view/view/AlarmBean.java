/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.util.GenericLogger;

/**
 * Managed bean representing the interface between the index page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar
 */
@Named(value = "alarmBean")
@ViewScoped
public class AlarmBean implements Serializable {
    @EJB
    Controller contr;
    private List<SNMPMessage> alarms;
    private List<SNMPMessage> criticalAlarms;
    private List<SNMPMessage> majorAlarms;
    private List<SNMPMessage> minorAlarms;
    private List<SNMPMessage> warningAlarms;
    private List<SNMPMessage> indeterminateAlarms;
    private List<SNMPMessage> clearedAlarms;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date updated = new Date();
    private SNMPMessage selectedAlarm;
    private SNMPMessage selectedCriticalAlarm;
    private SNMPMessage selectedMajorAlarm;
    private SNMPMessage selectedMinorAlarm;
    private SNMPMessage selectedWarningAlarm;
    private SNMPMessage selectedIndeterminateAlarm;
    private SNMPMessage selectedClearedAlarm;
        
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will call the controller to fetch a list of all alarms.
     */
    @PostConstruct
    public void init(){
        fetchAndSortAlarms();
    }
    
    /**
     * A method that is called on time-periods to partially update the view with new alarms.
     */
    public void fetchAlarms(){
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
        updated = new Date();
    }
    
    /**
     * A method that is called on time-periods to partially update the view with new alarms.
     */
    public void fetchAndSortAlarms(){
         alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
        criticalAlarms = (List) contr.getCriticalAlarms(alarms);
        if(criticalAlarms == null)
            criticalAlarms = new ArrayList();
        majorAlarms = (List) contr.getCriticalAlarms(alarms);
        if(majorAlarms == null)
            majorAlarms = new ArrayList();
        minorAlarms = (List) contr.getCriticalAlarms(alarms);
        if(minorAlarms == null)
            minorAlarms = new ArrayList();
        warningAlarms = (List) contr.getCriticalAlarms(alarms);
        if(warningAlarms == null)
            warningAlarms = new ArrayList();
        indeterminateAlarms = (List) contr.getCriticalAlarms(alarms);
        if(indeterminateAlarms == null)
            indeterminateAlarms = new ArrayList();
        clearedAlarms = (List) contr.getCriticalAlarms(alarms);
        if(clearedAlarms == null)
            clearedAlarms = new ArrayList();
        updated = new Date();
    }
    /**
     * Removes a selectedAlarm from the list of alarms
     */
    @GenericLogger
    public void removeSelectedAlarm(){
        contr.removeSelectedAlarm(selectedAlarm);
        fetchAlarms();
    }
    
    /**
     * Removes a selectedCriticalAlarm from the list of critical alarms
     */
    @GenericLogger
    public void removeSelectedCriticalAlarm(){
        contr.removeSelectedAlarm(selectedCriticalAlarm);
        fetchAlarms();
    }
    
    /**
     * Removes a selectedMajorAlarm from the list of major alarms
     */
    @GenericLogger
    public void removeSelectedMajorAlarm(){
        contr.removeSelectedAlarm(selectedMajorAlarm);
        fetchAlarms();
    }

    /**
     * Removes a selectedMinorAlarm from the list of minor alarms
     */
    @GenericLogger
    public void removeSelectedMinorAlarm(){
        contr.removeSelectedAlarm(selectedMinorAlarm);
        fetchAlarms();
    }
    
    /**
     * Removes a selectedWarningAlarm from the list of warning alarms
     */
    @GenericLogger
    public void removeSelectedWarningAlarm(){
        contr.removeSelectedAlarm(selectedWarningAlarm);
        fetchAlarms();
    }
    
    /**
     * Removes a selectedIndeterminateAlarm from the list of indeterminate alarms
     */
    @GenericLogger
    public void removeSelectedIndeterminateAlarm(){
        contr.removeSelectedAlarm(selectedIndeterminateAlarm);
        fetchAlarms();
    }
    
    /**
     * Removes a selectedClearedAlarm from the list of cleared alarms
     */
    @GenericLogger
    public void removeSelectedClearedAlarm(){
        contr.removeSelectedAlarm(selectedClearedAlarm);
        fetchAlarms();
    }
    
    /**
     * getAlarms
     * @return List of alarms
     */
    public List<SNMPMessage> getAlarms() {
        return alarms;
    }
    
    /**
     * getMostRecentCriticalAlarms
     * @return list containing the 7 most recent critical alarms
     */
    public List<SNMPMessage> getMostRecentCriticalAlarms(){
        Collections.sort(criticalAlarms);
        if(criticalAlarms.size() > 10){
            List<SNMPMessage> mostRecentCriticalAlarms = new ArrayList();
            for(int i = 0; i < 7; i++){
                mostRecentCriticalAlarms.add(criticalAlarms.get(i));
            }
            return mostRecentCriticalAlarms;
        }
        else
            return criticalAlarms;
    }
    
    /**
     * getCriticalAlarms
     * @return list of critical alarms
     */
    public List<SNMPMessage> getCriticalAlarms(){
        return criticalAlarms;
    }
    
    /**
     * getMajorAlarms
     * @return list of major alarms
     */
    public List<SNMPMessage> getMajorAlarms(){
        return majorAlarms;
    }
    
    /**
     * getMinorAlarms
     * @return list of minor alarms
     */
    public List<SNMPMessage> getMinorAlarms(){
        return minorAlarms;
    }
        
    /**
     * getWarningAlarms
     * @return list of warning alarms
     */
    public List<SNMPMessage> getWarningAlarms(){
        return warningAlarms;
    }
    
    /**
     * getIndeterminateAlarms
     * @return list of indeterminate alarms
     */
    public List<SNMPMessage> getIndeterminateAlarms(){
        return indeterminateAlarms;
    }
    
    /**
     * getClearedAlarms
     * @return list of cleared alarms
     */
    public List<SNMPMessage> getClearedAlarms(){
        return clearedAlarms;
    }
    
    /**
     * getUpdated
     * @return Date when the list of alarms was latest updated
     */
    public String getUpdated() {
        return dateFormat.format(updated);
    }
    
    /**
     * getSelectedAlarm
     * @return the alarm that is selected in the view
     */
    public SNMPMessage getSelectedAlarm() {
        return selectedAlarm;
    }
    
    /**
     * Updated the selected alarm
     * @param selectedAlarm
     */
    public void setSelectedAlarm(SNMPMessage selectedAlarm) {
        this.selectedAlarm = selectedAlarm;
    }

    /**
     * getSelectedCriticalAlarm
     * @return the critical alarm that is selected in the view
     */
    public SNMPMessage getSelectedCriticalAlarm() {
        return selectedCriticalAlarm;
    }

    /**
     * Update the selected critical alarm
     * @param selectedCriticalAlarm
     */
    public void setSelectedCriticalAlarm(SNMPMessage selectedCriticalAlarm) {
        this.selectedCriticalAlarm = selectedCriticalAlarm;
    }

    /**
     * getSelectedMajorAlarm
     * @return the major alarm that is selected in the view
     */
    public SNMPMessage getSelectedMajorAlarm() {
        return selectedMajorAlarm;
    }

    /**
     * Updates the selected major alarm
     * @param selectedMajorAlarm
     */
    public void setSelectedMajorAlarm(SNMPMessage selectedMajorAlarm) {
        this.selectedMajorAlarm = selectedMajorAlarm;
    }

    /**
     * getSelectedMinorAlarm
     * @return the minor alarm that is selected in the view
     */
    public SNMPMessage getSelectedMinorAlarm() {
        return selectedMinorAlarm;
    }

    /**
     * Updates the selected minor alarm
     * @param selectedMinorAlarm
     */
    public void setSelectedMinorAlarm(SNMPMessage selectedMinorAlarm) {
        this.selectedMinorAlarm = selectedMinorAlarm;
    }

    /**
     * getSelectedWarningAlarm
     * @return the warning alarm that is selected in the view
     */
    public SNMPMessage getSelectedWarningAlarm() {
        return selectedWarningAlarm;
    }

    /**
     * Updates the selected warning alarm
     * @param selectedWarningAlarm
     */
    public void setSelectedWarningAlarm(SNMPMessage selectedWarningAlarm) {
        this.selectedWarningAlarm = selectedWarningAlarm;
    }

    /**
     * getSelectedIndeterminateAlarm
     * @return the indeterminate alarm that is selected in the view
     */
    public SNMPMessage getSelectedIndeterminateAlarm() {
        return selectedIndeterminateAlarm;
    }

    /**
     * Updates the selected indeterminate alarm
     * @param selectedIndeterminateAlarm
     */
    public void setSelectedIndeterminateAlarm(SNMPMessage selectedIndeterminateAlarm) {
        this.selectedIndeterminateAlarm = selectedIndeterminateAlarm;
    }

    /**
     * getSelectedClearedAlarm
     * @return the cleared alarm that is selected in the view
     */
    public SNMPMessage getSelectedClearedAlarm() {
        return selectedClearedAlarm;
    }

    /**
     * Updates the selected cleared alarm
     * @param selectedClearedAlarm
     */
    public void setSelectedClearedAlarm(SNMPMessage selectedClearedAlarm) {
        this.selectedClearedAlarm = selectedClearedAlarm;
    }
    
    
}
