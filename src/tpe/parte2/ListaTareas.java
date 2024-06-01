package tpe.parte2;

import tpe.Tarea;

import java.util.ArrayList;

public class ListaTareas {

    private ArrayList<Tarea> tareas;
    private int tiempoEjecucionTotal;
    private int cantTareasCriticas;

    public ListaTareas() {
        this.tareas = new ArrayList<>();
        this.tiempoEjecucionTotal = 0;
        this.cantTareasCriticas = 0;
    }

    public ListaTareas(ListaTareas lNueva){

        this.tareas = new ArrayList<>(lNueva.getTareas());
        this.cantTareasCriticas = lNueva.cantTareasCriticas;
        this.tiempoEjecucionTotal = lNueva.getTiempoEjecucionTotal();
        this.cantTareasCriticas = lNueva.getCantTareasCriticas();

    }

    public ArrayList<Tarea> getTareas() {
        return new ArrayList<>(tareas);
    }

    public void removeTarea(Tarea t){

        if (tareas.contains(t)) {
            tareas.remove(t);
            if (t.isEs_critica())
                cantTareasCriticas--;

            this.tiempoEjecucionTotal -= t.getTiempo_ejecucion();
        }

    }

    public boolean isEmpty(){

        return this.tareas.isEmpty();

    }

    public void addTarea(Tarea t) {

        if (t.isEs_critica())
            this.cantTareasCriticas++;

        this.tiempoEjecucionTotal += t.getTiempo_ejecucion();
        this.tareas.add(t);
    }

    public int getTiempoEjecucionTotal() {
        return tiempoEjecucionTotal;
    }

    public int getCantTareasCriticas() {
        return cantTareasCriticas;
    }


    @Override
    public String toString() {
        return  "\n" +"tareas asociadas: " + '\n'  + tareas + '\n' + '\n'
                + "Tiempo total de ejecucion del procesador: "+ tiempoEjecucionTotal + '\n' +
                "---------------------------------------------------------" +"\n" ;
    }
}
