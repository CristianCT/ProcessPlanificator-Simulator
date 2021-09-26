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
public class Planificador {
    
    private int tiempo;
    private ArrayList<Proceso> procesos;

    public Planificador(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
        this.ordenarPorTiempoLlegada();
        this.tiempo = this.procesos.get(0).gettLlegada();
    }
    
    public ArrayList<Proceso> getModeloFIFO() {
        for(int x = 0 ; x < this.procesos.size() ; x++){
            if(x == 0){
                this.tiempo = this.procesos.get(x).gettLlegada();
            }
            this.procesos.get(x).settComienzo(tiempo);
            this.tiempo += this.procesos.get(x).gettCPU();
            this.procesos.get(x).settFinaliza(this.procesos.get(x).gettComienzo()+this.procesos.get(x).gettCPU());
        }
        this.ordenarPorId();
        return this.procesos;
    }
    
    public ArrayList<Proceso> getModeloSJF() {
        for(int x = 0 ; x < this.procesos.size() ; x++){
            if(x != 0){
                this.procesos.get(x).settComienzo(tiempo);
                this.procesos.get(x).settFinaliza(this.procesos.get(x).gettComienzo()+this.procesos.get(x).gettCPU());
                this.tiempo += this.procesos.get(x).gettCPU();
            } else {
                this.tiempo = this.procesos.get(x).gettLlegada();
                this.procesos.get(x).settComienzo(tiempo);
                this.procesos.get(x).settFinaliza(this.procesos.get(x).gettComienzo()+this.procesos.get(x).gettCPU());
                this.tiempo += this.procesos.get(x).gettCPU();
                this.ordenarPorProcesoCorto();
            }
        }
        this.ordenarPorId();
        return this.procesos;
    }

    public ArrayList<Proceso> getModeloSRTF() {
        
        int x = 1;
        int indexEjecucion = 0;
        do{
            if((this.tiempo + this.procesos.get(indexEjecucion).gettCPU()) >= this.procesos.get(x).gettLlegada() && this.procesos.get(indexEjecucion).gettCPU() > this.procesos.get(x).gettCPU() && !this.procesos.get(x).isCompletado()){
                this.procesos.get(indexEjecucion).settComienzo(this.tiempo);
                this.procesos.get(indexEjecucion).settFinaliza(this.procesos.get(x).gettLlegada());
                this.procesos.get(indexEjecucion).settCPU(this.procesos.get(indexEjecucion).gettCPU() - (this.procesos.get(x).gettLlegada() - this.tiempo));
                this.tiempo =  this.procesos.get(x).gettLlegada();
                indexEjecucion = x;
                x = 0;
            } else {
                if(x == this.procesos.size()-1){
                    this.procesos.get(indexEjecucion).settComienzo(this.tiempo);
                    this.procesos.get(indexEjecucion).settFinaliza(this.tiempo + this.procesos.get(indexEjecucion).gettCPU());
                    this.procesos.get(indexEjecucion).settCPU(0);
                    this.tiempo =  this.procesos.get(indexEjecucion).gettFinaliza();
                    indexEjecucion = this.getIncompletedShortTime();
                    if(indexEjecucion != -1){
                        this.procesos.get(indexEjecucion).settLlegada(this.tiempo);
                    }
                    x = indexEjecucion;
                } else {
                    x++;
                }
            }
        }while(!this.areProcesosCompletados());
        
        this.ordenarPorId();
        return this.procesos;
    }

    public ArrayList<Proceso> getModeloExpropiativo() {
        int x = 1;
        int indexEjecucion = 0;
        do{
            if((this.tiempo + this.procesos.get(indexEjecucion).gettCPU()) >= this.procesos.get(x).gettLlegada() && this.procesos.get(indexEjecucion).getPrioridad() > this.procesos.get(x).getPrioridad() && !this.procesos.get(x).isCompletado()){
                this.procesos.get(indexEjecucion).settComienzo(this.tiempo);
                this.procesos.get(indexEjecucion).settFinaliza(this.procesos.get(x).gettLlegada());
                this.procesos.get(indexEjecucion).settCPU(this.procesos.get(indexEjecucion).gettCPU() - (this.procesos.get(x).gettLlegada() - this.tiempo));
                this.tiempo =  this.procesos.get(x).gettLlegada();
                indexEjecucion = x;
                x = 0;
            } else {
                if(x == this.procesos.size()-1){
                    this.procesos.get(indexEjecucion).settComienzo(this.tiempo);
                    this.procesos.get(indexEjecucion).settFinaliza(this.tiempo + this.procesos.get(indexEjecucion).gettCPU());
                    this.procesos.get(indexEjecucion).settCPU(0);
                    this.tiempo =  this.procesos.get(indexEjecucion).gettFinaliza();
                    indexEjecucion = this.getIncompletedPriority();
                    if(indexEjecucion != -1){
                        this.procesos.get(indexEjecucion).settLlegada(this.tiempo);
                    }
                    x = indexEjecucion;
                } else {
                    x++;
                }
            }
        }while(!this.areProcesosCompletados());
        this.ordenarPorId();
        return this.procesos;
    };
    
    private void ordenarPorProcesoCorto(){
        
        Proceso auxiliar;
        int tiempoAuxiliar = this.tiempo;
        int bandera=1;
        for(int x = 1 ; x < this.procesos.size(); x++){
            for(int y = x + 1 ; y < this.procesos.size() ; y++){
                if(this.procesos.get(y).gettCPU() <= this.procesos.get(x).gettCPU() &&  this.procesos.get(y).gettLlegada() <= tiempoAuxiliar){
                    auxiliar = this.procesos.get(x);
                    this.procesos.set(x, this.procesos.get(y));
                    this.procesos.set(y, auxiliar);
                    bandera = x;
                    tiempoAuxiliar += this.procesos.get(x).gettCPU();
                }
            }
            tiempoAuxiliar += this.procesos.get(bandera).gettCPU();
            bandera++;
        }
        System.out.println(this.procesos);
    };
    
    private void ordenarPorTiempoLlegada(){
        
        Proceso auxiliar;
        
        for(int x = 0 ; x < this.procesos.size(); x++){
            for(int y = x + 1 ; y < this.procesos.size() ; y++){
                if(this.procesos.get(y).gettLlegada() <= this.procesos.get(x).gettLlegada()){
                    auxiliar = this.procesos.get(x);
                    this.procesos.set(x, this.procesos.get(y));
                    this.procesos.set(y, auxiliar);
                    
                }
            }
        }
    };
    
    private void ordenarPorId(){
        
        Proceso auxiliar;
        
        for(int x = 0 ; x < this.procesos.size(); x++){
            for(int y = x + 1 ; y < this.procesos.size() ; y++){
                if(this.procesos.get(y).getNumeroIdProceso() <= this.procesos.get(x).getNumeroIdProceso()){
                    auxiliar = this.procesos.get(x);
                    this.procesos.set(x, this.procesos.get(y));
                    this.procesos.set(y, auxiliar);
                    
                }
            }
        }
    };
    
    private boolean areProcesosCompletados() {
        for(int x = 0 ; x < this.procesos.size() ; x++){
            if(!this.procesos.get(x).isCompletado()){
                return false;
            }
        }
        return true;
    };
    
    private int getIncompletedPriority(){
        int auxiliar = 4;
        int indexAuxiliar = 0;
        for(int x = 0 ; x < this.procesos.size() ; x++){
            if(this.procesos.get(x).gettLlegada() <= this.tiempo && this.procesos.get(x).getPrioridad() < auxiliar && !this.procesos.get(x).isCompletado()){
                auxiliar = this.procesos.get(x).getPrioridad();
                indexAuxiliar = x;
            }
        }
        if(auxiliar != 4){
            return indexAuxiliar;
        }
        return -1;
    }
    
    private int getIncompletedShortTime(){
        int auxiliar =  Integer.MAX_VALUE;
        int indexAuxiliar = 0;
        for(int x = 0 ; x < this.procesos.size() ; x++){
            if(this.procesos.get(x).gettLlegada() <= this.tiempo && this.procesos.get(x).gettCPU() < auxiliar && !this.procesos.get(x).isCompletado()){
                auxiliar = this.procesos.get(x).gettCPU();
                indexAuxiliar = x;
            }
        }
        if(auxiliar != Integer.MAX_VALUE){
            return indexAuxiliar;
        }
        return -1;
    }
}
