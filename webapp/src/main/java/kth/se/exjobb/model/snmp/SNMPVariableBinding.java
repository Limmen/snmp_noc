package kth.se.exjobb.model.snmp;

/**
 * Represents a variable binding within an SNMP-message.
 * Contains an oid and a corresponding value.
 * @author Marcus Blom
 */
public class SNMPVariableBinding
{
    byte[] oid;  //todo(Marcus): How to represent oids?
    Object value; //todo(Marcus): How to handle values of possibly different data types?

    public SNMPVariableBinding(byte[] oid, Object value)
    {
        this.oid = oid;
        this.value = value;
    }

    public byte[] getOid()
    {
        return oid;
    }

    public Object getValue()
    {
        return value;
    }
    
}
