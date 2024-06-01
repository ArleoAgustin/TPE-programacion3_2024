package tpe;

import tpe.parte2.Backtracking;
import tpe.utils.CSVReader;

import java.util.ArrayList;

public class Main {

	public static void main(String args[]) {

		CSVReader csvReader = new CSVReader();

		String csvTareas = "./src/tpe/datasets/Tareas.csv";
		String csvProcesadores = "./src/tpe/datasets/Procesadores.csv";


		tpe.Servicios servicios = new tpe.Servicios(csvProcesadores, csvTareas);

		System.out.println("");
		System.out.println("Servicio 1:");


		Tarea t = servicios.servicio1("T6");	//ingresar id de tarea

		if (t != null)
			System.out.println(t);
		else
			System.out.println("No hay tareas con el id ingresado");

		System.out.println("");
		System.out.println("Servicio 2:");
		System.out.println("");
		ArrayList<Tarea> lista = (ArrayList<Tarea>) servicios.servicio2(false);	//ver criticas o no criticas
		if (!lista.isEmpty()) {
			if (lista.get(0).isEs_critica()) {
				System.out.println("Tareas criticas: ");
				System.out.println(lista);
			} else {
				System.out.println("Tareas no criticas");
				System.out.println(lista);
			}
		}
		else
			System.out.println("No hay tareas");



		System.out.println("");
		System.out.println("Servicio 3:");

		int prioridadDesde = 35;
		int prioridadHasta = 60;
		System.out.println("");
		System.out.println("Tareas de prioridad " +prioridadDesde + " hasta " + prioridadHasta);

		System.out.println(servicios.servicio3(prioridadDesde,prioridadHasta));


		System.out.println("");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Parte 2");
		System.out.println("");

		/////////////////////////////////////BACKTRACKING/////////////////////////////////////////////////////

		Backtracking back = new Backtracking(csvProcesadores, csvTareas, 35);

		System.out.println("Backtracking:");

		System.out.println(back.asignarTareas());
		//back.asignarTareas().forEach( (key,value) ->{

			/*
			System.out.println("");
			System.out.println( "  "+key + " ");
			System.out.println(" ");
			System.out.println(value);
*/
		//});


		System.out.println("");

		System.out.println("Tiempo maximo de ejecucion: "+ back.getmejorTiempomaximoDeEjecucion());
		System.out.println("Cantidad de estados: "+ back.getContEstados());

	}
}
