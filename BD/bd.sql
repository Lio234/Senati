-- En caso de tener una bd
drop database compuware;

-- En caso de de no tener una bd
create database Compuware;

-- Usar la bd
use Compuware;

-- Crear la tabla de Departamentos
CREATE TABLE
    tb_departamento (
        id_departamento CHAR(5) NOT NULL PRIMARY KEY,
        departamento VARCHAR(25) NOT NULL
    );

-- Crear la tabla de Provincia
CREATE TABLE
    tb_provincia (
        id_provincia CHAR(5) NOT NULL PRIMARY KEY,
        provincia VARCHAR(50) NOT NULL,
        id_departamento CHAR(5) NOT NULL,
        FOREIGN KEY (id_departamento) REFERENCES tb_departamento (id_departamento)
    );
    
-- Crear la tabla de Distrito
CREATE TABLE
    tb_distrito (
        id_distrito CHAR(5) NOT NULL PRIMARY KEY,
        distrito VARCHAR(50) NOT NULL,
        id_provincia CHAR(5) NOT NULL,
        FOREIGN KEY (id_provincia) REFERENCES tb_provincia (id_provincia)
    );

-- Crear la tabla de Cliente
CREATE TABLE
    tb_cliente (
        id_cliente CHAR(5) NOT NULL PRIMARY KEY,
        nombre VARCHAR(20) NOT NULL,
        ap_paterno VARCHAR(20) NOT NULL,
        ap_materno VARCHAR(20) NOT NULL,
        direccion VARCHAR(50) NOT NULL,
        correo VARCHAR(50) NOT NULL,
        telefono VARCHAR(12) not null,
        id_distrito CHAR(5) NOT NULL,
        FOREIGN KEY (id_distrito) REFERENCES tb_distrito (id_distrito)
    );

-- Crear la tabla de Marca
CREATE TABLE
    tb_marca (
        id_marca CHAR(5) NOT NULL PRIMARY KEY,
        marca VARCHAR(30) NOT NULL
    );

-- Crear la tabla de Categoría
CREATE TABLE
    tb_categoria (
        id_categoria CHAR(5) NOT NULL PRIMARY KEY,
        categoria VARCHAR(30) NOT NULL
    );

-- Crear la tabla de Producto
CREATE TABLE
    tb_producto (
        id_producto CHAR(5) NOT NULL PRIMARY KEY,
        producto VARCHAR(40) NOT NULL,
        costo FLOAT NOT NULL,
        ganancia FLOAT NOT NULL,
        id_marca CHAR(5) NOT NULL,
        id_categoria CHAR(5) NOT NULL,
        FOREIGN KEY (id_marca) REFERENCES tb_marca (id_marca),
        FOREIGN KEY (id_categoria) REFERENCES tb_categoria (id_categoria)
    );

-- Crear la tabla de Pedido
CREATE TABLE
    tb_pedido (
        id_pedido CHAR(5) NOT NULL PRIMARY KEY,
        fecha DATE NOT NULL,
        total FLOAT NOT NULL,
        id_cliente CHAR(5) NOT NULL,
        FOREIGN KEY (id_cliente) REFERENCES tb_cliente (id_cliente)
    );

-- Crear la tabla de Detalle_Pedido
CREATE TABLE
    tb_detalle_pedido (
        id_detalle_pedido CHAR(5) NOT NULL PRIMARY KEY,
        cantidad INT NOT NULL,
        precio_unitario FLOAT NOT NULL,
        precio_subtotal FLOAT NOT NULL,
        id_producto CHAR(5) NOT NULL,
        id_pedido CHAR(5) NOT NULL,
        FOREIGN KEY (id_producto) REFERENCES tb_producto (id_producto),
        FOREIGN KEY (id_pedido) REFERENCES tb_pedido (id_pedido)
    );