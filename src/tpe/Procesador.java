package tpe;

public class Procesador {

    private String id_procesador, codigo_procesador;
    private int anio_funcionamiento;
    private boolean esta_refrigerado;



    public Procesador(String id_procesador, String codigo_procesador, int anio_funcionamiento, boolean esta_refrigerado) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.anio_funcionamiento = anio_funcionamiento;
        this.esta_refrigerado = esta_refrigerado;
    }

    public String getId_procesador() {
        return id_procesador;
    }

    public void setId_procesador(String id_procesador) {
        this.id_procesador = id_procesador;
    }

    public String getCodigo_procesador() {
        return codigo_procesador;
    }

    public void setCodigo_procesador(String codigo_procesador) {
        this.codigo_procesador = codigo_procesador;
    }

    public int getAnio_funcionamiento() {
        return anio_funcionamiento;
    }

    public void setAnio_funcionamiento(int anio_funcionamiento) {
        this.anio_funcionamiento = anio_funcionamiento;
    }

    public boolean isEsta_refrigerado() {
        return esta_refrigerado;
    }

    public void setEsta_refrigerado(boolean esta_refrigerado) {
        this.esta_refrigerado = esta_refrigerado;
    }

    @Override
    public String toString() {
        return "\n" +"Procesador{" +"\n" +
                "   ID: '" + id_procesador + "," + '\n' +
                "   CODIGO: '" + codigo_procesador + "," + '\n' +
                "   AÑO FUNCIONAMIENTO: " + anio_funcionamiento + "," + "\n" +
                "   ES REFRIGERADO: " + esta_refrigerado + "," + "\n" +
                '}'+ "\n";
    }
}
