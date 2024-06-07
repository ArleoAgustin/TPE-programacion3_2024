package tpe;

import tpe.parte2.ListaTareas;
import tpe.utils.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "tpe.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private HashMap<Procesador, ListaTareas> procesadores;
	private HashMap<String, Tarea> tareas;
	private ArrayList es_critica, no_esCritica;

	/*
     * La complejidad temporal del constructor es O(P + T), siendo P el numero
     * de procesadores y T el numero de tareas.
     */
	public Servicios(String pathProcesadores, String pathTareas) {

		CSVReader reader = new CSVReader();
		this.procesadores = reader.readProcessors(pathProcesadores);
		this.tareas = reader.readTasks(pathTareas);
		this.es_critica = new ArrayList();
		this.no_esCritica = new ArrayList();

		this.tareas.forEach( (key, value) ->{
			if (value.isEs_critica())
				this.es_critica.add(value);
			else
				this.no_esCritica.add(value);
		});
	}


	/*
     * La complejidad temporal del servicio 1 es O(1), ya que se busca por el id de la tarea,
     * el cual es la key en el hashmap por lo que accede directamente a la tarea.
     */
	public Tarea servicio1(String ID) { return this.tareas.get(ID); }
    
    /*
     * La complejidad temporal del servicio 2 es O(1), ya que verifica si se piden las tareas
     * criticas o las no criticas y devuelve la lista respectiva.
     */
	public List<Tarea> servicio2(boolean esCritica) {

		if (esCritica)
			return this.es_critica;
		else
			return this.no_esCritica;
	}

    /*
     * La complejidad temporal del servicio 3 es O(n), igual que para el servicio 2 hay
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


	public ArrayList getEs_critica() {
		return es_critica;
	}

	public ArrayList getNo_esCritica() {
		return no_esCritica;
	}

	public HashMap<Procesador, ListaTareas> getProcesadores() {
		return procesadores;
	}

	public HashMap<String, Tarea> getTareas() {
		return tareas;
	}

}
