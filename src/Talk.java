import java.io.IOException;

public class Talk {

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            // If no arguments are provided or '--help' is passed, show help instructions
            printHelp();
        } else if (args[0].equals("-s")) {
            // Server mode: expects an optional port number
            int port = args.length >= 2 ? Integer.parseInt(args[1]) : 12987; // Default port is 12987
            startServer(port);
        } else if (args[0].equals("-h")) {
            // Client mode: expects hostname and an optional port number
            String hostname = args.length >= 2 ? args[1] : "localhost"; // Default hostname is localhost
            int port = args.length >= 4 ? Integer.parseInt(args[3]) : 12987; // Default port is 12987
            startClient(hostname, port);
        } else if (args[0].equals("-a")) {
            // Auto mode: try to connect as a client, if no server found, become a server
            String hostname = args.length >= 2 ? args[1] : "localhost";
            int port = args.length >= 4 ? Integer.parseInt(args[3]) : 12987;
            startAutoMode(hostname, port);
        } else {
            // Invalid argument handling
            System.out.println("Invalid arguments.");
            printHelp();
        }
    }

    // Method to start the server
    private static void startServer(int port) {
        System.out.println("Starting server on port " + port);
        try {
            TalkServer server = new TalkServer(port);
            server.start();
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

    // Method to start the client
    private static void startClient(String hostname, int port) {
        System.out.println("Connecting to server at " + hostname + " on port " + port);
        try {
            TalkClient client = new TalkClient(hostname, port);
            client.start();
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }

    // Method for auto mode
    private static void startAutoMode(String hostname, int port) {
        System.out.println("Auto mode: Trying to connect to " + hostname + " on port " + port);
        try {
            TalkClient client = new TalkClient(hostname, port);
            client.start();
        } catch (IOException e) {
            System.out.println("No server found, starting as a server on port " + port);
            startServer(port);
        }
    }

    // Method to display the help/usage instructions
    private static void printHelp() {
        System.out.println("Usage: Talk [-s|-h hostname] [-p port]");
        System.out.println("-s [port]: Start the program as a server on the given port (default: 12987).");
        System.out.println("-h [hostname] [port]: Start the program as a client and connect to the given hostname and port.");
        System.out.println("-a [hostname] [port]: Auto mode. Attempt to connect as a client; if no server is found, switch to server mode.");
        System.out.println("--help: Show this help message.");
    }
}
