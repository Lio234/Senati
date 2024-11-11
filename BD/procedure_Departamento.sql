USE compuware;

DELIMITER $$
CREATE PROCEDURE sp_agregar_departamento(
    IN p_id_departamento CHAR(5),
    IN p_departamento VARCHAR(25)
)
BEGIN
    INSERT INTO tb_departamento (id_departamento, departamento)
    VALUES (p_id_departamento, p_departamento);
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_obtener_departamentos()
BEGIN
    SELECT * FROM tb_departamento ORDER BY departamento ASC;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_obtener_departamento(
    IN p_id_departamento CHAR(5)
)
BEGIN
    SELECT * FROM tb_departamento
    WHERE id_departamento = p_id_departamento;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_actualizar_departamento(
    IN p_id_departamento CHAR(5),
    IN p_departamento VARCHAR(25)
)
BEGIN
    UPDATE tb_departamento
    SET departamento = p_departamento
    WHERE id_departamento = p_id_departamento;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_eliminar_departamento(
    IN p_id_departamento CHAR(5)
)
BEGIN
    DELETE FROM tb_departamento
    WHERE id_departamento = p_id_departamento;
END$$
DELIMITER ;
