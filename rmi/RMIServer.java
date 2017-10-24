package rmi;

import com.sun.security.ntlm.Server;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.io.*;
import java.net.Socket;


public class RMIServer extends UnicastRemoteObject implements RMI {
    public static RMIServer rmi;
    public static int portRMI;
    public static String name = "SD";
    BufferedReader bf = null;
    public static Registry reg_1;

    public RMIServer() throws RemoteException {
        super();
        //Reanding RMI file
        try {
            InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("rmi/rmiFile.txt"));
            bf = new BufferedReader(input);
            portRMI = Integer.parseInt(bf.readLine());
            name = bf.readLine();

            //bf.close();
        } catch (Exception e) {
            System.out.println("Exception!");
        }
    }

    public static void primaryRMIServer(int portRMI) throws InterruptedException, RemoteException {
        ServerSocket listenSocket = null;
        try {
            LocateRegistry.createRegistry(portRMI);
            RMI rmi = new RMIServer();
            Naming.rebind("rmi://localhost:" + portRMI + "/rmi", rmi);

            /*
                        TCP Callback

             */


        } catch(AccessException | MalformedURLException e){
            System.out.println("AccessException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Failover
    /*Se o RMI primário falhar vai manter o RMI Secundário
    Verifica se o registo do RMI primário ainda existe. Caso deixe de existir substitui o anterior
    */
    private static void connectBackupRMI(RMI rmi, int portRMI) throws  RemoteException, InterruptedException {
        System.out.println("Backup RMI is ready!!");
        boolean flag = false;
        while(!flag){
            try{
                //rmi.teste();
                //Ligação UDP
                Thread.sleep(3000);
            } catch(InterruptedException e) {
                flag = true;
            }
        }
        primaryRMIServer(portRMI);
    }

    private static int verificaPorts(){
        int port = 3000;
        for(int i=0;i<3;i++){
            try{
                ServerSocket check = new ServerSocket(port);
                check.close();
                return port;
            } catch (IOException e){
                port+=1000;
            }
        }
        return 0;
    }


    public static void main(String args[]) throws InterruptedException, IOException, RemoteException {

        //System.getProperties().put("java.security.policy", "policy.all");
        //System.setSecurityManager(new RMISecurityManager(

        try {
            if (args.length != 2) {
                System.out.println("java RMI host port");
                System.exit(0);
            }
            portRMI = Integer.parseInt(args[1]);
            UDPConnection pinger = new UDPConnection(args[0],portRMI);
            pinger.start();
            while(true) {
                try {
                    //Secundário
                    RMI rmi_1 = (RMI) Naming.lookup("rmi://" + args[0] + ":" + portRMI + "/rmi");
                    connectBackupRMI(rmi_1, portRMI);
                } catch (NotBoundException e) {
                    //Primário
                    primaryRMIServer(portRMI);
                }
            }
        } catch(Exception e){
            System.out.println("Erro na porta " + e);
        }

    }

}
