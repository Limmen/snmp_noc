package kth.se.exjobb.model.snmp;

import java.util.HashMap;

/**
 * Represents a variable binding within an SNMP-message.
 * Contains an oid and a corresponding value.
 * @author Marcus Blom
 */
public class SNMPVariableBinding
{
    private final String oid;
    private final Object value; //todo(Marcus): How to handle values of possibly different data types?
    private final HashMap<String,String> oids;
    public SNMPVariableBinding(String oid, Object value, HashMap<String,String> oids)
    {
        this.oid = oid;
        this.value = value;
        this.oids = oids;         
    }

    public String getOid()
    {
        return oids.get(oid.trim());
    }

    public String getValue()
    {
        
        return value.toString();
    }
    
}
