
package sd;

import java.io.Serializable;

/**
 *
 * @author anacj
 */
public class User implements Serializable{
    protected int profissao; //1=estudante, 2=docente, 3=funcionario
    protected int cc;
    protected String user;//perceber se é admin, eleitor,...
    protected String password;
    protected Departamento dep;
    protected int telefone;
    protected String morada;

    protected Data validadeCC;
    
    
    public User(int profissao, int cc, Data validadeCC, String password, Departamento dep, int telefone, String morada)
    {
        this.profissao=profissao;
        this.cc=cc;
        this.validadeCC=validadeCC;
        this.password=password;
        this.dep=dep;
        this.telefone=telefone;
        this.morada=morada;
    }

    public int getTipo() {
        return profissao;
    }

    public void setTipo(int profissao) {
        this.profissao = profissao;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }
    
    
}
