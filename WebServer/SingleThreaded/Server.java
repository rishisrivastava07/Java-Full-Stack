import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws Exception{
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(10000);
        while(true){
            try{
                System.out.println("Server is listening on port : " + port);
                Socket acceptedConnection = socket.accept();
                System.out.println("Connection accepted from client : " + acceptedConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream()); // used to convert text message to convert it into bits stream and send it to the channel
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream())); // used to combine the bits stream and convert it into the meaningful text message and send the result to the channel

                toClient.println("Hello from the Server");
                toClient.close();
                fromClient.close();
                acceptedConnection.close();
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try{
            server.run();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
