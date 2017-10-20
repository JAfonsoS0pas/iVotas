package rmi;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.net.*;
import java.io.*;



public class UDPConnection {

    //UDP server
    public void mainUDPConnect() {
        //Criar a thread
        Thread mainUDPConnect = new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket aSocket = null;
                String req;
                String msg = "Pong";
                try {
                    aSocket = new DatagramSocket(6789);
                    System.out.println("Socket datagram listening on port 6789");
                    while (true) {
                        byte[] buffer = new byte[1024];

                        //Receive resquest - Ping
                        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                        aSocket.receive(request);
                        req = new String(request.getData(), 0, request.getLength());
                        System.out.println("Received from backup rmi.RMI server: " + req);

                        //Sending reply - Pong
                        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
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
                } finally {
                    if (aSocket != null)
                        aSocket.close();
                }
            }
        });
        mainUDPConnect.start();
    }

    //UDP client
    public void backupConnect() {
        Thread backupConnect = new Thread(new Runnable() {
            @Override
            public void run() {
                String r;
                DatagramSocket backupSocket = null;
                String msg = "Ping";

                try {
                    backupSocket = new DatagramSocket();
                    backupSocket.setSoTimeout(1000);
                    while (true) {
                        byte[] buffer = msg.getBytes();

                        InetAddress host = InetAddress.getByName("localhost");
                        int sendPort = 6789; //Porta UDP

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
        });
        backupConnect.start();
    }

}
