package rmi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient extends Thread{
    private int socketPort[] = new int[2]; // portas para comunicar

    public UDPClient(int socketPort_1, int socketPort_2) {
        this.socketPort[0] = socketPort_1;
        this.socketPort[1] = socketPort_2;
    }

    //UDP client
    public void run() {
        String r;
        int rmi_servers = 2;
        DatagramSocket backupSocket = null;
        String msg = "Ping";

        try {
            backupSocket = new DatagramSocket();
            backupSocket.setSoTimeout(1000);
            while (true) {
                for(int i=1; i<rmi_servers; i++) {
                    int sendPort = socketPort[i];
                    byte[] buffer = msg.getBytes();

                    InetAddress host = InetAddress.getByName("localhost");

                    //Sending - Pong
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length, host, sendPort);
                    backupSocket.send(request);

                    //Receiving - Ping
                    byte[] replyBuffer = new byte[1024];
                    DatagramPacket reply = new DatagramPacket(replyBuffer, replyBuffer.length);
                    backupSocket.receive(reply);

                    r = new String(reply.getData(), 0, reply.getLength());
                    System.out.println("Received from main server: " + r);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (SocketException se) {
            System.out.println("Socket Exception: " + se.getMessage());
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally{
            if(backupSocket != null)
                backupSocket.close();;
        }

    }

    public void send(){
        start();
    }


    }

