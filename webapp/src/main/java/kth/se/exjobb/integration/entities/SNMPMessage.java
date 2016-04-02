/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.integration.entities;

import kth.se.exjobb.model.snmp.SNMPVariableBinding;
import kth.se.exjobb.model.snmp.Severity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A entity class representing an SNMP message.
 *
 * @author Marcus Blom
 */
@Entity
public class SNMPMessage implements Serializable, Comparable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger messageId;
    private int versionNumber;
    private String community;
    private int PDUType;
    private int requestID;
    private int error;
    private int errorIndex;
    private Severity severity;
    private String sysName;
    private List<SNMPVariableBinding> variableBindings;
    @Temporal(TemporalType.TIMESTAMP)
    private final Date receivedDate = new Date();    

    /**
     *
     */
    public SNMPMessage() {
    }
    /**
     * Class constructor
     * @param versionNumber SNMP version
     * @param community SNMP community string
     * @param requestID SNMP request ID
     * @param error SNMP error bit
     * @param errorIndex SNMP error index
     * @param PDUType
     * @param variableBindings SNMP VariableBindings
     */
    public SNMPMessage(int versionNumber, String community, int requestID, int error, int errorIndex,int PDUType, List<SNMPVariableBinding> variableBindings)
    {
        this.versionNumber = versionNumber;
        this.community = community;
        this.requestID = requestID;
        this.error = error;
        this.errorIndex = errorIndex;
        this.variableBindings = variableBindings;
        severity = new Severity(findSeverity());
        sysName = findSysName();
        this.PDUType = PDUType;
    }
    
    /**
     * getVersionNumber
     * @return version number
     */
    public int getVersionNumber()
    {
        return versionNumber;
    }
    
    /**
     * getCommunity
     * @return community string
     */
    public String getCommunity()
    {
        return community;
    }
    
    /**
     * getRequestID
     * @return requestID
     */
    public int getRequestID()
    {
        return requestID;
    }
    
    /**
     * getError
     * @return error value
     */
    public int getError()
    {
        return error;
    }
    
    /**
     * getErrorIndex
     * @return error index
     */
    public int getErrorIndex()
    {
        return errorIndex;
    }
    
    /**
     * getSeverity
     * @return severity of the SNMP alarm
     */
    public Severity getSeverity() {
        return severity;
    }
    
    /**
     * getVariableBindings
     * @return snmp variable bindings
     */
    public List<SNMPVariableBinding> getVariableBindings() {
        return variableBindings;
    }
    
    /**
     * getDate
     * @return receivedDate when the snmp message was received
     */
    public String getRecievedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(receivedDate);
    }

    /**
     * getDate
     * @return receivedDate when the snmp message was received
     */
    public Date getRawDate() 
    {
        return receivedDate;
    }
    /**
     * getSysName
     * @return system name
     */
    public String getSysName() 
    {
        return sysName;
    }

    /**
     * getPDUType
     *
     * @return PDUType (integer)
     */
    public int getPDUType()
    {
        return PDUType;
    }

    /**
     * getMessageId
     *
     * @return id of the entity.
     */
    public BigInteger getMessageId() {
        return messageId;
    }
    
    private String findSeverity(){
        for(SNMPVariableBinding binding : variableBindings){
            if(binding.getOid() != null){
                if(binding.getOid().equals("calSeverity"))
                    return binding.getValue().toString();
            }     
        }
        return "Cleared";
    }

    private String findSysName(){
        for(SNMPVariableBinding binding : variableBindings){
            if(binding.getOid() != null){
                if(binding.getOid().equals("sysName.0"))
                    return binding.getValue().toString();
            }
        }
        return "Not found";
    }

    /**
     * Used for sorting SNMPMessages. Sorts on severity and then date.
     *
     * @param t object to compare against.
     * @return 0 if the objects have equal order, 1 if this object has higher order, -1 if this
     * object has lower order.
     */
    @Override
    public int compareTo(Object t) {
        SNMPMessage compare = (SNMPMessage) t;
        if(severity.compareTo(compare.getSeverity()) != 0){
            return severity.compareTo(compare.getSeverity());
        }
        else
            return receivedDate.compareTo(compare.getRawDate());
    }

    /**
     * Method to convert the entity to a string representation
     *
     * @return string representation of the entity.
     */
    @Override
    public String toString(){
        return "RequestId: " + requestID + "\t " + "SysName: " + sysName + "\t Severity: " + severity;
    }
    
}
