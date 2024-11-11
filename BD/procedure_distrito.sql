use compuware;

DELIMITER $$

CREATE PROCEDURE sp_agregar_distrito(
    IN p_id_distrito CHAR(5),
    IN p_distrito VARCHAR(50),
    IN p_id_provincia CHAR(5)
)
BEGIN
    INSERT INTO compuware.tb_distrito (id_distrito, distrito, id_provincia)
    VALUES (p_id_distrito, p_distrito, p_id_provincia);
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_obtener_distritos()
BEGIN
    SELECT * FROM compuware.tb_distrito ORDER BY distrito ASC;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_actualizar_distrito(
    IN p_id_distrito CHAR(5),
    IN p_distrito VARCHAR(50),
    IN p_id_provincia CHAR(5)
)
BEGIN
    UPDATE compuware.tb_distrito
    SET distrito = p_distrito, id_provincia = p_id_provincia
    WHERE id_distrito = p_id_distrito;
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_eliminar_distrito(
    IN p_id_distrito CHAR(5)
)
BEGIN
    DELETE FROM compuware.tb_distrito
    WHERE id_distrito = p_id_distrito;
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_obtener_distrito(
    IN p_id_distrito CHAR(5)
)
BEGIN
    SELECT * FROM compuware.tb_distrito
    WHERE id_distrito = p_id_distrito;
END$$

DELIMITER ;


