package kth.se.exjobb.integration.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

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
    private String severity;

    public Configuration() {
    }

    public Configuration(String policy, String severity) {
        this.policy = policy;
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

    public String getSeverity() {
        return severity;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

}
