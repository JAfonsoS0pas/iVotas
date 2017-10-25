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
public class Data {
    protected int dia,mes,ano,hora,min;
    
    
    public Data(int dia, int mes,int ano, int hora, int min)
    {
        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
        this.hora=hora;
        this.min=min;
    }

    public Data(int dia, int mes, int ano)
    {
        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
    }
    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
}
