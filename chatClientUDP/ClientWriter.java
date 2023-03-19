import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientWriter extends Thread {
    DatagramSocket ds;
    public ClientWriter(DatagramSocket ds) {
        this.ds = ds;
    }

    public void run() {
        try {
            while(true) {
                byte[] buffer = new byte[1024];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                ds.receive(incoming);
                String received = (new String(incoming.getData())).trim();
                // protocol
                // incoming message from another client message:<from>:<the message>
                System.out.println("Received: " + received);
            }
        } catch (Exception e) {

        }
    }
}
