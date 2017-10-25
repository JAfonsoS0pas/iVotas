package tcp;

import rmi.*;

import java.net.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.*;


class TCPServer{
    private static String host="localhost";
    private static int serverPort = 12345;
    private static int rmiPort = 1099;
    private static String rmiName = "rmi";
    static TCP service_tcp = null;
    private static RMI rmiServer = null;


    public static void main(String args[]){
        int number=0; //numero de clientes ligados a este servidor
        User users=new User();

        try{
            //TCP tcpServer = (TCP)  LocateRegistry.getRegistry(host, rmiPort).lookup(rmiName);
            System.out.println(rmiName);
            System.out.println(rmiPort);
            rmiServer = (RMI) LocateRegistry.getRegistry(rmiPort).lookup(rmiName);
            System.out.println("A escura no porto"+serverPort);
            ServerSocket listenSocket = new ServerSocket(serverPort);
            new Manager(users);
            System.out.println("fora");
            while(true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("1");
                users.addUser(clientSocket);
                System.out.println("2");
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                number++;

                new Connection(clientSocket, number, rmiServer, users);
            }
        }
        catch(IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }catch(NotBoundException e) { //execao para rmi
            System.out.println("RMI:" + e);
        }
    }


}
class Manager extends Thread{
    User users;
    Scanner input = new Scanner(System.in);

    public Manager(User users){
        this.users=users;
        this.start();
    }

    public void run(){
        int numeroTerminal;
        int id;
        //desbloquear terminal
        while(true){
            //identifica o votante
            System.out.print("ID do votante: ");
            id=input.nextInt();
            //liberta mesa de voto
            System.out.print("Terminal a desbloquear:");
            numeroTerminal=input.nextInt();
            users.userList.get(numeroTerminal).setId(id);
            users.userList.get(numeroTerminal).permitonChange();
            System.out.println("=======>Terminal " + numeroTerminal + " desbloqueado!");
        }
    }
}

class Connection extends Thread {
    BufferedReader in;
    PrintWriter out;
    Socket clientSocket;
    int thread_number;
    RMI server;
    User users;

    public Connection(Socket aClientSocket, int numero, RMI server, User users) {
        this.server = server;
        this.users = users;
        thread_number = numero;
        try {
            clientSocket = aClientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            System.out.println("tcp.Connection:" + e.getMessage());
        }
    }

    public void run(){
        String autenticação;
        String data;
        Info no=null;

        for(Info node : users.userList){
            if(node.userSocket==clientSocket){
                no=node;
                System.out.println("Client found!");
            }
        }
        try{
            if(no.getId()!=0){
                System.out.println("id= "+ no.getId());
            }

            data = in.readLine();
            if(no.getPermition()){
                String dataParts [];
                String partedT[];
                String partedP[];
                //alterar spilt
                dataParts=data.split(";");
                partedT=dataParts[0].split("//");
                partedP=dataParts[1].split("//");

                if(partedT[0].equals("type")){
                    if(partedT[1].equals("login")){
                        if(partedP[0].equals("password")){
                            String pass;
                            pass=partedP[1];
                        }
                        /*if (autenticação == true) { ciar autenticação

                            //RMI PARA VALIDAR
                            resposta = "trotas";

                            ///
                        } else {
                            resposta = "Autenticacao incorrecta";
                        }*/
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
class User{
    protected List<Info> userList = new ArrayList<Info>();

    public void addUser(Socket socket){

        userList.add(new Info(socket));
    }
}
class Info{
    Socket userSocket;
    private int id=0;
    private boolean permition;

    public Info(Socket socket){
        this.userSocket=socket;
        permition=false;
    }

    public void permitonChange(){
        if(permition==true){
            permition=false;
        }
        else if(permition==false){
            permition=true;
        }
    }

    public boolean getPermition(){
        return permition;
    }
    public int getId(){

        return this.id;
    }
    public void setId(int id){

        this.id=id;
    }

}