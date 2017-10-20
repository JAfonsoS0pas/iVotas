package rmi;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.io.*;


public class RMIServer extends UnicastRemoteObject implements RMI {
    public static RMIServer rmi;
    public static int portRMI = 1099;
    public static String name = "SD";
    BufferedReader bf = null;
    public static Registry reg_1;
    public static Registry reg_2;

    public RMIServer() throws RemoteException {
        super();
        //Reanding rmi.RMI file
        try {
            InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("rmi/rmiFile.txt"));
            bf = new BufferedReader(input);

            portRMI = Integer.parseInt(bf.readLine());
            name = bf.readLine();

            //bf.close();
        } catch (Exception e) {
            System.out.println("Exception!");
            System.exit(1);
        }
    }

    public static void initRMIServer() throws InterruptedException, RemoteException {
        //Starting rmi.RMI Server (Main)
        try {
            rmi = new RMIServer();
            reg_1 = LocateRegistry.createRegistry(portRMI);
            reg_1.rebind(name, rmi);
            System.out.println("rmi.RMI Server ready!!");
            //TCP Callback
            rmi.mainUDPConnect();

        } catch(AccessException e){
            System.out.println("AccessException: " + e.getMessage());
        }catch (RemoteException e) {
            connectBackupRMI();
        } catch(NotBoundException e){
            //retryTCPConnection();
        }

        try {
            new RMIServer().backupConnect();
            System.out.println("Backup Server Running");
        } catch (RemoteException re) {
            System.out.println("RemoteException: " + re.getMessage());
        }

    }

    private static void connectBackupRMI() throws  RemoteException, InterruptedException {
        System.out.println("Backup RMI is ready!!");
        boolean flag = false;
        while(!flag){
            try{
                Registry reg_2 = LocateRegistry.createRegistry(portRMI);
                rmi = new RMIServer();
                reg_1.rebind(name, rmi);
                flag = true;
            } catch(AccessException e) {
                System.out.println(" ");
            } catch(RemoteException e) {
                Thread.sleep(3000);
            } 
        }
    }


    public static void main(String args[]) throws NumberFormatException, IOException, ClassNotFoundException {

        //System.getProperties().put("java.security.policy", "policy.all");
        //System.setSecurityManager(new RMISecurityManager());

        initRMIServer();



    }

}
