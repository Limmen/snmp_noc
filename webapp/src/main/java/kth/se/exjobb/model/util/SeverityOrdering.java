/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.model.util;

import java.util.HashMap;

/**
 *  Singleton Read-Only class that represents the ordering of different severities.
 *
 * @author Kim Hammar
 */
public class SeverityOrdering {

    private SeverityOrdering(){}

    private static class instanceHolder {
        private static final SeverityOrdering instance = new SeverityOrdering();
    }

    public static final HashMap<String,Integer> severityOrdering = new HashMap();
    static
    {
        severityOrdering.put("Cleared", 0);
        severityOrdering.put("Indeterminate", 1);
        severityOrdering.put("Warning", 2);
        severityOrdering.put("Minor", 3);
        severityOrdering.put("Major", 4);
        severityOrdering.put("Critical", 5);
    }

    public static SeverityOrdering getInstance(){
        return instanceHolder.instance;
    }
}
