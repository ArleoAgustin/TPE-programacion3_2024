package tpe.utils;

import tpe.Procesador;
import tpe.Tarea;
import tpe.parte2.ListaTareas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVReader {


	public CSVReader() {
	}

	public HashMap readTasks(String taskPath) {

		HashMap<String, Tarea> tareas = new HashMap<>();


		ArrayList<String[]> lines = this.readContent(taskPath);

		for (String[] line: lines) {

			String id = line[0].trim();
			String nombre = line[1].trim();
			Integer tiempo = Integer.parseInt(line[2].trim());
			Boolean critica = Boolean.parseBoolean(line[3].trim());
			Integer prioridad = Integer.parseInt(line[4].trim());
			Tarea t = new Tarea(nombre,id,prioridad,tiempo,critica);
			tareas.put(id, t);
		}
		return tareas;
	}

	public HashMap<Procesador,ListaTareas> readProcessors(String processorPath) {

		HashMap<Procesador, ListaTareas> procesadores = new HashMap<>();
		ArrayList<String[]> lines = this.readContent(processorPath);

		for (String[] line: lines) {

			String id = line[0].trim();
			String codigo = line[1].trim();
			Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
			Integer anio = Integer.parseInt(line[3].trim());
			Procesador p = new Procesador(id,codigo,anio,refrigerado);
			procesadores.put(p, new ListaTareas());
		}
	return procesadores;
	}

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		File file = new File(path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}

		return lines;
	}

}