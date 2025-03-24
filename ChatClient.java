import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
1. Connect to Server:
    Loop until a connection is successfully established.
    Prompt the user to enter a port number.
    Attempt to create a Socket object with the given port.
2. Prompt user for a chat name.
3. Initialize Streams:
    Create BufferedReader for reading from the server.
    Create PrintWriter for sending messages to the server.
    Create BufferedReader for reading user input.
4. Send Chat Name:
    Use to prevent repeated user message print.
5. Read & Print Other Clients' Inputs:
    Create and start a new reader thread.
    Continuously read broadcast message from the server.
    Continuously print out the message to user's console.
6. Send User Input:
    Continuously read user input and send it to the server.
 */

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        int port = -1;

        while (socket == null || !socket.isConnected()) {
            System.out.print("Enter server IP address: ");
            String serverAddress = scanner.nextLine();
            System.out.print("Enter server port: ");
            try {
                port = Integer.parseInt(scanner.nextLine());
                socket = new Socket(serverAddress, port);
                if (socket.isConnected()) {
                    System.out.println("Connection established.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid port number.");
            } catch (IOException e) {
                System.out.println("Failed to connect. Please try again.");
            }
        }

        System.out.print("Enter chat name: ");
        String clientName = scanner.nextLine();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        out.println(clientName);

        Thread readerThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readerThread.start();

        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
        }

        socket.close();
    }
}