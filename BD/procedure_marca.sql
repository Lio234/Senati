use compuware;

DELIMITER $$
CREATE PROCEDURE sp_agregar_marca(
    IN p_id_marca CHAR(5),
    IN p_marca VARCHAR(30)
)
BEGIN
    INSERT INTO tb_marca (id_marca, marca)
    VALUES (p_id_marca, p_marca);
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_obtener_marcas()
BEGIN
    SELECT * FROM tb_marca ORDER BY marca ASC;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_actualizar_marca(
    IN p_id_marca CHAR(5),
    IN p_marca VARCHAR(30)
)
BEGIN
    UPDATE tb_marca
    SET marca = p_marca
    WHERE id_marca = p_id_marca;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminar_marca(
    IN p_id_marca CHAR(5)
)
BEGIN
    DELETE FROM tb_marca
    WHERE id_marca = p_id_marca;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_obtener_marca(
    IN p_id_marca CHAR(5)
)
BEGIN
    SELECT * FROM tb_marca
    WHERE id_marca = p_id_marca;
END$$
DELIMITER ;
