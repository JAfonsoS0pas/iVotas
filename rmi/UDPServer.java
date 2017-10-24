package rmi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Thread {
    private int port;

    public UDPServer(int port) {
        this.port = port;
    }

    //UDP server
    public void run() {
        DatagramSocket aSocket = null;
        String req;
        String msg = "Pong";
        try {
            //Cria o server socket
            aSocket = new DatagramSocket(port);
            System.out.println("Socket datagram listening on port 6789");
            while (true) {
                byte[] buffer = new byte[1024];

                //Receive resquest - Ping
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                req = new String(request.getData(), 0, request.getLength());
                System.out.println("Received from backup rmi.RMI server: " + req);

                //Sending reply - Pong
                //DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                DatagramPacket reply = new DatagramPacket(req.getBytes(), req.getBytes().length, request.getAddress(), request.getPort());
                aSocket.send(reply);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
