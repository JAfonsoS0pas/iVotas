package rmi;

import java.net.ServerSocket;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.lang.Object;
import java.rmi.server.*;
import java.io.*;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class RMIServer extends UnicastRemoteObject implements RMI {
    public static RMIServer rmi;
    public static int portRMI=1099;
    public static int portTCP = 6000;
    public static String name = "rmi";
    BufferedReader bf = null;
    private static Registry reg_rmi = null;
    public FicheirosDeObjeto file=new FicheirosDeObjeto();


    //ArrayLists
    public ArrayList<Departamento> departamentos;
    public ArrayList<Eleicao> eleicoes;
    public ArrayList<Universidade> universidades;
    public ArrayList<User>users;//todas as pessoas

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

    //Mudar data
    public void registarPessoa() {
        int telefone, cc,profissao;
        String password, dep,morada;
        Scanner sc=new Scanner(System.in);
        System.out.println("1)Estudante 2)docente ou 3)funcionario? \n");
        profissao=sc.nextInt();
        System.out.println("Cartao de Cidadao: \n");
        cc=sc.nextInt();
        System.out.println("Validade do CC");
        String [] validade=sc.next().split("/");
        Data validadeCC=new Data(Integer.parseInt(validade[0]),Integer.parseInt(validade[1]),Integer.parseInt(validade[2]));
        System.out.println("Password: \n");
        password=sc.next();
        System.out.println("Departamento: \n");
        dep=sc.next();

        System.out.println("Contacto:  \n");
        telefone=sc.nextInt();
        System.out.println("Morada: \n");
        morada=sc.next();

        for(int i=0;i<departamentos.size();i++) {
            if(departamentos.get(i).nome.equals(dep)) {
                User user=new User(profissao ,cc,validadeCC,password,departamentos.get(i), telefone, morada);
                users.add(user);
                departamentos.get(i).pessoas.add(user);
            }

        }

    }

    //Função para listar todas as pessoas
    public void listarUsers() {
        for(int i=0;i<users.size();i++) {
            System.out.println("Nr do CC: "+users.get(i).getCc()+" Tipo de profissao: "+users.get(i).getProfissao()+" Pertence ao departamento de: "+users.get(i).getDep()+"\n");

        }
    }

    public void alterarDadosPessoais() {
        Scanner sc=new Scanner(System.in);
        User user=new User();
        int opcao,ccU;
        listarUsers();
        System.out.println("Quer alterar os dados de que usar?(insira o nr de cc)\n");
        ccU=sc.nextInt();
        for(int u=0;u<users.size();u++) {
            if(users.get(u).getCc()==ccU) {
                user=users.get(u);
            }
        }
        System.out.println("Pretende alterar\n1)Password\n2)Profissao\n3)Numero do CC\n4)Validade do CC\n5)Departamento\n6)Telefone\n7)Morada\n");

        opcao=sc.nextInt();
        switch (opcao) {
            case 1:
                String pass;
                System.out.println("Insira a nova password:\n");
                pass=sc.next();
                user.password=pass;
                break;
            case 2:
                int pro;
                System.out.println("Profissao: \n1)Estudante\n2)Docente\n3)Funcionario\n");
                pro=sc.nextInt();
                user.profissao=pro;
                break;
            case 3:
                int cc;
                System.out.println("Insira o nr de cc:\n");
                cc=sc.nextInt();
                user.cc=cc;
                break;
            case 4:
                System.out.println("Validade do CC(dd/mm/aaaa):\n");
                String [] validade=sc.next().split("/");
                Data validadeCC=new Data(Integer.parseInt(validade[0]),Integer.parseInt(validade[1]),Integer.parseInt(validade[2]));
                user.validadeCC=validadeCC;
                break;
            case 5:
                String dep;
                System.out.println("Nome do departamento:\n");
                dep=sc.next();
                for(int i=0;i<departamentos.size();i++)
                {
                    if(departamentos.get(i).nome.equals(dep))
                    {
                        user.dep=departamentos.get(i);
                    }
                }
                break;
            case 6:
                int tel;
                System.out.println("Telefone:\n");
                tel=sc.nextInt();
                user.telefone=tel;
                break;
            case 7:
                String morada;
                System.out.println("Morada: \n");
                morada=sc.next();
                user.morada=morada;
                break;
            default:
                break;
        }

    }

    public void criarUni() {
        String nome;
        System.out.println("Insira o nome:\n");
        Scanner sc=new Scanner (System.in);
        nome=sc.next();
        Universidade uni=new Universidade(nome);
        if(universidades.contains(uni))
        {
            System.out.println("Essa universidade ja existe!");
        }
        else{
            universidades.add(uni);
        }
    }

    public void criarDep() {
        String uni,dep;
        System.out.println("Insira o nome da Universidade em que quer criar o departamento:\n");
        Scanner sc=new Scanner(System.in);
        uni=sc.next();
        Universidade univ=new Universidade(uni);
        if(universidades.contains(univ))
        {
            System.out.println("Insira o nome do departamento:\n");
            dep=sc.next();
            Departamento depart=new Departamento(uni,dep);
            if(univ.departamentos.contains(depart))
            {
                System.out.println("Esse departamento ja existe!");
            }
            else{
                univ.departamentos.add(depart);
                departamentos.add(depart);
            }
        }
    }

    //Função para listar todos os departamentos
    public void listarDeps() {
        for(int i=0;i<departamentos.size();i++) {
            System.out.println("Departamento de "+departamentos.get(i).getNome()+"\n");
        }
    }

    public void alterarDep() {
        String nome,resposta;
        int respost;
        Scanner sc=new Scanner(System.in);
        listarDeps();
        System.out.println("Escolha o departamento:\n");
        nome=sc.next();
        System.out.println("Alterar Departamento\n-1)Alterar nome do departamento\n-2)Alterar pessoas de um departamento\n");

        respost=sc.nextInt();
        if(respost==2)
        {
            int cc;
            System.out.println("Insira o numero do CC:\n");
            cc=sc.nextInt();
            for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).getNome().equals(nome))
                {
                    for(int j=0;j<departamentos.get(i).getPessoas().size();j++)
                    {
                        if(departamentos.get(i).getPessoas().get(j).getCc()==cc)
                        {
                            System.out.println("Pretente alterar ou remover?\n");
                            resposta=sc.next();
                            if("alterar".equals(resposta))
                            {
                                System.out.println("Alterar:\n-profissao\n-password\n-CC\n-validadeCC\n-telefone\n-morada\n");
                                resposta=sc.next();
                                if(resposta.equals("profissao"))
                                {
                                    System.out.println("Insira a profissao: \n1)Estudante\n2)Docente\n3)Funcionario");
                                    respost=sc.nextInt();
                                    departamentos.get(i).getPessoas().get(j).setProfissao(respost);
                                }
                                else if(resposta.equals("password"))
                                {
                                    System.out.println("Insira a nova password: \n");
                                    resposta=sc.next();
                                    departamentos.get(i).getPessoas().get(j).setPassword(resposta);

                                }
                                else if(resposta.equals("CC"))
                                {
                                    System.out.println("Insira o numero do CC: \n");
                                    respost=sc.nextInt();
                                    departamentos.get(i).getPessoas().get(j).setCc(respost);
                                }
                                else if(resposta.equals("validadeCC"))
                                {
                                    System.out.println("Insira a validade do CC:(dd/mm/aaaa)\n");
                                    String [] vali=sc.next().split("/");
                                    Data valiNew=new Data(Integer.parseInt(vali[0]),Integer.parseInt(vali[1]),Integer.parseInt(vali[2]));
                                    departamentos.get(i).getPessoas().get(j).setValidadeCC(valiNew);

                                }
                                else if(resposta.equals("telefone"))
                                {
                                    System.out.println("Insira o nº de telefone: \n");
                                    respost=sc.nextInt();
                                    departamentos.get(i).getPessoas().get(j).setTelefone(respost);
                                }
                                else if(resposta.equals("morada"))
                                {
                                    System.out.println("Insira a morada: \n");
                                    resposta=sc.next();
                                    departamentos.get(i).getPessoas().get(j).setMorada(resposta);
                                }
                            }
                            else if("remover".equals(resposta))
                            {
                                departamentos.get(i).getPessoas().remove(departamentos.get(i).getPessoas().get(j));
                            }
                        }
                    }
                }
            }
        }
        else if(respost==1)
        {
            System.out.println("Insira o novo nome: \n");
            resposta=sc.next();
            for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).getNome().equals(nome))
                {
                    departamentos.get(i).setNome(resposta);
                    System.out.println("Nome de departamento alterado!\n");
                }
            }
        }
    }

    public void removerDep(){
        String nome;
        Scanner sc=new Scanner(System.in);
        listarDeps();
        System.out.println("Escolha o departamento:\n");
        nome=sc.next();
        for(int i=0;i<departamentos.size();i++)
        {
            if(departamentos.get(i).getNome().equals(nome))
            {
                departamentos.remove(departamentos.get(i));//elimina departamento do array departamentos
                for(int j=0;j<universidades.size();j++)
                {
                    if(universidades.get(j).getDepartamentos().contains(departamentos.get(i)));
                    {
                        universidades.get(j).getDepartamentos().remove(departamentos.get(i));//elimina departamento dentro do array departamentos da universidade

                    }
                }

            }
        }
    }

    public void criarEleicao() throws ParseException{
        int tipo;
        String titulo, descricao,depart,univ;
        Scanner sc=new Scanner(System.in);
        System.out.println("Tipo: ");
        tipo=sc.nextInt();
        String data_inicio, data_fim;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        System.out.println("Data Inicio (dd-mm-yyyy hh:mm): ");
        data_inicio = sc.next();
        Date data = sdf.parse(data_inicio);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);

        System.out.println("Data Fim (dd-mm-yyyy hh:mm): ");
        data_fim = sc.next();
        Date data_f = sdf.parse(data_fim);
        Calendar calendar_f = Calendar.getInstance();
        calendar_f.setTime(data_f);

        System.out.println("Titulo\n");
        titulo=sc.next();
        System.out.println("Descricao\n");
        descricao=sc.next();

        if(tipo==1) {
            System.out.println("Sendo eleicao de nucleos, insira o departamento:\n");
            depart = sc.next();
            for (int i = 0; i < departamentos.size(); i++) {
                if (departamentos.get(i).getNome().equals(depart)) {
                    Eleicao elec = new Eleicao(departamentos.get(i), tipo, data, data_f, titulo, descricao);
                    eleicoes.add(elec);
                    System.out.println("Eleição criada!");
                }
            }
        } else if(tipo==5)
        {
            System.out.println("Sendo eleicao para a direção de uma faculdade, insira a faculdade:\n");
            univ=sc.next();
            for(int i=0;i<universidades.size();i++)
            {
                if(universidades.get(i).getNome().equals(univ))
                {
                    Eleicao elec=new Eleicao(universidades.get(i),tipo,data,data_f,titulo,descricao);
                    eleicoes.add(elec);
                    System.out.println("Eleicao criada!\n");
                }

            }

        }
        else if(tipo==6)
        {
            System.out.println("Sendo eleicao para a direção de um departamento, insira o departamento:\n");
            depart=sc.next();
            for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).getNome().equals(depart))
                {
                    Eleicao elec=new Eleicao(departamentos.get(i),tipo,data,data_f,titulo,descricao);
                    eleicoes.add(elec);
                    System.out.println("Eleicao criada!\n");
                }

            }

        } else {
            Eleicao eleicao = new Eleicao(tipo, data, data_f, titulo, descricao);
            eleicoes.add(eleicao);
        }
    }

    public void candidatura() {
        Eleicao ele = new Eleicao();
        int k, pessoas,numCC,tipoLista;
        String nomeLista, titulo_ele;
        Scanner sc=new Scanner(System.in);
        listarEleicoes();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                ele = eleicoes.get(k);
            }
        }
        System.out.println("Nome da lista:\n-");
        nomeLista=sc.next();
        switch (ele.getTipo()) {
            //eleicao de nucleo de estudantes APENAS ESTUDANTES
            case 1:
                Lista lista=new Lista(nomeLista,1);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).getProfissao()==1 && users.get(j).getCc()==numCC && users.get(j).getDep()==ele.getDep())
                        {
                            lista.getCandidatos().add(users.get(j));
                            System.out.println("Adicionado!");
                        }
                    }
                }
                ele.getListasCandidatas().add(lista);
                break;
            //eleicao de professores
            case 2:
                Lista listaDocente=new Lista(nomeLista,2);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).getCc()==numCC && users.get(j).getProfissao()==2)
                        {
                            listaDocente.getCandidatos().add(users.get(j));
                            System.out.println("Adicionado!");
                            break;
                        }
                    }
                }
                ele.getListasCandidatas().add(listaDocente);
                break;

//eleicao de funcionarios
            case 3:
                Lista listaFunc=new Lista(nomeLista,3);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).getCc()==numCC && users.get(j).getProfissao()==3)
                        {
                            listaFunc.getCandidatos().add(users.get(j));
                            System.out.println("Adicionado!");
                        }
                    }
                }
                ele.getListasCandidatas().add(listaFunc);
                break;

//CONSELHO GERAL listas de 3 tipos
            case 4:

                System.out.println("\nLista de 1=estudantes, 2=funcionarios ou 3=docentes?\n-");//
                tipoLista=sc.nextInt();
                Lista listaConselho=new Lista(nomeLista,tipoLista);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).getCc()==numCC && users.get(j).getProfissao()==tipoLista)
                        {
                            listaConselho.getCandidatos().add(users.get(j));
                            System.out.println("Adicionado");
                        }
                    }
                }
                ele.getListasCandidatas().add(listaConselho);
                break;

            //direçao de faculdade
            case 5:
                Lista listaFac=new Lista(nomeLista,2);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        for(int h=0;k<universidades.size();k++)
                        {
                            if(universidades.get(h).getDepartamentos().contains(users.get(j).getDep()))
                            {
                                if(users.get(j).getCc()==numCC && users.get(j).getProfissao()==2)
                                {
                                    listaFac.getCandidatos().add(users.get(j));
                                    System.out.println("Adicionado");
                                }
                            }
                        }
                    }
                }
                ele.listasCandidatas.add(listaFac);
                break;

            //direçao de departamento
            case 6:
                Lista listaDep=new Lista(nomeLista,2);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).getCc()==numCC && users.get(j).getProfissao()==2 && users.get(j).getDep()==ele.dep)
                        {
                            listaDep.getCandidatos().add(users.get(j));
                            System.out.println("Adicionado");
                        }
                    }
                }

                ele.getListasCandidatas().add(listaDep);
                break;

            default:
                break;
        }
    }

    //Função para listar todas as mesas de voto
    public void listarMesas() {
        for(int i=0;i<eleicoes.size();i++) {
            System.out.println("Eleicao");

        }
    }

    public void gerirMembrosMesa(MesaVoto mesa) {
        int membros=mesa.getMembros().size();
        int opcao, cc;
        System.out.println("Pretende: 1)Adicionar membros a mesa\n2)Remover membros\n");
        Scanner sc=new Scanner(System.in);
        opcao=sc.nextInt();
        if(opcao==1) {
            if(membros<3) {
                System.out.println("Insira o numero do CC do membro que quer inserir");
                cc=sc.nextInt();
                for(int i=0;i<users.size();i++) {
                    if(users.get(i).getCc()==cc) {
                        mesa.getMembros().add(users.get(i));
                        System.out.println("Adicionado!");
                        break;
                    }
                }
            } else if(membros==3){
                System.out.println("Nao pode inserir! ja existem 3 membros");
            }
        } else if(opcao==2) {
            System.out.println("Insira o cc do membro que quer remover:\n");
            cc=sc.nextInt();
            for(int j=0;j<mesa.getMembros().size();j++) {
                if(mesa.getMembros().get(j).getCc()==cc) {
                    mesa.getMembros().remove(mesa.getMembros().get(j));
                    System.out.println("Membro removido!\n");
                    break;
                }
            }
        }
    }

    public void adicionaMesa() {
        Scanner sc=new Scanner(System.in);
        String name,titulo_ele;
        int k;

        listarEleicoes();
        Eleicao ele=new Eleicao();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                ele = eleicoes.get(k);
            }
        }

        System.out.println("Quer inserir uma mesa de voto em que Departamento?\n-");
        name=sc.next();
        for(int i=0;i<departamentos.size();i++)
        {
            if(departamentos.get(i).getNome().equals(name))//se encontrar o departamento
            {
                int id;
                System.out.println("Insira o id da mesa:\n");
                id=sc.nextInt();
                MesaVoto mesa=new MesaVoto(departamentos.get(i),id);
                ele.getMesasVoto().add(mesa);
                System.out.println("Mesa adicionada com sucesso!");
            }
        }
    }

    public void removerMesa() {
        Scanner sc=new Scanner(System.in);
        String name,titulo_ele;
        int k;

        listarEleicoes();
        Eleicao ele=new Eleicao();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                ele = eleicoes.get(k);
            }
        }
        System.out.println("Quer remover mesa de voto de que Departamento?\n-");
        name=sc.next();
        for(int i=0;i<ele.getMesasVoto().size();i++)
        {
            if(ele.getMesasVoto().get(i).getDepartamento().getNome().equals(name))
            {
                ele.getMesasVoto().remove(ele.getMesasVoto().get(i));
                System.out.println("\nRemovida com sucesso!\n");
            }
        }
    }

    public void alteraEleicao() throws ParseException {
        int opcao, k;
        String titulo_ele;
        Scanner sc=new Scanner (System.in);
        listarEleicoes();
        Eleicao ele=new Eleicao();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                ele = eleicoes.get(k);
            }
        }
        String data_inicio, data_fim;
        System.out.println("Pretente alterar:\n1)Titulo\n2)Descricao\n3)Data de inicio\n4)Data de fim");
        opcao=sc.nextInt();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        switch (opcao) {
            case 1:
                int tipo;
                System.out.println("Insira o tipo(1.nucleo, 2.professores, 3.funcionarios, 4.conselho geral)\n");
                tipo=sc.nextInt();
                ele.setTipo(tipo);
                break;
            case 2:
                String titulo;
                System.out.println("Insira o titulo: \n");
                titulo=sc.next();
                ele.setTitulo(titulo);
                break;
            case 3:
                String descri;
                System.out.println("Insira a descricao:\n");
                descri=sc.next();
                ele.setDescricao(descri);
                break;
            case 4:
                System.out.println("Insira a data de inicio(dd/mm/aaaa):\n");
                data_inicio = sc.next();
                Date data = sdf.parse(data_inicio);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                break;
            case 5:
                System.out.println("Insira a data de fim(dd/mm/aaaa):\n");
                data_fim = sc.next();
                Date data_f = sdf.parse(data_fim);
                Calendar calendar_f = Calendar.getInstance();
                calendar_f.setTime(data_f);
                break;
            default:
                break;
        }
    }

    //Saber quantos eleitores votaram em cada mesa de voto
    public void eleitoresReal() {
        int k;
        String titulo_ele;
        Scanner sc=new Scanner (System.in);
        listarEleicoes();
        Eleicao ele=new Eleicao();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                ele = eleicoes.get(k);
            }
        }
        //confirmar se ainda esta a decorrer a eleicao?

        System.out.println("Mesas de Voto - Nr de eleitores que exerceram o voto\n");
        for(int i=0;i<ele.getMesasVoto().size();i++)
        {
            System.out.println("-"+ele.getMesasVoto().get(i).getDepartamento().getNome()+"("+ele.getMesasVoto().get(i).getEleitores().size()+" eleitores)");
        }

    }

    //se for b-branco, n-nulo,outro(confirmar se lista existe e se existir adicionar voto a lista)
    public boolean inserirVoto(int cc, String nomeElec,String voto){
        boolean flag=false;
        for(int i=0;i<eleicoes.size();i++){
            if(eleicoes.get(i).getTitulo().equals(nomeElec))
            {
                if("b".equals(voto))
                {
                    eleicoes.get(i).votosBranco++;
                }
                else if("n".equals(voto) || voto==null)
                {
                    eleicoes.get(i).votosNulo++;
                }
                else{
                    for(int j=0;j<eleicoes.get(i).listasCandidatas.size();j++)
                    {
                        if(eleicoes.get(i).listasCandidatas.get(j).nome.equals(voto))//lista existe bora votaaaaar!!!
                        {
                            for(int k=0;k<users.size();k++)
                            {
                                if(users.get(k).getCc()==cc && users.get(k).getTipo()==eleicoes.get(i).getListasCandidatas().get(j).getTipo())
                                {
                                    eleicoes.get(i).listasCandidatas.get(j).votos++;
                                    eleicoes.get(i).eleitores.add(cc);
                                    flag=true;

                                }
                            }

                        }
                        else{//lista nao exites logo o voto é nulo!!
                            eleicoes.get(i).votosNulo++;
                        }

                    }

                }
            }
        }
        return flag;
    }

    public void votoAntecipado() {
        int  cc;
        String voto,opcao;
        Scanner sc=new Scanner(System.in);
        System.out.println("Insira o seu CC:\n");
        cc=sc.nextInt();
        System.out.println("Em que eleicao quer votar?\n");
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getCc()==cc)//user existe
            {
                for(int j=0;j<eleicoes.size();j++)
                {
                    if(eleicoes.get(j).getTipo()==users.get(i).getTipo())//pode votar nesta eleicao
                    {
                        System.out.println("-"+eleicoes.get(j).getTitulo()+"\n");

                    }
                }

            }
        }
        opcao=sc.next();
        for(int x=0;x<eleicoes.size();x++)
        {
            if(eleicoes.get(x).getTitulo().equals(opcao))
            {
                System.out.println("Insira o seu voto:\n");
                voto=sc.next();
                inserirVoto(cc,eleicoes.get(x).getTitulo(),voto);
            }
        }

    }

    public void resultados() {
        int k;
        String titulo_ele;
        Scanner sc=new Scanner (System.in);
        listarEleicoes();
        Eleicao elec =new Eleicao();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                elec = eleicoes.get(k);
            }
        }
        if(elec.getConclusao()==1)//ja terminou ja pode ser consultada
        {
            int totalVotos=elec.eleitores.size();
            for(int i=0;i<elec.listasCandidatas.size();i++)
            {
                System.out.println("A lista "+elec.listasCandidatas.get(i).nome+" obteve "+elec.listasCandidatas.get(i).votos);//absoluto
                System.out.println(" - Percentagem: "+(elec.listasCandidatas.get(i).votos*100)/totalVotos);
                //percentagem
            }
            System.out.println("\nNumero de votos em branco: "+elec.votosBranco);//absoluto
            System.out.println(" - Percentagem de votos em branco:" +(elec.votosBranco*100)/totalVotos);
            //percentagem

        }
        else//nao esta fechada
        {
            System.out.println("A eleicao ainda nao encerrou");

        }
    }

    public void terminoEleicao(){
        int i, k;
        int dia=0, mes=0, ano=0, dia_t=0, mes_t=0, ano_t=0;
        int hora=0, min=0, hora_t=0, min_t=0;
        String titulo_ele;
        Scanner sc=new Scanner (System.in);
        listarEleicoes();
        Eleicao elec =new Eleicao();
        System.out.println("Qual a eleição que deseja?");
        titulo_ele = sc.next();
        for(k=0;k<eleicoes.size();k++){
            if(eleicoes.get(k).getTitulo().equals(titulo_ele)){
                elec = eleicoes.get(k);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for(i=0; i<eleicoes.size();i++){
            //Data da eleição
            dia = eleicoes.get(i).getDataFim().getDay();
            mes = eleicoes.get(i).getDataFim().getMonth();
            ano = eleicoes.get(i).getDataFim().getYear();
            hora = eleicoes.get(i).getDataFim().getHours();
            min = eleicoes.get(i).getDataFim().getMinutes();
            Calendar now = Calendar.getInstance();
            //Current data
            dia_t = now.get(Calendar.DAY_OF_MONTH);
            mes_t = now.get(Calendar.MONTH)+1;
            ano_t = now.get(Calendar.YEAR);
            hora_t = now.get(Calendar.HOUR_OF_DAY);
            min_t = now.get(Calendar.MINUTE);

            Date data_eleicao = new Date(dia, mes, ano, hora, min);
            Date current_date = new Date(dia_t,mes_t,ano_t,hora_t,min_t);


            if(eleicoes.get(i).getConclusao()==0) {
                if(data_eleicao.compareTo(current_date) > 0){
                    System.out.println("Data de encerramento da eleição ultrapassa a data real");
                    eleicoes.get(i).setConclusao(1);
                }
            }
        }
    }

    //Devolve uma lista de eleições onde o eleitor tem permissão para vontar
    public ArrayList verificaEleitor(int cc){
        int i, j, x,k;
        int flag=0;
        ArrayList<Eleicao> aux=null;
        for(i=0;i<users.size();i++){
            if(cc==users.get(i).getCc()){
                for(j=0; j<eleicoes.size(); j++) {
                    if (users.get(i).getTipo() == eleicoes.get(j).getTipo()) {
                        if ((eleicoes.get(j).getTipo() == 1 ||eleicoes.get(j).getTipo()==6) && users.get(i).getDep() == eleicoes.get(j).getDep() && eleicoes.get(j).getConclusao()==0) {//eleicoes para nucleo ou para direcao de departamento
                            for (x = 0; x < eleicoes.get(j).getEleitores().size(); x++) {
                                if (eleicoes.get(j).getEleitores().get(x) == cc) {
                                    flag = 1;
                                }
                            }
                        }
                        else if (eleicoes.get(j).getTipo() == 5 && eleicoes.get(j).getConclusao()==0) { //quado e eleicao para a direcao de faculdade temos de ver se o docente pertence a faculdade
                            if(eleicoes.get(j).getUni().getDepartamentos().contains(users.get(i).getDep())) {
                                for (x = 0; x < eleicoes.get(j).getEleitores().size(); x++) {
                                    if (eleicoes.get(j).getEleitores().get(x) == cc) {
                                        flag = 1;
                                    }
                                }
                            }
                        }
                    }
                    if (flag == 0) {
                        aux.add(eleicoes.get(j));
                    }
                }
            }
        }
        return aux;
    }

    public void listarEleicoes(){
        int i;
        for(i=0;i<eleicoes.size();i++){
            System.out.println(eleicoes.get(i).getTitulo());
        }
    }

    public boolean autenticacao(int username, String password){
        int i;
        for(i=0;i<=users.size();i++){
            if(users.get(i).getCc() == username && users.get(i).getPassword()==password){
                return true;
            }
        }
        return false;
    }



    public static void main(String args[]) throws InterruptedException, IOException, RemoteException {
        /*System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());*/

             try {
                 primaryRMIServer(portRMI);
             } catch (RemoteException e) {
                 connectBackupRMI(portRMI);
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }


    }

}
