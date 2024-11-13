package Modelos;

public class Distrito {
    private String id_distrito;
    private String distrito;
    private String id_provincia;

    // Constructores
    public Distrito() { }

    public Distrito(String id_distrito, String distrito, String id_provincia) {
        this.id_distrito = id_distrito;
        this.distrito = distrito;
        this.id_provincia = id_provincia;
    }

    // Getters y Setters
    public String getIdDistrito() {
        return id_distrito;
    }

    public void setIdDistrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getIdProvincia() {
        return id_provincia;
    }

    public void setIdProvincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }
}
