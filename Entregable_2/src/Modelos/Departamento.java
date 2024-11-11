package Modelos;

public class Departamento {
    private String id_departamento;
    private String departamento;

    // Constructor vacío
    public Departamento() {
    }

    // Constructor con parámetros
    public Departamento(String id_departamento, String departamento) {
        this.id_departamento = id_departamento;
        this.departamento = departamento;
    }

    // Getters y Setters
    public String getIdDepartamento() {
        return id_departamento;
    }

    public void setIdDepartamento(String id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
