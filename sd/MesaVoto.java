/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd;

/**
 *
 * @author anacj
 */
public class MesaVoto {
    
    protected Departamento departamento; //onde esta localizada
    
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
    
    
    
    
}
