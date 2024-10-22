-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 22-10-2024 a las 03:01:03
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `compuware`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`` PROCEDURE `sp_borrar_cliente` (IN `cliente_id` CHAR(5))   BEGIN
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
END$$

CREATE DEFINER=`` PROCEDURE `sp_borrar_producto` (IN `p_id_producto` CHAR(5))   BEGIN
    -- 1. Eliminar registros relacionados en tb_detalle_pedido
    DELETE FROM tb_detalle_pedido
    WHERE detalle_id_producto = p_id_producto;

    -- 2. Eliminar registros relacionados en tb_producto_proveedor
    DELETE FROM tb_producto_proveedor
    WHERE producto_id_producto = p_id_producto;

    -- 3. Finalmente, eliminar el producto de tb_producto
    DELETE FROM tb_producto
    WHERE id_producto = p_id_producto;

END$$

CREATE DEFINER=`` PROCEDURE `sp_buscar_cliente_por_id` (IN `id` CHAR(5))   BEGIN
    SELECT *
    FROM tb_cliente
    WHERE id_cliente =id;
END$$

CREATE DEFINER=`` PROCEDURE `sp_buscar_producto_por_id` (IN `id` CHAR(5))   BEGIN
    SELECT *
    FROM tb_producto
    WHERE id_producto =id;
END$$

CREATE DEFINER=`` PROCEDURE `sp_editar_cliente` (IN `c_id_cliente` CHAR(5), IN `c_nombre` VARCHAR(20), IN `c_ap_paterno` VARCHAR(20), IN `c_ap_materno` VARCHAR(20), IN `c_direccion` VARCHAR(50), IN `c_correo` VARCHAR(50), IN `c_telefono` VARCHAR(12), IN `c_cliente_id_distrito` VARCHAR(5))   BEGIN
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
END$$

CREATE DEFINER=`` PROCEDURE `sp_editar_producto` (IN `p_id_producto` CHAR(5), IN `p_producto` VARCHAR(40), IN `p_stock_disponible` INT, IN `p_costo` FLOAT, IN `p_ganancia` FLOAT, IN `p_producto_id_marca` CHAR(5), IN `p_producto_id_categoria` CHAR(5))   BEGIN
    UPDATE tb_producto
    SET 
        producto = p_producto,
        stock_disponible = p_stock_disponible,
        costo = p_costo,
        ganancia = p_ganancia,
        producto_id_marca = p_producto_id_marca,
        producto_id_categoria = p_producto_id_categoria
    WHERE id_producto = p_id_producto;
END$$

CREATE DEFINER=`` PROCEDURE `sp_filtrar_por_cliente` (IN `c_nombre_cliente` VARCHAR(40))   BEGIN
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
    INNER JOIN tb_distrito d ON c.cliente_id_distrito = d.id_distrito
    WHERE c.nombre LIKE CONCAT('%', c_nombre_cliente, '%'); -- Aquí filtras por nombre, no por id_cliente
END$$

CREATE DEFINER=`` PROCEDURE `sp_filtrar_por_producto` (IN `p_nombre_producto` VARCHAR(40))   BEGIN
    SELECT 
        p.id_producto, 
        p.producto, 
        p.stock_disponible, 
        p.costo, 
        p.ganancia, 
        ROUND((p.costo / (1 - (p.ganancia / 100))), 2) AS precio,
        m.marca AS marca, 
        c.categoria AS categoria
    FROM tb_producto p
    JOIN tb_marca m ON p.producto_id_marca = m.id_marca
    JOIN tb_categoria c ON p.producto_id_categoria = c.id_categoria
    WHERE 
        p.producto LIKE CONCAT('%', p_nombre_producto, '%');
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_areas_trabajo` ()   BEGIN
    SELECT * FROM tb_area_trabajo;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_categorias` ()   BEGIN
    SELECT * FROM tb_categoria order by categoria asc;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_clientes` ()   BEGIN
    SELECT 
        c.id_cliente, 
        c.nombre, 
        c.ap_paterno, 
        c.ap_materno, 
        c.direccion, 
        c.correo, 
        c.telefono, 
        d.distrito AS distrito
    FROM tb_cliente c
    INNER JOIN tb_distrito d ON c.cliente_id_distrito = d.id_distrito;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_departamentos` ()   BEGIN
    SELECT * FROM tb_departamento;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_detalles_pedidos` ()   BEGIN
    SELECT 
        dp.id_detalle_pedido, 
        dp.cantidad, 
        dp.precio_unitario, 
        prod.producto AS producto, 
        p.id_pedido AS pedido
    FROM tb_detalle_pedido dp
    INNER JOIN tb_producto prod ON dp.detalle_id_producto = prod.id_producto
    INNER JOIN tb_pedido p ON dp.detalle_id_pedido = p.id_pedido;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_distritos` ()   BEGIN
    SELECT 
        d.id_distrito, 
        d.distrito, 
        p.provincia AS provincia
    FROM tb_distrito d
    INNER JOIN tb_provincia p ON d.distrito_id_provincia = p.id_provincia;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_envios` ()   BEGIN
    SELECT 
        e.id_envio, 
        e.fecha_envio, 
        e.fecha_entrega_estimada, 
        p.id_pedido AS pedido, 
        t.nombre AS trabajador
    FROM tb_envio e
    INNER JOIN tb_pedido p ON e.envio_id_pedido = p.id_pedido
    INNER JOIN tb_trabajador t ON e.envio_id_trabajador = t.id_trabajador;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_marcas` ()   BEGIN
    SELECT * FROM tb_marca order by marca asc;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_pedidos` ()   BEGIN
    SELECT 
        p.id_pedido, 
        p.fecha, 
        p.estado, 
        c.nombre AS cliente, 
        t.nombre AS trabajador
    FROM tb_pedido p
    INNER JOIN tb_cliente c ON p.pedido_id_cliente = c.id_cliente
    INNER JOIN tb_trabajador t ON p.pedido_id_trabajador = t.id_trabajador;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_productos` ()   BEGIN
    SELECT 
        p.id_producto, 
        p.producto, 
        p.stock_disponible, 
        p.costo, 
        p.ganancia, 
        ROUND((p.costo / (1 - (p.ganancia / 100))), 2) AS precio,
        m.marca AS marca, 
        c.categoria AS categoria
    FROM tb_producto p
    JOIN tb_marca m ON p.producto_id_marca = m.id_marca
    JOIN tb_categoria c ON p.producto_id_categoria = c.id_categoria
    order by p.stock_disponible desc;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_productos_proveedores` ()   BEGIN
    SELECT 
        pp.id_producto_proveedor, 
        prod.producto AS producto, 
        prov.nombre AS proveedor, 
        pp.precio_proveedor
    FROM tb_producto_proveedor pp
    INNER JOIN tb_producto prod ON pp.producto_id_producto = prod.id_producto
    INNER JOIN tb_proveedor prov ON pp.proveedor_id_proveedor = prov.id_proveedor;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_proveedores` ()   BEGIN
    SELECT * FROM tb_proveedor;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_provincias` ()   BEGIN
    SELECT 
        p.id_provincia, 
        p.provincia, 
        d.departamento AS departamento
    FROM tb_provincia p
    INNER JOIN tb_departamento d ON p.provincia_id_departamento = d.id_departamento;
END$$

CREATE DEFINER=`` PROCEDURE `sp_listar_trabajadores` ()   BEGIN
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
    FROM tb_trabajador t
    INNER JOIN tb_area_trabajo a ON t.trabajador_id_area = a.id_area;
END$$

CREATE DEFINER=`` PROCEDURE `sp_mostrar_cliente_por_id` (IN `id_cliente` CHAR(5))   BEGIN
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
END$$

CREATE DEFINER=`` PROCEDURE `sp_mostrar_producto_por_id` (IN `id_producto` CHAR(5))   BEGIN
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
END$$

CREATE DEFINER=`` PROCEDURE `sp_registrar_cliente` (IN `c_id_cliente` CHAR(5), IN `c_nombre` VARCHAR(20), IN `c_ap_paterno` VARCHAR(20), IN `c_ap_materno` VARCHAR(20), IN `c_direccion` VARCHAR(50), IN `c_correo` VARCHAR(50), IN `c_telefono` VARCHAR(12), IN `c_cliente_id_distrito` VARCHAR(5))   BEGIN
    INSERT INTO tb_cliente (id_cliente, nombre, ap_paterno, ap_materno, direccion, correo, telefono,cliente_id_distrito)
    VALUES (c_id_cliente, c_nombre, c_ap_paterno, c_ap_materno, c_direccion, c_correo, c_telefono, c_cliente_id_distrito);
END$$

CREATE DEFINER=`` PROCEDURE `sp_registrar_producto` (IN `p_id_producto` CHAR(5), IN `p_producto` VARCHAR(40), IN `p_stock_disponible` INT, IN `p_costo` FLOAT, IN `p_ganancia` FLOAT, IN `p_producto_id_marca` CHAR(5), IN `p_producto_id_categoria` CHAR(5))   BEGIN
    INSERT INTO tb_producto (id_producto, producto, stock_disponible, costo, ganancia, producto_id_marca, producto_id_categoria)
    VALUES (p_id_producto, p_producto, p_stock_disponible, p_costo, p_ganancia, p_producto_id_marca, p_producto_id_categoria);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_area_trabajo`
--

CREATE TABLE `tb_area_trabajo` (
  `id_area` char(5) NOT NULL,
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_area_trabajo`
--

INSERT INTO `tb_area_trabajo` (`id_area`, `nombre`) VALUES
('A001', 'Ventas'),
('A002', 'Logística'),
('A003', 'Administración');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_categoria`
--

CREATE TABLE `tb_categoria` (
  `id_categoria` char(5) NOT NULL,
  `categoria` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_categoria`
--

INSERT INTO `tb_categoria` (`id_categoria`, `categoria`) VALUES
('C001', 'Electrónica'),
('C002', 'Electrodomésticos'),
('C003', 'Videojuegos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_cliente`
--

CREATE TABLE `tb_cliente` (
  `id_cliente` char(5) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `ap_paterno` varchar(20) NOT NULL,
  `ap_materno` varchar(20) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `telefono` varchar(12) NOT NULL,
  `cliente_id_distrito` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_cliente`
--

INSERT INTO `tb_cliente` (`id_cliente`, `nombre`, `ap_paterno`, `ap_materno`, `direccion`, `correo`, `telefono`, `cliente_id_distrito`) VALUES
('CL001', 'Juan', 'Pérez', 'García', 'Av. Los Rosales 123', 'juan.perez@example.com', '987654321', 'DI001'),
('CL002', 'Ana', 'Lopez', 'Martinez', 'Calle Secundaria 456', 'ana.lopez@mail.com', '912345678', 'DI002'),
('CL003', 'Carlos', 'Gomez', 'Ramos', 'Jr. Las Flores 789', 'carlos.gomez@mail.com', '923456789', 'DI003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_departamento`
--

CREATE TABLE `tb_departamento` (
  `id_departamento` char(5) NOT NULL,
  `departamento` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_departamento`
--

INSERT INTO `tb_departamento` (`id_departamento`, `departamento`) VALUES
('D001', 'Lima'),
('D002', 'Arequipa'),
('D003', 'Cusco');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_detalle_pedido`
--

CREATE TABLE `tb_detalle_pedido` (
  `id_detalle_pedido` char(5) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` float NOT NULL,
  `detalle_id_producto` char(5) NOT NULL,
  `detalle_id_pedido` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_detalle_pedido`
--

INSERT INTO `tb_detalle_pedido` (`id_detalle_pedido`, `cantidad`, `precio_unitario`, `detalle_id_producto`, `detalle_id_pedido`) VALUES
('DP001', 2, 300, 'P001', 'PED01'),
('DP002', 1, 500, 'P002', 'PED02'),
('DP003', 1, 400, 'P003', 'PED02');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_distrito`
--

CREATE TABLE `tb_distrito` (
  `id_distrito` char(5) NOT NULL,
  `distrito` varchar(50) NOT NULL,
  `distrito_id_provincia` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_distrito`
--

INSERT INTO `tb_distrito` (`id_distrito`, `distrito`, `distrito_id_provincia`) VALUES
('DI001', 'Miraflores', 'P001'),
('DI002', 'Yanahuara', 'P002'),
('DI003', 'Ollantaytambo', 'P003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_envio`
--

CREATE TABLE `tb_envio` (
  `id_envio` char(5) NOT NULL,
  `fecha_envio` date NOT NULL,
  `fecha_entrega_estimada` date DEFAULT NULL,
  `envio_id_pedido` char(5) NOT NULL,
  `envio_id_trabajador` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_envio`
--

INSERT INTO `tb_envio` (`id_envio`, `fecha_envio`, `fecha_entrega_estimada`, `envio_id_pedido`, `envio_id_trabajador`) VALUES
('E001', '2024-10-14', '2024-10-20', 'PED01', 'T002'),
('E002', '2024-10-15', '2024-10-22', 'PED02', 'T003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_marca`
--

CREATE TABLE `tb_marca` (
  `id_marca` char(5) NOT NULL,
  `marca` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_marca`
--

INSERT INTO `tb_marca` (`id_marca`, `marca`) VALUES
('M001', 'Samsung'),
('M002', 'LG'),
('M003', 'Sony');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_pedido`
--

CREATE TABLE `tb_pedido` (
  `id_pedido` char(5) NOT NULL,
  `fecha` date NOT NULL,
  `estado` varchar(20) NOT NULL,
  `total` float NOT NULL,
  `pedido_id_cliente` char(5) NOT NULL,
  `pedido_id_trabajador` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_pedido`
--

INSERT INTO `tb_pedido` (`id_pedido`, `fecha`, `estado`, `total`, `pedido_id_cliente`, `pedido_id_trabajador`) VALUES
('PED01', '2024-10-14', 'En Proceso', 900, 'CL001', 'T001'),
('PED02', '2024-10-15', 'Completado', 1300, 'CL002', 'T002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_producto`
--

CREATE TABLE `tb_producto` (
  `id_producto` char(5) NOT NULL,
  `producto` varchar(40) NOT NULL,
  `stock_disponible` int(11) NOT NULL,
  `costo` float NOT NULL,
  `ganancia` float NOT NULL,
  `producto_id_marca` char(5) NOT NULL,
  `producto_id_categoria` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_producto`
--

INSERT INTO `tb_producto` (`id_producto`, `producto`, `stock_disponible`, `costo`, `ganancia`, `producto_id_marca`, `producto_id_categoria`) VALUES
('P001', 'Televisor LED 40\"', 12, 300, 0.1, 'M001', 'C002'),
('P002', 'Refrigeradora 300L', 10, 500, 0.3, 'M002', 'C002'),
('P003', 'Consola PS4', 100, 2, 0.1, 'M003', 'C003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_producto_proveedor`
--

CREATE TABLE `tb_producto_proveedor` (
  `id_producto_proveedor` char(5) NOT NULL,
  `producto_id_producto` char(5) NOT NULL,
  `proveedor_id_proveedor` char(5) NOT NULL,
  `precio_proveedor` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_producto_proveedor`
--

INSERT INTO `tb_producto_proveedor` (`id_producto_proveedor`, `producto_id_producto`, `proveedor_id_proveedor`, `precio_proveedor`) VALUES
('PP001', 'P001', 'PR001', 270),
('PP002', 'P002', 'PR002', 450),
('PP003', 'P003', 'PR003', 350);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_proveedor`
--

CREATE TABLE `tb_proveedor` (
  `id_proveedor` char(5) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `proveedor_id_distrito` char(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_proveedor`
--

INSERT INTO `tb_proveedor` (`id_proveedor`, `nombre`, `direccion`, `telefono`, `correo`, `proveedor_id_distrito`) VALUES
('PR001', 'Electro Distribuciones S.A.', 'Av. Industrial 123', '987654321', 'ventas@electro.com', 'DI001'),
('PR002', 'Comercial Casa Grande', 'Jr. Comercio 456', '912345678', 'contacto@casagrande.com', 'DI002'),
('PR003', 'Tecnología Global SAC', 'Av. Innovación 789', '923456789', 'info@tecglobal.com', 'DI003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_provincia`
--

CREATE TABLE `tb_provincia` (
  `id_provincia` char(5) NOT NULL,
  `provincia` varchar(50) NOT NULL,
  `provincia_id_departamento` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_provincia`
--

INSERT INTO `tb_provincia` (`id_provincia`, `provincia`, `provincia_id_departamento`) VALUES
('P001', 'Lima', 'D001'),
('P002', 'Camaná', 'D002'),
('P003', 'Urubamba', 'D003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_trabajador`
--

CREATE TABLE `tb_trabajador` (
  `id_trabajador` char(5) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `ap_paterno` varchar(20) NOT NULL,
  `ap_materno` varchar(20) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `telefono` varchar(12) NOT NULL,
  `sueldo` int(11) NOT NULL,
  `codigo` int(9) NOT NULL,
  `trabajador_id_area` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_trabajador`
--

INSERT INTO `tb_trabajador` (`id_trabajador`, `nombre`, `ap_paterno`, `ap_materno`, `direccion`, `correo`, `telefono`, `sueldo`, `codigo`, `trabajador_id_area`) VALUES
('T001', 'María', 'Torres', 'Quispe', 'Calle Luna 101', 'maria.torres@mail.com', '932165487', 2000, 123456789, 'A001'),
('T002', 'Luis', 'Huaman', 'Cueva', 'Av. Sol 202', 'luis.huaman@mail.com', '965478123', 1800, 987654321, 'A002'),
('T003', 'Pedro', 'Mendoza', 'Cruz', 'Jr. Estrella 303', 'pedro.mendoza@mail.com', '941256789', 2200, 654987321, 'A003');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tb_area_trabajo`
--
ALTER TABLE `tb_area_trabajo`
  ADD PRIMARY KEY (`id_area`);

--
-- Indices de la tabla `tb_categoria`
--
ALTER TABLE `tb_categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `tb_cliente`
--
ALTER TABLE `tb_cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `cliente_id_distrito` (`cliente_id_distrito`);

--
-- Indices de la tabla `tb_departamento`
--
ALTER TABLE `tb_departamento`
  ADD PRIMARY KEY (`id_departamento`);

--
-- Indices de la tabla `tb_detalle_pedido`
--
ALTER TABLE `tb_detalle_pedido`
  ADD PRIMARY KEY (`id_detalle_pedido`),
  ADD KEY `detalle_id_producto` (`detalle_id_producto`),
  ADD KEY `detalle_id_pedido` (`detalle_id_pedido`);

--
-- Indices de la tabla `tb_distrito`
--
ALTER TABLE `tb_distrito`
  ADD PRIMARY KEY (`id_distrito`),
  ADD KEY `distrito_id_provincia` (`distrito_id_provincia`);

--
-- Indices de la tabla `tb_envio`
--
ALTER TABLE `tb_envio`
  ADD PRIMARY KEY (`id_envio`),
  ADD KEY `envio_id_pedido` (`envio_id_pedido`),
  ADD KEY `envio_id_trabajador` (`envio_id_trabajador`);

--
-- Indices de la tabla `tb_marca`
--
ALTER TABLE `tb_marca`
  ADD PRIMARY KEY (`id_marca`);

--
-- Indices de la tabla `tb_pedido`
--
ALTER TABLE `tb_pedido`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `pedido_id_trabajador` (`pedido_id_trabajador`),
  ADD KEY `pedido_id_cliente` (`pedido_id_cliente`);

--
-- Indices de la tabla `tb_producto`
--
ALTER TABLE `tb_producto`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `producto_id_marca` (`producto_id_marca`),
  ADD KEY `producto_id_categoria` (`producto_id_categoria`);

--
-- Indices de la tabla `tb_producto_proveedor`
--
ALTER TABLE `tb_producto_proveedor`
  ADD PRIMARY KEY (`id_producto_proveedor`),
  ADD KEY `producto_id_producto` (`producto_id_producto`),
  ADD KEY `proveedor_id_proveedor` (`proveedor_id_proveedor`);

--
-- Indices de la tabla `tb_proveedor`
--
ALTER TABLE `tb_proveedor`
  ADD PRIMARY KEY (`id_proveedor`),
  ADD KEY `proveedor_id_distrito` (`proveedor_id_distrito`);

--
-- Indices de la tabla `tb_provincia`
--
ALTER TABLE `tb_provincia`
  ADD PRIMARY KEY (`id_provincia`),
  ADD KEY `provincia_id_departamento` (`provincia_id_departamento`);

--
-- Indices de la tabla `tb_trabajador`
--
ALTER TABLE `tb_trabajador`
  ADD PRIMARY KEY (`id_trabajador`),
  ADD KEY `trabajador_id_area` (`trabajador_id_area`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tb_cliente`
--
ALTER TABLE `tb_cliente`
  ADD CONSTRAINT `tb_cliente_ibfk_1` FOREIGN KEY (`cliente_id_distrito`) REFERENCES `tb_distrito` (`id_distrito`);

--
-- Filtros para la tabla `tb_detalle_pedido`
--
ALTER TABLE `tb_detalle_pedido`
  ADD CONSTRAINT `tb_detalle_pedido_ibfk_1` FOREIGN KEY (`detalle_id_producto`) REFERENCES `tb_producto` (`id_producto`),
  ADD CONSTRAINT `tb_detalle_pedido_ibfk_2` FOREIGN KEY (`detalle_id_pedido`) REFERENCES `tb_pedido` (`id_pedido`);

--
-- Filtros para la tabla `tb_distrito`
--
ALTER TABLE `tb_distrito`
  ADD CONSTRAINT `tb_distrito_ibfk_1` FOREIGN KEY (`distrito_id_provincia`) REFERENCES `tb_provincia` (`id_provincia`);

--
-- Filtros para la tabla `tb_envio`
--
ALTER TABLE `tb_envio`
  ADD CONSTRAINT `tb_envio_ibfk_1` FOREIGN KEY (`envio_id_pedido`) REFERENCES `tb_pedido` (`id_pedido`),
  ADD CONSTRAINT `tb_envio_ibfk_2` FOREIGN KEY (`envio_id_trabajador`) REFERENCES `tb_trabajador` (`id_trabajador`);

--
-- Filtros para la tabla `tb_pedido`
--
ALTER TABLE `tb_pedido`
  ADD CONSTRAINT `tb_pedido_ibfk_1` FOREIGN KEY (`pedido_id_trabajador`) REFERENCES `tb_trabajador` (`id_trabajador`),
  ADD CONSTRAINT `tb_pedido_ibfk_2` FOREIGN KEY (`pedido_id_cliente`) REFERENCES `tb_cliente` (`id_cliente`);

--
-- Filtros para la tabla `tb_producto`
--
ALTER TABLE `tb_producto`
  ADD CONSTRAINT `tb_producto_ibfk_1` FOREIGN KEY (`producto_id_marca`) REFERENCES `tb_marca` (`id_marca`),
  ADD CONSTRAINT `tb_producto_ibfk_2` FOREIGN KEY (`producto_id_categoria`) REFERENCES `tb_categoria` (`id_categoria`);

--
-- Filtros para la tabla `tb_producto_proveedor`
--
ALTER TABLE `tb_producto_proveedor`
  ADD CONSTRAINT `tb_producto_proveedor_ibfk_1` FOREIGN KEY (`producto_id_producto`) REFERENCES `tb_producto` (`id_producto`),
  ADD CONSTRAINT `tb_producto_proveedor_ibfk_2` FOREIGN KEY (`proveedor_id_proveedor`) REFERENCES `tb_proveedor` (`id_proveedor`);

--
-- Filtros para la tabla `tb_proveedor`
--
ALTER TABLE `tb_proveedor`
  ADD CONSTRAINT `tb_proveedor_ibfk_1` FOREIGN KEY (`proveedor_id_distrito`) REFERENCES `tb_distrito` (`id_distrito`);

--
-- Filtros para la tabla `tb_provincia`
--
ALTER TABLE `tb_provincia`
  ADD CONSTRAINT `tb_provincia_ibfk_1` FOREIGN KEY (`provincia_id_departamento`) REFERENCES `tb_departamento` (`id_departamento`);

--
-- Filtros para la tabla `tb_trabajador`
--
ALTER TABLE `tb_trabajador`
  ADD CONSTRAINT `tb_trabajador_ibfk_1` FOREIGN KEY (`trabajador_id_area`) REFERENCES `tb_area_trabajo` (`id_area`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
