
package Modelos;

import java.util.Date;


public class MPedido {
    private String id_pedido;
    private Date fecha;
    private float total;
    private String id_cliente;
    
    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public MPedido(String id_pedido, Date fecha, float total, String id_cliente) {
        this.id_pedido = id_pedido;
        this.fecha = fecha;
        this.total = total;
        this.id_cliente = id_cliente;
    }



    
}
