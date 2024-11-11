
package Modelos;

public class Marca {
    private String id_marca;
    private String marca;    
    
    public Marca(){
    }
    public Marca(String id_marca, String marca) {
        this.id_marca = id_marca;
        this.marca = marca;
    }
    public String getIdMarca() {
        return this.id_marca;
    }
    public void setIdMarca(String id_marca) {
        this.id_marca = id_marca;
    }
    public String getMarca() {
        return this.marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
}
