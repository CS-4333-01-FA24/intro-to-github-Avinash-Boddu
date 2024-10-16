import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

        // Loop for continuous communication until client sends "exit"
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Client says: " + inputLine);
            out.println("Server received: " + inputLine);
            if ("exit".equalsIgnoreCase(inputLine)) {
                System.out.println("Client has exited.");
                break;
            }
        }
        // Close resources
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        int port = 8080; // You can change this to your desired port
        TalkServer server = new TalkServer(port);
        try {
            server.start();
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}