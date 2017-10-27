package rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.text.ParseException;
import java.util.ArrayList;

public interface RMI extends Remote {

    //Admin Console
    public void registarPessoa() throws ParseException;
    public void alterarDadosPessoais() throws ParseException;
    public void criarUni();
    public void criarDep();
    public void alterarDep() throws ParseException;
    public void removerDep();
    public void criarEleicao() throws ParseException;
    public void candidatura();
    public void gerirMembrosMesa();
    public void adicionaMesa();
    public void removerMesa();
    public void alteraEleicao() throws ParseException;
    public void eleitoresReal();
    public boolean inserirVoto(int cc, String nomeElec,String voto);
    public void votoAntecipado();
    public void resultados();
    public void terminoEleicao();
    public void estadoMesas();

    public ArrayList verificaEleitor(int cc);
    public boolean autenticacao(int username, String password);
    public void loadFiles() throws IOException;
    public void createObject() throws ParseException;
    public void save() throws IOException;




}

