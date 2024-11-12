-- LISTADO DE PEDIDOS
DELIMITER $$

CREATE PROCEDURE sp_listar_pedidos()
BEGIN
    SELECT 
        p.id_pedido,
        p.fecha,
        p.total,
        c.nombre AS cliente
    FROM tb_pedido p
    INNER JOIN tb_cliente c ON p.id_cliente = c.id_cliente
    ORDER BY p.fecha DESC;
END$$

DELIMITER ;


-- FILTRADO DE PEDIDOS POR CLIENTE
DELIMITER $$

CREATE PROCEDURE sp_filtrar_pedidos_por_cliente(IN id_cliente_param CHAR(5))
BEGIN
    SELECT 
        p.id_pedido,
        p.fecha,
        p.total,
        c.nombre AS cliente
    FROM tb_pedido p
    INNER JOIN tb_cliente c ON p.id_cliente = c.id_cliente
    WHERE p.id_cliente = id_cliente_param;
END$$

DELIMITER ;

-- AGREGAR DATOS DE PEDIDO
DELIMITER //

CREATE PROCEDURE sp_guardarPedido(
    IN p_idPedido CHAR(5),
    IN p_fecha DATE,
    IN p_total FLOAT,
    IN p_idCliente CHAR(5)
)
BEGIN
    INSERT INTO tb_pedido (id_pedido, fecha, total, id_cliente)
    VALUES (p_idPedido, p_fecha, p_total, p_idCliente);
END //

DELIMITER ;



-- EDITAR PEDIDO

DELIMITER $$

CREATE PROCEDURE sp_actualizar_pedido(
    IN p_id_pedido CHAR(5),
    IN p_fecha DATE,
    IN p_total FLOAT,
    IN p_id_cliente CHAR(5) 
)
BEGIN
    UPDATE tb_pedido
    SET fecha = p_fecha, total = p_total, id_cliente = p_id_cliente
    WHERE id_pedido = p_id_pedido;
END$$

DELIMITER ;



-- ELIMINAR PEDIDO
DELIMITER $$

CREATE PROCEDURE sp_eliminar_pedido(IN p_id_pedido CHAR(5))
BEGIN
    -- Eliminar detalles asociados al pedido
    DELETE FROM tb_detalle_pedido WHERE id_pedido = p_id_pedido;
    
    -- Eliminar el pedido de la tabla tb_pedido
    DELETE FROM tb_pedido WHERE id_pedido = p_id_pedido;
END$$

DELIMITER ;



