drop database compuware;
create database Compuware;
use Compuware;

CREATE TABLE
    tb_departamento (
        id_departamento CHAR(5) NOT NULL PRIMARY KEY,
        departamento VARCHAR(25) NOT NULL
    );

CREATE TABLE
    tb_provincia (
        id_provincia CHAR(5) NOT NULL PRIMARY KEY,
        provincia VARCHAR(50) NOT NULL,
        provincia_id_departamento CHAR(5) NOT NULL,
        FOREIGN KEY (provincia_id_departamento) REFERENCES tb_departamento (id_departamento)
    );

CREATE TABLE
    tb_distrito (
        id_distrito CHAR(5) NOT NULL PRIMARY KEY,
        distrito VARCHAR(50) NOT NULL,
        distrito_id_provincia CHAR(5) NOT NULL,
        FOREIGN KEY (distrito_id_provincia) REFERENCES tb_provincia (id_provincia)
    );

CREATE TABLE
    tb_cliente (
        id_cliente CHAR(5) NOT NULL PRIMARY KEY,
        nombre VARCHAR(20) NOT NULL,
        ap_paterno VARCHAR(20) NOT NULL,
        ap_materno VARCHAR(20) NOT NULL,
        direccion VARCHAR(50) NOT NULL,
        correo VARCHAR(50) NOT NULL,
        telefono VARCHAR(12) not null,
        cliente_id_distrito CHAR(5) NOT NULL,
        FOREIGN KEY (cliente_id_distrito) REFERENCES tb_distrito (id_distrito)
    );

create table
    tb_area_trabajo (
        id_area char(5) not null primary key,
        nombre varchar(20) not null
    );

create table
    tb_trabajador (
        id_trabajador char(5) not null primary key,
        nombre varchar(50) not null,
        ap_paterno VARCHAR(20) NOT NULL,
        ap_materno VARCHAR(20) NOT NULL,
        direccion VARCHAR(50) NOT NULL,
        correo VARCHAR(50) NOT NULL,
        telefono VARCHAR(12) not null,
        sueldo int not null,
        codigo int (9) not null,
        trabajador_id_area char(5) not null,
        foreign key (trabajador_id_area) references tb_area_trabajo (id_area)
    );

CREATE TABLE
    tb_marca (
        id_marca CHAR(5) NOT NULL PRIMARY KEY,
        marca VARCHAR(30) NOT NULL
    );

CREATE TABLE
    tb_categoria (
        id_categoria CHAR(5) NOT NULL PRIMARY KEY,
        categoria VARCHAR(30) NOT NULL
    );

    

CREATE TABLE
    tb_producto (
        id_producto CHAR(5) NOT NULL PRIMARY KEY,
        producto VARCHAR(40) NOT NULL,
        stock_disponible INT NOT NULL,
        costo FLOAT NOT NULL,
        ganancia FLOAT NOT NULL,
        producto_id_marca CHAR(5) NOT NULL,
        producto_id_categoria CHAR(5) NOT NULL,
        FOREIGN KEY (producto_id_marca) REFERENCES tb_marca (id_marca),
        FOREIGN KEY (producto_id_categoria) REFERENCES tb_categoria (id_categoria)
    );

CREATE TABLE
    tb_proveedor (
        id_proveedor CHAR(5) NOT NULL PRIMARY KEY,
        nombre VARCHAR(50) NOT NULL,
        direccion VARCHAR(100),
        telefono VARCHAR(15),
        correo VARCHAR(50),
        proveedor_id_distrito CHAR(5),
        FOREIGN KEY (proveedor_id_distrito) REFERENCES tb_distrito (id_distrito)
    );

CREATE TABLE
    tb_producto_proveedor (
        id_producto_proveedor CHAR(5) NOT NULL PRIMARY KEY,
        producto_id_producto CHAR(5) NOT NULL,
        proveedor_id_proveedor CHAR(5) NOT NULL,
        precio_proveedor FLOAT NOT NULL,
        FOREIGN KEY (producto_id_producto) REFERENCES tb_producto (id_producto),
        FOREIGN KEY (proveedor_id_proveedor) REFERENCES tb_proveedor (id_proveedor)
    );


CREATE TABLE
    tb_pedido (
        id_pedido CHAR(5) NOT NULL PRIMARY KEY,
        fecha DATE NOT NULL,
        estado VARCHAR(20) NOT NULL,
        total FLOAT NOT NULL,
        pedido_id_cliente CHAR(5) NOT NULL,
        pedido_id_trabajador CHAR(5) NOT NULL,
        FOREIGN KEY (pedido_id_cliente) REFERENCES tb_cliente (id_cliente),
        FOREIGN KEY (pedido_id_trabajador) REFERENCES tb_trabajador (id_trabajador)
    );

CREATE TABLE
    tb_detalle_pedido (
        id_detalle_pedido CHAR(5) NOT NULL PRIMARY KEY,
        cantidad INT NOT NULL,
        precio_unitario FLOAT NOT NULL,
        detalle_id_producto CHAR(5) NOT NULL,
        detalle_id_pedido CHAR(5) NOT NULL,
        FOREIGN KEY (detalle_id_producto) REFERENCES tb_producto (id_producto),
        FOREIGN KEY (detalle_id_pedido) REFERENCES tb_pedido (id_pedido)
    );

CREATE TABLE
    tb_envio (
        id_envio CHAR(5) NOT NULL PRIMARY KEY,
        fecha_envio DATE NOT NULL,
        fecha_entrega_estimada DATE,
        envio_id_pedido CHAR(5) NOT NULL,
        envio_id_trabajador CHAR(5) NOT NULL,
        FOREIGN KEY (envio_id_pedido) REFERENCES tb_pedido (id_pedido),
        FOREIGN KEY (envio_id_trabajador) REFERENCES tb_trabajador (id_trabajador)
    );


-- Datos para tb_departamento
INSERT INTO tb_departamento (id_departamento, departamento) VALUES
('D001', 'Lima'),
('D002', 'Arequipa'),
('D003', 'Cusco');

-- Datos para tb_provincia
INSERT INTO tb_provincia (id_provincia, provincia, provincia_id_departamento) VALUES
('P001', 'Lima', 'D001'),
('P002', 'Camaná', 'D002'),
('P003', 'Urubamba', 'D003');

-- Datos para tb_distrito
INSERT INTO tb_distrito (id_distrito, distrito, distrito_id_provincia) VALUES
('DI001', 'Miraflores', 'P001'),
('DI002', 'Yanahuara', 'P002'),
('DI003', 'Ollantaytambo', 'P003');

-- Datos para tb_cliente
INSERT INTO tb_cliente (id_cliente, nombre, ap_paterno, ap_materno, direccion, correo, telefono, cliente_id_distrito) VALUES
('CL001', 'Juan', 'Pérez', 'García', 'Av. Principal 123', 'juan.perez@mail.com', '987654321', 'DI001'),
('CL002', 'Ana', 'Lopez', 'Martinez', 'Calle Secundaria 456', 'ana.lopez@mail.com', '912345678', 'DI002'),
('CL003', 'Carlos', 'Gomez', 'Ramos', 'Jr. Las Flores 789', 'carlos.gomez@mail.com', '923456789', 'DI003');

-- Datos para tb_area_trabajo
INSERT INTO tb_area_trabajo (id_area, nombre) VALUES
('A001', 'Ventas'),
('A002', 'Logística'),
('A003', 'Administración');

-- Datos para tb_trabajador
INSERT INTO tb_trabajador (id_trabajador, nombre, ap_paterno, ap_materno, direccion, correo, telefono, sueldo, codigo, trabajador_id_area) VALUES
('T001', 'María', 'Torres', 'Quispe', 'Calle Luna 101', 'maria.torres@mail.com', '932165487', 2000, 123456789, 'A001'),
('T002', 'Luis', 'Huaman', 'Cueva', 'Av. Sol 202', 'luis.huaman@mail.com', '965478123', 1800, 987654321, 'A002'),
('T003', 'Pedro', 'Mendoza', 'Cruz', 'Jr. Estrella 303', 'pedro.mendoza@mail.com', '941256789', 2200, 654987321, 'A003');

-- Datos para tb_marca
INSERT INTO tb_marca (id_marca, marca) VALUES
('M001', 'Samsung'),
('M002', 'LG'),
('M003', 'Sony');

-- Datos para tb_categoria
INSERT INTO tb_categoria (id_categoria, categoria) VALUES
('C001', 'Electrónica'),
('C002', 'Electrodomésticos'),
('C003', 'Videojuegos');

-- Datos para tb_producto
INSERT INTO tb_producto (id_producto, producto, stock_disponible, costo, ganancia, producto_id_marca, producto_id_categoria) VALUES
('P001', 'Televisor LED 40"', 50, 300.00, 50.00, 'M001', 'C001'),
('P002', 'Refrigeradora 300L', 20, 500.00, 80.00, 'M002', 'C002'),
('P003', 'Consola PS5', 100, 400.00, 100.00, 'M003', 'C003');

-- Datos para tb_proveedor
INSERT INTO tb_proveedor (id_proveedor, nombre, direccion, telefono, correo, proveedor_id_distrito) VALUES
('PR001', 'Electro Distribuciones S.A.', 'Av. Industrial 123', '987654321', 'ventas@electro.com', 'DI001'),
('PR002', 'Comercial Casa Grande', 'Jr. Comercio 456', '912345678', 'contacto@casagrande.com', 'DI002'),
('PR003', 'Tecnología Global SAC', 'Av. Innovación 789', '923456789', 'info@tecglobal.com', 'DI003');

-- Datos para tb_producto_proveedor
INSERT INTO tb_producto_proveedor (id_producto_proveedor, producto_id_producto, proveedor_id_proveedor, precio_proveedor) VALUES
('PP001', 'P001', 'PR001', 270.00),
('PP002', 'P002', 'PR002', 450.00),
('PP003', 'P003', 'PR003', 350.00);

-- Datos para tb_pedido
INSERT INTO tb_pedido (id_pedido, fecha, estado, total, pedido_id_cliente, pedido_id_trabajador) VALUES
('PED01', '2024-10-14', 'En Proceso', 900.00, 'CL001', 'T001'),
('PED02', '2024-10-15', 'Completado', 1300.00, 'CL002', 'T002');

-- Datos para tb_detalle_pedido
INSERT INTO tb_detalle_pedido (id_detalle_pedido, cantidad, precio_unitario, detalle_id_producto, detalle_id_pedido) VALUES
('DP001', 2, 300.00, 'P001', 'PED01'),
('DP002', 1, 500.00, 'P002', 'PED02'),
('DP003', 1, 400.00, 'P003', 'PED02');

-- Datos para tb_envio
INSERT INTO tb_envio (id_envio, fecha_envio, fecha_entrega_estimada, envio_id_pedido, envio_id_trabajador) VALUES
('E001', '2024-10-14', '2024-10-20', 'PED01', 'T002'),
('E002', '2024-10-15', '2024-10-22', 'PED02', 'T003');

-- Listar todos los departamentos
DELIMITER $$
CREATE PROCEDURE sp_listar_departamentos()
BEGIN
    SELECT * FROM tb_departamento;
END $$
DELIMITER ;


-- Listar todas las provincias
DELIMITER $$
CREATE PROCEDURE sp_listar_provincias()
BEGIN
    SELECT 
        p.id_provincia, 
        p.provincia, 
        d.departamento AS departamento
    FROM tb_provincia p
    INNER JOIN tb_departamento d ON p.provincia_id_departamento = d.id_departamento;
END $$
DELIMITER ;

-- Listar todos los distritos
DELIMITER $$
CREATE PROCEDURE sp_listar_distritos()
BEGIN
    SELECT 
        d.id_distrito, 
        d.distrito, 
        p.provincia AS provincia
    FROM tb_distrito d
    INNER JOIN tb_provincia p ON d.distrito_id_provincia = p.id_provincia;
END $$
DELIMITER ;

-- Listar todas las áreas de trabajo
DELIMITER $$
CREATE PROCEDURE sp_listar_areas_trabajo()
BEGIN
    SELECT * FROM tb_area_trabajo;
END $$
DELIMITER ;

-- Listar Trabajadores
DELIMITER $$
CREATE PROCEDURE sp_listar_trabajadores()
BEGIN
    SELECT 
        t.id_trabajador,
        t.nombre,
        t.ap_paterno,
        t.ap_materno,
        t.direccion,
        t.correo,
        t.telefono,
        a.nombre AS area_trabajo
    FROM 
        tb_trabajador t
    JOIN 
        tb_area_trabajo a ON t.trabajador_id_area = a.id_area
    ORDER BY t.nombre ASC;
END $$
DELIMITER ;

-- Mostrar trabajador por ID
DELIMITER $$
CREATE PROCEDURE sp_mostrar_trabajador_por_id(
    IN id_trabajador CHAR(5)
)
BEGIN
    SELECT 
        t.id_trabajador,
        t.nombre,
        t.ap_paterno,
        t.ap_materno,
        t.direccion,
        t.correo,
        t.telefono,
        t.sueldo,
        t.codigo,
        a.nombre AS area_trabajo
    FROM 
        tb_trabajador t
    JOIN 
        tb_area_trabajo a ON t.trabajador_id_area = a.id_area
    WHERE t.id_trabajador = id_trabajador;
END $$
DELIMITER ;


-- Buscar trabajador por id
DELIMITER $$
CREATE PROCEDURE sp_buscar_trabajador_por_id(IN id CHAR(5))
BEGIN
    SELECT *
    FROM tb_trabajador
    WHERE id_trabajador = id;
END $$
DELIMITER ;

-- Filtrar por trabajador
DELIMITER $$
CREATE PROCEDURE sp_filtrar_por_trabajador(IN t_nombre_trabajador VARCHAR(40))
BEGIN
    SELECT *
    FROM tb_trabajador
    WHERE nombre LIKE CONCAT('%', t_nombre_trabajador, '%');
END $$
DELIMITER ;


-- Registrar trabajador
DELIMITER $$
CREATE PROCEDURE sp_registrar_trabajador(
    IN t_id_trabajador CHAR(5),
    IN t_nombre VARCHAR(50),
    IN t_ap_paterno VARCHAR(20),
    IN t_ap_materno VARCHAR(20),
    IN t_direccion VARCHAR(50),
    IN t_correo VARCHAR(50),
    IN t_telefono VARCHAR(12),
    IN t_sueldo INT,
    IN t_codigo INT,
    IN t_trabajador_id_area CHAR(5)
)
BEGIN
    INSERT INTO tb_trabajador (id_trabajador, nombre, ap_paterno, ap_materno, direccion, correo, telefono, sueldo, codigo, trabajador_id_area)
    VALUES (t_id_trabajador, t_nombre, t_ap_paterno, t_ap_materno, t_direccion, t_correo, t_telefono, t_sueldo, t_codigo, t_trabajador_id_area);
END $$
DELIMITER ;

-- Editar trabajador
DELIMITER $$
CREATE PROCEDURE sp_editar_trabajador(
    IN t_id_trabajador CHAR(5),
    IN t_nombre VARCHAR(50),
    IN t_ap_paterno VARCHAR(20),
    IN t_ap_materno VARCHAR(20),
    IN t_direccion VARCHAR(50),
    IN t_correo VARCHAR(50),
    IN t_telefono VARCHAR(12),
    IN t_sueldo INT,
    IN t_codigo INT,
    IN t_trabajador_id_area CHAR(5)
)
BEGIN
    UPDATE tb_trabajador
    SET 
        nombre = t_nombre,
        ap_paterno = t_ap_paterno,
        ap_materno = t_ap_materno,
        direccion = t_direccion,
        correo = t_correo,
        telefono = t_telefono,
        sueldo = t_sueldo,
        codigo = t_codigo,
        trabajador_id_area = t_trabajador_id_area
    WHERE id_trabajador = t_id_trabajador;
END $$
DELIMITER ;


-- Eliminar Trabajador
DELIMITER //
CREATE PROCEDURE sp_borrar_trabajador(IN trabajador_id CHAR(5))
BEGIN
    -- Verifica si el trabajador tiene pedidos
    DECLARE num_pedidos INT;
    SELECT COUNT(*) INTO num_pedidos
    FROM tb_pedido
    WHERE pedido_id_trabajador = trabajador_id;

    -- Si el trabajador tiene pedidos, se detiene el procedimiento
    IF num_pedidos > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede borrar el trabajador porque tiene pedidos asociados.';
    ELSE
        -- Si no tiene pedidos, se procede a eliminar
        DELETE FROM tb_trabajador WHERE id_trabajador = trabajador_id;
    END IF;
END //
DELIMITER ;

-- listar todas las marca
DELIMITER $$
CREATE PROCEDURE sp_listar_marcas()
BEGIN
    SELECT *
    FROM tb_marca
    ORDER BY marca ASC;
END $$
DELIMITER ;
-- listar todas las marcas
DELIMITER $$
CREATE PROCEDURE sp_buscar_marca_por_id(IN p_id_marca char(5))
BEGIN
    SELECT *
    FROM tb_marca
    WHERE id_marca =p_id_marca;
END $$
DELIMITER ;

-- Mostrar la marca por id
DELIMITER $$
CREATE PROCEDURE sp_mostrar_marca_por_id(
    IN p_id_marca CHAR(5)
)
BEGIN
Select
    id_marca,
    marca
    FROM tb_marca
    WHERE id_marca = p_id_marca;
END $$
DELIMITER ;

call sp_mostrar_marca_por_id("M001");
-- filtrar marca
DELIMITER $$
CREATE PROCEDURE sp_filtrar_marca(
    IN p_nombre_marca VARCHAR(30)
)
BEGIN
    SELECT *
    FROM tb_marca
    WHERE marca LIKE CONCAT('%', p_nombre_marca, '%');
END $$
DELIMITER ;

-- registrar marca
DELIMITER $$
CREATE PROCEDURE sp_registrar_marca(
    IN p_id_marca CHAR(5),
    IN p_marca VARCHAR(30)
)
BEGIN
    INSERT INTO tb_marca (id_marca, marca)
    VALUES (p_id_marca, p_marca);
END $$
DELIMITER ;


-- editar marca
DELIMITER $$
CREATE PROCEDURE sp_editar_marca(
    IN p_id_marca CHAR(5),
    IN p_marca VARCHAR(30)
)
BEGIN
    UPDATE tb_marca
    SET marca = p_marca
    WHERE id_marca = p_id_marca;
END $$
DELIMITER ;


-- Borrar marca
DELIMITER //
CREATE PROCEDURE sp_borrar_marca(
    IN p_id_marca CHAR(5)
)
BEGIN
    -- Verifica si la marca tiene productos asociados
    DECLARE num_productos INT;
    SELECT COUNT(*) INTO num_productos
    FROM tb_producto
    WHERE producto_id_marca = p_id_marca;

    -- Si la categoría tiene productos, se detiene el procedimiento
    IF num_productos > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede borrar la marca porque tiene productos asociados.';
    ELSE
        -- Si no tiene productos, se procede a eliminar
        DELETE FROM tb_marca WHERE id_marca = p_id_marca;
    END IF;
END //
DELIMITER ;


-- listar todas las categorías
DELIMITER $$
CREATE PROCEDURE sp_listar_categorias()
BEGIN
    SELECT *
    FROM tb_categoria
    ORDER BY categoria ASC;
END $$
DELIMITER ;


-- listar todas las categorías
DELIMITER $$
CREATE PROCEDURE sp_buscar_categoria_por_id(IN p_id_categoria char(5))
BEGIN
    SELECT *
    FROM tb_categoria
    WHERE id_categoria =p_id_categoria;
END $$
DELIMITER ;

-- Mostrar la categoria por id
DELIMITER $$
CREATE PROCEDURE sp_mostrar_categoria_por_id(
    IN p_id_categoria CHAR(5)
)
BEGIN
Select
    id_categoria,
    categoria
    FROM tb_categoria
    WHERE id_categoria = p_id_categoria;
END $$
DELIMITER ;


-- filtrar categoria
DELIMITER $$
CREATE PROCEDURE sp_filtrar_categoria(
    IN p_nombre_categoria VARCHAR(30)
)
BEGIN
    SELECT *
    FROM tb_categoria
    WHERE categoria LIKE CONCAT('%', p_nombre_categoria, '%');
END $$
DELIMITER ;

--registrar categoria
DELIMITER $$
CREATE PROCEDURE sp_registrar_categoria(
    IN p_id_categoria CHAR(5),
    IN p_categoria VARCHAR(30)
)
BEGIN
    INSERT INTO tb_categoria (id_categoria, categoria)
    VALUES (p_id_categoria, p_categoria);
END $$
DELIMITER ;


-- editar categoria
DELIMITER $$
CREATE PROCEDURE sp_editar_categoria(
    IN p_id_categoria CHAR(5),
    IN p_categoria VARCHAR(30)
)
BEGIN
    UPDATE tb_categoria
    SET categoria = p_categoria
    WHERE id_categoria = p_id_categoria;
END $$
DELIMITER ;


-- Borrar Categoria
DELIMITER //
CREATE PROCEDURE sp_borrar_categoria(
    IN p_id_categoria CHAR(5)
)
BEGIN
    -- Verifica si la categoría tiene productos asociados
    DECLARE num_productos INT;
    SELECT COUNT(*) INTO num_productos
    FROM tb_producto
    WHERE producto_id_categoria = p_id_categoria;

    -- Si la categoría tiene productos, se detiene el procedimiento
    IF num_productos > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede borrar la categoría porque tiene productos asociados.';
    ELSE
        -- Si no tiene productos, se procede a eliminar
        DELETE FROM tb_categoria WHERE id_categoria = p_id_categoria;
    END IF;
END //
DELIMITER ;


-- Listar todos los clientes 
DELIMITER $$
CREATE PROCEDURE sp_listar_clientes()
BEGIN
    SELECT 
        c.id_cliente,
        c.nombre,
        c.ap_paterno,
        c.ap_materno,
        c.direccion,
        c.correo,
        c.telefono,
        d.distrito,
        p.provincia,
        dep.departamento
    FROM 
        tb_cliente c
    JOIN 
        tb_distrito d ON c.cliente_id_distrito = d.id_distrito
    JOIN 
        tb_provincia p ON d.distrito_id_provincia = p.id_provincia
    JOIN 
        tb_departamento dep ON p.provincia_id_departamento = dep.id_departamento
	order by c.nombre asc;
END $$
DELIMITER ;

-- mostrar todos los clientes segun el codigo
DELIMITER $$
CREATE PROCEDURE sp_mostrar_cliente_por_id(
    IN id_cliente CHAR(5)
)
BEGIN
SELECT 
        c.id_cliente,
        c.nombre,
        c.ap_paterno,
        c.ap_materno,
        c.direccion,
        c.correo,
        c.telefono,
        d.distrito,
        p.provincia,
        dep.departamento
    FROM 
        tb_cliente c
    INNER JOIN tb_distrito d ON c.cliente_id_distrito = d.id_distrito
	INNER JOIN tb_provincia p ON d.distrito_id_provincia = p.id_provincia
    INNER JOIN tb_departamento dep ON p.provincia_id_departamento = dep.id_departamento
    WHERE c.id_cliente = id_cliente;
END $$
DELIMITER ;

-- buscar cliente por id
DELIMITER $$
CREATE PROCEDURE sp_buscar_cliente_por_id(IN id char(5))
BEGIN
    SELECT *
    FROM tb_cliente
    WHERE id_cliente =id;
END $$
DELIMITER ;

-- filtrar clientes por nombre
DELIMITER $$
CREATE PROCEDURE sp_filtrar_por_cliente(IN c_nombre_cliente VARCHAR(40))
BEGIN
    SELECT *
    FROM tb_cliente
    WHERE nombre LIKE CONCAT('%', c_nombre_cliente, '%');
END $$
DELIMITER ;

-- registrar clientes
DELIMITER $$
CREATE PROCEDURE sp_registrar_cliente(
    IN c_id_cliente CHAR(5),
    IN c_nombre VARCHAR(20),
    IN c_ap_paterno VARCHAR(20),
    IN c_ap_materno VARCHAR(20),
    IN c_direccion VARCHAR(50),
    IN c_correo VARCHAR(50),
    IN c_telefono VARCHAR(12),
    IN c_cliente_id_distrito VARCHAR(5)
)
BEGIN
    INSERT INTO tb_cliente (id_cliente, nombre, ap_paterno, ap_materno, direccion, correo, telefono,cliente_id_distrito)
    VALUES (c_id_cliente, c_nombre, c_ap_paterno, c_ap_materno, c_direccion, c_correo, c_telefono, c_cliente_id_distrito);
END $$
DELIMITER ;

-- editar cliente
DELIMITER $$
CREATE PROCEDURE sp_editar_cliente(
    IN c_id_cliente CHAR(5),
    IN c_nombre VARCHAR(20),
    IN c_ap_paterno VARCHAR(20),
    IN c_ap_materno VARCHAR(20),
    IN c_direccion VARCHAR(50),
    IN c_correo VARCHAR(50),
    IN c_telefono VARCHAR(12),
    IN c_cliente_id_distrito VARCHAR(5)
)
BEGIN
    UPDATE tb_cliente
    SET 
        nombre = c_nombre,
        ap_paterno= c_ap_paterno,
        ap_materno= c_ap_materno,
        direccion = c_direccion,
        correo = c_correo,
        telefono = c_telefono,
        cliente_id_distrito = c_cliente_id_distrito
    WHERE id_cliente = c_id_cliente;
END $$
DELIMITER ;


-- Borrar Cliente
DELIMITER //
CREATE PROCEDURE sp_borrar_cliente(IN cliente_id CHAR(5))
BEGIN
    -- Verifica si el cliente tiene pedidos
    DECLARE num_pedidos INT;
    SELECT COUNT(*) INTO num_pedidos
    FROM tb_pedido
    WHERE pedido_id_cliente = cliente_id;

    -- Si el cliente tiene pedidos, se detiene el procedimiento
    IF num_pedidos > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede borrar el cliente porque tiene pedidos asociados.';
    ELSE
        -- Si no tiene pedidos, se procede a eliminar
        DELETE FROM tb_cliente WHERE id_cliente = cliente_id;
    END IF;
END //
DELIMITER ;



-- Listar todos los productos completo
DELIMITER $$
CREATE PROCEDURE sp_listar_productos()
BEGIN
    SELECT 
        p.id_producto, 
        p.producto, 
        p.stock_disponible, 
        p.costo, 
        p.ganancia, 
        (p.costo / (1 - (p.ganancia / 100))) AS precio,
        m.marca AS marca, 
        c.categoria AS categoria
    FROM tb_producto p
    JOIN tb_marca m ON p.producto_id_marca = m.id_marca
    JOIN tb_categoria c ON p.producto_id_categoria = c.id_categoria
    order by p.stock_disponible desc;
END $$
DELIMITER ;


-- mostrar todos los productos segun el codigo
DELIMITER $$
CREATE PROCEDURE sp_mostrar_producto_por_id(
    IN id_producto CHAR(5)
)
BEGIN
    SELECT 
        p.id_producto,  
        p.producto, 
        p.stock_disponible, 
        p.costo, 
        p.ganancia, 
        (p.costo + (p.costo * (p.ganancia / 100))) AS precio, -- Cálculo del precio
        m.marca AS marca, 
        c.categoria AS categoria
    FROM tb_producto p
    INNER JOIN tb_marca m ON p.producto_id_marca = m.id_marca
    INNER JOIN tb_categoria c ON p.producto_id_categoria = c.id_categoria
    WHERE p.id_producto = id_producto;
END $$

DELIMITER ;

-- buscar productos por id
DELIMITER $$
CREATE PROCEDURE sp_buscar_producto_por_id(IN id char(5))
BEGIN
    SELECT *
    FROM tb_producto
    WHERE id_producto =id;
END $$
DELIMITER ;

-- filtrar productos por nombre
DELIMITER $$
CREATE PROCEDURE sp_filtrar_por_producto(IN p_nombre_producto VARCHAR(40))
BEGIN
    SELECT 
        p.id_producto,
        p.producto,
        p.stock_disponible,
        p.costo,
        p.ganancia,
        m.marca,      -- Nombre de la marca
        c.categoria   -- Nombre de la categoría
    FROM tb_producto p
    JOIN tb_marca m ON p.producto_id_marca = m.id_marca
    JOIN tb_categoria c ON p.producto_id_categoria = c.id_categoria
    WHERE p.producto LIKE CONCAT('%', p_nombre_producto, '%');
END $$
DELIMITER ;
call sp_filtrar_por_producto("t")
-- registrar productos
DELIMITER $$
CREATE PROCEDURE sp_registrar_producto(
    IN p_id_producto CHAR(5),
    IN p_producto VARCHAR(40),
    IN p_stock_disponible INT,
    IN p_costo FLOAT,
    IN p_ganancia FLOAT,
    IN p_producto_id_marca CHAR(5),
    IN p_producto_id_categoria CHAR(5)
)
BEGIN
    INSERT INTO tb_producto (id_producto, producto, stock_disponible, costo, ganancia, producto_id_marca, producto_id_categoria)
    VALUES (p_id_producto, p_producto, p_stock_disponible, p_costo, p_ganancia, p_producto_id_marca, p_producto_id_categoria);
END $$
DELIMITER ;


-- editar productos
DELIMITER $$
CREATE PROCEDURE sp_editar_producto(
    IN p_id_producto CHAR(5),
    IN p_producto VARCHAR(40),
    IN p_stock_disponible INT,
    IN p_costo FLOAT,
    IN p_ganancia FLOAT,
    IN p_producto_id_marca CHAR(5),
    IN p_producto_id_categoria CHAR(5)
)
BEGIN
    UPDATE tb_producto
    SET 
        producto = p_producto,
        stock_disponible = p_stock_disponible,
        costo = p_costo,
        ganancia = p_ganancia,
        producto_id_marca = p_producto_id_marca,
        producto_id_categoria = p_producto_id_categoria
    WHERE id_producto = p_id_producto;
END $$
DELIMITER ;


--Borrar Producto
DELIMITER $$
CREATE PROCEDURE sp_borrar_producto(
    IN p_id_producto CHAR(5)
)
BEGIN
    DELETE FROM tb_detalle_pedido
    WHERE detalle_id_producto = p_id_producto;

    DELETE FROM tb_producto_proveedor
    WHERE producto_id_producto = p_id_producto;

    DELETE FROM tb_producto
    WHERE id_producto = p_id_producto;

END $$
DELIMITER ;

-- Listar todos los proveedores
DELIMITER $$
CREATE PROCEDURE sp_listar_proveedores()
BEGIN
    SELECT * FROM tb_proveedor;
END $$
DELIMITER ;

-- Listar todos los productos por proveedor
DELIMITER $$
CREATE PROCEDURE sp_listar_productos_proveedores()
BEGIN
    SELECT 
        pp.id_producto_proveedor, 
        prod.producto AS producto, 
        prov.nombre AS proveedor, 
        pp.precio_proveedor
    FROM tb_producto_proveedor pp
    INNER JOIN tb_producto prod ON pp.producto_id_producto = prod.id_producto
    INNER JOIN tb_proveedor prov ON pp.proveedor_id_proveedor = prov.id_proveedor;
END $$
DELIMITER ;

-- Listar todos los pedidos
DELIMITER $$
CREATE PROCEDURE sp_listar_pedidos()
BEGIN
    SELECT 
        p.id_pedido,
        p.fecha,
        p.estado,
        p.total,
        c.nombre AS cliente,
        t.nombre AS trabajador
    FROM 
        tb_pedido p
    JOIN 
        tb_cliente c ON p.pedido_id_cliente = c.id_cliente
    JOIN 
        tb_trabajador t ON p.pedido_id_trabajador = t.id_trabajador
    ORDER BY p.fecha ASC;
END $$
DELIMITER ;

--Mostrar Pedidos

DELIMITER $$
CREATE PROCEDURE sp_mostrar_pedido_por_id(
    IN p_id_pedido CHAR(5)
)
BEGIN
    SELECT 
        p.id_pedido,
        p.fecha,
        p.estado,
        p.total,
        c.nombre AS cliente,
        t.nombre AS trabajador
    FROM 
        tb_pedido p
    JOIN 
        tb_cliente c ON p.pedido_id_cliente = c.id_cliente
    JOIN 
        tb_trabajador t ON p.pedido_id_trabajador = t.id_trabajador
    WHERE p.id_pedido = p_id_pedido;
END $$
DELIMITER ;

--Buscar Pedidos

DELIMITER $$
CREATE PROCEDURE sp_buscar_pedido_por_id(
    IN p_id_pedido CHAR(5)
)
BEGIN
    SELECT *
    FROM tb_pedido
    WHERE id_pedido = p_id_pedido;
END $$
DELIMITER ;


-- Filtrar Pedidos por estado 
DELIMITER $$
CREATE PROCEDURE sp_filtrar_pedidos_por_estado(
    IN p_estado VARCHAR(20)
)
BEGIN
    SELECT *
    FROM tb_pedido
    WHERE estado LIKE CONCAT('%', p_estado, '%');
END $$
DELIMITER ;

--Registrar Pedido
DELIMITER $$
CREATE PROCEDURE sp_registrar_pedido(
    IN p_id_pedido CHAR(5),
    IN p_fecha DATE,
    IN p_estado VARCHAR(20),
    IN p_total FLOAT,
    IN p_id_cliente CHAR(5),
    IN p_id_trabajador CHAR(5)
)
BEGIN
    INSERT INTO tb_pedido (id_pedido, fecha, estado, total, pedido_id_cliente, pedido_id_trabajador)
    VALUES (p_id_pedido, p_fecha, p_estado, p_total, p_id_cliente, p_id_trabajador);
END $$
DELIMITER ;

--Editar Pedido
DELIMITER $$
CREATE PROCEDURE sp_editar_pedido(
    IN p_id_pedido CHAR(5),
    IN p_fecha DATE,
    IN p_estado VARCHAR(20),
    IN p_total FLOAT,
    IN p_id_cliente CHAR(5),
    IN p_id_trabajador CHAR(5)
)
BEGIN
    UPDATE tb_pedido
    SET 
        fecha = p_fecha,
        estado = p_estado,
        total = p_total,
        pedido_id_cliente = p_id_cliente,
        pedido_id_trabajador = p_id_trabajador
    WHERE id_pedido = p_id_pedido;
END $$
DELIMITER ;


-- Borrar Pedido
DELIMITER //
CREATE PROCEDURE sp_borrar_pedido(
    IN p_id_pedido CHAR(5)
)
BEGIN
    -- Verifica si el pedido tiene detalles
    DECLARE num_detalles INT;
    SELECT COUNT(*) INTO num_detalles
    FROM tb_detalle_pedido
    WHERE detalle_id_pedido = p_id_pedido;

    -- Si el pedido tiene detalles, se detiene el procedimiento
    IF num_detalles > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede borrar el pedido porque tiene detalles asociados.';
    ELSE
        -- Si no tiene detalles, se procede a eliminar
        DELETE FROM tb_pedido WHERE id_pedido = p_id_pedido;
    END IF;
END //
DELIMITER ;


-- Listar todos los detalles de pedidos
DELIMITER $$
CREATE PROCEDURE sp_listar_detalles_pedidos()
BEGIN
    SELECT 
        dp.id_detalle_pedido, 
        dp.cantidad, 
        dp.precio_unitario, 
        prod.producto AS producto, 
        p.id_pedido AS pedido
    FROM tb_detalle_pedido dp
    INNER JOIN tb_producto prod ON dp.detalle_id_producto = prod.id_producto
    INNER JOIN tb_pedido p ON dp.detalle_id_pedido = p.id_pedido;
END $$
DELIMITER ;

-- Listar todos los envíos
DELIMITER $$
CREATE PROCEDURE sp_listar_envios()
BEGIN
    SELECT 
        e.id_envio, 
        e.fecha_envio, 
        e.fecha_entrega_estimada, 
        p.id_pedido AS pedido, 
        t.nombre AS trabajador
    FROM tb_envio e
    INNER JOIN tb_pedido p ON e.envio_id_pedido = p.id_pedido
    INNER JOIN tb_trabajador t ON e.envio_id_trabajador = t.id_trabajador;
END $$
DELIMITER ;
