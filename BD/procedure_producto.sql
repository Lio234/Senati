DELIMITER $$

-- Procedimiento para agregar un producto
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_agregar_producto`(
    IN p_id_producto CHAR(5),
    IN p_producto VARCHAR(40),
    IN p_costo FLOAT,
    IN p_ganancia FLOAT,
    IN p_id_marca CHAR(5),
    IN p_id_categoria CHAR(5)
)
BEGIN
    INSERT INTO tb_producto (id_producto, producto, costo, ganancia, id_marca, id_categoria)
    VALUES (p_id_producto, p_producto, p_costo, p_ganancia, p_id_marca, p_id_categoria);
END$$

-- Procedimiento para actualizar un producto
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_producto`(
    IN p_id_producto CHAR(5),
    IN p_producto VARCHAR(40),
    IN p_costo FLOAT,
    IN p_ganancia FLOAT,
    IN p_id_marca CHAR(5),
    IN p_id_categoria CHAR(5)
)
BEGIN
    UPDATE tb_producto
    SET producto = p_producto,
        costo = p_costo,
        ganancia = p_ganancia,
        id_marca = p_id_marca,
        id_categoria = p_id_categoria
    WHERE id_producto = p_id_producto;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_producto`(
    IN p_id_producto CHAR(5)
)
BEGIN
    DECLARE contador INT DEFAULT 0;

    -- Contar registros asociados en tb_detalle_pedido
    SELECT COUNT(*) INTO contador
    FROM tb_detalle_pedido
    WHERE id_producto = p_id_producto;

    -- Si existen registros, mostrar mensaje
    IF contador > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar el producto porque tiene registros asociados en tb_detalle_pedido.';
    ELSE
        -- Si no hay registros asociados, eliminar el producto
        DELETE FROM tb_producto
        WHERE id_producto = p_id_producto;
    END IF;
END

-- Procedimiento para obtener todos los productos
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_productos`()
BEGIN
    SELECT p.id_producto, p.producto, p.costo, p.ganancia, 
           m.marca AS nombre_marca, c.categoria AS nombre_categoria
    FROM tb_producto p
    JOIN tb_marca m ON p.id_marca = m.id_marca
    JOIN tb_categoria c ON p.id_categoria = c.id_categoria
    ORDER BY p.producto ASC;
END$$

-- Procedimiento para obtener un producto específico
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_producto`(
    IN p_id_producto CHAR(5)
)
BEGIN
    SELECT p.id_producto, p.producto, p.costo, p.ganancia, 
           p.id_marca, m.marca AS nombre_marca,
           p.id_categoria, c.categoria AS nombre_categoria
    FROM tb_producto p
    JOIN tb_marca m ON p.id_marca = m.id_marca
    JOIN tb_categoria c ON p.id_categoria = c.id_categoria
    WHERE p.id_producto = p_id_producto;
END$$

DELIMITER ;
