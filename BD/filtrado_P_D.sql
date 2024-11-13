DELIMITER $$

CREATE PROCEDURE FiltrarPedidosYDetalles (
    IN p_id_pedido CHAR(5) NULL,
    IN p_id_cliente CHAR(5) NULL,
    IN p_fecha DATE NULL
)
BEGIN
    -- Seleccionar los pedidos filtrados
    SELECT 
        p.id_pedido,
        p.fecha,
        p.total,
        c.nombre AS cliente_nombre,
        c.ap_paterno AS cliente_ap_paterno,
        c.ap_materno AS cliente_ap_materno
    FROM 
        tb_pedido p
    JOIN 
        tb_cliente c ON p.id_cliente = c.id_cliente
    WHERE
        (p_id_pedido IS NULL OR p.id_pedido = p_id_pedido) AND
        (p_id_cliente IS NULL OR p.id_cliente = p_id_cliente) AND
        (p_fecha IS NULL OR p.fecha = p_fecha);
    
    -- Seleccionar los detalles de los pedidos filtrados
    SELECT 
        dp.id_pedido,
        dp.id_producto,
        pr.producto,
        dp.cantidad,
        dp.precio_unitario,
        dp.precio_subtotal
    FROM 
        tb_detalle_pedido dp
    JOIN 
        tb_producto pr ON dp.id_producto = pr.id_producto
    WHERE
        (p_id_pedido IS NULL OR dp.id_pedido = p_id_pedido);
        
END $$

DELIMITER ;
