/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.util.ArrayList;

/**
 *
 * @author anacj
 */
public class Lista {
    protected String nome;
    protected int tipo;//1=estudantes, 2=docentes ou 3=funcionarios
    protected ArrayList<User> candidatos;
    protected int votos;
    
    
    public Lista(String nome, int tipo)
    {
        this.nome=nome;
        this.tipo=tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<User> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(ArrayList<User> candidatos) {
        this.candidatos = candidatos;
    }
    
    
    
    
}
