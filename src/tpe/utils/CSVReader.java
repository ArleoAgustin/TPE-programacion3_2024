package tpe.utils;

import tpe.Procesador;
import tpe.Tarea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVReader {

	public CSVReader() {
	}

	public HashMap<String, Tarea> readTasks(String taskPath) {
		HashMap<String, Tarea> tareas = new HashMap<>();
		ArrayList<String[]> lines = this.readContent(taskPath);

		for (String[] line : lines) {
			if (line.length != 5) {
				System.err.println("Formato incorrecto de línea: " + String.join(";", line));
				continue;
			}

			try {
				String id = line[0].trim();
				String nombre = line[1].trim();
				Integer tiempo = Integer.parseInt(line[2].trim());
				Boolean critica = Boolean.parseBoolean(line[3].trim());
				Integer prioridad = Integer.parseInt(line[4].trim());
				Tarea t = new Tarea(nombre, id, prioridad, tiempo, critica);
				tareas.put(id, t);
			} catch (NumberFormatException e) {
				System.err.println("Error en la conversión de número: " + String.join(";", line));
			}
		}
		return tareas;
	}

	public HashMap<Procesador, ArrayList<Tarea>> readProcessors(String processorPath) {
		HashMap<Procesador, ArrayList<Tarea>> procesadores = new HashMap<>();
		ArrayList<String[]> lines = this.readContent(processorPath);

		for (String[] line : lines) {
			if (line.length != 4) {
				System.err.println("Formato incorrecto de línea: " + String.join(";", line));
				continue;
			}

			try {
				String id = line[0].trim();
				String codigo = line[1].trim();
				Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
				Integer anio = Integer.parseInt(line[3].trim());
				Procesador p = new Procesador(id, codigo, anio, refrigerado);
				procesadores.put(p, new ArrayList<Tarea>());
			} catch (NumberFormatException e) {
				System.err.println("Error en la conversión de número: " + String.join(";", line));
			}
		}
		return procesadores;
	}

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
}
