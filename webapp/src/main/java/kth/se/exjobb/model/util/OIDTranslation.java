/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model.util;

import java.util.HashMap;

/**
 * Singleton Read-Only class that contains translations between OID and Description.
 *
 * @author Kim Hammar
 */
public class OIDTranslation {

    private OIDTranslation(){}

    private static class instanceHolder {
        private static final OIDTranslation instance = new OIDTranslation();
    }

    /**
     * HashMap between common OID's and their meaning.
     */
    public static final HashMap<String,String> oids = new HashMap();
    static
    {
        oids.put("1.3.6.1.2.1.1.3.0.", "sysUpTimeInstance");
        oids.put("1.3.6.1.6.3.1.1.4.1.0.", "snmpTrapOID.0");
        oids.put("1.3.6.1.6.3.1.1.3.0.", "snmpMIBObjects.3.0");
        oids.put("1.3.6.1.2.1.1.5.0.", "sysName.0");
        oids.put("1.3.6.1.2.1.1.1.0.", "sysDescr.0");
        oids.put("1.3.6.1.2.1.1.4.0.", "sysContact.0");
        oids.put("1.3.6.1.2.1.1.6.0.", "sysLocation.0");
        oids.put("3.6.1.4.1.9.1.1.1.2.2.1.1.7.", "calSeverity");
    }

    /**
     * Returns the sole instance of this singleton class.
     * 
     * @return instance
     */
    public static OIDTranslation getInstance(){
        return instanceHolder.instance;
    }
}
