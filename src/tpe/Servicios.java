package tpe;

import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "tpe.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private HashMap<String, Procesador> procesadores;
	private HashMap<String, Tarea> tareas;
	/*
     * La complejidad temporal del constructor es O(n), donde n es la cantidad de lineas
     * que contiene el csv para asi luego crear el objeto y agregarla al hashmap
     */
	public Servicios(String pathProcesadores, String pathTareas) {

		CSVReader reader = new CSVReader();
		this.procesadores = reader.readProcessors(pathProcesadores);
		this.tareas = reader.readTasks(pathTareas);

	}

	public HashMap<String, Procesador> getProcesadores() {
		return procesadores;
	}

	public HashMap<String, Tarea> getTareas() {
		return tareas;
	}

	/*
     * La complejidad temporal del servicio 1 es O(1), ya que se busca por el id de la tarea,
     * el cual es la key en el hashmap por lo que accede directamente a la tarea.
     */
	public Tarea servicio1(String ID) {

		return this.tareas.get(ID);
	}
    
    /*
     * La complejidad temporal del servicio 2 es O(n), ya que hay que iterar sobre todas
     * las tareas para poder obtener si es critica o no, por lo que n son las tareas.
     */
	public List<Tarea> servicio2(boolean esCritica) {

		List<Tarea> listaTareas = new ArrayList<>();

		this.tareas.forEach( (key, value) ->{
			if (esCritica && value.isEs_critica())
				listaTareas.add(value);

			else if (!esCritica && !value.isEs_critica()) {

				listaTareas.add(value);
			}
		});
		return listaTareas;
	}

    /*
     * La complejidad temporal del ejercicio 3 es O(n), igual que para el servicio 2 hay
     * que iterar sobre todas las tareas del hashmap para ver si esta dentro del rango de
     * prioridad.
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {

		List<Tarea> listaTareas = new ArrayList<>();

		this.tareas.forEach( (key, value) ->{

			if (value.getNivel_prioridad() >= prioridadInferior && value.getNivel_prioridad() <= prioridadSuperior)
				listaTareas.add(value);
		});

		return listaTareas;
	}

}
