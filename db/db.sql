
CREATE DATABASE compuware;


USE compuwaree;


CREATE TABLE tb_departamento (
    id_departamento CHAR(5) NOT NULL PRIMARY KEY,
    departamento VARCHAR(25) NOT NULL
);


CREATE TABLE tb_provincia (
    id_provincia CHAR(5) NOT NULL PRIMARY KEY,
    provincia VARCHAR(50) NOT NULL,
    id_departamento CHAR(5) NOT NULL,
    FOREIGN KEY (id_departamento) REFERENCES tb_departamento(id_departamento)
);


CREATE TABLE tb_distrito (
    id_distrito CHAR(5) NOT NULL PRIMARY KEY,
    distrito VARCHAR(50) NOT NULL,
    id_provincia CHAR(5) NOT NULL,
    FOREIGN KEY (id_provincia) REFERENCES tb_provincia(id_provincia)
);


CREATE TABLE tb_cliente (
    id_cliente CHAR(5) NOT NULL PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    ap_paterno VARCHAR(20) NOT NULL,
    ap_materno VARCHAR(20) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    telefono VARCHAR(12) NOT NULL,
    id_distrito CHAR(5) NOT NULL,
    FOREIGN KEY (id_distrito) REFERENCES tb_distrito(id_distrito)
);



CREATE TABLE tb_marca (
    id_marca CHAR(5) NOT NULL PRIMARY KEY,
    marca VARCHAR(30) NOT NULL
);


CREATE TABLE tb_categoria (
    id_categoria CHAR(5) NOT NULL PRIMARY KEY,
    categoria VARCHAR(30) NOT NULL
);


CREATE TABLE tb_producto (
    id_producto CHAR(5) NOT NULL PRIMARY KEY,
    producto VARCHAR(40) NOT NULL,
    costo FLOAT NOT NULL,
    ganancia FLOAT NOT NULL,
    id_marca CHAR(5) NOT NULL,
    id_categoria CHAR(5) NOT NULL,
    FOREIGN KEY (id_marca) REFERENCES tb_marca(id_marca),
    FOREIGN KEY (id_categoria) REFERENCES tb_categoria(id_categoria)
);


CREATE TABLE tb_pedido (
    id_pedido CHAR(5) NOT NULL PRIMARY KEY,
    fecha DATE NOT NULL,
    total FLOAT NOT NULL,
    id_cliente CHAR(5) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES tb_cliente(id_cliente)
);


CREATE TABLE tb_detalle_pedido (
    cantidad INT NOT NULL,
    precio_unitario FLOAT NOT NULL,
    precio_subtotal FLOAT NOT NULL,
    id_producto CHAR(5) NOT NULL,
    id_pedido CHAR(5) NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES tb_producto(id_producto),
    FOREIGN KEY (id_pedido) REFERENCES tb_pedido(id_pedido)
);


CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE() 
);

CREATE PROCEDURE sp_consultar_marca
    @id_marca VARCHAR(5)  
AS
BEGIN
    
    SELECT 
        id_marca, 
        marca
    FROM 
        tb_marca  
    WHERE 
        id_marca = @id_marca;
END;


CREATE PROCEDURE sp_consultar_categoria
    @id_categoria VARCHAR(5)
AS
BEGIN
    
    SELECT 
        id_categoria, 
        categoria
    FROM 
        tb_categoria  
    WHERE 
        id_categoria = @id_categoria;
END;


CREATE PROCEDURE sp_listar_categoria
AS
BEGIN
   
    SELECT 
        id_categoria,   
        categoria       
    FROM 
        tb_categoria   
    ORDER BY 
        categoria;     
END;

CREATE PROCEDURE sp_listar_marca
AS
BEGIN
   
    SELECT 
        id_marca,   
        marca      
    FROM 
        tb_marca   
    ORDER BY 
        marca; 
END;

EXEC sp_consultar_marca 'M0001';  
EXEC sp_listar_marca;




CREATE PROCEDURE sp_consultar_producto
    @id_producto VARCHAR(5)
AS
BEGIN
    SELECT 
        p.id_producto,
        p.producto,
        p.costo,
        p.ganancia,
        m.marca,
        c.categoria
    FROM 
        tb_producto p
    INNER JOIN 
        tb_marca m ON p.id_marca = m.id_marca
    INNER JOIN 
        tb_categoria c ON p.id_categoria = c.id_categoria
    WHERE 
        p.id_producto = @id_producto;
END;

CREATE PROCEDURE sp_listar_productos
AS
BEGIN
    SELECT 
        p.id_producto,
        p.producto,
        p.costo,
        p.ganancia,
        m.marca,
        c.categoria
    FROM 
        tb_producto p
    INNER JOIN 
        tb_marca m ON p.id_marca = m.id_marca
    INNER JOIN 
        tb_categoria c ON p.id_categoria = c.id_categoria
    ORDER BY 
        p.producto;
END;

CREATE PROCEDURE sp_consultar_clientes
    @id_cliente VARCHAR(5)
AS
BEGIN
    SELECT 
        c.id_cliente,
        c.nombre,
        c.ap_paterno,
        c.ap_materno,
        c.direccion,
        c.correo,
        c.telefono,
        c.id_distrito -- Se mantiene solo el ID del distrito
    FROM 
        tb_cliente c
    WHERE 
        c.id_cliente = @id_cliente;
END;


CREATE PROCEDURE sp_listar_clientes
AS
BEGIN
    SELECT 
        c.id_cliente,
        c.nombre,
        c.ap_paterno,
        c.ap_materno,
        c.direccion,
        c.telefono,
        c.correo,
        d.distrito  -- Obtener el nombre del distrito en lugar del id
    FROM tb_cliente c
    INNER JOIN tb_distrito d ON c.id_distrito = d.id_distrito; -- JOIN con la tabla tb_distrito
END



EXEC sp_listar_clientes;
EXEC sp_consultar_clientes 'C001';

    DROP PROCEDURE sp_consultar_clientes;
	DROP PROCEDURE sp_listar_clientes;



INSERT INTO tb_departamento (id_departamento, departamento)
VALUES 
    ('D001', 'Lima'),
    ('D002', 'Cusco'),
    ('D003', 'Arequipa');



INSERT INTO tb_provincia (id_provincia, provincia, id_departamento)
VALUES 
    ('P001', 'Lima', 'D001'),
    ('P002', 'Huarochirí', 'D001'),
    ('P003', 'Cusco', 'D002'),
    ('P004', 'Urubamba', 'D002'),
    ('P005', 'Arequipa', 'D003');

	INSERT INTO tb_distrito (id_distrito, distrito, id_provincia)
VALUES 
    ('T001', 'Miraflores', 'P001'),
    ('T002', 'San Isidro', 'P001'),
    ('T003', 'Chaclacayo', 'P002'),
    ('T004', 'San Sebastián', 'P003'),
    ('T005', 'Ollantaytambo', 'P004'),
    ('T006', 'Cayma', 'P005');

	INSERT INTO tb_cliente (id_cliente, nombre, ap_paterno, ap_materno, direccion, correo, telefono, id_distrito)
VALUES 
    ('C001', 'Juan', 'Pérez', 'García', 'Av. Arequipa 1234', 'juan.perez@example.com', '987654321', 'T001'),
    ('C002', 'María', 'Lopez', 'Hernandez', 'Calle Lima 456', 'maria.lopez@example.com', '987123456', 'T002'),
    ('C003', 'Luis', 'Castro', 'Vargas', 'Jr. Cusco 789', 'luis.castro@example.com', '987987987', 'T004'),
    ('C004', 'Ana', 'Torres', 'Ramirez', 'Av. Arequipa 321', 'ana.torres@example.com', '987654987', 'T006');

	INSERT INTO tb_marca (id_marca, marca)
VALUES 
    ('M001', 'Samsung'),
    ('M002', 'LG'),
    ('M003', 'Sony');

	INSERT INTO tb_categoria (id_categoria, categoria)
VALUES 
    ('C001', 'Electrodomésticos'),
    ('C002', 'Televisores'),
    ('C003', 'Celulares');

	INSERT INTO tb_producto (id_producto, producto, costo, ganancia, id_marca, id_categoria)
VALUES 
    ('P001', 'Refrigeradora', 1200, 300, 'M001', 'C001'),
    ('P002', 'Televisor 4K', 1500, 400, 'M003', 'C002'),
    ('P003', 'Smartphone Galaxy', 800, 200, 'M001', 'C003'),
    ('P004', 'Lavadora', 1000, 250, 'M002', 'C001');
