/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.agents;

import java.io.File;
import java.io.IOException;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.agent.BaseAgent;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.ManagedObject;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.RowStatus;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB;
import org.snmp4j.agent.mo.snmp.SnmpNotificationMIB;
import org.snmp4j.agent.mo.snmp.SnmpTargetMIB;
import org.snmp4j.agent.mo.snmp.StorageType;
import org.snmp4j.agent.mo.snmp.VacmMIB;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.transport.TransportMappings;

/**
 *
 * @author kim
 */

//BaseAgent is a framework for writing SNMP Agents with SNMP4j
public class SNMPAgent extends BaseAgent {
    private final UdpAddress host_address;
    private final UdpAddress target_address;
    
    public SNMPAgent(String target, String host) throws IOException {
        
        // These files does not exist and are not used but has to be specified
        // Read snmp4j docs for more info
        super(new File("conf.agent"), new File("bootCounter.agent"),
                new CommandProcessor(
                        new OctetString(MPv3.createLocalEngineID())));
        this.host_address = new UdpAddress(target);
        this.target_address = new UdpAddress(host);
    }
    public SNMPAgent() throws IOException {
        
        // These files does not exist and are not used but has to be specified
        // Read snmp4j docs for more info
        super(new File("conf.agent"), new File("bootCounter.agent"),
                new CommandProcessor(
                        new OctetString(MPv3.createLocalEngineID())));
        this.target_address = new UdpAddress("127.0.0.1/9888");
        this.host_address = new UdpAddress("127.0.0.1/9999");
    }
    
    public void start() throws IOException, InterruptedException{
        //Initialize transport mappings, message dispatcher, basic MIB modules etc.
        init();
        //Adds a shutdown hook that saves the internal config into the config file
        //when a SIGTERM (Ctrl-C) is terminating the agent.
        addShutdownHook();
        
        //fetches the default managedobject-server (MO-server) that keeps the "database"
        //of MIBs. Add context name public.
        getServer().addContext(new OctetString("public"));
        //Finishes intialization of the agent by connecting server
        //and command processor, setting up USM, VACM etc.
        finishInit();
        //Starts the agent by let it listen on the configured SNMP
        //agent ports (transport mappings).
        run();
        sendColdStartNotification();
    }
    
    //Register additional managed objects (MIB's) at the agent's server.
    @Override
    protected void registerManagedObjects() {
        try {
            // Since BaseAgent registers some MIBs by default we need to unregister
            // one before we register our own sysDescr. Normally you would
            // override that method and register the MIBs that you need
            unregisterManagedObject(getSnmpv2MIB());
            
            OID system_descr = new OID(".1.3.6.1.2.1.1.5.0");
            ManagedObject mo = MOScalarFactory.createReadOnly(system_descr,
                    "SIMULATED SNMP AGENT - EXJOBB MIC NORDIC 2016");
            server.register(mo, null);
            system_descr = new OID(".1.3.6.1.2.1.1.1.0");
            mo = MOScalarFactory.createReadOnly(system_descr,
                    "SNMP AGENT developed with SNMP4J");
            server.register(mo, null);
            system_descr = new OID(".1.3.6.1.2.1.1.4.0");
            mo = MOScalarFactory.createReadOnly(system_descr,
                    "Kim Hammar, Marcus Blom");
            server.register(mo, null);
            system_descr = new OID(".1.3.6.1.2.1.1.6.0");
            mo = MOScalarFactory.createReadOnly(system_descr,
                    "Royal Institute of Technology");
            server.register(mo, null);
        } catch (DuplicateRegistrationException ex) {
            throw new RuntimeException(ex);
        }
    }
    //Unregister managed objects
    @Override
    protected void unregisterManagedObjects() {
        
    }
    //Usm = User Based Security Model. Used for SNMPv3
    @Override
    protected void addUsmUser(USM usm) {
        
    }
    //Adds initial notification targets and filters.
    @Override
    protected void addNotificationTargets(SnmpTargetMIB stmib, SnmpNotificationMIB snmib) {
        
    }
    //Adds initial VACM configuration.
    //The View-based Access Control Model interface defines methods and constants
    //that a contrete implementation of such a model has to implemen
    @Override
    protected void addViews(VacmMIB vacm) {
        vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, new OctetString(
                "cpublic"), new OctetString("v1v2group"),
                StorageType.nonVolatile);
        
        vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
                SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV,
                MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"),
                new OctetString("fullWriteView"), new OctetString(
                        "fullNotifyView"), StorageType.nonVolatile);
        
        vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"),
                new OctetString(), VacmMIB.vacmViewIncluded,
                StorageType.nonVolatile);
    }
    //Adds community to security name mappings needed for SNMPv1 and SNMPv2c.
    @Override
    protected void addCommunities(SnmpCommunityMIB communityMIB) {
        Variable[] com2sec = new Variable[] {
            new OctetString("public"), // community name
            new OctetString("cpublic"), // security name
            getAgent().getContextEngineID(), // local engine ID
            new OctetString("public"), // default context name
            new OctetString(), // transport tag
            new Integer32(StorageType.nonVolatile), // storage type
            new Integer32(RowStatus.active) // row status
        };
        MOTableRow row = communityMIB.getSnmpCommunityEntry().createRow(
                new OctetString("public2public").toSubIndex(true), com2sec);
        communityMIB.getSnmpCommunityEntry().addRow(row);
    }
    
    //Initializes the transport mappings (ports) to be used by the agent.
    @Override
    protected void initTransportMappings() throws IOException {
        transportMappings = new TransportMapping[1];
        TransportMapping tm = TransportMappings.getInstance()
                .createTransportMapping(host_address);
        transportMappings[0] = tm;
    }
    
    /**
     * Clients can register the MO they need
     */
    public void registerManagedObject(ManagedObject mo) {
        try {
            server.register(mo, null);
        } catch (DuplicateRegistrationException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void unregisterManagedObject(MOGroup moGroup) {
        moGroup.unregisterMOs(server, getContext(moGroup));
    }
    public void sendSnmpV2Trap() {
        try {
            String trapOid = ".1.3.6.1.2.1.1.6";
            // Create Transport Mapping
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            
            // Create Target
            Target comtarget = getTarget(target_address);
            
            // Create PDU for V2
            PDU pdu = new PDU();            
            // need to specify the system up time
            long sysUpTime = 111111;
            pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(sysUpTime)));
            pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(trapOid)));
            pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress, target_address));            
            pdu.add(new VariableBinding(SnmpConstants.sysName, new OctetString("SIMULATED SNMP AGENT - EXJOBB MIC NORDIC 2016")));
            pdu.add(new VariableBinding(SnmpConstants.sysDescr, new OctetString("SNMP AGENT developed with SNMP4J")));
            pdu.add(new VariableBinding(SnmpConstants.sysContact, new OctetString("Kim Hammar, Marcus Blom")));
            pdu.add(new VariableBinding(SnmpConstants.sysLocation, new OctetString("Royal Institute of Technology")));            
            // variable binding for Enterprise Specific objects, Severity (should be defined in MIB file)
            pdu.add(new VariableBinding(new OID(trapOid), new OctetString("Major")));
            pdu.setType(PDU.NOTIFICATION);
            
            // Send the PDU
            Snmp snmp = new Snmp(transport);
            System.out.println("Sending V2 Trap to " + target_address.getInetAddress() + " on Port " + target_address.getPort());
            snmp.send(pdu, comtarget);
            snmp.close();
        } catch (Exception e) {
            System.err.println("Error in Sending V2 Trap to " + target_address.getInetAddress() + " on Port " + target_address.getPort());
            System.err.println("Exception Message = " + e.getMessage());
        }
    }
    
    private Target getTarget(UdpAddress address){
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(address);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);        
        return target;
    }
}
