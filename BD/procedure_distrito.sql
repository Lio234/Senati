use compuware;

DELIMITER $$

CREATE PROCEDURE agregar_distrito(
    IN p_id_distrito INT,
    IN p_distrito VARCHAR(100),
    IN p_id_provincia INT
)
BEGIN
    INSERT INTO tb_distrito (id_distrito, distrito, id_provincia) 
    VALUES (p_id_distrito, p_distrito, p_id_provincia);
END $$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE obtener_distritos()
BEGIN
    SELECT d.id_distrito, d.distrito, p.provincia, dep.departamento
    FROM tb_distrito d
    JOIN tb_provincia p ON d.id_provincia = p.id_provincia
    JOIN tb_departamento dep ON p.id_departamento = dep.id_departamento;
END $$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE actualizar_distrito(
    IN p_id_distrito INT,
    IN p_distrito VARCHAR(100),
    IN p_id_provincia INT
)
BEGIN
    UPDATE tb_distrito 
    SET distrito = p_distrito, id_provincia = p_id_provincia 
    WHERE id_distrito = p_id_distrito;
END $$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE eliminar_distrito(
    IN p_id_distrito INT
)
BEGIN
    DELETE FROM tb_distrito WHERE id_distrito = p_id_distrito;
END $$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE mostrar_distritos()
BEGIN
    SELECT d.id_distrito, d.distrito, p.provincia, dep.departamento
    FROM tb_distrito d
    JOIN tb_provincia p ON d.id_provincia = p.id_provincia
    JOIN tb_departamento dep ON p.id_departamento = dep.id_departamento;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_insertar_distrito(
    IN p_id_distrito INT,
    IN p_distrito VARCHAR(255),
    IN p_id_provincia INT
)
BEGIN
    INSERT INTO tb_distrito (id_distrito, distrito, id_provincia) 
    VALUES (p_id_distrito, p_distrito, p_id_provincia);
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_eliminar_distrito(
    IN p_id_distrito INT
)
BEGIN
    DELETE FROM tb_distrito WHERE id_distrito = p_id_distrito;
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_actualizar_distrito(
    IN p_id_distrito INT,
    IN p_distrito VARCHAR(255),
    IN p_id_provincia INT
)
BEGIN
    UPDATE tb_distrito 
    SET distrito = p_distrito, id_provincia = p_id_provincia 
    WHERE id_distrito = p_id_distrito;
END $$

DELIMITER ;