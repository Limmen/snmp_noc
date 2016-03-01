package parser;

import java.util.List;

/**
 * A class representing an SNMP message.
 * @author Marcus Blom
 */
public class SNMPMessage 
{
    int versionNumber;
    String community;
    int requestID;
    int error;
    int errorIndex;
    
    List<SNMPVariableBinding> variableBindings;

    public SNMPMessage(int versionNumber, String community, int requestID, int error, int errorIndex, List<SNMPVariableBinding> variableBindings)
    {
        this.versionNumber = versionNumber;
        this.community = community;
        this.requestID = requestID;
        this.error = error;
        this.errorIndex = errorIndex;
        this.variableBindings = variableBindings;
    }
    
    

    public int getMessageSequenceLength() 
    {
        return messageSequenceLength;
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
      
}
