import java.net.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.*;


class TCPServer{
    private static String host="localhost";
    private static int serverPort = 6000;
    private static int rmiPort = 1099;
    private static String rmiName = "rmi";
    int id;



    public static void main(String args[]){
        int number=0; //numero de clientes ligados a este servidor
        User users=new User();
        id=getMesaID();

        try{
            /*RMI rmiServer = (RMI) LocateRegistry.getRegistry(rmiPort).lookup(rmiName);*/
            id=getIDMesa();
            this.rmiPort=rmiPort+id
            System.out.println("A escura no porto"+serverPort);
            ServerSocket listenSocket = new ServerSocket(serverPort);
            new Manager(users);
            while(true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                users.addUser(clientSocket);
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                number++;

                new Connection(clientSocket, number, rmiServer, users);
            }
        }
        catch(IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }catch (SocketTimeoutException e) {
        catch(NotBoundException e) { //execao para rmi
            System.out.println("RMI:" + e.getMessage());
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
            verificaEleitor(id);
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
            clientSocket.setSoTimeout(60000);
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run(){
        String data;
        int i;
        int cc=0;
        Info no=null;
        String resposta=null;
        String listas=null;
        arrayList<Eleitor> aux;

        for(Info node : users.userList){
            if(node.userSocket==clientSocket){
                no=node;
                System.out.println("Client found!");
            }
        }
        try{
            data = in.readLine();
            if(no.getPermition()){
                String dataParts [];
                dataParts=data.split(" ");
                if(dataParts[0]=="login"){
                    if(rmi.autenticação(Integer.parseInt(dataParts[1]),dataParts[2])==true){
                        resposta="autenticacao correcta";
                        cc=Integer.parseInt(dataParts[1]);
                        aux=verificaEleitor(dataParts[2]);
                        listas="type|item_list;item_count|"+aux.getListasCandidatas().size()+";";
                        for(i=0;i<aux.getListasCandidatas().size();i++){
                            listas+="item_"+i+"_name"+aux.getListasCandidatas().get(i).getNome();
                        }
                        out.println(resposta);
                    }
                    else{
                        resposta="Username ou Password erradas";
                        out.println(resposta);
                    }

                }
                else if(dataParts[0]=="vote"){
                    if(dataParts.size()==3){
                        rmi.inserirVotos(cc,dataParts[1],dataParts[2])
                    }
                    else if(dataParts.size()==2){
                        rmi.inserirVotos(cc,dataParts[1],null);
                    }
                }
                else{
                    out.println("Operacao inexistente");
                }
            }
        } catch(SocketTimeoutException e){
            no=null;
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