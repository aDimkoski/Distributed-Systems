import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        String name = "petko";
        String writeTo = "petko";
        String serverHost = "127.0.0.1";
        int serverPort = 7777;

        try{
            byte[] sendData  = new byte[1024];

            // login
            // login protocol: login:<name>
            String toSend="login:" + name + "\r\n";
            // Assign the message to the send buffer
            sendData = toSend.getBytes();

            // Create a DatagramPacket to send, using the buffer, the clients IP address, and the clients port
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(),serverPort);
//
            DatagramSocket sendingSocket = new DatagramSocket();
//            // Send the echoed message
            sendingSocket.send(sendPacket);


            String list="list" + "\r\n";
            sendData=list.getBytes();
            DatagramPacket sendList = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(),serverPort);
            sendingSocket.send(sendList);


            byte[] buffer = new byte[1024];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            System.out.printf(sendingSocket.getLocalSocketAddress().toString());
            // get list
            DatagramSocket ds = new DatagramSocket(1235);
            ds.receive(incoming);
            String clientMessage = (new String(incoming.getData())).trim();

            System.out.printf(clientMessage);
            // start the client writer
            ClientWriter cw = new ClientWriter(ds);
            cw.start();

            // protocol for sending messages
            // <message>:<name-to>:<the message>
            for(int i = 0; i<100; i++) {
                // write to writeTo

                // login
                // login protocol: login:<name>
                String Send="message:" + writeTo + ":" + "this is the real message " + i + "\r\n";
                // Assign the message to the send buffer
                sendData = Send.getBytes();

                // Create a DatagramPacket to send, using the buffer, the clients IP address, and the clients port
                DatagramPacket senPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(),serverPort);
//
                DatagramSocket sendinSocket = new DatagramSocket();
//            // Send the echoed message
                sendinSocket.send(senPacket);
            }

        } catch(IOException ex){
            System.err.println("Error Connection!");
            ex.printStackTrace();
        }
    }
}