import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.net.*;
import java.io.*;


public class RMIServer extends UnicastRemoteObject implements RMI {
    public RMIServer() throws RemoteException {
        super();
    }

    public void initRMIServer() throws RemoteException {
        int port = 1099;
        String name = "SD";

        //Reanding RMI file
        try{
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader bf = new BufferedReader(input);

            port = Integer.parseInt(bf.readLine());
            name = bf.readLine();
            bf.close();

        } catch(Exception e) {
            System.out.println("Exception!");
            System.exit(1);
        }



        //Starting RMI Server (Main)
        try{
            RMIServer rmi = new RMIServer();
            Registry r = LocateRegistry.createRegistry(port);
            r.rebind(name, rmi);
            System.out.println("RMI Server ready!!");

            rmi.mainUDPConnect();
        } catch (ExportException ee) {
            System.out.println("Exception : " + ee);

            try{
                new RMIServer().backupConnect();
                System.out.println("Backup Server Running");
            }catch(RemoteException re){
                System.out.println("RemoteException: " + re.getMessage());
            }

        } catch (RemoteException re){
            System.out.println("RemoteException: " + re);
        }

    }


    //UDP server
        public void mainUDPConnect() throws RemoteException {
            //Criar a thread
            Thread mainUDPConnect = new Thread(new Runnable() {
                @Override
                public void run() {
                    DatagramSocket aSocket = null;
                    String req;
                    String msg = "Mensagem_1";
                    try{
                        aSocket = new DatagramSocket(6789);
                        System.out.println("Socket datagram listening on port 6789");
                        while(true){
                            byte[] buffer = new byte[1024];

                            //Receive resquest - Mensagem_2
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(request);
                            req = new String(request.getData(), 0, request.getLength());
                            System.out.println("Received from backup RMI server: " + req);

                            //Sending reply - Mensagem_1
                            DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                            aSocket.send(reply);

                            try{
                                Thread.sleep(1000);
                            }catch(InterruptedException ex){
                                Thread.currentThread().interrupt();
                            }
                        }
                    } catch(SocketException e){
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
        public void backupConnect() throws RemoteException {

            Thread backupConnect = new Thread(new Runnable() {
                @Override
                public void run() {
                    String r;
                    DatagramSocket backupSocket = null;

                    String msg = "Mensagem_2";

                    try {
                        backupSocket = new DatagramSocket();
                        backupSocket.setSoTimeout(1000);
                        while (true) {
                            byte[] buffer = msg.getBytes();

                            InetAddress host = InetAddress.getByName("localhost");
                            int sendPort = 6789;

                            //Sending - Mensagem_2
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length, host, sendPort);
                            backupSocket.send(request);

                            //Receiving - Mensagem_1
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
                    }

                }
            });
            backupConnect.start();
        }


    public static void main(String args[]) {

        //System.getProperties().put("java.security.policy", "policy.all");
        //System.setSecurityManager(new RMISecurityManager());



    }

}
