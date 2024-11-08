--Procedure de verificacion usuario----
DELIMITER $$
CREATE PROCEDURE sp_verificar_usuario(
    IN p_email VARCHAR(100),
    IN p_password VARCHAR(255),
    OUT p_result INT
)
BEGIN
    DECLARE v_count INT;
    SELECT COUNT(*) INTO v_count
    FROM users
    WHERE email = p_email
      AND password = SHA2(p_password, 256);
    IF v_count > 0 THEN
        SET p_result = 1;
    ELSE
        SET p_result = 0;
    END IF;
END$$ 
DELIMITER ;

--Registrar usuario--
DELIMITER //

CREATE PROCEDURE register_user(
    IN p_username VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_password VARCHAR(255)
)
BEGIN
    DECLARE email_exists INT DEFAULT 0;
    SELECT COUNT(*) INTO email_exists
    FROM users
    WHERE email = p_email;

    IF email_exists = 0 THEN
        INSERT INTO users (username, email, password)
        VALUES (p_username, p_email, p_password);
    ELSE

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El correo electrónico ya está en uso.';
    END IF;
END //

DELIMITER ;
