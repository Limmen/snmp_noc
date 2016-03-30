/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.util;

import javax.validation.ConstraintValidatorContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 * Unit-test suite for ValidIPImplementation.java
 * 
 * @author Kim Hammar
 */
public class ValidIPImplementationTest {
    private ValidIPImplementation validator;
    public ValidIPImplementationTest() {
    }
    
    @Before
    public void setUp() {
        validator = new ValidIPImplementation();
    }
    
    @After
    public void tearDown() {
        validator = null;
    }

    /**
     * Test of isValid method, of class ValidIPImplementation.
     */
    @Test
    public void testIsValid() {
        ConstraintValidatorContext mockContext = mock(ConstraintValidatorContext.class);
        Assert.assertEquals(true, validator.isValid("192.168.1.1", mockContext));
        Assert.assertEquals(true, validator.isValid("127.0.0.1", mockContext));
        Assert.assertEquals(true, validator.isValid("2001:0000:1234:0000:0000:C1C0:ABCD:0876", mockContext));
        Assert.assertEquals(true, validator.isValid("3ffe:0b00:0000:0000:0001:0000:0000:000a", mockContext));
        Assert.assertEquals(true, validator.isValid("FF02:0000:0000:0000:0000:0000:0000:0001", mockContext));
        Assert.assertEquals(true, validator.isValid("::2:3:4:5:6:7:8", mockContext));
        Assert.assertEquals(false, validator.isValid("10.10.10", mockContext));
        Assert.assertEquals(false, validator.isValid("10.10", mockContext));
        Assert.assertEquals(false, validator.isValid("a.a.a.a", mockContext));
        Assert.assertEquals(false, validator.isValid("999.10.10.20", mockContext));
        Assert.assertEquals(false, validator.isValid("2222.22.22.22", mockContext));   
        Assert.assertEquals(false, validator.isValid("2001:0000:1234:0000:0000:C1C0:ABCD:0876  0", mockContext));
        Assert.assertEquals(false, validator.isValid("912.456.123.123", mockContext));
        Assert.assertEquals(false, validator.isValid("000.0000.00.00", mockContext));
    }
    
}
