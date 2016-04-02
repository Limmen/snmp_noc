package kth.se.exjobb.model.snmp;

import kth.se.exjobb.model.util.RelativeByteBuffer;

import java.util.List;
import kth.se.exjobb.integration.entities.SNMPMessage;

/**
 * Encodes an SNMP message for UDP tranmission.
 * @author Marcus Blom
 */
public class SNMPEncoder
{
    public static byte[] encode(SNMPMessage message)
    {
        RelativeByteBuffer data = new RelativeByteBuffer(404);
        
        RelativeByteBuffer PDUData = null;
        RelativeByteBuffer PDUDescriptor = null;
        
        //Encode the variable bindings.
        RelativeByteBuffer varBindingSequence = new RelativeByteBuffer(404);
        RelativeByteBuffer encodedVariableBindings = new RelativeByteBuffer(404);
        List<SNMPVariableBinding> variableBindings = message.getVariableBindings();
            
        for(int i = 0; i < variableBindings.size();i++)
        {
            RelativeByteBuffer encodedBinding = new RelativeByteBuffer(200);
            RelativeByteBuffer encodedOID = new RelativeByteBuffer(200);
            RelativeByteBuffer encodedValue = new RelativeByteBuffer(200);
            SNMPVariableBinding binding = variableBindings.get(i);
            //Encode the OID
            String oid = binding.getOid();
                
            encodedOID.setNext(SNMPConstants.OID);
            
            //Encode the length of the oid
            int oidLength = (oid.length()+1)/2 - 1; 
            
            EncodeASN1ObjectLength(encodedOID,oidLength);
            
            //The first two integers of the id are encoded together.
            int firstByte = Character.getNumericValue(oid.charAt(0)) * 40 
                             + Character.getNumericValue(oid.charAt(2));
            encodedOID.setNext(firstByte);
                
            //Encode the remaining ints individually
            for(int j = 4; j < oid.length(); j = j+2)
            {
                int currentInt = Character.getNumericValue(oid.charAt(j));
                if(currentInt < 128)
                {
                    encodedOID.setNext(currentInt);
                }
                //todo(Marcus): Handle oids larger than or equal to 128.             
            }
                
            Object value = binding.getValue();
            SNMPVariableBinding.DataTypes valueDataType = binding.getValueDataType();
            
            switch (valueDataType)
            {
                case nullValue:
                    encodedValue.setNext(0x05);
                    encodedValue.setNext(0x00);
                    break;
                case integer:
                    EncodeASN1Integer(encodedValue,(int) value);
                    break;
                case string:
                    EncodeASN1OctetString(encodedValue, (String) value);
                    break;
                default:
                    break;
            }
                                
            //Encode the var binding sequence.
            encodedBinding.setNext(0x30);
            //Encode the length(number of bytes) of the sequence.
            EncodeASN1ObjectLength(encodedBinding,encodedOID.size() + encodedValue.size());
            //Add the encoded content: oid + value
            encodedBinding.setNext(encodedOID);
            encodedBinding.setNext(encodedValue);
                
            encodedVariableBindings.setNext(encodedBinding);
        } //end for
            
        //Add the full sequence of all var bindings.
        varBindingSequence.setNext(0x30);
        int varBindingsByteLength = encodedVariableBindings.size();
            
        EncodeASN1ObjectLength(varBindingSequence,varBindingsByteLength);
            
        varBindingSequence.setNext(encodedVariableBindings);
        
        if(message.getPDUType() != SNMPConstants.TRAP)
        {
            
            //Encode the full PDU sequence.
            PDUData = new RelativeByteBuffer(50);
            EncodeASN1Integer(PDUData,message.getRequestID());
            EncodeASN1Integer(PDUData,message.getError());
            EncodeASN1Integer(PDUData,message.getErrorIndex());
            
            //Encode PDU-descriptor
            PDUDescriptor = new RelativeByteBuffer(40);
            PDUDescriptor.setNext(message.getPDUType());
            EncodeASN1ObjectLength(PDUDescriptor,PDUData.size() + varBindingSequence.size());
        }        
            //Encode version number and community string.
            RelativeByteBuffer versionNumberAndCommunityString = new RelativeByteBuffer(100);
            EncodeASN1Integer(versionNumberAndCommunityString, message.getVersionNumber());
            EncodeASN1OctetString(versionNumberAndCommunityString, message.getCommunity());
            
            //Put together the final sequence.
            //First, insert the total sequence length.
            data.setNext(0x30);
            EncodeASN1ObjectLength(data,versionNumberAndCommunityString.size()
                                   + PDUDescriptor.size() + PDUData.size()
                                   + varBindingSequence.size());
            
            //Add all the remaining data to the result.
            data.setNext(versionNumberAndCommunityString);
            data.setNext(PDUDescriptor);
            data.setNext(PDUData);
            data.setNext(varBindingSequence);

        return data.getByteArray();
    }
    
    /**
     * Encodes an integer describing the length in bytes of an ASN1 object.
     * @param data
     * @param length 
     */
    private static void EncodeASN1ObjectLength(RelativeByteBuffer data,int length)
    {
        if(length < 128) //The length is contained in one byte
        {
            data.setNext(length);
        }
        else if (length >= 128)
        {
            //Figure out how many bytes the length is contained in.
            int[] lengthInt = IntegerToBytes(length);
            int numberOfBytes = lengthInt.length;
               
            //Add the length
            data.setNext(128+numberOfBytes);
            
            //Add the individual bytes of the integer.
            for(int i = 0; i < numberOfBytes;i++)
            {
                data.setNext(lengthInt[i]);
            }
        }
    }
    
    private static void EncodeASN1OctetString(RelativeByteBuffer data,String string)
    {
        data.setNext(SNMPConstants.STRING);
        //Encode the length information
        EncodeASN1ObjectLength(data,string.length());
        //Encode each individual character as one byte.
        for(int i = 0; i < string.length(); i++)
        {
            data.setNext(string.charAt(i));
        }
    }
    
    private static void EncodeASN1Integer(RelativeByteBuffer data, int integer)
    {
        data.setNext(SNMPConstants.INT32);
        if(integer == 0)
        {
            data.setNext(1);
            data.setNext(integer);
        }
        else
        {
            int[] integerBytes = IntegerToBytes(integer);
            EncodeASN1ObjectLength(data,integerBytes.length);
            //Add the bytes
            for(int i = integerBytes.length-1; i >= 0;i--)
            {
                data.setNext(integerBytes[i]);
            }
        }
    }
    
    //todo(Marcus): Make public in utility class?
    /**
     * Subdivides an integer into its separate bytes.
     * @param i The integer to divide.
     * @return int[] With the least significant byte at index 0.
     */
    private static int[] IntegerToBytes(int i)
    {
        //Split the int into four separate bytes
        //The most significant byte is stored in index 0
        int[] fullBytes = new int[] 
        {
            (byte) (i >>> 24) & 0xFF,
            (byte) (i >>> 16) & 0xFF,
            (byte) (i >>> 8) & 0xFF,
            (byte) i & 0xFF
        };
        
        int numberOfBytes = 4;
        int index = 0;
            
        //Figure out how many bytes the integer subdivided into.
        while(index < 4 && fullBytes[index] == 0x00)
        {
            numberOfBytes--;
            index++;
        }
        
        //Create new array without padding.
        int[] result = new int[numberOfBytes];
        
        for(index = 3; index > (3 - numberOfBytes);index--)
        {
            result[3-index] = fullBytes[index];
        }
        
        return result;
    }
}