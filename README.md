# LocalNetworkChat

A terminal text chat application that allows multiple users to communicate simultaneously over a local network.

## Usage

### Starting the Server

1. Compile the `ChatServer.java` file:
    ```sh
    javac ChatServer.java
    ```

2. Run the `ChatServer`:
    ```sh
    java ChatServer
    ```

3. The server will display its IP address and port number. Note these details for client connections.

### Connecting Clients

1. Compile the `ChatClient.java` file:
    ```sh
    javac ChatClient.java
    ```

2. Run the `ChatClient`:
    ```sh
    java ChatClient
    ```

3. Enter the server's IP address and port number when prompted.

4. Enter a chat name when prompted.

5. Start chatting with other connected clients.

## Code Overview

### `ChatServer.java`

- Initializes client handlers to keep track of connected clients.
- Starts the server on a random port and displays the IP address and port number.
- Accepts client connections and starts a new `ClientHandler` thread for each connection.
- Handles client communication, broadcasting messages to all connected clients.

### `ChatClient.java`

- Prompts the user to enter the server's IP address and port number.
- Connects to the server and prompts the user to enter a chat name.
- Initializes input and output streams for communication.
- Reads and prints messages from other clients.
- Sends user input to the server.

## Example

1. Start the server:
    ```sh
    java ChatServer
    ```

    Output:
    ```
    Server live on IP address 192.168.1.2 port 12345
    ```

2. Connect a client:
    ```sh
    java ChatClient
    ```

    Input:
    ```
    Enter server IP address: 192.168.1.2
    Enter server port: 12345
    Enter chat name: User1
    ```

3. Start chatting:
    ```
    User1: Hello, everyone!
    ```

## Requirements

- Java Development Kit (JDK) 8 or higher

## License

This project is licensed under the MIT License.