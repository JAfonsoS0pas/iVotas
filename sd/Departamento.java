/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd;

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

    
    public void inserePessoa(User pessoa)
    {
        pessoas.add(pessoa);
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<User> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<User> pessoas) {
        this.pessoas = pessoas;
    }
    
    
    
}
