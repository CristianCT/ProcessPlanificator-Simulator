/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Hp
 */
public class Proceso {
    private static int numeroProceso = 0;
    private int idProceso;
    private int tLlegada = 0;
    private int tComienzo = 0;
    private int tCPU = 0;
    private int tFinaliza = 0;
    private int tEspera = 0;
    private int prioridad = 0;
    private boolean completado;
    private boolean sw;
    
    public Proceso() {}

    public Proceso(int tLlegada, int tCPU) {
        this.numeroProceso++;
        this.idProceso = this.numeroProceso;
        this.tLlegada = tLlegada;
        this.tCPU = tCPU;
        this.sw = false;
    }

    public Proceso(int tLlegada, int tCPU, int prioridad) {
        this.numeroProceso++;
        this.idProceso = this.numeroProceso;
        this.tLlegada = tLlegada;
        this.tCPU = tCPU;
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

    public int gettLlegada() {
        return tLlegada;
    }

    public int gettComienzo() {
        return tComienzo;
    }

    public int gettCPU() {
        return tCPU;
    }

    public int gettFinaliza() {
        return tFinaliza;
    }

    public int gettEspera() {
        return tEspera;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean isCompletado() {
        return completado;
    }
   
    public void settLlegada(int tLlegada) {
        this.tEspera += (tLlegada - this.tLlegada);
        this.tLlegada = tLlegada;
    }

    public void settComienzo(int tComienzo) {
        this.tComienzo = tComienzo;
        if(!this.sw){
            this.tEspera += (this.tComienzo - this.tLlegada);
            this.sw = true;
        }
        System.out.println(this.tEspera + " - " + this.tFinaliza + " - " + this.tComienzo);
    }

    public void settCPU(int tCPU) {
        this.tCPU = tCPU;
        if(this.tCPU == 0){
            this.setCompletado(true);
        }
    }

    public void settFinaliza(int tFinaliza) {
        this.tFinaliza = tFinaliza;
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
