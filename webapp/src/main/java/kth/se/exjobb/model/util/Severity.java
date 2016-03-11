/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.model.util;

import java.util.HashMap;

/**
 *
 * @author kim
 */
public class Severity implements Comparable {

    private final String severity;
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
    
    @Override
    public int compareTo(Object t) {
        Severity sev = (Severity) t;
        int val1 = severityOrdering.get(severity);
        int val2 = severityOrdering.get(sev.getSeverity());
        
        if(val1 == val2)
            return 0;        
        if(val1 > val2)
            return -1;

        return 1;    
    }

    @Override
    public String toString(){
        return severity;
    }
    public String getSeverity() {
        return severity;
    }        
    
}
