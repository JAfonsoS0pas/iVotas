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
public class Departamento extends Universidade{
    protected String nome;
    protected ArrayList <User> pessoas;
    
    
    public Departamento(String uni,String nome)
    {  
        super(uni);
        this.nome=nome;
    }

    
   
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
    
    public ArrayList<User> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<User> pessoas) {
        this.pessoas = pessoas;
    }
    
    
    
}
