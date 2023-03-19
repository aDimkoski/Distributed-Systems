import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void response(Socket socket) throws IOException {
        //response wait
        ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
        try {
            Object object = (Response) objectInput.readObject();
            Response response = (Response) object;
            System.out.println(response.getPoraka());
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String serverHost = "127.0.0.1";
        int serverPort = 4444;

        Randomizer randomizer = new Randomizer();
        Request request = new Request();
        request.setClientUser(new ClientUser(randomizer.random()));
        request.setType("login");
        // login
        // login protocol: login:<name>
        try {
            // connect
            Socket socket = new Socket(serverHost, serverPort);
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(request);
            objectOutput.flush();

            //lista request
            Request requestList = new Request();
            requestList.setType("list");
            objectOutput.writeObject(requestList);
            objectOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // connect
            Socket socket = new Socket(serverHost, serverPort);

            //lista request
            Request requestList = new Request();
            requestList.setType("list");

            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(requestList);
            objectOutput.flush();


            //response wait
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            try {
                Object object = (Response) objectInput.readObject();
                Response response = (Response) object;
                for (ClientUser u : response.getUsers()) {
                    System.out.println(u.getName());
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // <message>:<name-to>:<the message>
        for (int i = 0; i < 100; i++) {
            try {
                Request request1 = new Request();
                request1.setType("poraka");
                request1.setMessage("poraka " + i + ": " + request.getClientUser().getName());
                // connect
                Socket socket1 = new Socket(serverHost, serverPort);
                ObjectOutputStream objectOutput = new ObjectOutputStream(socket1.getOutputStream());
                objectOutput.writeObject(request1);
                objectOutput.flush();

                response(socket1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}