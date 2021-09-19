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
        this.tiempo = 0;
        this.ordenarPorTiempoLlegada();
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

    public ArrayList<Proceso> getModeloSJRF() {
        return this.procesos;
    }

    public ArrayList<Proceso> getModeloExpropiativo() {
        return this.procesos;
    }
        
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
}
