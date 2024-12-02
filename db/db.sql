
CREATE DATABASE compuware2;


USE compuware2;


-- TABLAS 

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

-- DATOS
INSERT INTO tb_departamento (id_departamento, departamento) VALUES
('D001', 'Lima'),
('D002', 'Arequipa'),
('D003', 'Cusco');

INSERT INTO tb_provincia (id_provincia, provincia, id_departamento) VALUES
('P001', 'Lima', 'D001'),
('P002', 'Arequipa', 'D002'),
('P003', 'Cusco', 'D003'),
('P004', 'Callao', 'D001'),
('P005', 'Sullana', 'D002');

INSERT INTO tb_distrito (id_distrito, distrito, id_provincia) VALUES
('D0101', 'Miraflores', 'P001'),
('D0102', 'San Isidro', 'P001'),
('D0103', 'Callao', 'P004'),
('D0201', 'Arequipa', 'P002'),
('D0301', 'Centro Histórico', 'P003'),
('D0202', 'Sullana', 'P005');

INSERT INTO tb_cliente (id_cliente, nombre, ap_paterno, ap_materno, direccion, correo, telefono, id_distrito) VALUES
('C001', 'Juan', 'Perez', 'Lopez', 'Av. Los Olivos 123', 'juan.perez@email.com', '987654321', 'D0101'),
('C002', 'Maria', 'Gomez', 'Martinez', 'Calle Tacna 456', 'maria.gomez@email.com', '976543210', 'D0201'),
('C003', 'Carlos', 'Diaz', 'Fernandez', 'Jr. Puno 789', 'carlos.diaz@email.com', '965432109', 'D0301');

INSERT INTO tb_marca (id_marca, marca) VALUES
('M001', 'Samsung'),
('M002', 'Apple'),
('M003', 'Sony');

INSERT INTO tb_categoria (id_categoria, categoria) VALUES
('C001', 'Electrónica'),
('C002', 'Accesorios'),
('C003', 'Celulares');

INSERT INTO tb_producto (id_producto, producto, costo, ganancia, id_marca, id_categoria) VALUES
('P001', 'Samsung Galaxy S22', 800.00, 200.00, 'M001', 'C003'),
('P002', 'iPhone 13', 1000.00, 250.00, 'M002', 'C003'),
('P003', 'Auriculares Sony', 150.00, 50.00, 'M003', 'C002');

INSERT INTO tb_pedido (id_pedido, fecha, total, id_cliente) VALUES
('O001', '2024-11-01', 1000.00, 'C001'),
('O002', '2024-11-02', 1200.00, 'C002'),
('O003', '2024-11-03', 150.00, 'C003');




-- PROCEDURES:

-- CONSULTAR:

CREATE PROCEDURE sp_consultar_departamento
    @id_departamento CHAR(5)
AS
BEGIN
    SELECT id_departamento, departamento
    FROM tb_departamento
    WHERE id_departamento = @id_departamento;
END;


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


CREATE PROCEDURE sp_consultar_cliente
    @id_cliente CHAR(5)
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
        d.distrito,
        p.provincia,
        dep.departamento
    FROM 
        tb_cliente c
    INNER JOIN 
        tb_distrito d ON c.id_distrito = d.id_distrito
    INNER JOIN 
        tb_provincia p ON d.id_provincia = p.id_provincia
    INNER JOIN 
        tb_departamento dep ON p.id_departamento = dep.id_departamento
    WHERE 
        c.id_cliente = @id_cliente;
END;


CREATE PROCEDURE sp_consultar_producto
    @id_producto CHAR(5)
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

CREATE PROCEDURE sp_consultar_pedido
    @id_pedido CHAR(5)
AS
BEGIN
    SELECT 
        p.id_pedido,
        p.fecha,
        p.total,
        CONCAT(c.nombre, ' ', c.ap_paterno, ' ', c.ap_materno) AS cliente
    FROM 
        tb_pedido p
    INNER JOIN 
        tb_cliente c ON p.id_cliente = c.id_cliente
    WHERE 
        p.id_pedido = @id_pedido;
END;

CREATE PROCEDURE sp_consultar_distrito
    @id_distrito CHAR(5)
AS
BEGIN
    SELECT 
        d.id_distrito,
        d.distrito,
        p.provincia,
        dep.departamento
    FROM 
        tb_distrito d
    INNER JOIN 
        tb_provincia p ON d.id_provincia = p.id_provincia
    INNER JOIN 
        tb_departamento dep ON p.id_departamento = dep.id_departamento
    WHERE 
        d.id_distrito = @id_distrito;
END;

CREATE PROCEDURE sp_consultar_provincia
    @id_provincia CHAR(5)
AS
BEGIN
    SELECT 
        p.id_provincia,
        p.provincia,
        dep.departamento
    FROM 
        tb_provincia p
    INNER JOIN 
        tb_departamento dep ON p.id_departamento = dep.id_departamento
    WHERE 
        p.id_provincia = @id_provincia;
END;


-- LISTAR:
CREATE PROCEDURE sp_listar_departamento
AS
BEGIN
   
    SELECT 
        id_departamento,   
        departamento       
    FROM 
        tb_departamento
    ORDER BY 
        departamento;     
END;

CREATE PROCEDURE sp_listar_provincia
AS
BEGIN
    SELECT 
        p.id_provincia, 
        p.provincia, 
        d.departamento
    FROM 
        tb_provincia p
    INNER JOIN 
        tb_departamento d ON p.id_departamento = d.id_departamento;
END;

CREATE PROCEDURE sp_listar_distrito
AS
BEGIN
    SELECT 
        d.id_distrito, 
        d.distrito, 
        p.provincia
    FROM 
        tb_distrito d
    INNER JOIN 
        tb_provincia p ON d.id_provincia= d.id_provincia;
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
		c.correo,
		c.telefono, 
		d.distrito
    FROM 
        tb_cliente c
    INNER JOIN 
        tb_distrito d ON c.id_distrito= d.id_distrito;
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

CREATE PROCEDURE sp_listar_producto
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
        tb_marca m ON p.id_marca= m.id_marca
	INNER JOIN 
        tb_categoria c ON p.id_categoria= c.id_categoria;
END;

CREATE PROCEDURE sp_listar_pedido
AS
BEGIN
    SELECT 
        o.id_pedido, 
        o.fecha, 
        o.total, 
		c.nombre 
		
    FROM 
        tb_pedido o
    INNER JOIN 
        tb_cliente c ON o.id_cliente= c.id_cliente;
END;

EXEC sp_consultar_pedido 'O001';  
EXEC sp_listar_pedido;


