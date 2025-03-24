import java.io.*;
import java.net.*;
import java.util.*;

/*
1. Initialize Client Handlers:
    Create a set to keep track of connected clients.
2. Start Server:
    Create a ServerSocket on a random port.
3. Accept Client Connections:
    Continuously look for client connections
    For each connection, create and start a new ClientHandler thread.
4. Handle Client:
    Initialize input BufferedReader and output PrintWriter streams.
    Read the client name from the input stream.
    Add the client handler to the set of client handlers.
    Continuously read messages from the client.
    Broadcast each message to other clients.
 */

public class ChatServer {
    private static final Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            System.out.println("Server started on port " + serverSocket.getLocalPort());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            new ClientHandler(serverSocket.accept()).start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                clientName = in.readLine();
                System.out.println(clientName + " has connected.");
                out.println("\nWelcome to chat room, " + clientName + "!\n");

                synchronized (clientHandlers) {
                    clientHandlers.add(this);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(clientName + " said: \"" + message + "\"");
                    broadcastMessage(clientName, message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientHandlers) {
                    clientHandlers.remove(this);
                }
                System.out.println(clientName + " has disconnected.");
            }
        }

        private void broadcastMessage(String senderName, String message) {
            synchronized (clientHandlers) {
                for (ClientHandler handler : clientHandlers) {
                    if (handler != this) {
                        handler.out.println(senderName + ": " + message);
                    }
                }
            }
        }
    }
}