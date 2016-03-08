package kth.se.exjobb.model.snmp;

import java.util.Date;
import java.util.List;


/**
 * A class representing an SNMP message.
 * @author Marcus Blom
 */
public class SNMPMessage
{
    private int versionNumber;
    private String community;
    private int requestID;
    private int error;
    private int errorIndex;
    private String severity = "Fine";    
    private List<SNMPVariableBinding> variableBindings;
    private Date date = new Date();
    
    public SNMPMessage(int versionNumber, String community, int requestID, int error, int errorIndex, List<SNMPVariableBinding> variableBindings)
    {
        this.versionNumber = versionNumber;
        this.community = community;
        this.requestID = requestID;
        this.error = error;
        this.errorIndex = errorIndex;
        this.variableBindings = variableBindings;
    }
    
    public int getVersionNumber()
    {
        return versionNumber;
    }
    
    public String getCommunity()
    {
        return community;
    }
    
    public int getRequestID()
    {
        return requestID;
    }
    
    public int getError()
    {
        return error;
    }
    
    public int getErrorIndex()
    {
        return errorIndex;
    }
    
    public String getSeverity() {
        return severity;
    }

    public List<SNMPVariableBinding> getVariableBindings() {
        return variableBindings;
    }

    public Date getDate() {
        return date;
    }
    
    
}
