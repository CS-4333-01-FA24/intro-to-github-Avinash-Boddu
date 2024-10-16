import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TalkClient {
    private String hostname;
    private int port;

    public TalkClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("TalkClient connecting to " + hostname + " on port " + port);
        Socket socket = new Socket(hostname, port);

        // Set up input and output streams to send and receive messages
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Loop for continuous communication until 'exit' is typed
        String userInput;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type a message to send to the server (type 'exit' to quit):");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Server says: " + in.readLine());
            if ("exit".equalsIgnoreCase(userInput)) {
                System.out.println("Exiting client.");
                break;
            }
        }

        // Close resources
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        String hostname = "localhost"; // You can change this if needed
        int port = 8080; // Same port as server
        TalkClient client = new TalkClient(hostname, port);
        try {
            client.start();
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}