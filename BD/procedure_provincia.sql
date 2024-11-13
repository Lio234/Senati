use compuware;
DELIMITER $$

CREATE PROCEDURE sp_agregar_provincia(
    IN p_id_provincia CHAR(5),
    IN p_provincia VARCHAR(50),
    IN p_id_departamento CHAR(5)
)
BEGIN
    INSERT INTO tb_provincia (id_provincia, provincia, id_departamento)
    VALUES (p_id_provincia, p_provincia, p_id_departamento);
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_obtener_provincias()
BEGIN
    SELECT * FROM tb_provincia ORDER BY provincia ASC;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_actualizar_provincia(
    IN p_id_provincia CHAR(5),
    IN p_provincia VARCHAR(50),
    IN p_id_departamento CHAR(5)
)
BEGIN
    UPDATE tb_provincia
    SET provincia = p_provincia,
        id_departamento = p_id_departamento
    WHERE id_provincia = p_id_provincia;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_eliminar_provincia(
    IN p_id_provincia CHAR(5)
)
BEGIN
    DELETE FROM tb_provincia
    WHERE id_provincia = p_id_provincia;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_obtener_provincia(
    IN p_id_provincia CHAR(5)
)
BEGIN
    SELECT * FROM tb_provincia
    WHERE id_provincia = p_id_provincia;
END$$

DELIMITER ;
