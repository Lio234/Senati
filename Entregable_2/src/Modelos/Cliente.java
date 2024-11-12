package Modelos;

public class Cliente {

    private String id;
    private String nombre;
    private String ap_paterno;
    private String ap_materno;
    private String direccion;
    private String correo;
    private String telefono;
    private String id_distrito;
    
    public Cliente(){
    }

    public Cliente(String id, String nombre, String ap_paterno, String ap_materno, String direccion, String correo, String telefono, String id_distrito) {
       
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id_cliente) {
        this.id = id_cliente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return this.ap_paterno;
    }

    public void setApPaterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getApMaterno() {
        return this.ap_materno;
    }

    public void setApMaterno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdDistrito() {
        return this.id_distrito;
    }

    public void setIdDistrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }
    
}
