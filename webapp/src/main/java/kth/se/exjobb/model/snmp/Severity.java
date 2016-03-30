/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.model.snmp;

import java.io.Serializable;
import kth.se.exjobb.model.util.SeverityOrdering;

/**
 * Class that represent severities of alarms. 
 * @author Kim Hammar
 */
public class Severity implements Serializable, Comparable {
    private String severity;

    /**
     *
     */
    public Severity(){}
    /**
     * Class constructor.
     * 
     * @param severity string that represent the name of the severity.
     */
    public Severity(String severity){
        this.severity = severity;
    }        
    
    /**
     * Overriden from the comparable interface. 
     * This function is used when ordering alarms in the GUI.
     * 
     * @param object Object to compare with this object.
     * @return 0 if the objects have equal order, 1 if this object has higher order, -1 if this
     * object has lower order.
     */
    @Override
    public int compareTo(Object object) {
        Severity sverityToCompare = (Severity) object;
        int severityValue1 = SeverityOrdering.severityOrdering.get(severity);
        int severityValue2 = SeverityOrdering.severityOrdering.get(sverityToCompare.getSeverity());
        
        if(severityValue1 == severityValue2)
            return 0;        
        if(severityValue1 > severityValue2)
            return 1;

        return -1;    
    }

    /**
     * toString
     * @return string representation of this object
     */
    @Override
    public String toString(){
        return severity;
    }

    /**
     * getSeverity
     * @return severity string
     */
    public String getSeverity() {
        return severity;
    }        
    
}
