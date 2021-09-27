/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Hp
 */
public class Proceso {
    private static int numeroProceso = 0;
    private int idProceso;
    private ArrayList tLlegada;
    private ArrayList tComienzo;
    private ArrayList tCPU;
    private ArrayList tFinaliza;
    private ArrayList tEspera;
    private int prioridad = 0;
    private boolean completado;
    private boolean sw;
    
    public Proceso() {}

    public Proceso(int tLlegada, int tCPU) {
        this.numeroProceso++;
        this.idProceso = this.numeroProceso;
        
        this.tLlegada = new ArrayList();
        this.tLlegada.add(tLlegada);
        
        this.tCPU = new ArrayList();
        this.tCPU.add(tCPU);
        
        this.tComienzo = new ArrayList();
        this.tFinaliza = new ArrayList();
        this.tEspera = new ArrayList();
        
        this.sw = false;
    }

    public Proceso(int tLlegada, int tCPU, int prioridad) {
        this.numeroProceso++;
        this.idProceso = this.numeroProceso;
        
        this.tLlegada = new ArrayList();
        this.tLlegada.add(tLlegada);
        
        this.tCPU = new ArrayList();
        this.tCPU.add(tCPU);
        
        this.tComienzo = new ArrayList();
        this.tFinaliza = new ArrayList();
        this.tEspera = new ArrayList();
        
        this.prioridad = prioridad;
        this.completado = false;
        this.sw = false;
    }

    public String getIdProceso() {
        return "" + Character.toString((char)(64+this.idProceso));
    }
    
    public int getNumeroIdProceso() {
        return this.idProceso;
    }

    public int gettLlegada(int option) {
        if(option == 0){
            return (int) this.tLlegada.get(this.tLlegada.size()-1);
        }
        return (int) this.tLlegada.get(0);
    }

    public int gettComienzo() {
        return (int) this.tComienzo.get(this.tComienzo.size()-1);
    }
    
    public String gettComienzoTotal() {
        String str = "";
        for(int  x = 0 ; x < this.tComienzo.size() ; x++) {
            if(x != 0){
                str = str + " - " + this.tComienzo.get(x);
            } else {
                str = str + this.tComienzo.get(x);
            }
        }
        return str;
    }

    public int gettCPU() {
        return (int) this.tCPU.get(this.tCPU.size()-1);
    }
    
    public String gettCPUTotal() {
        String str = "";
        for(int  x = 0 ; x < this.tCPU.size() ; x++) {
            if(x != 0){
                str = str + " - " + this.tCPU.get(x);
            } else {
                str = str + this.tCPU.get(x);
            }
        }
        return str;
    }

    public int gettFinaliza() {
        return (int) this.tFinaliza.get(this.tFinaliza.size()-1);
    }
    
    public String gettFinalizaTotal() {
        String str = "";
        for(int  x = 0 ; x < this.tFinaliza.size() ; x++) {
            if(x != 0){
                str = str + " - " + this.tFinaliza.get(x);
            } else {
                str = str + this.tFinaliza.get(x);
            }
        }
        return str;
    }

    public String gettEspera() {
        String str = "";
        for(int  x = 0 ; x <this.tEspera.size() ; x++) {
            if(x != 0){
                str = str + " + " + this.tEspera.get(x);
            } else {
                str = str + this.tEspera.get(x);
            }
        }
        return str;
    }
    
    public int gettEsperaTotal() {
        int total = 0;
        for(int  x = 0 ; x < this.tEspera.size() ; x++) {
            total += (int) this.tEspera.get(x);
        }
        return total;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean isCompletado() {
        return completado;
    }
   
    public void settLlegada(int tLlegada) {
        this.tLlegada.add(tLlegada);
    }

    public void settComienzo(int tComienzo) {
        this.tComienzo.add(tComienzo);
    }

    public void settCPU(int tCPU) {
        this.tCPU.add(tCPU);
        if(tCPU == 0){
            this.setCompletado(true);
        }
    }

    public void settFinaliza(int tFinaliza) {
        if(!this.sw){
            this.tEspera.add((int)this.tComienzo.get(this.tComienzo.size()-1) - (int)this.tLlegada.get(0));
            this.sw = true;
        } else {
            this.tEspera.add((int)this.tComienzo.get(this.tComienzo.size()-1) - (int)this.tFinaliza.get(this.tFinaliza.size()-1));
        }
        this.tFinaliza.add(tFinaliza);
    }
    
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    private void setCompletado(boolean completado) {
        this.completado = completado;
    }    
    
    public void resetIdProcesos() {
        this.numeroProceso = 0;
    }
}
