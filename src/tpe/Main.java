package tpe;

import tpe.parte2.Backtracking;
import tpe.Procesador;
import tpe.Tarea;
import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		String csvTareas = "./src/tpe/datasets/Tareas.csv";
		String csvProcesadores = "./src/tpe/datasets/Procesadores.csv";

		// Definir el tiempo de ejecución para procesadores no refrigerados
		int tProcesadoresNoRefrigerados = 35;

		// Crear una instancia de la clase Backtracking
		Backtracking back = new Backtracking(csvProcesadores, csvTareas, tProcesadoresNoRefrigerados);

		// Asignar tareas utilizando el algoritmo de backtracking
		back.asignarTareas();

		back.recorrerSolucion();




	}
}
