
package sd;

import java.util.ArrayList;

/**
 *
 * @author anacj
 */
public class Eleicao {
    
    protected int tipo; //1=nucleo, 2=conselho geral, de 3=professores ou de 4=funcionarios
    protected Departamento dep;//para o caso dos nucleos, saber se os candidatos pertencem aquele departamento
    protected Data dataInit;
    protected Data dataFim;
    protected String titulo;
    protected String descricao;
    protected ArrayList<Lista> listasCandidatas;
    protected ArrayList <MesaVoto> mesasVoto;    
    protected ArrayList <User> eleitores;//pessoas que ja votaram votar
    protected int votosBranco;
    protected int votosNulo;

    
    public Eleicao(int tipo, Data dataInit, Data dataFim, String titulo, String descricao){
        
        this.tipo=tipo;
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.titulo=titulo;
        this.descricao=descricao;
        
    }
    public Eleicao(Departamento dep,int tipo, Data dataInit, Data dataFim, String titulo, String descricao){
        this.dep=dep;
        this.tipo=tipo;
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.titulo=titulo;
        this.descricao=descricao;
        
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
   


    public ArrayList<User> getEleitores() {
        return eleitores;
    }

    public void setEleitores(ArrayList<User> eleitores) {
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

   
    
    public Data getDataInit() {
        return dataInit;
    }

    public void setDataInit(Data dataInit) {
        this.dataInit = dataInit;
    }

    public Data getDataFim() {
        return dataFim;
    }

    public void setDataFim(Data dataFim) {
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
