
package Modelos;


public class Marca {
    private String id_marca;
    private String marca;    

    public Marca(String id_marca, String marca) {
        this.id_marca = id_marca;
        this.marca = marca;
    }

    public String getId_marca() {
        return id_marca;
    }

    public void setId_marca(String id_marca) {
        this.id_marca = id_marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
}
