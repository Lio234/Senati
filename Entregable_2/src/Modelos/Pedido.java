
package Modelos;

import java.util.Date;


public class Pedido {
    private String id_pedido;
    private Date fecha;
    private float total;
    private String id_cliente;

    public Pedido(){
    }
    public Pedido(String id_pedido, Date fecha, float total, String id_cliente) {
        this.id_pedido = id_pedido;
        this.fecha = fecha;
        this.total = total;
        this.id_cliente = id_cliente;
    }

    public String getIdPedido() {
        return id_pedido;
    }

    public void setIdPedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getIdCliente() {
        return this.id_cliente;
    }

    public void setIdCliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }
    
}
