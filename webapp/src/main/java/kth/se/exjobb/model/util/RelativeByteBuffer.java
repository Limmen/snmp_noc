/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/

package kth.se.exjobb.model.util;

/**
 * A wrapper class with a byte array and an index that increases with each
 * access to the buffer.
 *
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
     *
     * @return
     */
    public byte[] getByteArray()
   {
       return data;
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
   
    /**
     *
     * @param i
     */
    public void setNext(int i)
   {
       setNext((byte) i);
   }
   
   /**
    * Adds an array of bytes to the buffer, incrementing the pointer with
    * each set.
    * @param byteArray 
    */
   public void setNext(byte[] byteArray)
   {
       for(int i = 0; i < byteArray.length;i++)
       {
           data[index] = byteArray[i];
           index++;
       }
   }
   
   /**
    * Adds a specified number of bytes sequentially from an array of bytes.
    * @param byteArray
    * @param numberOfBytes 
    */
   public void setNext(byte[] byteArray,int numberOfBytes)
   {
       for(int i = 0; i < numberOfBytes;i++)
       {
           data[index] = byteArray[i];
           index++;
       }
   }
   
    /**
     *
     * @return
     */
    public int size()
   {
       return index+1;
   }
   
    /**
     *
     * @return
     */
    public int getCurrentIndex()
   {
       return index;
   }
   
}
