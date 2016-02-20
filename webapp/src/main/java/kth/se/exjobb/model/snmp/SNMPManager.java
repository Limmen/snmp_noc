/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.model.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.entities.Alarm;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author kim
 */
public class SNMPManager {
    
    private UdpAddress target_address;
    private UdpAddress host_address;
    private Snmp manager;
    private Controller contr;
    public SNMPManager(){
        
    }
    public SNMPManager(Controller contr){
        //Address of default localhost daemon.
        this.target_address = new UdpAddress("127.0.0.1/161");
        this.host_address = new UdpAddress("127.0.0.1/9888");
        this.contr = contr;
    }
    public void updateView(Alarm a){
        contr.persistAlarm(a);
    }
    public void start() throws IOException, InterruptedException{
        TransportMapping transport = new DefaultUdpTransportMapping(host_address);
        manager = new Snmp(transport);
        manager.addCommandResponder(new TrapReceiver(this));
        manager.listen();
        updateView(sendDefaultRequest(target_address));
    }
    public void stop() throws IOException{
        manager.close(); //Closes the session and frees all resources
    }
    public Alarm sendDefaultRequest(UdpAddress target_address) throws IOException{
        ArrayList <OID> oids = new ArrayList();
        oids.add(new OID(".1.3.6.1.2.1.1.5.0"));//sysName
        oids.add(new OID(".1.3.6.1.2.1.1.1.0"));//sysDescr
        oids.add(new OID(".1.3.6.1.2.1.1.4.0"));//sysContact
        oids.add(new OID(".1.3.6.1.2.1.1.6.0"));//sysLocation
        Target target = getTarget(target_address);
        System.out.println("MANAGER SENDING GET-REQUEST to SNMPD localhost");
        return sendGetRequest(oids,target);       
    }
    public Alarm sendGetRequest(ArrayList<OID> oids, Target target) throws IOException{
        PDU pdu = new PDU();
        for(OID oid : oids){
            pdu.add(new VariableBinding(oid)); // sysDescr
        }
        //Set the PDU type.
        pdu.setType(PDU.GET);
        ResponseEvent response = manager.send(pdu, target);
        if (response.getResponse() == null) {
            //Throw timeout exception
            System.out.println("Time out!");
            Alarm timeout = new Alarm();
            timeout.setName("Request timeout");
            return timeout;
        }
        else {
            System.out.println("Received response from: "+
                    response.getPeerAddress());
            List<VariableBinding> vbs = response.getResponse().getVariableBindings();
            Alarm alarm = new Alarm();
            
            for(VariableBinding vb : vbs){
                switch(vb.getOid().toString()){
                    case "1.3.6.1.2.1.1.5.0":
                        alarm.setName(vb.getVariable().toString());
                        break;
                    case "1.3.6.1.2.1.1.1.0":
                        alarm.setDescr(vb.getVariable().toString());
                        break;
                    case "1.3.6.1.2.1.1.4.0":
                        alarm.setContact(vb.getVariable().toString());
                        break;
                    case "1.3.6.1.2.1.1.6.0":
                        alarm.setLocation(vb.getVariable().toString());
                        break;
                    case "1.3.6.1.2.1.1.6":
                        alarm.setSeverity(vb.getVariable().toString());
                        break;
                }                      
            }            
            return alarm;
        }
    }
    public Target getTarget(UdpAddress address){
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(address);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }
}
