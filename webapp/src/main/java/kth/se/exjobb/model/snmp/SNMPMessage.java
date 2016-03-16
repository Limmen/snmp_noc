/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model.snmp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A class representing an SNMP message.
 * @author Marcus Blom
 */
public class SNMPMessage
{
    private final int versionNumber;
    private final String community;
    private final int requestID;
    private final int error;
    private final int errorIndex;
    private final Severity severity;
    private final String sysName;
    private final List<SNMPVariableBinding> variableBindings;
    private final Date date = new Date();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Class constructor
     * @param versionNumber SNMP version
     * @param community SNMP community string
     * @param requestID SNMP request ID
     * @param error SNMP error bit
     * @param errorIndex SNMP error index
     * @param variableBindings SNMP VariableBindings
     */
    public SNMPMessage(int versionNumber, String community, int requestID, int error, int errorIndex, List<SNMPVariableBinding> variableBindings)
    {
        this.versionNumber = versionNumber;
        this.community = community;
        this.requestID = requestID;
        this.error = error;
        this.errorIndex = errorIndex;
        this.variableBindings = variableBindings;
        severity = new Severity(findSeverity());
        sysName = findSysName();
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
     * @return date when the snmp message was received
     */
    public String getDate() {
        return dateFormat.format(date);
    }

    /**
     * getDate
     * @return date when the snmp message was received
     */
    public Date getRawDate() {
        return date;
    }
    /**
     * getSysName
     * @return system name
     */
    public String getSysName() {
        return sysName;
    }
    
    private String findSeverity(){
        for(SNMPVariableBinding binding : variableBindings){
            if(binding.getOid() != null){
                if(binding.getOid().equals("calSeverity"))
                    return binding.getValue();
            }     
        }
        return "Cleared";
    }
    
    private String findSysName(){
        for(SNMPVariableBinding binding : variableBindings){
            if(binding.getOid() != null){
                if(binding.getOid().equals("sysName.0"))
                    return binding.getValue();
            }
        }
        return "Not found";
    }
    
}
