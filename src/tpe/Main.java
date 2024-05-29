package tpe;

import tpe.parte2.Backtracking;
import tpe.utils.CSVReader;

public class Main {

	public static void main(String args[]) {

		CSVReader csvReader = new CSVReader();

		String csvTareas = "./src/tpe/datasets/Tareas.csv";
		String csvProcesadores = "./src/tpe/datasets/Procesadores.csv";

/*
		tpe.Servicios servicios = new tpe.Servicios(csvProcesadores, csvTareas);

		System.out.println("");
		System.out.println("Procesadores:");

		servicios.getProcesadores().forEach( (key, value) ->{

			System.out.println(key.getId_procesador());

		});

		System.out.println("");
		System.out.println("Tareas:");

		servicios.getTareas().forEach( (key, value) ->{

			System.out.println(value);

		});

		System.out.println("");
		System.out.println("Servicio 1:");

		System.out.println(servicios.servicio1("T3"));

		System.out.println("");
		System.out.println("Servicio 2:");

		System.out.println(servicios.servicio2(true));


		System.out.println("");
		System.out.println("Servicio 3:");

		System.out.println(servicios.servicio3(35,60));

*/

		/////////////////////////////////////BACKTRACKING/////////////////////////////////////////////////////

		Backtracking back = new Backtracking(csvProcesadores, csvTareas, 35);

		back.asignarTareas();

		back.getProcesadores().forEach( (key,value) ->{

			System.out.println( key + " ");
			System.out.println(value);

		});


	}
}
