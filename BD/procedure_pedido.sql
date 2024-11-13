-- LISTADO DE PEDIDOS
DELIMITER $$

CREATE PROCEDURE sp_listar_pedido()
BEGIN
    SELECT 
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
    FROM tb_pedido p
    INNER JOIN tb_cliente c ON p.id_cliente = c.id_cliente
    INNER JOIN tb_detalle_pedido dp ON p.id_pedido = dp.id_pedido
    INNER JOIN tb_producto prod ON dp.id_producto = prod.id_producto;
END $$

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
DELIMITER //

CREATE PROCEDURE sp_eliminar_pedido_y_detalles(IN p_id_pedido VARCHAR(20))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Eliminar los detalles del pedido
    DELETE FROM tb_detalle_pedido WHERE id_pedido = p_id_pedido;

    -- Eliminar el pedido
    DELETE FROM tb_pedido WHERE id_pedido = p_id_pedido;

    COMMIT;
END //

DELIMITER ;



DELIMITER $$


DELIMITER $$

CREATE PROCEDURE sp_obtener_pedido(
    IN p_id_pedido CHAR(5)
)
BEGIN
    SELECT 
        tb_pedido.id_pedido,
        CONCAT(tb_cliente.id_cliente, ' - ', tb_cliente.nombre) AS cliente,
        tb_pedido.fecha,
        CONCAT(tb_producto.id_producto, ' - ', tb_producto.producto) AS producto,
        tb_detalle_pedido.precio_unitario,
        tb_detalle_pedido.cantidad,
        tb_detalle_pedido.precio_subtotal AS precio_total
    FROM 
        tb_pedido
    INNER JOIN 
        tb_cliente ON tb_pedido.id_cliente = tb_cliente.id_cliente
    INNER JOIN 
        tb_detalle_pedido ON tb_pedido.id_pedido = tb_detalle_pedido.id_pedido
    INNER JOIN 
        tb_producto ON tb_detalle_pedido.id_producto = tb_producto.id_producto
    WHERE 
        tb_pedido.id_pedido = p_id_pedido;
END$$

DELIMITER ;



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

