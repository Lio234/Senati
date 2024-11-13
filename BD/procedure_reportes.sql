
CREATE PROCEDURE obtener_resumen_compras()
BEGIN
    SELECT 
        c.id_cliente,
        CONCAT(c.nombre, ' ', c.ap_paterno, ' ', c.ap_materno) AS cliente,
        COUNT(p.id_pedido) AS numero_compras,
        SUM(dp.precio_subtotal) AS total_compras,
        d.distrito,
        p1.provincia,
        p2.departamento
    FROM 
        tb_cliente c
    LEFT JOIN 
        tb_pedido p ON c.id_cliente = p.id_cliente
    LEFT JOIN 
        tb_detalle_pedido dp ON p.id_pedido = dp.id_pedido
    LEFT JOIN 
        tb_distrito d ON c.id_distrito = d.id_distrito
    LEFT JOIN 
        tb_provincia p1 ON d.id_provincia = p1.id_provincia
    LEFT JOIN 
        tb_departamento p2 ON p1.id_departamento = p2.id_departamento
    GROUP BY 
        c.id_cliente
    ORDER BY 
        total_compras DESC;
END $$

DELIMITER ;
LIMITER $$

CREATE PROCEDURE obtener_cliente_mayor_compra()
BEGIN
    SELECT CONCAT(c.nombre, ' ', c.ap_paterno, ' ', c.ap_materno) AS cliente, 
           SUM(dp.precio_subtotal) AS total_compras
    FROM tb_cliente c
    LEFT JOIN tb_pedido p ON c.id_cliente = p.id_cliente
    LEFT JOIN tb_detalle_pedido dp ON p.id_pedido = dp.id_pedido
    GROUP BY c.id_cliente
    ORDER BY total_compras DESC
    LIMIT 1;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE obtener_resumen_compras_por_ubicacion()
BEGIN
    SELECT 
        p2.departamento,
        p1.provincia,
        d.distrito,
        COUNT(p.id_pedido) AS numero_compras,
        SUM(dp.precio_subtotal) AS total_compras
    FROM 
        tb_pedido p
    LEFT JOIN 
        tb_cliente c ON p.id_cliente = c.id_cliente
    LEFT JOIN 
        tb_detalle_pedido dp ON p.id_pedido = dp.id_pedido
    LEFT JOIN 
        tb_distrito d ON c.id_distrito = d.id_distrito
    LEFT JOIN 
        tb_provincia p1 ON d.id_provincia = p1.id_provincia
    LEFT JOIN 
        tb_departamento p2 ON p1.id_departamento = p2.id_departamento
    GROUP BY 
        p2.departamento, p1.provincia, d.distrito
    ORDER BY 
        total_compras DESC;
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE obtener_ubicacion_mayor_compra()
BEGIN
    SELECT 
        p2.departamento,
        p1.provincia,
        d.distrito,
        SUM(dp.precio_subtotal) AS total_compras
    FROM 
        tb_cliente c
    LEFT JOIN 
        tb_pedido p ON c.id_cliente = p.id_cliente
    LEFT JOIN 
        tb_detalle_pedido dp ON p.id_pedido = dp.id_pedido
    LEFT JOIN 
        tb_distrito d ON c.id_distrito = d.id_distrito
    LEFT JOIN 
        tb_provincia p1 ON d.id_provincia = p1.id_provincia
    LEFT JOIN 
        tb_departamento p2 ON p1.id_departamento = p2.id_departamento
    GROUP BY 
        p2.departamento, p1.provincia, d.distrito
    ORDER BY 
        total_compras DESC;
END $$

DELIMITER ;