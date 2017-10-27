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
public class MesaVoto {
    protected int id;
    protected Departamento departamento; //onde esta localizada
    protected ArrayList<User>eleitores;
    protected ArrayList<User>membros;//tem que ter 3 membros

    public MesaVoto(){}
    public MesaVoto(Departamento departamento, int id) {
        this.id = id;
        this.departamento=departamento;
    }
    
    

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getEleitores() {
        return eleitores;
    }

    public void setEleitores(ArrayList<User> eleitores) {
        this.eleitores = eleitores;
    }

    public ArrayList<User> getMembros() {
        return membros;
    }

    public void setMembros(ArrayList<User> membros) {
        this.membros = membros;
    }
    
    
    
    
}
