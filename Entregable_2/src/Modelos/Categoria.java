package Modelos;

public class Categoria {
    private String id_categoria;
    private String categoria;
    
    
    public Categoria() {
    }

    
    public Categoria(String id_categoria, String categoria) {
        this.id_categoria = id_categoria;
        this.categoria = categoria;
    }

    
    public String getIdCategoria() {
        return this.id_categoria;   
    }

    
    public void setIdCategoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    
    public String getCategoria() {
        return this.categoria;
    }

    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
