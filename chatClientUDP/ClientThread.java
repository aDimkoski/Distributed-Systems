import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ClientThread implements Runnable {
    DatagramSocket udpServerSocket;
    DatagramPacket receivePacket;
    private PrintWriter clientOut;
    private ChatServer server;

    public ClientThread(ChatServer server, DatagramSocket udpServerSocket, DatagramPacket receivePacket) {
        this.server = server;
        this.udpServerSocket = udpServerSocket;
        this.receivePacket = receivePacket;
    }

    private PrintWriter getWriter() {
        return clientOut;
    }

    @Override
    public void run() {
        try {
            String name = "";
            String clientMessage = (new String(receivePacket.getData())).trim();
            System.out.println("Server received: " + clientMessage);
            String[] commandSeparated = clientMessage.split("[:]");

            // login
            if (commandSeparated[0].equals("login")) {
                name = commandSeparated[1];
                server.putClient(commandSeparated[1], udpServerSocket);

                Thread.sleep(6000);
            } else if (commandSeparated[0].equals("list")) {
                HashMap<String, DatagramSocket> clients = server.getClients();
                Set<String> clientList = clients.keySet();
                String response = "";
                for (String clientOne : clientList) {
                    response = response + clientOne + ",";
                }
                response = response + "\r\n";
                byte[] sendData  = new byte[1024];

                // Assign the message to the send buffer
                sendData = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), 1235);

                // Send the echoed message
                udpServerSocket.send(sendPacket);
                System.out.printf(new String(sendData));
                Thread.sleep(6000);

            } else if (commandSeparated[0].equals("message")) {
                String to = commandSeparated[1];
                String theMessage = commandSeparated[2];

                // get the client
                DatagramSocket toSocket = server.getClients().get(to);
                if (toSocket != null) {
                    byte[] sendData  = new byte[1024];

                    String toSend="message:" + name + ":" + theMessage + "\r\n";
                    // Assign the message to the send buffer
                    sendData = toSend.getBytes();
                    System.out.printf(receivePacket.getAddress()+" "+" "+receivePacket.getPort());
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), 1235);

                    // Send the echoed message
                    udpServerSocket.send(sendPacket);
                } else {
                    System.out.println("Client " + to + " does not exist");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}