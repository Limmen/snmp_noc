package parser;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

/**
 * Parses the binary data of an SNMP UDP message.
 * @author Marcus Blom
 */
public class SNMPParser 
{
    public static void parse(byte[] message)
    {
        //This function sequentially parses each individual byte
        //of the message. the index is therefore to be incremented
        //with each acces of an individual byte from the message.
        int i = 0;
        int[] returnValueAndUpdatedIndex;
        
        //todo(Marcus): contain these in a SNMP message class.
        int messageSequenceLength = 0;
        int versionNumber = 0;
        String community;
        int requestID;
        int error;
        int errorIndex;
        
        int numberOfVarBindings;
        
        if(message[i++] == 0x30) //Sequence
        {
            
            returnValueAndUpdatedIndex = ASN1ObjectLength(message,i);
            messageSequenceLength = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
        }
        else
        {
            return;
        }
        
        //Read the version number.
        if(message[i++] == 0x02)
        {
            returnValueAndUpdatedIndex = readASN1Integer(message,i);
            versionNumber = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
        }
        
        //Read the community string
        if(message[i++] == 0x04)
        {
            returnValueAndUpdatedIndex = ASN1ObjectLength(message,i);
            int length = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
            
            char[] chars = new char[length];
            
            //Every upcoming byte is an ASCII-character
            for(int j = 0; j < length; j++)
            {
                chars[j] = (char) message[i++];
            }
            
            community = new String(chars);
        }
        
        //PDU
        
        //Read the request id.
        if(message[i++] == 0x02)
        {
            returnValueAndUpdatedIndex = readASN1Integer(message,i);
            requestID = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
        }
        
        //Read error
        if(message[i++] == 0x02)
        {
            returnValueAndUpdatedIndex = readASN1Integer(message,i);
            error = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
        }
        
        //Read error index
        if(message[i++] == 0x02)
        {
            returnValueAndUpdatedIndex = readASN1Integer(message,i);
            errorIndex = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
        }
        
        //Read the variable bindings list.
        if(message[i++] == 0x30) //Sequence
        {
            
            returnValueAndUpdatedIndex = ASN1ObjectLength(message,i);
            numberOfVarBindings = returnValueAndUpdatedIndex[0];
            i = returnValueAndUpdatedIndex[1];
            
            //Each variable binding will contain:
            //sequence
            //oid
            //value
            for(int j = 0; j < numberOfVarBindings;j++)
            {
                if(message[i++] == 0x30) //Sequence
                {
                    returnValueAndUpdatedIndex = ASN1ObjectLength(message,i);
                    int sequenceLength = returnValueAndUpdatedIndex[0];
                    i = returnValueAndUpdatedIndex[1];
                    
                    //Read the oid
                    returnValueAndUpdatedIndex = ASN1ObjectLength(message,i);
                    int oidLength = returnValueAndUpdatedIndex[0];
                    i = returnValueAndUpdatedIndex[1];
                    
                    byte[] oid = new byte[oidLength+1];
                    
                    //The first byte contains the first two numbers of the OID.
                    byte firstByte = message[i++];
                    oid[0] = (byte) (firstByte / 40);
                    oid[1] = (byte) (firstByte % 40);
                    
                    //Loop through the rest
                    for(j = 2; j < oidLength + 1;j++)
                    {
                        byte currentOctet = message[i++];
                        
                        //If the first bit of the byte is '0' we read
                        //the full byte as one part of the oid.
                        if(((currentOctet >> 8) & 1) == 0)
                        {
                            oid[j] = currentOctet;
                        }
                        //todo(Marcus): Handle oid numbers larger than 128
                    }
                    
                    //Read the value
                    byte objectClass = message[i++];
                    
                    if(objectClass == 0x02) //Integer
                    {
                        returnValueAndUpdatedIndex = readASN1Integer(message,i);
                        i = returnValueAndUpdatedIndex[1];
                    }
                    else if(objectClass == 0x04) //Octet string
                    {
                        readASN1String(message,i);
                    }
                    
                    //todo(Marcus): store the values and oid
                    
                }
            }
        }
    }
    
    private static int[] readASN1Integer(byte[] data,int index)
    {
        
        int[] lengthIndex ,result = new int[2];
        
        lengthIndex = ASN1ObjectLength(data,index);
        int intByteLength = lengthIndex[0];
        index = lengthIndex[1];
            
        //Read the integer of specified length.
        if(intByteLength == 1)
        {
            result[0] = data[index++];
        }
        else
        {
            //todo(Marcus): Compress this to own function? It appears twice in code.
            for(int j = intByteLength; j > 128;j--)
            {
                result[0] = ((data[index++] & (0xFF)) << (8*((j-intByteLength)-1))) | result[0];
            } 
        }
        
        result[1] = index;
        
        return result;
    }
    
    //todo(Marcus): Might change the index to the Integer wrapper class
    //just so I can have a pointer to it and have easier return values...
    private static Object[] readASN1String(byte[] data,int index)
    {
        Object[] result = new Object[2];
        int[] returnValueAndUpdatedIndex = ASN1ObjectLength(data,index);
        int length = returnValueAndUpdatedIndex[0];
        index = returnValueAndUpdatedIndex[1];
            
        char[] chars = new char[length];
            
        //Every upcoming byte is an ASCII-character
        for(int j = 0; j < length; j++)
        {
            chars[j] = (char) data[index++];
        }
            
        result[0] = new String(chars);
        result[1] = index;
        return result;
    }
    
    /**
     * Finds the length in bytes of an ASN.1 object.
     * @param data The message from whithin the object resides
     * @param index The starting index of the objects legnth declaring
     * octet. This means the first byte after the one delcaring the object type.
     * @return int[]. int[0] = the length of the data content.
     * int[1] = the index after the length defining bytes in the message.
     * This will point to the objects contents.
     */
    private static int[] ASN1ObjectLength(byte[] data,int index)
    {
        //todo(Marcus): If needed, move to a sperate ASN-decoding package.
        
        int[] result = new int[2];
        
        //Find the length of the sequence
        int lengthOctet = data[index++];
        if(lengthOctet <= 128)
        {
            result[0] = lengthOctet;
        }
        else //The next (lengthOctet - 128) octets contain the sequence length
        {
            //We find the length by shifting multiple bytes into their correct
            //positions in an integer.
            result[0] = 0;
            for(int j = lengthOctet; j > 128;j--)
            {
                result[0] = ((data[index++] & (0xFF)) << (8*((j-lengthOctet)-1))) | result[0];
            }
        }
        
        result[1] = index;
        
        return result;
    }
}