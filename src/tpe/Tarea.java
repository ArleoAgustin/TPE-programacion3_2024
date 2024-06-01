package tpe;

public class Tarea {

    private String nombre_tarea, id_tarea;
    private int nivel_prioridad, tiempo_ejecucion;
    private boolean es_critica;

    public Tarea(String nombre_tarea, String id_tarea, int nivel_prioridad, int tiempo_ejecucion, boolean es_critica) {
        this.nombre_tarea = nombre_tarea;
        this.id_tarea = id_tarea;
        this.nivel_prioridad = nivel_prioridad;
        this.tiempo_ejecucion = tiempo_ejecucion;
        this.es_critica = es_critica;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public String getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(String id_tarea) {
        this.id_tarea = id_tarea;
    }

    public int getNivel_prioridad() {
        return nivel_prioridad;
    }

    public void setNivel_prioridad(int nivel_prioridad) {
        this.nivel_prioridad = nivel_prioridad;
    }

    public int getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public void setTiempo_ejecucion(int tiempo_ejecucion) {
        this.tiempo_ejecucion = tiempo_ejecucion;
    }

    public boolean isEs_critica() {
        return es_critica;
    }

    public void setEs_critica(boolean es_critica) {
        this.es_critica = es_critica;
    }

    @Override
    public String toString() {
         return '\n' +
                 "       NOMBRE: '" + nombre_tarea + "," + '\n' +
                 "       ID TAREA : '" + id_tarea + "," + '\n' +
                 "       NIVEL DE PRIORIDAD: " + nivel_prioridad + "," + '\n' +
                 "       TIEMPO DE EJECUCION: " + tiempo_ejecucion + "," + '\n' +
                 "       ES CRITICA: " + es_critica + "," + '\n';
    }
}
