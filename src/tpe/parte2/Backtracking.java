package tpe.parte2;

import tpe.Procesador;
import tpe.Tarea;
import tpe.utils.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public class Backtracking {

    private HashMap<Procesador, ListaTareas> procesadores;
    private HashMap<String, Tarea> tareas;
    private int contEstados;
    private HashMap<Procesador, ListaTareas> mejorSolucion;
    private int mejorTiempomaximoDeEjecucion;
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
     * Cuando ya no quedan tareas para asignar se verifica si existe alguna mejorSolucion o en el caso de que ya exista se compara
     * la solucion actual que seria el estado del hashmap de procesadores con la mejor solucion. Si el estado actual es mejor que
     * la mejor solucion, se realiza una copia del hashmap procesadores junto con sus tareas y se vuelve la mejor solucion.
     */


    private void backtracking(ArrayList<Tarea> tareasSinAsignar, int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {

        contEstados++;

        if (tareasSinAsignar.isEmpty()) {

            if (this.mejorSolucion.isEmpty()){
                mejorSolucion = this.hacercopia();
                this.asignoTodas = true;
                return;
            }

            if (this.actualEsMejorSolucion()) {
                mejorSolucion = this.hacercopia();
                this.asignoTodas = true;
            }
        }
        else {

            Tarea tarea = tareasSinAsignar.get(0);

            for (Procesador p : procesadores.keySet()) { // Recorre todos los procesadores

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


    /*
    * Para verificar si el estado del hashmap de procesadores (estado actual) es mejor solucion, se recorre
    * procesadores uno a uno al igual que los de  mejorSolucion. Se obtiene el tiempo de ejecucion total de todas las tareas
    * de cada procesador y lo mismo hace para la mejorSolucion, entonces luego se verifica si el tiempo de ejecucion del
    * procesador con estado actual es menor que el del procesador de mejorSolucion. En el caso de que un procesador del estado actual tenga
    * un tiempo de ejecucion mayor se corta la busqueda y devuelve false;
    * No hace falta recorrer todas las listas, ya que ListaTarea es un objeto que contiene la lista y un atributo que lleva
    * la cuenta del tiempo total de ejecucion de una lista de tareas.
    */


    //se recorren los hashmap de procesadores y mejorSolucion
    private boolean actualEsMejorSolucion() {

        for (ListaTareas tareasPActual : procesadores.values()) {

            int tiempoDeEjecucionTotal = tareasPActual.getTiempoEjecucionTotal();

            for (ListaTareas tareasMjSolucion : mejorSolucion.values()) {

                int tiempoDeEjecucionTotalMjSolucion = tareasMjSolucion.getTiempoEjecucionTotal();

                if (tiempoDeEjecucionTotal > tiempoDeEjecucionTotalMjSolucion)  //si al encontrar una lista ya es mayor el tiempo no busca mas
                    return false;
            }
        }
        return true;
    }



    private HashMap<Procesador, ListaTareas> hacercopia(){

        HashMap<Procesador, ListaTareas> copia = new HashMap<>();

        for (Map.Entry<Procesador, ListaTareas> procesadores : procesadores.entrySet()) {

            Procesador procesador = procesadores.getKey();                          //crea una copia del procesador
            ListaTareas lista = new ListaTareas(procesadores.getValue());   //crea una copia de la lista (objeto)
            mejorTiempomaximoDeEjecucion += lista.getTiempoEjecucionTotal();
            //aumenta el tiempo total que tardarian todos los procesadores en realizar todas las tareas

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


    public int getmejorTiempomaximoDeEjecucion() {
        return mejorTiempomaximoDeEjecucion;
    }

    public int getContEstados() {
        return contEstados;
    }

    public boolean isAsignoTodas() {
        return asignoTodas;
    }
}