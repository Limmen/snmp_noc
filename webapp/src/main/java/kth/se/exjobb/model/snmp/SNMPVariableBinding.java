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
    private final DataTypes dataType;
    
    public enum DataTypes
    {
        string,
        integer,
        application,
        oid,
        nullValue;
    }
    
    /**
     * Class constructor
     *
     * @param oid Object identifier
     * @param value Object value
     * @param dataType
     */
    public SNMPVariableBinding(String oid, Object value, DataTypes dataType)
    {
        this.oid = oid;
        this.value = value;
        this.dataType = dataType;
    }
    
    /**
     * getOid
     * @return object identifier
     */
    public String getOid()
    {
        return oid;
    }
    
    /**
     * getValue
     * @return object value
     */
    public Object getValue()
    {
        return value;
    }
    
    /**
     * getStringValue
     * @return get string representation of the value, neccessary for presentation layer
     */
    public String getStringValue(){
        return value.toString();
    }
    
    /**
     * getOidDescr
     * @return Description string of the OID.
     */
    public String getOidDescr(){
         return SNMPParser.translateOID(oid);
    }
    
    /**
     * getValueDataType
     * @return datatype of the value
     */
    public DataTypes getValueDataType()
    {
        return dataType;
    }

}