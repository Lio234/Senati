package Modelos;

public class Departamento {
    private String id_departamento;
    private String departamento;

    
    public Departamento() {
    }

    public Departamento(String id_departamento, String departamento) {
        this.id_departamento = id_departamento;
        this.departamento = departamento;
    }

    
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
