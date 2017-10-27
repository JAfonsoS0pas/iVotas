package rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;

public interface RMI extends Remote {

    //Admin Console
    public void registarPessoa() throws RemoteException;
    public void alterarDadosPessoais() throws RemoteException;
    public void criarUni() throws RemoteException;
    public void criarDep() throws RemoteException;
    public void alterarDep() throws ParseException, RemoteException;
    public void removerDep()throws RemoteException;
    public void criarEleicao() throws ParseException, RemoteException;
    public void candidatura()throws RemoteException;
    public void gerirMembrosMesa() throws RemoteException;
    public void adicionaMesa() throws RemoteException;
    public void removerMesa() throws RemoteException;
    public void alteraEleicao() throws RemoteException;
    public void eleitoresReal() throws RemoteException;
    public boolean inserirVoto(int cc, String nomeElec,String voto) throws RemoteException;
    public void votoAntecipado() throws RemoteException;
    public void resultados() throws RemoteException;
    public void terminoEleicao() throws RemoteException;
    public void estadoMesas() throws RemoteException;

    public ArrayList verificaEleitor(int cc) throws RemoteException;
    public int autenticacao(int username, String password) throws RemoteException;
    public void loadFiles() throws IOException, RemoteException;
    public void createObject() throws ParseException, RemoteException;
    public void save() throws IOException, RemoteException;




}

