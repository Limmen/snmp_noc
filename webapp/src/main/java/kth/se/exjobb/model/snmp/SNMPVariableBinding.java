/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model.snmp;

import java.io.Serializable;

/**
 * Represents a variable binding within an SNMP-message.
 * Contains an oid and a corresponding value.
 * @author Marcus Blom
 */
public class SNMPVariableBinding implements Serializable
{
    private final String oid;
    private final Object value; //todo(Marcus): How to handle values of possibly different data types?
    
    /**
     * Class constructor
     *
     * @param oid Object identifier
     * @param value Object value
     */
    public SNMPVariableBinding(String oid, Object value)
    {
        this.oid = oid;
        this.value = value;
    }
    
    /**
     * getOid
     * @return object identifier
     */
    public String getOid()
    {
        return SNMPParser.translateOID(oid);
    }
    
    /**
     * getValue
     * @return object value
     */
    public String getValue()
    {
        return value.toString();
    }

}
