
package rmi;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author anacj
 */
public class Eleicao {
    
    protected int tipo; //1=nucleo, 2=professores ou de 3=funcionarios,4=conselho geral,5=direçao faculdade, 6=direcao departamento
    protected Universidade uni;//para o caso da eleicao para direçao de faculdade
    protected Departamento dep;//para o caso dos nucleos, saber se os candidatos pertencem aquele departamento
    protected Date dataInit;
    protected Date dataFim;
    protected String titulo;
    protected int conclusao=1;
    protected String descricao;
    protected ArrayList<Lista> listasCandidatas;
    protected ArrayList <MesaVoto> mesasVoto;    
    protected ArrayList <Integer> eleitores;//pessoas que ja votaram votar
    protected int votosBranco;
    protected int votosNulo;

    public Eleicao(){}
    public Eleicao(int tipo, Date dataInit, Date dataFim, String titulo, String descricao){
        
        this.tipo=tipo;
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.titulo=titulo;
        this.descricao=descricao;
        
    }

    public Eleicao(Departamento dep, int tipo, Date dataInit, Date dataFim, String titulo, String descricao){
        this.dep=dep;
        this.tipo=tipo;
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.titulo=titulo;
        this.descricao=descricao;
    }


    public Eleicao(Universidade uni, int tipo, Date dataInit, Date dataFim, String titulo, String descricao){
        this.uni=uni;
        this.tipo=tipo;
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.titulo=titulo;
        this.descricao=descricao;
    }

    public int getConclusao() {
        return conclusao;
    }

    public void setConclusao(int conclusao) {
        this.conclusao = conclusao;
    }

    public Departamento getDep() {
        return dep;
    }

    public void setDep(Departamento dep) {
        this.dep = dep;
    }
    public void insereLista(Lista lista)
    {
        listasCandidatas.add(lista);
    }

    public Universidade getUni() {
        return uni;
    }

    public void setUni(Universidade uni) {
        this.uni = uni;
    }

    public ArrayList<Integer> getEleitores() {
        return eleitores;
    }

    public void setEleitores(ArrayList<Integer> eleitores) {
        this.eleitores = eleitores;
    }

    public int getVotosBranco() {
        return votosBranco;
    }

    public void setVotosBranco(int votosBranco) {
        this.votosBranco = votosBranco;
    }

    public int getVotosNulo() {
        return votosNulo;
    }

    public void setVotosNulo(int votosNulo) {
        this.votosNulo = votosNulo;
    }
    
    public ArrayList<MesaVoto> getMesasVoto() {
        return mesasVoto;
    }

    public void setMesasVoto(ArrayList<MesaVoto> mesasVoto) {
        this.mesasVoto = mesasVoto;
    }
    
    public ArrayList<Lista> getListasCandidatas() {
        return listasCandidatas;
    }

    public void setListasCandidatas(ArrayList<Lista> listasCandidatas) {
        this.listasCandidatas = listasCandidatas;
    }

   
    
    public Date getDataInit() {
        return dataInit;
    }

    public void setDataInit(Date dataInit) {
        this.dataInit = dataInit;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
}
