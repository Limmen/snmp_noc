/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.model.security;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Kim Hammar on 2016-03-25.
 */
public class SHA512Test {

        /**
         * Test of encrypt method, of class SHA512.
         */
        @Test
        public void testEncrypt()
        {
            boolean exception = false;
            try{
                Assert.assertEquals("125d6d03b32c84d492747f79cf0bf6e179d287f341384eb5d6d3197525ad6be8e6df0116032935698f99a09e265073d1d6c32c274591bf1d0a20ad67cba921bc",SHA512.encrypt("testtest"));
                Assert.assertEquals("ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff",SHA512.encrypt("test"));
                Assert.assertEquals("da51b44001979df36941316003333e3a1ed07cc7fe5dd073a7a7d3b13e18c88d674dcb2dfad383a0693bffdf90d5dbbd98a686c6793f9449e9bcfc8d57471c31",SHA512.encrypt("lindberg"));
                Assert.assertEquals("ba2e6a898a2879d9657571e2de442f3ad49314e9cbcb90fec1b23cc00ac0acdd66388c087c69ee24be7760fe77037985795e0deb725a193cda907db4dbebc16a",SHA512.encrypt("superbowl"));
            }catch(Exception e){
                exception = true;
            }
            assertFalse(exception);
        }
}