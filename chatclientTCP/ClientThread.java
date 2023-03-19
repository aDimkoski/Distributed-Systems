import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private ChatServer server;
    BufferedReader br;
    OutputStreamWriter wr;

    public ClientThread(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        // setup
    }

    private PrintWriter getWriter() {
        return clientOut;
    }

    @Override
    public void run() {
        System.out.println("Thread");
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            try {
                Object object = (Request) objectInput.readObject();
                Request request = (Request) object;

                String name = "";
                // start protocol loop
                System.out.println("Server received: " + request.toString());

                // login
                if (request.getType().equals("login") && request.getType() != null) {
                    server.putClient(request.getClientUser(), socket);
                    Thread.sleep(6000);
                } else if (request.getType().equals("list")) {
                    server.putClient(new ClientUser("ekkd"), socket);
                    HashMap<ClientUser, Socket> clients = server.getClients();
                    Set<ClientUser> clientList = clients.keySet();

                    Response response = new Response();
                    response.setType("list");
                    response.setUsers(clientList.toArray(new ClientUser[]{}));

                    ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                    objectOutput.writeObject(response);
                } else if (request.getType().equals("poraka")) {
                    Response response = new Response();
                    response.setType("poraka");
                    response.setPoraka(request.getMessage());

                    ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                    objectOutput.writeObject(response);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}