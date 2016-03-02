package kth.se.exjobb.model.snmp;

/**
 * Represents a variable binding within an SNMP-message.
 * Contains an oid and a corresponding value.
 * @author Marcus Blom
 */
public class SNMPVariableBinding
{
    String oid;
    Object value; //todo(Marcus): How to handle values of possibly different data types?

    public SNMPVariableBinding(String oid, Object value)
    {
        this.oid = oid;
        this.value = value;
    }

    public String getOid()
    {
        return oid;
    }

    public String getValue()
    {
        
        return value.toString();
    }
    
}
