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
    private List<SNMPMessage> agent1Alarms;
    private List<SNMPMessage> agent2Alarms;
    private List<SNMPMessage> agent3Alarms;
    private List<SNMPMessage> agent4Alarms;
    private List<SNMPMessage> agent5Alarms;
    private List<SNMPMessage> agent6Alarms;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date updated = new Date();
    private SNMPMessage selectedAlarm;
    private SNMPMessage selectedCriticalAlarm;
    private SNMPMessage selectedMajorAlarm;
    private SNMPMessage selectedMinorAlarm;
    private SNMPMessage selectedWarningAlarm;
    private SNMPMessage selectedIndeterminateAlarm;
    private SNMPMessage selectedClearedAlarm;
    private SNMPMessage selectedAgent1Alarm;
    private SNMPMessage selectedAgent2Alarm;
    private SNMPMessage selectedAgent3Alarm;
    private SNMPMessage selectedAgent4Alarm;
    private SNMPMessage selectedAgent5Alarm;
    private SNMPMessage selectedAgent6Alarm;
        
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * The method will call the controller to fetch a list of all alarms.
     */
    @PostConstruct
    public void init(){
        fetchAndSortAlarmsSeverity();
        fetchAndSortAlarmsAgents();
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
    public void fetchAndSortAlarmsSeverity(){
         alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
        criticalAlarms = (List) contr.getCriticalAlarms(alarms);
        if(criticalAlarms == null)
            criticalAlarms = new ArrayList();
        majorAlarms = (List) contr.getMajorAlarms(alarms);
        if(majorAlarms == null)
            majorAlarms = new ArrayList();
        minorAlarms = (List) contr.getMinorAlarms(alarms);
        if(minorAlarms == null)
            minorAlarms = new ArrayList();
        warningAlarms = (List) contr.getWarningAlarms(alarms);
        if(warningAlarms == null)
            warningAlarms = new ArrayList();
        indeterminateAlarms = (List) contr.getIndeterminateAlarms(alarms);
        if(indeterminateAlarms == null)
            indeterminateAlarms = new ArrayList();
        clearedAlarms = (List) contr.getClearedAlarms(alarms);
        if(clearedAlarms == null)
            clearedAlarms = new ArrayList();
        updated = new Date();
    }
    /**
     * A method that is called on time-periods to partially update the view with new alarms.
     */
    public void fetchAndSortAlarmsAgents(){
         alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
        agent1Alarms = (List) contr.getAgent1Alarms(alarms);
        if(agent1Alarms == null)
            agent1Alarms = new ArrayList();
        agent2Alarms = (List) contr.getAgent2Alarms(alarms);
        if(agent2Alarms == null)
            agent2Alarms = new ArrayList();
        agent3Alarms = (List) contr.getAgent3Alarms(alarms);
        if(agent3Alarms == null)
            agent3Alarms = new ArrayList();
        agent4Alarms = (List) contr.getAgent4Alarms(alarms);
        if(agent4Alarms == null)
            agent4Alarms = new ArrayList();
        agent5Alarms = (List) contr.getAgent5Alarms(alarms);
        if(agent5Alarms == null)
            agent5Alarms = new ArrayList();
        agent6Alarms = (List) contr.getAgent6Alarms(alarms);
        if(agent6Alarms == null)
            agent6Alarms = new ArrayList();
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
        fetchAndSortAlarmsSeverity();
    }
    
    /**
     * Removes a selectedMajorAlarm from the list of major alarms
     */
    @GenericLogger
    public void removeSelectedMajorAlarm(){
        contr.removeSelectedAlarm(selectedMajorAlarm);
        fetchAndSortAlarmsSeverity();
    }

    /**
     * Removes a selectedMinorAlarm from the list of minor alarms
     */
    @GenericLogger
    public void removeSelectedMinorAlarm(){
        contr.removeSelectedAlarm(selectedMinorAlarm);
        fetchAndSortAlarmsSeverity();
    }
    
    /**
     * Removes a selectedWarningAlarm from the list of warning alarms
     */
    @GenericLogger
    public void removeSelectedWarningAlarm(){
        contr.removeSelectedAlarm(selectedWarningAlarm);
        fetchAndSortAlarmsSeverity();
    }
    
    /**
     * Removes a selectedIndeterminateAlarm from the list of indeterminate alarms
     */
    @GenericLogger
    public void removeSelectedIndeterminateAlarm(){
        contr.removeSelectedAlarm(selectedIndeterminateAlarm);
        fetchAndSortAlarmsSeverity();
    }
    
    /**
     * Removes a selectedClearedAlarm from the list of cleared alarms
     */
    @GenericLogger
    public void removeSelectedClearedAlarm(){
        contr.removeSelectedAlarm(selectedClearedAlarm);
        fetchAndSortAlarmsSeverity();
    }
    
        /**
     * Removes a selectedAgent1Alarm from the list of agent1 alarms
     */
    @GenericLogger
    public void removeSelectedAgent1Alarm(){
        contr.removeSelectedAlarm(selectedAgent1Alarm);
        fetchAndSortAlarmsAgents();
    }
    
    /**
     * Removes a selectedAgent2Alarm from the list of agent2 alarms
     */
    @GenericLogger
    public void removeSelectedAgent2Alarm(){
        contr.removeSelectedAlarm(selectedAgent2Alarm);
        fetchAndSortAlarmsAgents();
    }

    /**
     * Removes a selectedAgent3Alarm from the list of agent3 alarms
     */
    @GenericLogger
    public void removeSelectedAgent3Alarm(){
        contr.removeSelectedAlarm(selectedAgent3Alarm);
        fetchAndSortAlarmsAgents();
    }
    
    /**
     * Removes a selectedAgent4Alarm from the list of agent4 alarms
     */
    @GenericLogger
    public void removeSelectedAgent4Alarm(){
        contr.removeSelectedAlarm(selectedAgent4Alarm);
        fetchAndSortAlarmsAgents();
    }
    
    /**
     * Removes a selectedAgent5Alarm from the list of agent5 alarms
     */
    @GenericLogger
    public void removeSelectedAgent5Alarm(){
        contr.removeSelectedAlarm(selectedAgent5Alarm);
        fetchAndSortAlarmsAgents();
    }
    
    /**
     * Removes a selectedAgent6Alarm from the list of agent6 alarms
     */
    @GenericLogger
    public void removeSelectedAgent6Alarm(){
        contr.removeSelectedAlarm(selectedAgent6Alarm);
        fetchAndSortAlarmsAgents();
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

    public List<SNMPMessage> getAgent1Alarms() {
        return agent1Alarms;
    }

    public List<SNMPMessage> getAgent2Alarms() {
        return agent2Alarms;
    }

    public List<SNMPMessage> getAgent3Alarms() {
        return agent3Alarms;
    }

    public List<SNMPMessage> getAgent4Alarms() {
        return agent4Alarms;
    }

    public List<SNMPMessage> getAgent5Alarms() {
        return agent5Alarms;
    }

    public List<SNMPMessage> getAgent6Alarms() {
        return agent6Alarms;
    }

    public SNMPMessage getSelectedAgent1Alarm() {
        return selectedAgent1Alarm;
    }

    public void setSelectedAgent1Alarm(SNMPMessage selectedAgent1Alarm) {
        this.selectedAgent1Alarm = selectedAgent1Alarm;
    }

    public SNMPMessage getSelectedAgent2Alarm() {
        return selectedAgent2Alarm;
    }

    public void setSelectedAgent2Alarm(SNMPMessage selectedAgent2Alarm) {
        this.selectedAgent2Alarm = selectedAgent2Alarm;
    }

    public SNMPMessage getSelectedAgent3Alarm() {
        return selectedAgent3Alarm;
    }

    public void setSelectedAgent3Alarm(SNMPMessage selectedAgent3Alarm) {
        this.selectedAgent3Alarm = selectedAgent3Alarm;
    }

    public SNMPMessage getSelectedAgent4Alarm() {
        return selectedAgent4Alarm;
    }

    public void setSelectedAgent4Alarm(SNMPMessage selectedAgent4Alarm) {
        this.selectedAgent4Alarm = selectedAgent4Alarm;
    }

    public SNMPMessage getSelectedAgent5Alarm() {
        return selectedAgent5Alarm;
    }

    public void setSelectedAgent5Alarm(SNMPMessage selectedAgent5Alarm) {
        this.selectedAgent5Alarm = selectedAgent5Alarm;
    }

    public SNMPMessage getSelectedAgent6Alarm() {
        return selectedAgent6Alarm;
    }

    public void setSelectedAgent6Alarm(SNMPMessage selectedAgent6Alarm) {
        this.selectedAgent6Alarm = selectedAgent6Alarm;
    }
    
}
