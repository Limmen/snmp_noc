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
   
   /**
    * Wraps a byte array in the relative byte buffer.
    * @param data 
    */
   public RelativeByteBuffer(byte[] data)
   {
       this.data = data;
       index = 0;
   }
   
   /**
    * Creates an empty buffer of specified length.
    * @param bufferLength 
    */
   public RelativeByteBuffer(int bufferLength)
   {
       data = new byte[bufferLength];
       index = 0;
   }
   
   /**
    * Gets current byte and increments the pointer.
    * @return byte
    */
   public byte getNext()
   {
       byte result = data[index++];
       return result;
   }
   
   /**
    * Sets the current byte and increments the pointer.
    * @param b A byte of data to insert into the buffer.
    */
   public void setNext(byte b)
   {
       data[index] = b;
       index++;
   }
}
