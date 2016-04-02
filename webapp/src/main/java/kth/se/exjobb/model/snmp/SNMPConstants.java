package kth.se.exjobb.model.snmp;

/**
 *
 * @author Marcus Blom
 */
public class SNMPConstants
{
    public static final int GET             = 0xA0;
    public static final int GET_NEXT        = 0xA1;
    public static final int GET_RESPONSE    = 0xA2;
    public static final int SET             = 0xA3;
    public static final int TRAP            = 0xA4;
    public static final int GET_BULK        = 0xA5;
    public static final int V2_TRAP         = 0xA7;
    
    public static final int RESPONSE        = 0x01;
    public static final int REQUEST         = 0x02;
    
    //SNMP-Trap constants
    public static final int     COLD_START                      =0x0;
    public static final int	WARM_START                      =0x1;
    public static final int	LINK_DOWN			=0x2;
    public static final int     LINK_UP				=0x3;
    public static final int	AUTH_FAILURE                    =0x4;	
    public static final int	EGP_NEBOR_LOSS                  =0x5;
    public static final int	ENTERPRISE_SPECIFIC             =0x6;
    
    //ASN.1 constants used for encoding and decoding.
    public final static int INT8 = 0x00;
    public final static int INT16 = 0x01;
    public final static int INT32 = 0x02;
    public final static int STRING = 0x04;
    public final static int NULL    = 0x05;
    public final static int OID     = 0x06;
}