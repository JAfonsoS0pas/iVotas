package tcp;

import rmi.*;
import java.net.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;


class TCPServer {
    private static String host = "localhost";
    private static int serverPort = 6000;
    private static int rmiPort = 1099;
    private static String rmiName = "rmi";
    private static RMI rmiServer = null;
    int id;


    public static void main(String args[]) {
        int number = 0; //numero de clientes ligados a este servidor
        TCPUser users = null;

        try {
            rmiServer = (RMI) LocateRegistry.getRegistry(rmiPort).lookup(rmiName);
            System.out.println("A escura no porto" + serverPort);
            ServerSocket listenSocket = new ServerSocket(serverPort);
            new Manager(users, rmiServer);
            while (true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                users.addUser(clientSocket);
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                number++;

                new Connection(clientSocket, number, rmiServer, users);
            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());
        } catch (NotBoundException e) { //execao para rmi
            System.out.println("RMI:" + e.getMessage());
        }
    }
}

     class Manager extends Thread {
        TCPUser users;
        RMI rmiServer;
        Scanner input = new Scanner(System.in);

        public Manager(TCPUser users, RMI rmiServer) {
            users = users;
            this.rmiServer=rmiServer;
            this.start();
        }

        public void run() {
            int numeroTerminal;
            int id;
            //desbloquear terminal
            while (true) {
                //identifica o votante
                System.out.print("ID do votante: ");
                id = input.nextInt();
                try {
                    rmiServer.verificaEleitor(id);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //liberta mesa de voto
                System.out.print("Terminal a desbloquear:");
                numeroTerminal = input.nextInt();
                users.getUserList().get(numeroTerminal).setId(id);
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
        RMI rmiServer;
        TCPUser users;

        public Connection(Socket aClientSocket, int numero, RMI rmiServer, TCPUser users) {
            this.rmiServer = rmiServer;
            users = users;
            thread_number = numero;
            try {
                clientSocket = aClientSocket;
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientSocket.setSoTimeout(120000);
                this.start();
            } catch (IOException e) {
                System.out.println("Connection:" + e.getMessage());
            }
        }

        public void run() {
            String data;
            int i, j, aux2 = 0;
            int cc = 0;
            Info no = null;
            String resposta = null;
            String listas = null;
            ArrayList<Eleicao> aux;
            boolean b1= false;
            int b2=0; //0 = sem resposta 1 = true 2=false
            long time;

            try {
                while(in.readLine()!=null){
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Info node : users.getUserList()) {
                if (node.userSocket == clientSocket) {
                    no = node;
                    System.out.println("Client found!");
                }
            }
            try {
                //aux2 += aux.get(i).getListasCandidatas().size();
                data = in.readLine();
                String dataParts[];
                dataParts = data.split(" ");
                if (no.getPermition()) {
                    if (dataParts[0] == "login") {
                        time = System.currentTimeMillis();
                        b2 = rmiServer.autenticacao(Integer.parseInt(dataParts[1]), dataParts[2]);
                        while (b2 == 0 && time + System.currentTimeMillis() < time + 30000) {
                            b2 = rmiServer.autenticacao(Integer.parseInt(dataParts[1]), dataParts[2]);
                        }
                        if (b2 == 0) {
                            out.println("Operacao nao realizada [Server Down]");
                            no.permitonChange();
                        } else if (b2 == 1) {
                            out.println("Autenticacao correcta");
                            cc = Integer.parseInt(dataParts[1]);
                            aux = rmiServer.verificaEleitor(cc);
                            time = System.currentTimeMillis();
                            while (aux == null && time + System.currentTimeMillis() < time + 30000) {
                                aux = rmiServer.verificaEleitor(cc);
                            }
                            if (aux == null) {
                                out.println("operaco nao realizada [Server Down]");
                                no.permitonChange();
                            }
                            for (i = 0; i < aux.size(); i++) {
                                aux2 += aux.get(i).getListasCandidatas().size();
                            }
                            listas = "type|item_list;item_count|" + aux2 + ";";
                            for (i = 0; i < aux.size(); i++) {
                                for (j = 0; j < aux.get(i).getListasCandidatas().size(); j++) {
                                    int count = i + j;
                                    listas += "item_" + count + "_name" + aux.get(i).getListasCandidatas().get(j).getNome();
                                }
                            }
                            out.println(listas);
                        } else if (b2 == 2) {
                            out.println("autenticacao incorreta");
                        }
                    } else if (dataParts[0] == "vote") {
                        time = System.currentTimeMillis();
                        if (dataParts.length == 3) {
                            while (b1 = rmiServer.inserirVoto(cc, dataParts[1], dataParts[2]) == false && time + System.currentTimeMillis() < time + 30000) {
                                b1 = rmiServer.inserirVoto(cc, dataParts[1], dataParts[2]);
                            }
                            if (b1 == false) {
                                out.println("voto nao registado");
                            }
                        } else if (dataParts.length == 2) {
                            while (b1 = rmiServer.inserirVoto(cc, dataParts[1], null) == false && time + System.currentTimeMillis() < time + 30000) {
                                b1 = rmiServer.inserirVoto(cc, dataParts[1], null);
                            }
                            if (b1 == false) {
                                out.println("voto nao registado");
                            }
                        }
                    } else {
                        out.println("operacao inexistente");
                    }
                }
            }catch (SocketTimeoutException e){
                out.flush();
                no.setId(0);
                no.permitonChange();
            } catch (IOException e1){
                e1.printStackTrace();
            }

        }
    }

    class TCPUser {
        protected ArrayList<Info> userList = new ArrayList<Info>();

        public TCPUser(ArrayList<Info> userList) {
            this.userList = userList;
        }

        public ArrayList<Info> getUserList() {
            return userList;
        }

        public void addUser(Socket socket) {

            userList.add(new Info(socket));
        }
    }

    class Info {
        Socket userSocket;
        private int id = 0;
        private boolean permition;

        public Info(Socket socket) {
            this.userSocket = socket;
            permition = false;
        }

        public void permitonChange() {
            if (permition == true) {
                permition = false;
            } else if (permition == false) {
                permition = true;
            }
        }

        public boolean getPermition() {
            return permition;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }


    }

