package Modelos;

public class Categoria {
    private String id_categoria;
    private String categoria;
    
    // Constructor vacío (sin parámetros)
    public Categoria() {
    }

    // Constructor con parámetros (por si lo necesitas)
    public Categoria(String id_categoria, String categoria) {
        this.id_categoria = id_categoria;
        this.categoria = categoria;
    }

    // Getter para id_categoria
    public String getIdCategoria() {
        return this.id_categoria;   
    }

    // Setter para id_categoria
    public void setIdCategoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    // Getter para categoria
    public String getCategoria() {
        return this.categoria;
    }

    // Setter para categoria
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
