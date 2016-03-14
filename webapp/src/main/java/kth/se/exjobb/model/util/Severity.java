/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.model.util;

import java.util.HashMap;

/**
 * Class that represent severities of alarms. 
 * @author Kim Hammar
 */
public class Severity implements Comparable {

    private final String severity;

    /**
     * Class constructor.
     * 
     * @param severity string that represent the name of the severity.
     */
    public Severity(String severity){
        this.severity = severity;
    }
    
    private static final HashMap<String,Integer> severityOrdering = new HashMap();
    static
    {
        severityOrdering.put("Cleared", 0);
        severityOrdering.put("Indeterminate", 1);
        severityOrdering.put("Warning", 2);
        severityOrdering.put("Minor", 3);
        severityOrdering.put("Major", 4);
        severityOrdering.put("Critical", 5);
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
        int severityValue1 = severityOrdering.get(severity);
        int severityValue2 = severityOrdering.get(sverityToCompare.getSeverity());
        
        if(severityValue1 == severityValue2)
            return 0;        
        if(severityValue1 > severityValue2)
            return -1;

        return 1;    
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
