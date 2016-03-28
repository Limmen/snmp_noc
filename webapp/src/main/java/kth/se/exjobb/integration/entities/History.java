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

/**
 * Entity class representing configuration history.
 *
 * @author Kim Hammar on 2016-03-26.
 */
@Entity
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    private int requestId;
    private String systemName;
    private Severity severity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date removedDate = new Date();

    /**
     * Default class constructor
     */
    public History() {
    }

    /**
     * Classconstructor
     * 
     * @param requestId requestId of the removed alarm
     * @param systemName systemName of the removed alarm
     * @param severity severity of the removed alarm
     */
    public History(int requestId, String systemName, Severity severity) {
        this.requestId = requestId;
        this.systemName = systemName;
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
    /**
     * getRemovalDate
     * 
     * @return Date when this history was created
     */
    public Date getRemovedDate() {
        return removedDate;
    }

    /**
     * getRequestId
     * 
     * @return id of the deleted request
     */
    public int getRequestId() {
        return requestId;
    }
    /**
     * getSystemName
     * 
     * @return systemname of the deleted alarm
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * getSeverity
     * 
     * @return Severity of the deleted alarm
     */
    public Severity getSeverity() {
        return severity;
    }
    

}
