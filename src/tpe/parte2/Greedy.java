package tpe.parte2;

import tpe.Procesador;
import tpe.Tarea;
import tpe.utils.CSVReader;

import java.util.*;

public class Greedy {

    private HashMap<Procesador, ListaTareas> procesadores;
    private HashMap<String, Tarea> tareas;
    private ArrayList<Tarea> tareasSinAsignar;
    private int contEstados, tiempoMaximoDeEjecucion, contTareasNoAsignadas;

    public Greedy(String pathProcesadores, String pathTareas) {

        CSVReader reader = new CSVReader();
        this.procesadores = reader.readProcessors(pathProcesadores);
        this.tareas = reader.readTasks(pathTareas);
        this.contEstados = 0;
        this.tiempoMaximoDeEjecucion = 0;
        this.tareasSinAsignar = new ArrayList<>(tareas.values());
        this.contTareasNoAsignadas = 0;
    }

    public HashMap<Procesador,ListaTareas> asignarTareas(int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {

        this.asignarTareasGreedy(tiempoDeEjecucionParaProcesadoresNoRefrigerados);
        return procesadores;
    }

    /*
    * Para poder realizar la asignacion de tareas con greedy se recorren todas las tareas, por cada iteracion
    * se establece un mejor candidato (procesador) en null y se comienza a iterar sobre los procesadores. Por
    * cada uno se verifica si cumple con los requisitos para poder asignarse la tarea. En el caso de que asi sea,
    * se crea un atributo que contiene el tiempo de ejecucion que tiene ese procesador + la tarea a asignar y se
    * compara con el de un procesador ya iterado (anterior). En caso de que este tiempo sea menor que el de otro procesador,
    * se establece a ese procesador como mejor candidato y el mejor tiempo de ejecucion se vuelve el de el. En el primer caso el mejor
    * tiempo va a ser el de MAX_VALUE().
    * Una vez iterado todos los procesadores se verifica si hubo candidato y si es asi, se agrega la tarea a el y se
    * aumenta el tiempo de ejecucion total, evitando de esta manera recorrer todas las listas de los procesadores para
    * verificar el tiempo total.
    */

    private void asignarTareasGreedy(int tiempoMaximoNoRefrigerado) {

        for (Tarea tarea : this.tareasSinAsignar) {      //recorre todas las tareas

            contEstados++;
            Procesador mejorCandidato = null;
            int menorTiempoTotal = Integer.MAX_VALUE;       //el mejor tiempo de ejecucion (se establece en max para el primer caso)
            for (Procesador p : procesadores.keySet()) {

                if (cumpleRequisitos(tarea,p, tiempoMaximoNoRefrigerado)) {

                    int tiempoTotalDeEjecucion =  procesadores.get(p).getTiempoEjecucionTotal() + tarea.getTiempo_ejecucion();
                    if (tiempoTotalDeEjecucion < menorTiempoTotal) {
                        menorTiempoTotal = tiempoTotalDeEjecucion;
                        mejorCandidato = p;                                        //se establece el mejor candidato
                    }
                }

            }

            if (mejorCandidato != null) {                              //si existe candidato
                procesadores.get(mejorCandidato).addTarea(tarea);       //le agrega la tarea
                this.tiempoMaximoDeEjecucion += tarea.getTiempo_ejecucion();            //aumenta el tiempo de ejecucion total
            }
            else
                this.contTareasNoAsignadas++;

        }
    }

    private boolean cumpleRequisitos(Tarea tarea, Procesador p, int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {

        ListaTareas tareasDeProcesador = procesadores.get(p);

        if (!tareasDeProcesador.isEmpty()) {
            if (tareasDeProcesador.getCantTareasCriticas() >= 2) //si la cantidad de tareas criticas es menor a 2 no entra
                return false;
        }

        int tiempoTotalDeEjecucion = tareasDeProcesador.getTiempoEjecucionTotal();

        tiempoTotalDeEjecucion += tarea.getTiempo_ejecucion();
        //verifica que el tiempo acumulado + la nueva tarea sea menor al tiempo permitido
        if (!p.isEsta_refrigerado() && tiempoTotalDeEjecucion > tiempoDeEjecucionParaProcesadoresNoRefrigerados)
            return false;

        return true;
    }


    public int getTiempomaximoDeEjecucion() {
        return tiempoMaximoDeEjecucion;
    }

    public int getContEstados() {
        return contEstados;
    }

    public int getContTareasNoAsignadas() {
        return contTareasNoAsignadas;
    }
}
