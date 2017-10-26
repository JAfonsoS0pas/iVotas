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
    public ArrayList<User> users = new ArrayList();
    public ArrayList<Lista> listas = new ArrayList();
    public ArrayList<Eleicao> eleicoes = new ArrayList();
    public ArrayList<Universidade> universidades = new ArrayList();
    public FicheirosDeObjeto file=new FicheirosDeObjeto();


    public void loadFiles(){

        //loads users

        if(file.abrirLeitura("users.dat")==true){ //atualiza o array de users
          try{
              users=(ArrayList)fo.lerObjeto();
          }catch(ClassNotFoundException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }
          file.fecharLeitura();
        } 

        //loads Listas


        if(file.abrirLeitura("listas.dat")==true){ //atualiza o array de listas candidatas
          try{
              listas=(ArrayList)fo.lerObjeto();
          }catch(ClassNotFoundException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }
          file.fecharLeitura();
        } 
        
        //loads Eleições


        if(file.abrirLeitura("eleicoes.dat")==true){ //atualiza o array de eleicoes
          try{
              eleicoes=(ArrayList)fo.lerObjeto();
          }catch(ClassNotFoundException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }
          file.fecharLeitura();
        }

         //loads Univ
        if(file.abrirLeitura("universidades.dat")==true){ //atualiza o array de universidades
          try{
              universidades=(ArrayList)fo.lerObjeto();
          }catch(ClassNotFoundException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }
          file.fecharLeitura();
        }
       
       
    }

    public void createObject()
    {
        Universidade uni1=new Universidade("Coimbra");
        Universidade uni2=new Universidade("Aveiro");
        Departamento dep1=new Departamento("Coimbra", "Engenharia Informatica");
        Departamento dep2=new Departamento("Coimbra","Fisica");
        Departamento dep3=new Departamento("Coimbra","Quimica");
        uni1.departamentos.add(dep1);
        uni1.departamentos.add(dep2);
        uni1.departamentos.add(dep3);
        User est1=new User(1,12345,12/03/2019,estudante1,dep2,912345345);
        User est2=new User(1,54321,11/02/2018,estudante2,dep2,961231234);
        User est3=new User(1,67899,10/01/2018,estudante3,dep2,913877281);
        User est4=new User(1,11122,08/06/2020,estudante4,dep3,965872111);
        User est5=new User(1,33444,07/02/2018,estudante5,dep3,938722444);
        User est6=new User(1,55444,06/02/2020,estudante6,dep1,913456789);
        User est7=new User(1,32432,04/09/2018,estudante7,dep1,912675834);
        User est8=new User(1,12543,05/05/2019,estudante8,dep1,967231123);

        User doc1=new User(2,55555,09/09/2023,docente1,dep1,913422277);
        User doc2=new User(2,66666,08/12/2022,docente2,dep1,963123432);
        User doc3=new User(2,77777,10/10/2024,docente3,dep2,912000656);
        User doc4=new User(2,88888,24/05/2024,docente4,dep2,913244113);
        User doc5=new User(2,99999,07/08/2023,docente5,dep3,967742592);
        User doc6=new User(2,11111,13/10/2022,docente6,dep3,935288222);

        User fun1=new User(3,33344,18/04/2025,funcionario1,dep1,965235656);
        User fun2=new User(3,44433,30/06/2013,funcionario2,dep2,913422345);

        universidades.add(uni1);
        universidades.add(uni2);
        users.add(est1);
        users.add(est2);
        users.add(est3);
        users.add(est4);
        users.add(est5);
        users.add(est6);
        users.add(est7);
        users.add(est8);
        users.add(doc1);
        users.add(doc2);
        users.add(doc3);
        users.add(doc4);
        users.add(doc5);
        users.add(doc6);
        users.add(fun1);
        users.add(fun2);

    }

    public void save()
    {
       

        fo.abrirEscrita("users.dat");
          try{
              fo.escreverObjeto(users);
              
              
          }catch(IOException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }
          //System.out.println("Foi inserido");
          fo.fecharEscrita();

        fo.abrirEscrita("listas.dat");
          try{
              fo.escreverObjeto(listas);
              
              
          }catch(IOException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }

          fo.fecharEscrita();

        fo.abrirEscrita("eleicoes.dat");
          try{
              fo.escreverObjeto(eleicoes);
              
              
          }catch(IOException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }

          fo.fecharEscrita();

        fo.abrirEscrita("universidades.dat");
          try{
              fo.escreverObjeto(universidades);
              
              
          }catch(IOException e)
          {
              System.out.println("Ocorreu um erro do tipo "+e);
          }

          fo.fecharEscrita();
    }

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
