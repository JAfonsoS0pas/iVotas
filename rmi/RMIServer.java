package rmi;

import tcp.TCP;

import com.sun.security.ntlm.Server;

import javax.xml.bind.SchemaOutputResolver;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class RMIServer extends UnicastRemoteObject implements RMI {
    public static RMIServer rmi;
    public static int portRMI=1099;
    public static int portTCP = 6000;
    public static String name = "SD";
    BufferedReader bf = null;
    private static TCP tcp;
    private static Registry reg_rmi = null;
    private static Registry reg_tcp = null;

    public RMIServer() throws RemoteException {
        super();
        //Reanding RMI file
        try {
            File myFile = new File("rmiFile.txt");
            System.out.println("Attempting to read from file in: "+myFile.getCanonicalPath());
            FileReader inputFile = new FileReader(myFile);
            BufferedReader bf = new BufferedReader(inputFile);

            Scanner scanner = new Scanner(myFile);
            while(scanner.hasNextInt()){
                portRMI = scanner.nextInt();
                System.out.println(portRMI);
            }
            name = scanner.next();
            System.out.println(name);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void primaryRMIServer(int portRMI) throws InterruptedException, RemoteException, ClassNotFoundException {
        ServerSocket listenSocket = null;
        RMI rmi = new RMIServer();
        try {
            reg_rmi = LocateRegistry.createRegistry(portRMI);
            reg_rmi.rebind(name, rmi);
        }  catch (ExportException e){
            reg_rmi = LocateRegistry.getRegistry(portRMI);
        }

    }

    //Failover
    /*Se o RMI primário falhar vai manter o RMI Secundário
    Verifica se o registo do RMI primário ainda existe. Caso deixe de existir substitui o anterior
    */
    private static void connectBackupRMI(int portRMI) throws  RemoteException, InterruptedException {
        System.out.println("Backup RMI is ready!!");
        boolean flag = false;
        while(!flag){
            try{
                RMI rmi_aux = new RMIServer();
                //Ligação UDP
                UDPConnection pinger = new UDPConnection(portRMI);
                pinger.start();
                Thread.sleep(3000);
            } catch(InterruptedException e) {
                flag = true;
            }
        }
        System.out.println("Primário!!!!");
        try {
            primaryRMIServer(portRMI);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void TCPConnection(String s_rmi, Registry reg_1, RMI rmi) throws RemoteException, InterruptedException{
        boolean flag = false;
        while(!flag){
            try{
                reg_1.rebind(s_rmi, rmi);
                flag=true;
            } catch (AccessException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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
        /*System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());*/

             try {
                 primaryRMIServer(portRMI);
             } catch (RemoteException e) {
                 connectBackupRMI(portRMI);
                 //Primário
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }


    }

}
