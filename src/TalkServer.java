import java.io.*;
import java.net.*;

public class TalkServer {
    private int port;

    public TalkServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("TalkServer started on port " + port);
        ServerSocket serverSocket = new ServerSocket(port);

        // Wait for a client to connect
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        // Set up input and output streams to send and receive messages
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Basic communication loop (you can improve this in future phases)
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Client says: " + inputLine);
            out.println("Server received: " + inputLine);  // Echo back to the client
        }

        // Close resources
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}