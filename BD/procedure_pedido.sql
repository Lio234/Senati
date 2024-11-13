-- LISTADO DE PEDIDOS
DELIMITER $$

CREATE PROCEDURE sp_listar_pedidos()
BEGIN
    SELECT 
<<<<<<< HEAD
        p.id_pedido,
        p.fecha,
        p.total,
        c.nombre AS cliente
=======
        p.id_pedido, 
        c.nombre AS cliente,  -- Selecciona el nombre del cliente
        p.fecha, 
        p.total
    FROM tb_pedido p
    INNER JOIN tb_cliente c ON p.id_cliente = c.id_cliente;
END $$

DELIMITER ;


-- LISTADO DE DETALLES

DELIMITER $$

CREATE PROCEDURE sp_listar_detalle()
BEGIN
    SELECT 
        p.id_pedido, 
        c.nombre AS cliente,  -- Nombre del cliente
        prod.producto,  -- Producto
        dp.precio_unitario,  -- Precio unitario
        dp.cantidad,  -- Cantidad
        (dp.precio_unitario * dp.cantidad) AS precio_total  -- Precio total
>>>>>>> 098e874340846b9ff889c189fb6db166d669c397
    FROM tb_pedido p
    INNER JOIN tb_cliente c ON p.id_cliente = c.id_cliente
    ORDER BY p.fecha DESC;
END$$

DELIMITER ;

<<<<<<< HEAD
call sp_listar_pedidos()
=======
>>>>>>> 098e874340846b9ff889c189fb6db166d669c397

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
<<<<<<< HEAD
=======

DELIMITER $$

CREATE PROCEDURE sp_guardar_pedido(
    IN p_id_cliente CHAR(5),
    IN p_fecha DATE,
    IN p_total FLOAT,
    IN p_id_producto CHAR(5),
    IN p_cantidad INT,
    IN p_precio_unitario FLOAT
)
BEGIN
    DECLARE v_id_pedido CHAR(5);
    DECLARE v_precio_subtotal FLOAT;
    DECLARE pedido_count INT;

    -- Obtener el nuevo ID para el pedido, con prefijo 'PE'
    SELECT COUNT(*) + 1 INTO pedido_count FROM tb_pedido;
    SET v_id_pedido = CONCAT('PE', LPAD(pedido_count, 3, '0'));
    
    -- Insertar en la tabla tb_pedido
    INSERT INTO tb_pedido (id_pedido, fecha, total, id_cliente)
    VALUES (v_id_pedido, p_fecha, p_total, p_id_cliente);
    
    -- Calcular el subtotal para el detalle
    SET v_precio_subtotal = p_cantidad * p_precio_unitario;
    
    -- Insertar en la tabla tb_detalle_pedido sin usar id_detalle_pedido
    INSERT INTO tb_detalle_pedido (cantidad, precio_unitario, precio_subtotal, id_producto, id_pedido)
    VALUES (p_cantidad, p_precio_unitario, v_precio_subtotal, p_id_producto, v_id_pedido);
END $$

DELIMITER ;

-- EDITAR PEDIDO
DELIMITER $$

CREATE PROCEDURE sp_editar_pedido(
    IN p_idPedido CHAR(5),
    IN p_idCliente CHAR(5),
    IN p_fecha DATE,
    IN p_total FLOAT,
    IN p_idProducto CHAR(5),
    IN p_cantidad INT,
    IN p_costoUnitario FLOAT
)
BEGIN
    -- Actualizar el pedido principal
    UPDATE tb_pedido
    SET id_cliente = p_idCliente,
        fecha = p_fecha,
        total = p_total
    WHERE id_pedido = p_idPedido;

    -- Actualizar el detalle del pedido
    UPDATE tb_detalle_pedido
    SET cantidad = p_cantidad,
        precio_unitario = p_costoUnitario,
        precio_subtotal = p_cantidad * p_costoUnitario
    WHERE id_pedido = p_idPedido AND id_producto = p_idProducto;
    
END $$

DELIMITER ;

-- ELIMINAR PEDIDOS Y DETALLES
>>>>>>> 098e874340846b9ff889c189fb6db166d669c397
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


CALL sp_insertar_pedido('PE004','2023-11-10', 500.00, 'C002');

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
CALL sp_actualizar_pedido('PE003', '2023-11-15', 600.00, 'C001');


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

<<<<<<< HEAD
CALL sp_eliminar_pedido('');

=======


DELIMITER $$

CREATE PROCEDURE AgregarProductoDetallePedido(
    IN p_id_pedido CHAR(5),
    IN p_id_producto CHAR(5),
    IN p_cantidad INT,
    IN p_precio_unitario FLOAT
)
BEGIN
    DECLARE p_precio_subtotal FLOAT;

    -- Calcular el precio_subtotal
    SET p_precio_subtotal = p_precio_unitario * p_cantidad;

    -- Insertar el detalle del pedido
    INSERT INTO tb_detalle_pedido (cantidad, precio_unitario, precio_subtotal, id_producto, id_pedido)
    VALUES (p_cantidad, p_precio_unitario, p_precio_subtotal, p_id_producto, p_id_pedido);
    
END $$

DELIMITER ;

>>>>>>> 098e874340846b9ff889c189fb6db166d669c397
