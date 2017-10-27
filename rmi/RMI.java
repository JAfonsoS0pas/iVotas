package rmi;

import java.rmi.Remote;
import java.text.ParseException;
import java.util.ArrayList;

public interface RMI extends Remote {

    //Admin Console
    public void registarPessoa();                       //1
    public void alterarDadosPessoais(User user);        //16
    public void criarUni();                             //
    public void criarDep();                             //
    public void alterarDep(String nome);                //
    public void removerDep(String nome);                //
    public void criarEleicao() throws ParseException;                         //3
    public void candidatura(Eleicao ele);               //
    public void gerirMembrosMesa(MesaVoto mesa);        //5
    public void adicionaMesa(Eleicao ele);              //5.1
    public void removerMesa(Eleicao ele);               //5.2
    public void alteraEleicao(Eleicao ele) throws ParseException;             //
    public void eleitoresReal(Eleicao ele);             //
    public void resultados(Eleicao elec);
    public void terminoEleicao(Eleicao elec);           //13

    public ArrayList verificaEleitor(int cc);
    public boolean autenticacao(int username, String password);



    //
    public int getIDMesa();


}

