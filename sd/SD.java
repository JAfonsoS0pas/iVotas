
package sd;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author anacj
 */
public class SD {

    
    public ArrayList<Estudante> estudantes;//estudantes registados
    public ArrayList<Docente> docentes;//docentes registados
    public ArrayList<Funcionario>funcionarios;//funcionarios registados
    public ArrayList<Departamento>departamentos;
    public ArrayList<Eleicao> eleicoes;
    public ArrayList<Universidade> universidades;
    public ArrayList<User>users;//todas as pessoas
    public void registarPessoa()
    {
        int telefone, cc;
        String profissao,password, dep,morada;
        Scanner sc=new Scanner(System.in);
        System.out.println("Estudante, docente ou funcionario? \n");
        profissao=sc.next();
        
        System.out.println("Cartao de Cidadao: \n");
        cc=sc.nextInt();
        System.out.println("Validade do CC");
        String [] validade=sc.next().split("/");
        Data validadeCC=new Data(Integer.parseInt(validade[0]),Integer.parseInt(validade[1]),Integer.parseInt(validade[2]));
        System.out.println("Password: \n");
        password=sc.next();
        System.out.println("Departamento: \n");
        dep=sc.next();
        System.out.println("Contacto:  \n");
        telefone=sc.nextInt();
        System.out.println("Morada: \n");
        morada=sc.next();
        
        if("estudante".equals(profissao))
        {
            Estudante aluno=new Estudante(1 ,cc,validadeCC,password,dep, telefone, morada);
            estudantes.add(aluno);
            for(int i=0;i<departamentos.size();i++)
        {
            if(departamentos.get(i).nome.equals(dep))
            {
                departamentos.get(i).pessoas.add(aluno);
            }
        }
   
        }
        if("docente".equals(profissao))
        {
            Docente docente=new Docente(2,cc,validadeCC,password,dep, telefone, morada);
            docentes.add(docente);
            for(int i=0;i<departamentos.size();i++)
            {
            if(departamentos.get(i).nome.equals(dep))
            {
                departamentos.get(i).pessoas.add(docente);
            }
            }
   
        }
        else if("funcionario".equals(profissao))
        {
            Funcionario func=new Funcionario(3 ,cc,validadeCC,password,dep, telefone, morada);
            funcionarios.add(func);
            for(int i=0;i<departamentos.size();i++)
            {
            if(departamentos.get(i).nome.equals(dep))
            {
                departamentos.get(i).pessoas.add(func);
            }
            }
   
        }
        
     
        
    }
    
    public void inserirDep(String uni,String dep)
    {
        Departamento depart=new Departamento(uni,dep);
        departamentos.add(depart);
        
        
        
    }
    public void alterarDep(String nome)
    {
        String resposta;
        int respost;
        System.out.println("Alterar Departamento\n-1)Alterar nome do departamento\n-2)Alterar pessoas de um departamento\n");
        Scanner sc=new Scanner(System.in);
        respost=sc.nextInt();
        if(respost==2)
        {
            int cc;
            System.out.println("Insira o numero do CC:\n");
            cc=sc.nextInt();
            for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).nome.equals(nome))
                {
                    for(int j=0;j<departamentos.get(i).pessoas.size();j++)
                    {
                        if(departamentos.get(i).pessoas.get(j).cc==cc)
                        {                          
                            System.out.println("Pretente alterar ou remover?\n");
                            resposta=sc.next();
                            if("alterar".equals(resposta))
                            {
                                System.out.println("Alterar:\n-profissao\n-password\n-CC\n-validadeCC\n-telefone\n-morada\n");
                                resposta=sc.next();
                                if(resposta.equals("profissao"))
                                {
                                    int pro=0;
                                    System.out.println("Insira a profissao: \n");
                                    resposta=sc.next();
                                    if(resposta=="estudante")
                                    {
                                        pro=1;
                                    }
                                    else if(resposta=="docente")
                                    {
                                        pro=2;
                                    }
                                    else if(resposta=="funcionario")
                                    {
                                        pro=3;
                                    }
                                    departamentos.get(i).pessoas.get(j).profissao=pro;
                                          
                                }
                                else if(resposta.equals("password"))
                                {
                                    System.out.println("Insira a nova password: \n");
                                    resposta=sc.next();
                                    departamentos.get(i).pessoas.get(j).password=resposta;
                                    
                                }
                                else if(resposta.equals("CC"))
                                {
                                    //int respost;
                                    System.out.println("Insira o numero do CC: \n");
                                    respost=sc.nextInt();
                                    departamentos.get(i).pessoas.get(j).cc=respost;
                                }
                                else if(resposta.equals("validadeCC"))
                                {
                                    
                                    System.out.println("Insira a validade do CC:(dd/mm/aaaa)\n");
                                    String [] vali=sc.next().split("/");
                                    Data valiNew=new Data(Integer.parseInt(vali[0]),Integer.parseInt(vali[1]),Integer.parseInt(vali[2]));
                                    departamentos.get(i).pessoas.get(j).validadeCC=valiNew;
                                    
                                }
                                else if(resposta.equals("telefone"))
                                {                                    
                                    System.out.println("Insira o nº de telefone: \n");
                                    respost=sc.nextInt();
                                    departamentos.get(i).pessoas.get(j).telefone=respost;
                                }
                                else if(resposta.equals("morada"))
                                {                                   
                                    System.out.println("Insira a morada: \n");
                                    resposta=sc.next();
                                    departamentos.get(i).pessoas.get(j).morada=resposta;
                                }
                            }
                            else if("remover".equals(resposta))
                            {
                                departamentos.get(i).pessoas.remove(departamentos.get(i).pessoas.get(j));
                                
                            }
                        }
                            
                    }
                }
            }
        }
        else if(respost==1)
        {
            System.out.println("Insira o novo nome: ");
            resposta=sc.next();
            for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).nome.equals(nome))
                {
                    departamentos.get(i).nome=resposta;
                    
                }
            }
            
            
            
        }
      
        
        
    }
    public void removerDep(String nome){
        for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).nome.equals(nome))
                {
                    departamentos.remove(departamentos.get(i));//elimina departamento do array departamentos
                    for(int j=0;j<universidades.size();j++)
                    {
                        if(universidades.get(j).departamentos.contains(departamentos.get(i)));
                        {
                            universidades.get(j).departamentos.remove(departamentos.get(i));//elimina departamento dentro do array departamentos da universidade
                            
                        }
                    }
                    
                }
            }
        
    }
    public void criarEleicao()
    {
        int tipo;
        String titulo, descricao;
        Scanner sc=new Scanner(System.in);
        System.out.println("Tipo\n");
        tipo=sc.nextInt();
        System.out.println("Data de Inicio: ");
        String [] dataInit=sc.next().split("/");
        System.out.println("\nHora de Inicio: ");
        String [] horaI=sc.next().split(":");
        Data inicio=new Data(Integer.parseInt(dataInit[0]),Integer.parseInt(dataInit[1]),Integer.parseInt(dataInit[2]),Integer.parseInt(horaI[0]),Integer.parseInt(horaI[1]));
         System.out.println("Data de Encerramento: ");
        String [] dataFim=sc.next().split("/");
        System.out.println("\nHora de Encerramento: ");
        String [] horaF=sc.next().split(":");
        Data fim=new Data(Integer.parseInt(dataFim[0]),Integer.parseInt(dataFim[1]),Integer.parseInt(dataFim[2]),Integer.parseInt(horaF[0]),Integer.parseInt(horaF[1]));
        System.out.println("Titulo\n");
        titulo=sc.next();
        System.out.println("Descricao\n");
        descricao=sc.next();
        
        Eleicao eleicao=new Eleicao(tipo,inicio,fim,titulo,descricao);
        eleicoes.add(eleicao);
        if(tipo==1)
        {
           
          //  eleicao.eleitores=estudantes.clone();
        }
      
        
    }
    
    
    public void candidatura(Eleicao ele)
    {
        int pessoas,numCC,tipoLista;
        String nomeLista;
        Scanner sc=new Scanner(System.in);
        System.out.println("Nome da lista:\n-");
        nomeLista=sc.next();
        switch (ele.tipo) {
        //eleicao de nucleo de estudantes APENAS ESTUDANTES
            case 1:
                
                Lista lista=new Lista(nomeLista,1);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<estudantes.size();j++)
                    {
                        if(estudantes.get(j).cc==numCC)
                        {
                            lista.candidatos.add(estudantes.get(j));
                            
                        }
                        else
                        {
                            System.out.println("Estudante não encontrado!");
                        }
                    }
                }
                ele.listasCandidatas.add(lista);
                lista.participacoes.add(ele);
                break;
        //CONSELHO GERAL listas de 3 tipos
            case 2:
                System.out.println("\nLista de 1=estudantes, 2=funcionarios ou 3=docentes?\n-");//
                tipoLista=sc.nextInt();
                Lista listaConselho=new Lista(nomeLista,tipoLista);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).cc==numCC && users.get(j).profissao==tipoLista)
                        {
                            listaConselho.candidatos.add(users.get(j));
                            
                        }
                        else
                        {
                            System.out.println("Pessoa não encontrado ou com profissão não correspondente ao tipo de lista!");
                        }
                    }
                }
                ele.listasCandidatas.add(listaConselho);
                listaConselho.participacoes.add(ele);
                
                
                break;
        //eleicao de professores
            case 3:
                Lista listaDocente=new Lista(nomeLista,2);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<docentes.size();j++)
                    {
                        if(docentes.get(j).cc==numCC)
                        {
                            listaDocente.candidatos.add(docentes.get(j));
                            
                        }
                        else
                        {
                            System.out.println("Docente não encontrado!");
                        }
                    }
                }
                ele.listasCandidatas.add(listaDocente);
                listaDocente.participacoes.add(ele);
                break;

        //eleicao de funcionarios
            case 4:
                 Lista listaFunc=new Lista(nomeLista,3);
                System.out.println("Quantas pessoas terá a lista candidata? ");
                pessoas=sc.nextInt();
                for(int i=0;i<pessoas;i++)
                {
                    System.out.println("Insira o numero do CC:");
                    numCC=sc.nextInt();
                    for(int j=0;j<funcionarios.size();j++)
                    {
                        if(funcionarios.get(j).cc==numCC)
                        {
                            listaFunc.candidatos.add(funcionarios.get(j));
                            
                        }
                        else
                        {
                            System.out.println("Funcionario não encontrado!");
                        }
                    }
                }
                ele.listasCandidatas.add(listaFunc);
                listaFunc.participacoes.add(ele);
                break;

            default:
                break;
        }
          
        
        
            
       
    }
    
    public void adicionaMesa(Eleicao ele)
    {
        String name;
        Scanner sc=new Scanner(System.in);
        System.out.println("Quer inserir uma mesa de voto em que Departamento?\n-");
        name=sc.next();
        for(int i=0;i<departamentos.size();i++)
        {
            if(departamentos.get(i).nome.equals(name))//se encontrar o departamento
            {
                MesaVoto mesa=new MesaVoto(departamentos.get(i));
                ele.mesasVoto.add(mesa);
                System.out.println("Mesa adicionada com sucesso!");
                
            }
            else
            {
                System.out.println("Departamento não existe!");
            }
        }
        
        
    }
    
    public void removerMesa(Eleicao ele)
    {
        String name;
        Scanner sc=new Scanner(System.in);
        System.out.println("Quer remover mesa de voto de que Departamento?\n-");
        name=sc.next();
        for(int i=0;i<ele.mesasVoto.size();i++)
        {
            if(ele.mesasVoto.get(i).departamento.nome.equals(name))
            {
                ele.mesasVoto.remove(ele.mesasVoto.get(i));
                System.out.println("\nRemovida com sucesso!\n");
            }
            else
            {
                System.out.println("\nDepartamento não contém mesa de voto!\n");
            }
        }
        
    }
    
    public void votar(Eleicao ele)
    {
        //cada eleicao tem uma lista d epessoas que podem votar
        //quando votarem retiramos dessa lista
        //para um eleitor poder fazer login procuramos na lista eleitores a existencia do CC dele, se ainda estiver pode fazer login
        //se nao, é porque ja votou e nao pode entrar
        
    }
    
}
