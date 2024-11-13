package Modelos;

public class Producto {

    private String id_producto;
    private String producto;
    private float costo;
    private float ganancia;
    private String id_marca;
    private String id_categoria;

    public Producto() {
    }

    public Producto(String id_producto, String producto,float costo, float ganancia, String id_marca, String id_categoria) {
        this.id_producto = id_producto;
        this.producto = producto;
        this.costo = costo;
        this.ganancia = ganancia;
        this.id_categoria = id_categoria;
        this.id_marca = id_marca;
        
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getGanancia() {
        return ganancia;
    }

    public void setGanancia(float ganancia) {
        this.ganancia = ganancia;
    }

    public String getId_marca() {
        return id_marca;
    }

    public void setId_marca(String id_marca) {
        this.id_marca = id_marca;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

}
