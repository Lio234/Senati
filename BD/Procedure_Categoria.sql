use compuware;
DELIMITER $$

CREATE PROCEDURE sp_agregar_categoria(
    IN p_id_categoria CHAR(5),
    IN p_categoria VARCHAR(30)
)
BEGIN
    INSERT INTO tb_categoria (id_categoria, categoria)
    VALUES (p_id_categoria, p_categoria);
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_obtener_categorias()
BEGIN
    SELECT * FROM tb_categoria ORDER BY categoria ASC;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_actualizar_categoria(
    IN p_id_categoria CHAR(5),
    IN p_categoria VARCHAR(30)
)
BEGIN
    UPDATE tb_categoria
    SET categoria = p_categoria
    WHERE id_categoria = p_id_categoria;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_eliminar_categoria(
    IN p_id_categoria CHAR(5)
)
BEGIN
    DELETE FROM tb_categoria
    WHERE id_categoria = p_id_categoria;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE sp_obtener_categoria(
    IN p_id_categoria CHAR(5)
)
BEGIN
    SELECT * FROM tb_categoria
    WHERE id_categoria = p_id_categoria;
END$$

DELIMITER ;
