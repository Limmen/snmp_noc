package kth.se.exjobb.model.util;

/**
 * A wrapper class with a byte array and an index that increases with each
 * access to the buffer.
 * @author Marcus Blom
 */
public class RelativeByteBuffer 
{
    byte[]  data;
    int     index;
    
   public RelativeByteBuffer(byte[] data)
   {
       this.data = data;
       index = 0;
   }
   
   public byte getNext()
   {
       byte result = data[index++];
       return result;
   }
}
