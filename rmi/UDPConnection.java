package rmi;

import java.io.IOException;
import java.net.*;

public class UDPConnection extends Thread{
    int server;

    public UDPConnection(int server) {
        this.server = server;
    }

    public void run(){
        DatagramSocket aSocket = null;
        String msg = "hi";
        while(true){
            //Se for primário
            if(this.server == 1){



                try{
                    aSocket = new DatagramSocket();
                    while(true){
                        byte[] mBuffer = msg.getBytes();
                        InetAddress host = InetAddress.getByName("localhost");
                        int serverPort = 6789;
                        DatagramPacket request = new DatagramPacket(mBuffer, mBuffer.length, host, serverPort);
                        Thread.sleep(1000);
                        aSocket.send(request);
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(aSocket != null)
                        aSocket.close();
                }
            }
            //Se for servidor secundário - backup
            else if(this.server == 2){
                try{
                    String s = new String();
                    aSocket = new DatagramSocket(6789);
                    System.out.println("Socket datagram listening on port 6789");
                    while(true){
                         byte[] buffer = new byte[1024];
                         DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                         try{
                             aSocket.setSoTimeout(3000);
                             aSocket.receive(request);
                         } catch (SocketException e) {
                             //Pôr RMI?????
                             this.server = 1; //Muda para primário

                             break;
                         }
                    }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                    if(aSocket != null)
                        aSocket.close();
                }
            }
        }
    }
}
