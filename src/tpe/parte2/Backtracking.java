package tpe.parte2;

import tpe.Procesador;
import tpe.Tarea;
import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backtracking {

    private HashMap<Procesador, ArrayList<Tarea>> procesadores;
    private List<HashMap<Procesador, ArrayList<Tarea>>> soluciones;
    private HashMap<Procesador, ArrayList<Tarea>> solucion;
    private HashMap<String, Tarea> tareas;
    private int tiempoDeEjecucionParaProcesadoresNoRefrigerados;

    public Backtracking(String pathProcesadores, String pathTareas, int tiempoDeEjecucionParaProcesadoresNoRefrigerados) {
        CSVReader reader = new CSVReader();
        this.procesadores = reader.readProcessors(pathProcesadores);
        this.tareas = reader.readTasks(pathTareas);
        this.tiempoDeEjecucionParaProcesadoresNoRefrigerados = tiempoDeEjecucionParaProcesadoresNoRefrigerados;
        this.soluciones = new ArrayList<>();
        this.solucion = new HashMap<>();
    }

    public void asignarTareas() {
        this.backtracking(new ArrayList<>(tareas.values()));
    }

    private void backtracking(ArrayList<Tarea> tareasSinAsignar) {
        if (tareasSinAsignar.isEmpty()) {
            if (solucion.isEmpty() || tiempoEjecucionFinal(this.procesadores) < tiempoEjecucionFinal(solucion)) {
                System.out.println("-> " + tiempoEjecucionFinal(this.procesadores));
                this.solucion = cloneProcesadores(this.procesadores);
            }
        } else {
            for (Procesador procesador : this.procesadores.keySet()) {
                Tarea tarea = tareasSinAsignar.remove(0);
                if (this.cumpleRequisitos(procesador, tarea)) {
                    this.procesadores.get(procesador).add(tarea);
                    backtracking(new ArrayList<>(tareasSinAsignar));
                    this.procesadores.get(procesador).remove(tarea);
                }
                tareasSinAsignar.add(0, tarea); // Reponer la tarea para backtracking
            }

        }
    }

    public boolean cumpleRequisitos(Procesador p, Tarea t) {
        List<Tarea> tareas = this.procesadores.get(p);
        if (!p.isEsta_refrigerado() && sumarTiempoTareas(tareas) + t.getTiempo_ejecucion() > this.tiempoDeEjecucionParaProcesadoresNoRefrigerados) {
            return false;
        }
        if (cantCriticas(tareas) >= 2) {
            return false;
        }
        return true;
    }

    public int sumarTiempoTareas(List<Tarea> tareas) {
        int total = 0;
        for (Tarea t : tareas) {
            total += t.getTiempo_ejecucion();
        }
        return total;
    }

    public int cantCriticas(List<Tarea> tareas) {
        int cant = 0;
        for (Tarea t : tareas) {
            if (t.isEs_critica()) {
                cant++;
            }
        }
        return cant;
    }

    public int tiempoEjecucionFinal(HashMap<Procesador, ArrayList<Tarea>> procesadores) {
        int tiempo = -1;
        for (ArrayList<Tarea> tareas : procesadores.values()) {
            tiempo = Math.max(tiempo, tiempoAcumuladoTareas(tareas));
        }
        return tiempo;
    }

    private int tiempoAcumuladoTareas(ArrayList<Tarea> tareas) {
        int tiempoAcumulado = 0;
        for (Tarea tarea : tareas) {
            tiempoAcumulado += tarea.getTiempo_ejecucion();
        }
        return tiempoAcumulado;
    }

    private HashMap<Procesador, ArrayList<Tarea>> cloneProcesadores(HashMap<Procesador, ArrayList<Tarea>> original) {
        HashMap<Procesador, ArrayList<Tarea>> copy = new HashMap<>();
        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : original.entrySet()) {
            Procesador p = entry.getKey();
            ArrayList<Tarea> tareasOriginales = entry.getValue();
            ArrayList<Tarea> tareasClonadas = new ArrayList<>(tareasOriginales);
            copy.put(p, tareasClonadas);
        }
        return copy;
    }

    public void recorrerSolucion() {
        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : solucion.entrySet()) {
            Procesador procesador = entry.getKey();
            ArrayList<Tarea> tareas = entry.getValue();
            System.out.println("Procesador: " + procesador);
            for (Tarea tarea : tareas) {
                System.out.println("  Tarea: " + tarea);
            }
        }
    }




}
