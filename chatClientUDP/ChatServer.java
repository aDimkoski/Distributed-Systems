import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatServer {

    private static final int portNumber = 7777;

    private int serverPort;
    private HashMap<String, DatagramSocket> clients;

    public static void main(String[] args){
        ChatServer server = new ChatServer(portNumber);
        server.startServer();
    }

    public ChatServer(int portNumber){
        this.serverPort = portNumber;
    }

    public HashMap<String, DatagramSocket> getClients(){
        return clients;
    }

    public boolean putClient(String name, DatagramSocket udpServerSocket) {
        try {
            clients.put(name, udpServerSocket);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void acceptClients(DatagramSocket udpServerSocket){

        System.out.println("server starts port = " + udpServerSocket.getLocalSocketAddress());
        while(true){
            try{
                byte[] receiveData = new byte[1024];

                // Create an empty DatagramPacket packet
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Block until there is a packet to receive, then receive it  (into our empty packet)
                udpServerSocket.receive(receivePacket);

                System.out.println("accepts : " + receivePacket.getSocketAddress());
                ClientThread client = new ClientThread(this, udpServerSocket,receivePacket);
                Thread thread = new Thread(client);
                thread.start();
            } catch (IOException ex){
                System.out.println("Accept failed on : " + serverPort);
            }
        }
    }

    private void startServer(){
        clients = new HashMap<>();
        try {
            DatagramSocket udpServerSocket=new DatagramSocket(serverPort);
            acceptClients(udpServerSocket);
        } catch (IOException e){
            System.err.println("Could not listen on port: "+serverPort);
            System.exit(1);
        }
    }
}