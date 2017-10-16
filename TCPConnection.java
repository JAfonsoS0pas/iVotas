import java.net.*;
import java.io.*;

public class TCPConnection {
    public static void main(String args[]){
        int numero=0;

        try{
            int serverPort=6000;
            System.out.println("A Escuta no Porto 6000");
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("LISTEN SOCKET="+listenSocket);
            while(true){
                Socket clientSocket = listenSocket.accept();
                System.out.println("Client SOcket (accpeted)="+clientSocket);
                numero++;
                new TCPConnection(clientSocket, numero);
            }
        }catch(IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }
    }
}




class TCPConnection extends Thread{
        DataInputStream dis;
        DataOutputStream dos;
        Socket clientSocket;
        int threadNumber;

        public Connection (Socket clientSocket, int n){
            threadNumber= n;
            try{
                this.clientSocket = clientSocket;
                dis = new DataInputStream(clientSocket.getInputStream());
                dos = new DataOutputStream(clientSocket.getOutputStream());
                this.start();
            }catch(IOException e){
                System.out.println("Connection:" + e.getMessage());
            }
        }
}