package kth.se.exjobb.model.snmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kth.se.exjobb.model.util.RelativeByteBuffer;



/**
 * Parses the binary data of an SNMP UDP message.
 * @author Marcus Blom
 */
public class SNMPParser 
{
    
    //todo(Marcus): Decide on how to store this..
    //todo(Marcus): Public?
    private static final HashMap<String,String> oids = new HashMap();
    static
    {
        oids.put("1.3.6.1.2.1.1.3.0.", "sysUpTimeInstance");
        oids.put("1.3.6.1.6.3.1.1.4.1.0.", "snmpTrapOID.0");
        oids.put("1.3.6.1.6.3.1.1.3.0.", "snmpMIBObjects.3.0");
        oids.put("1.3.6.1.2.1.1.5.0.", "sysName.0");
        oids.put("1.3.6.1.2.1.1.1.0.", "sysDescr.0");
        oids.put("1.3.6.1.2.1.1.4.0.", "sysContact.0");
        oids.put("1.3.6.1.2.1.1.6.0.", "sysLocation.0"); 
    }
    
    /**
     * Parses the binary data of an SNMP UDP message
     * @param message byte[] a received SNMP message.
     * @return SNMPMessage. 
     */
    public static SNMPMessage parse(byte[] message)
    {
        RelativeByteBuffer data = new RelativeByteBuffer(message);
        
        //todo(Marcus): contain these in a SNMP message class.
        int messageSequenceLength = 0;
        int versionNumber = 0;
        String community = null;
        int requestID = 0;
        int error = 0;
        int errorIndex = 0;
        
        int numberOfVarBindings;
        
        List<SNMPVariableBinding> variableBindings = new ArrayList<>(); 
        
        if(data.getNext() == 0x30) //Sequence
        {
            
            messageSequenceLength = ASN1ObjectLength(data);
        }
        else
        {
            return null; //todo(Marcus): Exception handling.
        }
        
        //Read the version number.
        if(data.getNext() == 0x02)
        {
            versionNumber = readASN1Integer(data);
        }
        
        //Read the community string
        if(data.getNext() == 0x04)
        {
            community = readASN1String(data);
        }
        
        if((data.getNext() & 0xFF )== 0xa7)
        {
            ASN1ObjectLength(data); //todo(Marcus): do we need this?
        }
        
        //PDU
        
        //Read the request id.
        if(data.getNext() == 0x02)
        {
            requestID = readASN1Integer(data);
        }
        
        //Read error
        if(data.getNext() == 0x02)
        {
            error = readASN1Integer(data);
        }
        
        //Read error index
        if(data.getNext() == 0x02)
        {
            errorIndex = readASN1Integer(data);
        }
        
        //Read the variable bindings list.
        if(data.getNext() == 0x30) //Sequence
        {
            
            numberOfVarBindings = ASN1ObjectLength(data);
            
            //Each variable binding will contain:
            //sequence
            //oid
            //value
            for(;;) //Loop until the class code is 0x00. This means we're at the end of the message.
            {
                if(data.getNext() == 0x30) //Sequence
                {
                    int sequenceLength = ASN1ObjectLength(data);
                    
                    if(data.getNext() == 0x06) //oid
                    {
                        //Read the oid
                        char[] oid = readOID(data);
                        
                        //Read the value
                        Object value = null;
                        
                        byte objectClass = data.getNext();

                        if(objectClass == 0x02) //Integer
                        {
                            value = readASN1Integer(data);
                        }
                        else if(objectClass == 0x04) //Octet string
                        {
                            value = readASN1String(data);
                        }
                        else if((objectClass & 0b01000000) == 0x40) //Application
                        {
                            value = readASN1Integer(data);
                        }
                        else if(objectClass == 0x06) //oid
                        {
                            value = readOID(data);
                        }

                        SNMPVariableBinding variableBinding = new SNMPVariableBinding(new String(oid),value);
                        variableBindings.add(variableBinding);
                    }
                    
                }
                else
                {
                    break;
                }
            }
        }
    
    SNMPMessage result = new SNMPMessage(versionNumber, community, requestID,  error,  errorIndex, variableBindings);
    return result;
        
    }
    
    public static String translateOID(String oid)
    {
        return oids.get(oid.trim());
    }
    
    private static char[] readOID(RelativeByteBuffer data)
    {
        int oidByteLength = ASN1ObjectLength(data);
        int oidLength = (((oidByteLength+1)*2) - 1);
        //All characters except for the last one will be followed by a dot
        char[] oid = new char[((oidLength+1)*2) - 1];

        //The first byte contains the first two numbers of the OID.
        byte firstByte = data.getNext();
        oid[0] = Integer.toString((firstByte / 40)).charAt(0);       
        oid[1] = '.';
        oid[2] = Integer.toString((firstByte % 40)).charAt(0);
        oid[3] = '.';

        //Loop through the rest
        int j = 4;
        while(j < oidLength)
        {
            byte currentOctet = data.getNext();

            //If the first bit of the byte is '0' we read
            //the full byte as one part of the oid.
            if(((currentOctet >> 8) & 1) == 0)
            {
                oid[j++] = Integer.toString(currentOctet).charAt(0);
                if(j != (oidLength-1)) //If we're not on the last element.
                {
                    oid[j++] = '.';
                }
            }
            //todo(Marcus): Handle oid numbers larger than 128
        }
        
        return oid;
    }
    
    private static int readASN1Integer(RelativeByteBuffer data)
    {
        
        int result;
        
        int intByteLength = ASN1ObjectLength(data);
            
        //Read the integer of specified length.
        if(intByteLength == 1)
        {
            result = data.getNext();
        }
        else
        {
            result = intFromBytes(data,(intByteLength));
        }
        
        return result;
    }
    
    private static String readASN1String(RelativeByteBuffer data)
    {
        String result;
        int length = ASN1ObjectLength(data);
            
        char[] chars = new char[length];
            
        //Every upcoming byte is an ASCII-character
        for(int j = 0; j < length; j++)
        {
            chars[j] = (char) data.getNext();
        }
            
        result = new String(chars);
        return result;
    }
    
    /**
     * Finds the length in bytes of an ASN.1 object.
     * @param data The message from within the object resides.
     * @return int the length of contents in bytes.
     * This will point to the objects contents.
     */
    private static int ASN1ObjectLength(RelativeByteBuffer data)
    {
        //todo(Marcus): If needed, move to a sperate ASN-decoding package.
        
        int result;
        
        //Find the length of the sequence
        int lengthOctet = 0xFF & data.getNext();
        if(lengthOctet < 128)
        {
            result = lengthOctet;
        }
        else //The next (lengthOctet - 128) octets contain the sequence length
        {
            result = intFromBytes(data,(lengthOctet - 128));
        }
        
        return result;
    }
    
    private static int intFromBytes(RelativeByteBuffer data, int numberOfBytes)
    {
        int result = 0;
        for(int j = numberOfBytes; j > 0;j--)
        {
            result = ((data.getNext() & (0xFF)) << (8*(j-1))) | result;
        }
        return result;
    }
}