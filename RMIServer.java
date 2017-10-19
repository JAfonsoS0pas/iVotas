import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.net.*;
import java.io.*;


public class RMIServer extends UnicastRemoteObject implements RMI {
    public static RMIServer rmi;
    public static int portRMI = 1099;
    public static String name = "SD";
    BufferedReader bf = null;

    public RMIServer() throws RemoteException {
        super();
        //Reanding RMI file
        try {
            InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("rmiFile.txt"));
            bf = new BufferedReader(input);

            portRMI = Integer.parseInt(bf.readLine());
            name = bf.readLine();

            //bf.close();
        } catch (Exception e) {
            System.out.println("Exception!");
            System.exit(1);
        }
    }

    public static void initRMIServer() throws RemoteException {
        //Starting RMI Server (Main)
        try {
            System.out.println("serafim");
            rmi = new RMIServer();
            Registry r = LocateRegistry.createRegistry(portRMI);
            r.rebind(name, rmi);
            System.out.println("Serafim 2");
            System.out.println("RMI Server ready!!");

            rmi.mainUDPConnect();

        } catch (Exception e) {
            boolean flag = false;
            while(flag = false){
                try{
                    rmi = new RMIServer();
                    Registry r = LocateRegistry.createRegistry(portRMI);
                    r.rebind(name, rmi);
                    System.out.println("RMI Server 2 ready!!");
                    flag = true;
                } catch(Exception e2){
                    System.out.println("Vou mudar, bye!!");
                }
            }
        }
        try {
            new RMIServer().backupConnect();
            System.out.println("Backup Server Running");
        } catch (RemoteException re) {
            System.out.println("RemoteException: " + re.getMessage());
        }

    }

        //UDP server
        public void mainUDPConnect() {
            //Criar a thread
            Thread mainUDPConnect = new Thread(new Runnable() {
                @Override
                public void run() {
                    DatagramSocket aSocket = null;
                    String req;
                    String msg = "Pong";
                    try{
                        aSocket = new DatagramSocket(6789);
                        System.out.println("Socket datagram listening on port 6789");
                        while(true){
                            byte[] buffer = new byte[1024];

                            //Receive resquest - Ping
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(request);
                            req = new String(request.getData(), 0, request.getLength());
                            System.out.println("Received from backup RMI server: " + req);

                            //Sending reply - Pong
                            DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                            aSocket.send(reply);
                            System.out.println("Sending message from mainUDPConnection: ");

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
                            int sendPort = 6789;

                            //Sending - Pong
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length, host, sendPort);
                            backupSocket.send(request);
                            System.out.println("Sending message from backupConnection: ");

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
                    }

                }
            });
            backupConnect.start();
        }


    public static void main(String args[]) throws NumberFormatException, IOException, ClassNotFoundException {

        //System.getProperties().put("java.security.policy", "policy.all");
        //System.setSecurityManager(new RMISecurityManager());

        initRMIServer();



    }

}
