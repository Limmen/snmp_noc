
package kth.se.exjobb.model.snmp;
import java.util.List;
import kth.se.exjobb.entities.Alarm;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.VariableBinding;
/**
 * Snmp-instances processes responses to requests and forwards PDUs of other
 * SNMP messages to registered CommandResponder listener instances.
 *
 * This class is a CommandResponder listener.
 * @author kim
 */
public class TrapReceiver implements CommandResponder {
    
    private SNMPManager manager;
    public TrapReceiver(SNMPManager manager){
        this.manager=manager;
    }
    @Override
    public void processPdu(CommandResponderEvent event) {
        System.out.println("SNMP-MANAGER RECEIVED TRAP");
        System.out.println("FROM: " + event.getPeerAddress());
        List<VariableBinding> vbs = event.getPDU().getVariableBindings();
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
        System.out.println("Adding alarm to bean");
        manager.updateView(alarm);
    }
    
    
}
