
package sd;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author anacj
 */
public class SD {

    
    public ArrayList<Departamento>departamentos;
    public ArrayList<Eleicao> eleicoes;
    public ArrayList<Eleicao>fechadas;//eleicoes que ja terminaram
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
      
        
       
        {
            
            for(int i=0;i<departamentos.size();i++)
            {
            if(departamentos.get(i).nome.equals(dep))
            {
                 if("estudante".equals(profissao))
                 {
                      User user=new User(2 ,cc,validadeCC,password,departamentos.get(i), telefone, morada);
                     users.add(user);
                     departamentos.get(i).pessoas.add(user);
                     
                 }
                 else if("docente".equals(profissao))
                {   
                    User user=new User(2 ,cc,validadeCC,password,departamentos.get(i), telefone, morada);
                     users.add(user);
                     departamentos.get(i).pessoas.add(user);
                
                
                
                }
                else if("funcionario".equals(profissao))
                {
                     User user=new User(3 ,cc,validadeCC,password,departamentos.get(i), telefone, morada);
                     users.add(user);
                     departamentos.get(i).pessoas.add(user);
            }
                 
   
        }
        
            }}
        
    }
    
    public void alterarDadosPessoais(User user)
    {
        int opcao;
        System.out.println("Pretende alterar\n1)Password\n2)Profissao\n3)Numero do CC\n4)Validade do CC\n5)Departamento\n6)Telefone\n7)Morada\n");
        Scanner sc=new Scanner(System.in);
        opcao=sc.nextInt();
        switch (opcao) {
            case 1:
                String pass;
                System.out.println("Insira a nova password:\n");
                pass=sc.next();
                user.password=pass;
                break;
            case 2:
                int pro;
                System.out.println("Profissao: \n1)Estudante\n2)Docente\n3)Funcionario\n");
                pro=sc.nextInt();
                user.profissao=pro;
                break;
            case 3:
                int cc;
                System.out.println("Insira o nr de cc:\n");
                cc=sc.nextInt();
                user.cc=cc;
                break;
            case 4:
                System.out.println("Validade do CC(dd/mm/aaaa):\n");
                String [] validade=sc.next().split("/");
                Data validadeCC=new Data(Integer.parseInt(validade[0]),Integer.parseInt(validade[1]),Integer.parseInt(validade[2]));
                user.validadeCC=validadeCC;
                break;
            case 5:
                String dep;
                System.out.println("Nome do departamento:\n");
                dep=sc.next();
                for(int i=0;i<departamentos.size();i++)
                {
                    if(departamentos.get(i).nome.equals(dep))
                    {
                        user.dep=departamentos.get(i);
                    }
                }
                break;
            case 6:
                int tel;
                System.out.println("Telefone:\n");
                tel=sc.nextInt();
                user.telefone=tel;
                break;
            case 7:
                String morada;
                System.out.println("Morada: \n");
                morada=sc.next();
                user.morada=morada;
                break;
            default:
                break;
        }
        
    }
    public void criarUni()
    {
        String nome;
        System.out.println("Insira o nome:\n");
        Scanner sc=new Scanner (System.in);
        nome=sc.next();
        Universidade uni=new Universidade(nome);
        if(universidades.contains(uni))
        {
            System.out.println("Essa universidade ja existe!");
        }
        else{
             universidades.add(uni);
        }
       
    }
    public void criarDep()
    {
        String uni,dep;
        System.out.println("Insira o nome da Universidade em que quer criar o departamento:\n");
        Scanner sc=new Scanner(System.in);
        uni=sc.next();
        Universidade univ=new Universidade(uni);
        if(universidades.contains(univ))
        {
            System.out.println("Insira o nome do departamento:\n");
            dep=sc.next();
            Departamento depart=new Departamento(uni,dep);
            if(univ.departamentos.contains(depart))
                {
                    System.out.println("Esse departamento ja existe!");
                }
            else{
                univ.departamentos.add(depart);
                departamentos.add(depart);
            }
            
            
        }
        
    
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
                    break;
                    
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
        String titulo, descricao,depart;
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
        
        if(tipo==1)
        {
            System.out.println("Sendo eleicao de nucleos, insira o departamento:\n");
            depart=sc.next();
            for(int i=0;i<departamentos.size();i++)
            {
                if(departamentos.get(i).nome.equals(depart))
                {
                    Eleicao elec=new Eleicao(departamentos.get(i),tipo,inicio,fim,titulo,descricao);
                    eleicoes.add(elec);
                    break;
                    
                }
               
            }
            
        }
        else{
             Eleicao eleicao=new Eleicao(tipo,inicio,fim,titulo,descricao);
             eleicoes.add(eleicao);
            
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
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).profissao==1 && users.get(j).cc==numCC && users.get(j).dep==ele.dep)
                        {
                            lista.candidatos.add(users.get(j));
                            System.out.println("Adicionado!");
                            break;
                            
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
                            System.out.println("Adicionado");
                            break;
                            
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
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).cc==numCC && users.get(j).profissao==2)
                        {
                            listaDocente.candidatos.add(users.get(j));
                            System.out.println("Adicionado!");
                            break;
                            
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
                    for(int j=0;j<users.size();j++)
                    {
                        if(users.get(j).cc==numCC && users.get(j).profissao==3)
                        {
                            listaFunc.candidatos.add(users.get(j));
                            System.out.println("Adicionado!");
                            break;
                            
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
    public void gerirMembrosMesa(MesaVoto mesa)
    {
        int membros=mesa.membros.size();
        int opcao, cc;
        System.out.println("Pretende: 1)Adicionar membros a mesa\n2)Remover membros\n");
        Scanner sc=new Scanner(System.in);
        opcao=sc.nextInt();
        if(opcao==1)
        {
            if(membros<3)
            {
                
                System.out.println("Insira o numero do CC do membro que quer inserir");
                cc=sc.nextInt();
                for(int i=0;i<users.size();i++)
                {
                    if(users.get(i).cc==cc)
                    {
                        mesa.membros.add(users.get(i));
                        System.out.println("Adicionado!");
                        break;
                    }
                }
            }
            else if(membros==3){
                System.out.println("Nao pode inserir! ja existem 3 membros");
            }
        }
        else if(opcao==2)
        {
            System.out.println("Insira o cc do membro que quer remover:\n");
            cc=sc.nextInt();
            for(int j=0;j<mesa.membros.size();j++)
            {
                if(mesa.membros.get(j).cc==cc)
                {
                    mesa.membros.remove(mesa.membros.get(j));
                    System.out.println("Membro removido!\n");
                    break;
                }
            }
            
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
                break;
                
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
                break;
            }
        }
        
    }
    
    public void votar(Eleicao ele, User eleitor)
    {
        switch (ele.tipo) {
        //nucleo, ou seja, so estudantes
            case 1:
                if(eleitor.profissao==1)
                {
                   
                   
                    
                    
                }
                else
                {
                    //nao pode votar
                }   break;
        //conselhor geral
            case 2:
                if(eleitor.profissao==1)
                {
                    //é estudante portanto so pode votar nas listas de estudantes
                }
                else if(eleitor.profissao==2)
                {
                    //é docente
                }
                else if(eleitor.profissao==3)
                {
                    //é funcionario
                }   break;
        //docentes
            case 3:
                if(eleitor.profissao==2)
                {
                    //pode votar
                }
                else
                {
                    //naopode votar
                }   break;
        //FUNCIONARIOS
            case 4:
                if(eleitor.profissao==3)
                {
                    //pode votar
                }
                else
                {
                    //nao pode votar
                }   break;
            default:
                break;
        }
        
        
    }
    
    public void alteraEleicao(Eleicao ele)
    {
        int opcao;
        System.out.println("Pretente alterar:\n1)Titulo\n2)Descricao\n3)Data de inicio\n4)Data de fim");
        Scanner sc=new Scanner (System.in);
        opcao=sc.nextInt();
        switch (opcao) {
            case 1:
                String titulo;
                System.out.println("Insira o titulo: \n");
                titulo=sc.next();
                ele.titulo=titulo;
                break;
            case 2:
                String descri;
                System.out.println("Insira a descricao:\n");
                descri=sc.next();
                ele.descricao=descri;
                break;
            case 3:
                System.out.println("Insira a data de inicio(dd/mm/aaaa):\n");
                String [] dataIn=sc.next().split("/");
                Data data=new Data(Integer.parseInt(dataIn[0]),Integer.parseInt(dataIn[1]),Integer.parseInt(dataIn[2]));
                ele.dataInit=data;
                
                break;
            case 4:
                System.out.println("Insira a data de inicio(dd/mm/aaaa):\n");
                String [] dataF=sc.next().split("/");
                Data dat=new Data(Integer.parseInt(dataF[0]),Integer.parseInt(dataF[1]),Integer.parseInt(dataF[2]));
                ele.dataInit=dat;
                
                break;
            default:
                break;
        }
        
    }
    
    public void eleitoresReal(Eleicao ele)//Saber quantos eleitores votaram em cada mesa de voto
    {
        //confirmar se ainda esta a decorrer a eleicao?
        System.out.println("Mesas de Voto - Nr de eleitores que exerceram o voto\n");
        for(int i=0;i<ele.mesasVoto.size();i++)
        {
            System.out.println("-"+ele.mesasVoto.get(i).departamento.nome+"("+ele.mesasVoto.get(i).eleitores.size()+" eleitores)");
        }
        
    }
    
    public void resultados(Eleicao elec)
    
    {
        if(fechadas.contains(elec))//ja terminou ja pode ser consultada
        {
            int totalVotos=elec.eleitores.size();
            for(int i=0;i<elec.listasCandidatas.size();i++)
            {
                System.out.println("A lista "+elec.listasCandidatas.get(i).nome+" obteve "+elec.listasCandidatas.get(i).votos);//absoluto
                System.out.println(" - Percentagem: "+(elec.listasCandidatas.get(i).votos*100)/totalVotos);
        //percentagem
            }
            System.out.println("\nNumero de votos em branco: "+elec.votosBranco);//absoluto
            System.out.println(" - Percentagem de votos em branco:" +(elec.votosBranco*100)/totalVotos);
            //percentagem
            
        }
        else//nao esta fechada 
        {
            System.out.println("A eleicao ainda nao encerrou");
            
        }
    }
    
}
