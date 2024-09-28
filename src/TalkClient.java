import java.io.*;
import java.net.*;

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

        // Set up input and output streams for communication
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Read user input and send to the server
        String userInput;
        System.out.println("Type a message to send to the server: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Server says: " + in.readLine());  // Print the server's response
        }

        // Close resources
        out.close();
        in.close();
        socket.close();
    }
}