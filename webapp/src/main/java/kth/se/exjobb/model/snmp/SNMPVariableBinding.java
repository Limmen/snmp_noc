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

    public SNMPVariableBinding(String oid, Object value)
    {
        this.oid = oid;
        this.value = value;        
    }

    public String getOid()
    {
        return SNMPParser.translateOID(oid);
    }

    public String getValue()
    {
        
        return value.toString();
    }
    
}
