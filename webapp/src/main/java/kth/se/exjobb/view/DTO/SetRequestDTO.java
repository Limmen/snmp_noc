/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.view.DTO;

/**
 * DataTransferObject of set-request parameters that the user have specified.
 * @author kim
 */
public class SetRequestDTO {
    private String sysName;
    private String sysDescr;
    private String sysContact;
    private String sysLocation;
    
    /**
     * getSysName
     * @return sysName
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * updates the system name parameter
     * @param sysName string sysName
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    /**
     * getSysDescr
     * @return sysDescr
     */
    public String getSysDescr() {
        return sysDescr;
    }

    /**
     * Updates the system description parameter
     * @param sysDescr string sysDescr
     */
    public void setSysDescr(String sysDescr) {
        this.sysDescr = sysDescr;
    }

    /**
     * getSysContact
     * @return sysContact
     */
    public String getSysContact() {
        return sysContact;
    }

    /**
     * Updates the sysContact parameter
     * @param sysContact sysContact string
     */
    public void setSysContact(String sysContact) {
        this.sysContact = sysContact;
    }

    /**
     * getSysLocation
     * @return sysLocation
     */
    public String getSysLocation() {
        return sysLocation;
    }

    /**
     * Updates the  sysLocation parameter
     * @param sysLocation sysLocation string
     */
    public void setSysLocation(String sysLocation) {
        this.sysLocation = sysLocation;
    }
    
}
