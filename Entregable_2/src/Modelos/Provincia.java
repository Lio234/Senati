package Modelos;

public class Provincia {

    private String id_provincia;
    private String provincia;
    private String id_departamento;

    // Constructor vacío
    public Provincia() {
    }

    // Constructor con parámetros
    public Provincia(String id_provincia, String provincia, String id_departamento) {
        this.id_provincia = id_provincia;
        this.provincia = provincia;
        this.id_departamento = id_departamento;
    }

    // Getters y Setters
    public String getIdProvincia() {
        return id_provincia;
    }

    public void setIdProvincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getIdDepartamento() {
        return id_departamento;
    }

    public void setIdDepartamento(String id_departamento) {
        this.id_departamento = id_departamento;
    }
}
