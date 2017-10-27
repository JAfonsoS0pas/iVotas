package sd;

import rmi.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author anacj
 */
public class Admin {
    private static int rmiPort = 1099;
    private static String rmiName = "rmi";
    private static RMI rmiServer = null;


    public static void main(String[] args) throws RemoteException, ParseException {

        try {
            rmiServer = (RMI) LocateRegistry.getRegistry(rmiPort).lookup(rmiName);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
            System.out.println("Menu:");
            System.out.println("1 - Registar Pessoas");
            System.out.println("2 - Gerir departamentos e faculdades");
            System.out.println("3 - Criar eleição");
            System.out.println("4 - Gerir listas de candidatos a uma eleição");
            System.out.println("5 - Gerir mesas de voto");
            System.out.println("6 - Alterar propriedades de uma eleição");
            System.out.println("7 - Informação do local em que cada eleitor votou");
            System.out.println("8 - Visualizar estado das mesas de voto ");
            System.out.println("9 - Visualizar eleitores em tempo real");
            System.out.println("10 - Término da eleição");
            System.out.println("11 - Consultar resultados detalhados de eleições passadas");
            System.out.println("12 - Voto antecipado");
            System.out.println("13 - Alterar dados pessoais");
            System.out.println("14 - Gerir membros de cada mesa de voto");
            System.out.println("15 - Eleições para direção de departamento e de faculdade");
            System.out.printf("Introduza a opção: ");
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    rmiServer.registarPessoa();
                    break;
                case 2:
                    int opc_2;
                    do {
                        String nomeDep;
                        System.out.println("O que deseja aplicar? ");
                        System.out.println("1- Criar");
                        System.out.println("2- Alterar");
                        System.out.println("3- Remover");
                        System.out.printf("Introduza a opção: ");
                        opc_2 = sc.nextInt();
                        switch(opc_2){
                            case 1:
                                rmiServer.criarDep();
                                break;
                            case 2:
                                System.out.println("Nome do departamento a alterar: ");
                                nomeDep = sc.next();
                                rmiServer.alterarDep(nomeDep);
                                break;
                            case 3:
                                System.out.println("Nome do departamento a alterar: ");
                                nomeDep = sc.next();
                                rmiServer.removerDep(nomeDep);
                                break;
                            default:
                                break;
                        }
                    }while(opc_2!=0);
                    break;
                case 3:
                    rmiServer.criarEleicao();
                    break;
                case 4:
                    rmiServer.candidatura();
                    break;
                case 5:
                    int opc_3;
                    do {
                        System.out.printf("O que deseja aplicar?");
                        System.out.println("1- Adicionar mesa de voto");
                        System.out.println("2- Remover mesa de voto");
                        System.out.printf("Introduza a opção: ");
                        opc_3 = sc.nextInt();
                        switch (opc_3){
                            case 1:
                                rmiServer.adicionaMesa();
                                break;
                            case 2:
                                rmiServer.removerMesa();
                                break;
                            default:
                                break;
                        }
                    } while(opc_3!=0);
                    break;
                case 6:
                    rmiServer.alteraEleicao();
                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:
                    rmiServer.eleitoresReal();
                    break;
                case 10:
                    rmiServer.terminoEleicao();
                    break;
                case 11:
                    rmiServer.resultados();
                    break;
                case 12:

                    break;
                case 13:
                    rmiServer.alterarDadosPessoais();
                    break;
                case 14:
                    rmiServer.gerirMembrosMesa();
                    break;
                case 15:

                    break;
                default:
                    break;
            }
        } while(opc != 0);
    }

    

    

    
}
