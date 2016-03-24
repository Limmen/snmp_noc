package kth.se.exjobb.model.snmp;

import kth.se.exjobb.integration.entities.SNMPMessage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class that sends UDP packet to a specified ip address
 *
 * @author Kim Hammar on 2016-03-23.
 */
public class SNMPSender implements Runnable {

    private byte[] data;
    private InetAddress address;

    public SNMPSender(byte[] data, String ip) throws UnknownHostException {
        this.data = data;
        address = InetAddress.getByName(ip);
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(8888);
            send(socket);
            listen(socket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(DatagramSocket socket) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length,
                address, 9994);
        socket.send(packet);
        socket.close();
    }

    void listen(DatagramSocket socket) throws IOException {
        socket.setSoTimeout(5000);
        byte[] buf = new byte[484];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        SNMPMessage msg = SNMPParser.parse(buf);
        //contr.newAlarm(msg);
    }

}
