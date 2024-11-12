
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_cliente`(
    IN p_id_cliente CHAR(5),
    IN p_nombre VARCHAR(20),
    IN p_ap_paterno VARCHAR(20),
    IN p_ap_materno VARCHAR(20),
    IN p_direccion VARCHAR(50),
    IN p_correo VARCHAR(50),
    IN p_telefono VARCHAR(12),
    IN p_id_distrito CHAR(5)
)
BEGIN
    UPDATE tb_cliente
    SET nombre = p_nombre,
        ap_paterno = p_ap_paterno,
        ap_materno = p_ap_materno,
        direccion = p_direccion,
        correo = p_correo,
        telefono = p_telefono,
        id_distrito = p_id_distrito
    WHERE id_cliente = p_id_cliente;
END$$
DELIMITER ;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_agregar_cliente`(
    IN p_id_cliente CHAR(5),
    IN p_nombre VARCHAR(20),
    IN p_ap_paterno VARCHAR(20),
    IN p_ap_materno VARCHAR(20),
    IN p_direccion VARCHAR(50),
    IN p_correo VARCHAR(50),
    IN p_telefono VARCHAR(12),
    IN p_id_distrito CHAR(5)
)
BEGIN
    INSERT INTO tb_cliente (id_cliente, nombre, ap_paterno, ap_materno, direccion, correo, telefono, id_distrito)
    VALUES (p_id_cliente, p_nombre, p_ap_paterno, p_ap_materno, p_direccion, p_correo, p_telefono, p_id_distrito);
END
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_cliente`(
    IN p_id_cliente CHAR(5)
)
BEGIN
    DELETE FROM tb_cliente
    WHERE id_cliente = p_id_cliente;
END
DELIMITER ;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_clientes`()
BEGIN
    SELECT c.id_cliente, c.nombre, c.ap_paterno, c.ap_materno, c.direccion, c.correo, c.telefono, d.distrito AS nombre_distrito
    FROM tb_cliente c
    JOIN tb_distrito d ON c.id_distrito = d.id_distrito
    ORDER BY c.nombre ASC;
END
DELIMITER ;
DELIMITER $$


CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_cliente`(
    IN p_id_cliente CHAR(5)
)
BEGIN
    SELECT c.id_cliente, c.nombre, c.ap_paterno, c.ap_materno, c.direccion, c.correo, c.telefono, 
           c.id_distrito, d.distrito AS nombre_distrito
    FROM tb_cliente c
    JOIN tb_distrito d ON c.id_distrito = d.id_distrito
    WHERE c.id_cliente = p_id_cliente;
END
DELIMITER ;