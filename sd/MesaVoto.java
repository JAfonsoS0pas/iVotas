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
public class MesaVoto {
    
    protected Departamento departamento; //onde esta localizada
    protected ArrayList<User>eleitores;
    protected ArrayList<User>membros;//tem que ter 3 membros
    
    public MesaVoto(Departamento departamento)
    {
        this.departamento=departamento;
    }
    
    

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
