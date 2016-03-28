/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to hash passwords with the SHA-512 algorithm.
 *
 * TODO: Add salting.
 *
 * @author Kim Hammar on 2016-03-25.
 */
public class SHA512 {

    /**
     * Generates a hash of a specified password-string.
     *
     * @param pw password-string
     * @return hash-value
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic
     * algorithm is requested but is not available in the environment.
     */
    public static String encrypt(String pw) throws NoSuchAlgorithmException
    {
        String encryptedPassword = null;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytes = md.digest(pw.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        encryptedPassword = sb.toString();
        return encryptedPassword;
    }
}
