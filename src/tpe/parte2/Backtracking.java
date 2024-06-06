package tpe.parte2;

import tpe.Procesador;
import tpe.Tarea;
import tpe.utils.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public class Backtracking {

    private HashMap<Procesador, ListaTareas> procesadores, mejorSolucion;
    private HashMap<String, Tarea> tareas;
    private int contEstados, mejorTiempomaximoDeEjecucion;
    private boolean asignoTodas;

    public Backtracking(String pathProcesadores, String pathTareas) {

        CSVReader reader = new CSVReader();
        this.procesadores = reader.readProcessors(pathProcesadores);
        this.tareas = reader.readTasks(pathTareas);
        this.contEstados = 0;
        this.mejorSolucion = new HashMap();
        this.mejorTiempomaximoDeEjecucion = Integer.MAX_VALUE;
        this.asignoTodas = false;
    }


    public HashMap<Procesador, ListaTareas> asignarTareas(int tiempoDeEjecucionParaProcesadoresNoRefrigerados){

        this.backtracking(new ArrayList<>(tareas.values()), tiempoDeEjecucionParaProcesadoresNoRefrigerados);
        return mejorSolucion;
    }

    /*
     * Para poder realizar la asignacion de tareas con backtracking lo que hicimos fue pasarle por parametro una lista de tareas
     * sin asignar. Obtenemos la primera tarea y luego se itera el hashmap de procesadores, verificando si el procesador cumple
     * con los requisitos para poder asignarle la tarea, en el caso de que asi sea se agrega la tarea a la lista del procesador.
     * Luego se quita esa tarea de la lista sin asignar y se realiza la recursion, una vez que vuelve se remueve la tarea de la
     * lista de tareas y se agrega a las tareas sin asignar de nuevo para asi el procesador siguiente puede probar una solucion
     * con esa tarea.
     * Cuando ya no quedan tareas para asignar se se obtiene el mejor tiempo de procesamiento de un procesador de la solucion
     * y se verifica si no hay alguna mejor solucion (si esta vacio) o si el tiempo es menor al de la mejor solucion. En caso de que se cumpla
     * alguna de estas dos condiciones se hace una copia del hashmap de procesadores, luego se confirma que al menos una solucion hubo
     * y se actualiza el maximo tiempo de procesamiento.
     */


    private void backtracking(ArrayList<Tarea> tareasSinAsignar, int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {

        contEstados++;

        if (tareasSinAsignar.isEmpty()) {
            int tiempo = calcularMaximoTiempoDeEjecucion(); //obtiene el maximo tiempo de procesamiento de un procesador
            if (mejorSolucion.isEmpty() || tiempo < this.mejorTiempomaximoDeEjecucion) {
                this.mejorSolucion = this.hacercopia();
                this.asignoTodas = true;
                this.mejorTiempomaximoDeEjecucion = tiempo;
            }
        }
        else {

                for (Procesador p : procesadores.keySet()) { // Recorre todos los procesadores
                    Tarea tarea = tareasSinAsignar.get(0);
                    if (cumpleRequisitos(tarea, p, tiempoDeEjecucionParaProcesadoresNoRefrigerados)) {
                        tareasSinAsignar.remove(tarea);
                        procesadores.get(p).addTarea(tarea);     // Agrega la tarea al procesador
                        backtracking(tareasSinAsignar, tiempoDeEjecucionParaProcesadoresNoRefrigerados);
                        procesadores.get(p).removeTarea(tarea);      // elimina la tarea del hashmap
                        tareasSinAsignar.add(0, tarea);
                    }
                }
            }
        }


    private int calcularMaximoTiempoDeEjecucion() {

        int tiempoActual = 0;
        for (ListaTareas tareas : procesadores.values()) {

            if (tareas.getTiempoEjecucionTotal() > tiempoActual)
                tiempoActual = tareas.getTiempoEjecucionTotal();
        }
        return tiempoActual;
    }



    private HashMap<Procesador, ListaTareas> hacercopia(){

        HashMap<Procesador, ListaTareas> copia = new HashMap<>();

        for (Map.Entry<Procesador, ListaTareas> procesadores : procesadores.entrySet()) {

            Procesador procesador = procesadores.getKey();                          //crea una copia del procesador
            ListaTareas lista = new ListaTareas(procesadores.getValue());   //crea una copia de la lista (objeto)
            copia.put(procesador, lista);

        }
        return copia;
    }


    private boolean cumpleRequisitos(Tarea tarea, Procesador procesador, int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {

        ListaTareas listaTareasProcesador = procesadores.get(procesador);

        if (!listaTareasProcesador.isEmpty()) {
            //si la cantidad de tareas criticas es menor a 2 no entra
            if (procesadores.get(procesador).getCantTareasCriticas() >= 2)
                return false;
        }

        int tiempoTotalDeEjecucion = procesadores.get(procesador).getTiempoEjecucionTotal();

        tiempoTotalDeEjecucion += tarea.getTiempo_ejecucion();
        //verifica que el tiempo acumulado + la nueva tarea sea menor al tiempo permitido
        if (!procesador.isEsta_refrigerado() && tiempoTotalDeEjecucion > tiempoDeEjecucionParaProcesadoresNoRefrigerados)
            return false;

        return true;
    }

    public int getContEstados() {
        return contEstados;
    }

    public boolean isAsignoTodas() {
        return asignoTodas;
    }

    public int getMejorTiempomaximoDeEjecucion() {
        return mejorTiempomaximoDeEjecucion;
    }
}