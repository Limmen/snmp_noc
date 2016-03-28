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
    @Temporal(TemporalType.TIMESTAMP)
    private Date configDate;
    private String history;
    @Temporal(TemporalType.TIMESTAMP)
    private Date historyDate;

    public Configuration() {
    }

    public Configuration(String policy, Severity severity, String history) {
        setPolicy(policy);
        setHistory(history);
        this.severity = severity;        
    }

    /**
     * getId
     *
     * @return id of the entity.
     */
    public BigInteger getId() {
        return id;
    }

    public String getPolicy() {
        return policy;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
        this.configDate = SavingPolicies.getInstance().getDate(policy);
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Date getConfigDate() {
        return configDate;
    }
    
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
        this.historyDate = SavingPolicies.getInstance().getDate(history);
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    
}
