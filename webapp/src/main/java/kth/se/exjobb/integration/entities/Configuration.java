/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.integration.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import kth.se.exjobb.model.snmp.Severity;
import kth.se.exjobb.model.util.SavingPolicies;

/**
 * Entity class representing a NOC configuration.
 *
 * @author Kim Hammar on 2016-03-25.
 */
@Entity
public class Configuration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    private String policy;
    private Severity severity;
    private String history;
    private Severity notification;
    private Severity statistics;

    /**
     * Default Class constructor
     */
    public Configuration() {
    }

    /**
     * Class constructor
     * 
     * @param policy saving-policy for alarms
     * @param severity severity-policy for alarms
     * @param history saving-policy for history
     * @param notification
     * @param statistics
     */
    public Configuration(String policy, Severity severity, String history, Severity notification, Severity statistics) {
        this.policy = policy;
        this.history = history;
        this.severity = severity;
        this.notification = notification;
        this.statistics = statistics;
    }

    /**
     * getId
     *
     * @return id of the entity.
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * getPolicy.
     * 
     * @return saving-policy for alarms
     */
    public String getPolicy() {
        return policy;
    }

    /**
     * getSeverity
     * 
     * @return severity-policy for alarms
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * updates the saving-policy for alarms.
     * 
     * @param policy new saving-policy
     */
    public void setPolicy(String policy) {
        this.policy = policy;        
    }

    /**
     * Updates the sevetiy-policy for alarms.
     * 
     * @param severity new severity-policy
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * getConfigDate
     * 
     * @return date from where alarms should be saved in the database
     */
    public Date getConfigDate() {
        return SavingPolicies.getInstance().getDate(policy);
    }
    
    /**
     * getHistory
     * 
     * @return history-policy
     */
    public String getHistory() {
        return history;
    }

    /**
     * Updates the history-policy
     * 
     * @param history new history-policy
     */
    public void setHistory(String history) {
        this.history = history;        
    }

    /**
     * getHistoryDate. 
     * 
     * @return date from where history should be saved in the database
     */
    public Date getHistoryDate() {
        return SavingPolicies.getInstance().getDate(history);
    }

    /**
     * getNotification
     * 
     * @return severity-policy for notifications
     */
    public Severity getNotification() {
        return notification;
    }

    /**
     * Updates the severity-policy for notifications.
     * 
     * @param notification updated policy
     */
    public void setNotification(Severity notification) {
        this.notification = notification;
    }

    /** 
     * getStatistics
     * 
     * @return severity-policy for statistics
     */
    public Severity getStatistics() {
        return statistics;
    }

    /**
     * Updates the severity-policy for statistics
     * 
     * @param statistics updated policy
     */
    public void setStatistics(Severity statistics) {
        this.statistics = statistics;
    }

    
}
