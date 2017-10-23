
package sd;

import java.util.ArrayList;

/**
 *
 * @author anacj
 */
public class Eleicao {
    
    protected int tipo; //1=nucleo, 2=conselho geral, de 3=professores ou de 4=funcionarios
    protected Data dataInit;
    protected Data dataFim;
    protected String titulo;
    protected String descricao;
    protected ArrayList<Lista> listasCandidatas;
    protected ArrayList <MesaVoto> mesasVoto;
    protected ArrayList <Integer> votos;
    protected ArrayList <User> eleitores;//pessoas que podem votar

    
    public Eleicao(int tipo, Data dataInit, Data dataFim, String titulo, String descricao){//falta acrescentar a data
        
        this.tipo=tipo;
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.titulo=titulo;
        this.descricao=descricao;
        
    }
    
    public void insereLista(Lista lista)
    {
        listasCandidatas.add(lista);
    }
    public void insereMesa(MesaVoto mesa)
    {
        mesasVoto.add(mesa);
        
    }
    public void removerMesa(MesaVoto mesa)
    {
        mesasVoto.remove(mesa);
        
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
