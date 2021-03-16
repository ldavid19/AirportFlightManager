import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ReservationServer {

    private ServerSocket serverSocket;
    int port;

    public ReservationServer() throws IOException {
        this.serverSocket = new ServerSocket(0);
    }

    public static void main(String[] args) {
        try {
            ReservationServer server = new ReservationServer();
            try {
                server.startServer();
            } catch (Exception e) {
                System.out.println("Server start error! Check ReservationServer main");
            }

            try {
                server.stopServer();
            } catch (Exception e) {
                System.out.println("Server close error! Check ReservationServer main");
            }
        } catch (Exception e)  {
            System.out.println("creating server object error!");
        }

    }

    private void startServer() throws Exception {
        while (true) {
            System.out.printf("<Waiting to serve clients on port %d>%n", this.serverSocket.getLocalPort());
            Socket clientSocket = serverSocket.accept();
            System.out.println("Get booking request");
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    private void stopServer() throws Exception {
        if (serverSocket != null) {
            try {
                System.out.println("Server is closing...");
                serverSocket.close();
                System.out.println("Server is closed! Goodbye!");
            } catch (Exception e) {
                System.out.println("Server closing error!");
            }
        }
    }



}
