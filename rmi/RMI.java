package rmi;

import java.rmi.Remote;
import java.text.ParseException;
import java.util.ArrayList;

public interface RMI extends Remote {

    //Admin Console
    public void registarPessoa();
    public void alterarDadosPessoais();
    public void criarUni();
    public void criarDep();
    public void alterarDep();
    public void removerDep();
    public void criarEleicao() throws ParseException;
    public void candidatura();
    public void gerirMembrosMesa(MesaVoto mesa);
    public void adicionaMesa();
    public void removerMesa();
    public void alteraEleicao() throws ParseException;
    public void eleitoresReal();
    public boolean inserirVoto(int cc, String nomeElec,String voto);
    public void votoAntecipado();
    public void resultados();
    public void terminoEleicao();

    public ArrayList verificaEleitor(int cc);
    public boolean autenticacao(int username, String password);



}

