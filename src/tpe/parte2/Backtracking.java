package tpe.parte2;

import tpe.Procesador;
import tpe.Tarea;
import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public class Backtracking {

    private HashMap<Procesador, ArrayList<Tarea>> procesadores;
    private HashMap<String, Tarea> tareas;

    private int tiempoDeEjecucionParaProcesadoresNoRefrigerados;

    public Backtracking(String pathProcesadores, String pathTareas, int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {

        CSVReader reader = new CSVReader();
        this.procesadores = reader.readProcessors(pathProcesadores);
        this.tareas = reader.readTasks(pathTareas);
        this.tiempoDeEjecucionParaProcesadoresNoRefrigerados = tiempoDeEjecucionParaProcesadoresNoRefrigerados;

    }


    public void asignarTareas(){
    this.tareas.forEach((key,value) -> {

        this.backtracking(new ArrayList<Tarea>(tareas.values()));

    });

    }

    private void backtracking(ArrayList<Tarea> tareasSinAsignar){

        if (tareasSinAsignar.isEmpty()){    //si ya no hay tareas para asignar
            return;                         //corta iteracion
        }

        Tarea tarea = tareasSinAsignar.get(0);  //obtiene la primer tarea de la lista


        for (Procesador p : this.procesadores.keySet()) {       //recorre todos los procesadores

            if (this.cumpleRequisitos(tarea, p)){ //si cumple los requisitos
                this.procesadores.get(p).add(tarea);        //agrega la tarea a la lista del procesador
                tareasSinAsignar.remove(tarea);         //quita la tarea asignada de la lista sin asignar

                if(!tareasSinAsignar.isEmpty()){    // si no fueron asignadas todas las tareas
                    backtracking(tareasSinAsignar);
                    this.procesadores.get(p).remove(tarea); //retira la tarea asignada a un procesador ??
                    tareasSinAsignar.add(0,tarea);    //y si la tarea fue asignada??
                }
            }

        }
    }

    private boolean cumpleRequisitos(Tarea tarea, Procesador procesador) {

        ArrayList<Tarea> tareasProcesador = procesadores.get(procesador);
        if (!tareasProcesador.isEmpty()){
            if (tareasProcesador.get(tareasProcesador.size() - 1).isEs_critica() && tarea.isEs_critica())   //verifica que no sea critica la ultima ingresada
                return false;
        }
    //verifica que el tiempo acumulado + la nueva tarea sea menor al tiempo permitido
        if (!procesador.isEsta_refrigerado() && tiempoAcumuladoTareas(tareasProcesador) + tarea.getTiempo_ejecucion() > tiempoDeEjecucionParaProcesadoresNoRefrigerados)
            return false;

        return true;
    }

//calcula el tiempo de ejecucion de todas las tareas que tiene el procesador

    private int tiempoAcumuladoTareas(ArrayList<Tarea> tareas) {
        int tiempoAcumulado = 0;
        for (Tarea tarea : tareas) {
            tiempoAcumulado += tarea.getTiempo_ejecucion();
        }
        return tiempoAcumulado;
    }


    public HashMap<Procesador, ArrayList<Tarea>> getProcesadores() {
        return procesadores;
    }
}