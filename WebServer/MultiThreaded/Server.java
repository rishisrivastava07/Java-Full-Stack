import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    // we will use funtional interface because we can use it as an argurments or pass it like an arguements
    // it is a first class citizen
    public Consumer<Socket> getConsumers(){
        return (clientSocket) -> {
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientSocket.close();

            } catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        };  
    }

    public static void main(String args[]) {
        int port = 8010;
        Server server = new Server();

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening port : " + port);

            while(true){
                Socket acceptedSocket = serverSocket.accept(); // we now donot need to send anything to client
                // instead we will make a new socket connection for another client and keeping this in a Thread 

                Thread thread = new Thread(() -> server.getConsumers().accept(acceptedSocket)); // it will run a function and communicate with the specific client assoicated with this thread
                thread.start();
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
