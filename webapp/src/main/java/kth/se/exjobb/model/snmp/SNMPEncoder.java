package kth.se.exjobb.model.snmp;

import java.util.ArrayList;
import java.util.List;
import kth.se.exjobb.model.util.RelativeByteBuffer;

/**
 * Encodes an SNMP message for UDP tranmission.
 * @author Marcus Blom
 */
public class SNMPEncoder
{
    public static byte[] encode(SNMPMessage message)
    {
        RelativeByteBuffer data = new RelativeByteBuffer(404);
        
        //We encode the data starting from the last byte of the sequence.
        //This is beacause ASN1 sequences have the sequence length in byte
        //as their corresponding data.
        
        RelativeByteBuffer PDUData = null;
        RelativeByteBuffer PDUDescriptor = null;
        
        //Add the variable bindings.
        RelativeByteBuffer varBindingSequence = new RelativeByteBuffer(404);
        ArrayList<RelativeByteBuffer> encodedVariableBindings = new ArrayList<>();
        List<SNMPVariableBinding> variableBindings = message.getVariableBindings();
            
        for(int i = 0; i < variableBindings.size();i++)
        {
            RelativeByteBuffer encodedBinding = new RelativeByteBuffer(200);
            ArrayList<Byte> encodedOID = new ArrayList<>();
            ArrayList<Byte> encodedValue = new ArrayList<>();
            SNMPVariableBinding binding = variableBindings.get(i);
            //Encode the OID
            String oid = binding.getOid();
                
            encodedOID.add((byte) 0x06);
                
            //The first two integers of the id are encoded together.
            int firstByte = Character.getNumericValue(oid.charAt(0)) * 40 
                             + Character.getNumericValue(oid.charAt(2));
            encodedOID.add((byte) firstByte);
                
            //Encode the remaining ints individually
            for(int j = 4; j < oid.length(); j = j+2)
            {
                int currentInt = Character.getNumericValue(oid.charAt(j));
                if(currentInt < 128)
                {
                    encodedOID.add((byte) currentInt);
                }
                //todo(Marcus): Handle oids larger than or equal to 128.
                                
            }
                
            //todo(Marcus): Encode the value
                
                                
            //Encode the var binding sequence.
            encodedBinding.setNext(0x30);
            //Encode the length(number of bytes) of the sequence.
            EncodeASN1ObjectLength(encodedBinding,encodedOID.size() + encodedValue.size());
                
            encodedVariableBindings.add(encodedBinding);
        }
            
        //Add the full sequence of all var bindings.
        varBindingSequence.setNext(0x30);
        int varBindingsByteLength = 0;
            
        for(RelativeByteBuffer rbb : encodedVariableBindings)
        {
            varBindingsByteLength += rbb.size();
        }
            
        EncodeASN1ObjectLength(varBindingSequence,varBindingsByteLength);
            
        for(RelativeByteBuffer rbb : encodedVariableBindings)
        {
            varBindingSequence.setNext(rbb.getByteArray(),rbb.size());
        }
            
        if(message.getPDUType() != SNMPConstants.TRAP)
        {
            
            //Encode the full PDU sequence.
            PDUData = new RelativeByteBuffer(50);
            EncodeASN1ObjectLength(PDUData,message.getRequestID());
            EncodeASN1ObjectLength(PDUData,message.getError());
            EncodeASN1ObjectLength(PDUData,message.getErrorIndex());
            
            //Encode PDU-descriptor
            PDUDescriptor = new RelativeByteBuffer(40);
            EncodeASN1ObjectLength(PDUDescriptor,message.getPDUType());
            EncodeASN1ObjectLength(PDUDescriptor,PDUData.size() +
                                   varBindingSequence.size());
        }    
            
            //Encode version number and community string.
            RelativeByteBuffer versionNumberAndCommunityString =
                    new RelativeByteBuffer(100);
            EncodeASN1ObjectLength(versionNumberAndCommunityString,
                                   message.getVersionNumber());
            EncodeASN1OctetString(versionNumberAndCommunityString,
                                  message.getCommunity());
            
            //Put together the final sequence.
            
            //First, insert the total sequence length.
            data.setNext(0x30);
            EncodeASN1ObjectLength(data,versionNumberAndCommunityString.size()
                                   + PDUDescriptor.size() + PDUData.size()
                                   + varBindingSequence.size());
            
            //Add all the remaining data to the result.
            data.setNext(versionNumberAndCommunityString.getByteArray()
                         ,versionNumberAndCommunityString.size());
            data.setNext(PDUDescriptor.getByteArray(),PDUDescriptor.size());
            data.setNext(PDUData.getByteArray(),PDUData.size());
            data.setNext(varBindingSequence.getByteArray(),
                         varBindingSequence.size());
            
            
        
        
        return data.getByteArray();
    }
    
    /**
     * Encodes an integer describing the length in bytes of an ASN1 object.
     * Can also be used to fully encode an integer.
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
            byte[] lengthInt = new byte[] 
            {
                (byte)  (length >>> 24),
                (byte)  (length >>> 16),
                (byte)  (length >>> 8),
                (byte)   length
            };
            
            int numberOfBytes = 4;
            int index = 0;
            
            while(lengthInt[index] == 0x00)
            {
                numberOfBytes--;
                index++;
            }
            
            data.setNext(128+numberOfBytes);
            
            //Set the length bytes
            for(int i = length-1; i >= 0;i--)
            {
                data.setNext(lengthInt[i]);
            }
        }
    }
    
    private static void EncodeASN1OctetString(RelativeByteBuffer data,String string)
    {
        //Encode the length information
        EncodeASN1ObjectLength(data,string.length());
        //Encode each individual character as one byte.
        for(int i = 0; i < string.length(); i++)
        {
            data.setNext(string.charAt(i));
        }
    }
}