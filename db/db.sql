
CREATE DATABASE compuware;


USE compuware;


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




