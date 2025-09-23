CREATE DATABASE Gestion_de_Reuniones;

-- ========================
-- 1. ROLES Y USUARIOS
-- ========================
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL, -- ADMINISTRADOR, ORGANIZADOR, PARTICIPANTE, ESPECTADOR
    descripcion TEXT
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    rol_id INT NOT NULL REFERENCES roles(id),
    activo BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT NOW()
);

-- ========================
-- 2. REUNIONES
-- ========================
CREATE TABLE reuniones (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL CHECK (char_length(titulo) >= 3),
    descripcion TEXT,
    organizador_id INT NOT NULL REFERENCES usuarios(id),
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN (
        'BORRADOR','PROGRAMADA','CONFIRMADA','EN_CURSO',
        'COMPLETADA','CANCELADA','POSPUESTA'
    )),
    limite_participantes INT DEFAULT 100 CHECK (limite_participantes BETWEEN 1 AND 100),
    creado_en TIMESTAMP DEFAULT NOW()
);

-- Validación: duración entre 15min y 8h
ALTER TABLE reuniones ADD CONSTRAINT chk_duracion_valida
CHECK (
    fecha_fin > fecha_inicio + interval '15 minutes' 
    AND fecha_fin <= fecha_inicio + interval '8 hours'
);

-- ========================
-- 3. PARTICIPANTES EN REUNIONES
-- ========================
CREATE TABLE participantes_reunion (
    id SERIAL PRIMARY KEY,
    reunion_id INT REFERENCES reuniones(id) ON DELETE CASCADE,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    tipo_participacion VARCHAR(20) NOT NULL CHECK (tipo_participacion IN ('OBLIGATORIO','OPCIONAL','INFORMATIVO')),
    confirmado BOOLEAN DEFAULT FALSE,
    fecha_confirmacion TIMESTAMP,
    UNIQUE(reunion_id, usuario_id)
);

-- ========================
-- 4. GRABACIONES Y TRANSCRIPCIONES
-- ========================
CREATE TABLE grabaciones (
    id SERIAL PRIMARY KEY,
    reunion_id INT NOT NULL REFERENCES reuniones(id) ON DELETE CASCADE,
    url TEXT NOT NULL,
    consentimiento BOOLEAN DEFAULT FALSE,
    fecha_inicio TIMESTAMP,
    fecha_fin TIMESTAMP,
    retencion_hasta TIMESTAMP
);

CREATE TABLE transcripciones (
    id SERIAL PRIMARY KEY,
    reunion_id INT NOT NULL REFERENCES reuniones(id) ON DELETE CASCADE,
    texto TEXT,
    confianza NUMERIC(5,2), -- porcentaje
    estado VARCHAR(20) CHECK (estado IN ('ALTA','MEDIA','BAJA')),
    creada_en TIMESTAMP DEFAULT NOW()
);

-- ========================
-- 5. ACUERDOS Y COMPROMISOS
-- ========================
CREATE TABLE acuerdos (
    id SERIAL PRIMARY KEY,
    reunion_id INT NOT NULL REFERENCES reuniones(id) ON DELETE CASCADE,
    descripcion TEXT NOT NULL,
    responsable_id INT NOT NULL REFERENCES usuarios(id),
    fecha_limite DATE,
    estado VARCHAR(20) NOT NULL CHECK (estado IN (
        'IDENTIFICADO','CONFIRMADO','EN_PROGRESO',
        'COMPLETADO','VENCIDO','CANCELADO'
    )),
    resultado TEXT,
    creado_en TIMESTAMP DEFAULT NOW()
);

-- ========================
-- 6. NOTIFICACIONES
-- ========================
CREATE TABLE notificaciones (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id),
    compromiso_id INT REFERENCES acuerdos(id),
    tipo VARCHAR(30) NOT NULL CHECK (tipo IN ('RECORDATORIO_SEMANAL','RECORDATORIO_URGENTE','ALERTA_CRITICA','VENCIMIENTO')),
    programada_para TIMESTAMP NOT NULL,
    enviada BOOLEAN DEFAULT FALSE,
    enviada_en TIMESTAMP
);

-- ========================
-- 7. AUDITORÍA
-- ========================
CREATE TABLE auditoria (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id),
    accion VARCHAR(100) NOT NULL,
    entidad VARCHAR(50) NOT NULL,
    entidad_id INT,
    datos_antes JSONB,
    datos_despues JSONB,
    exito BOOLEAN,
    ip VARCHAR(45),
    timestamp TIMESTAMP DEFAULT NOW()
);

-- ========================
-- 8. DOCUMENTOS
-- ========================
CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    nombre_original VARCHAR(255) NOT NULL, -- acta.pdf, minuta.docx, etc.
    tipo_mime VARCHAR(100) NOT NULL,       -- application/pdf, application/vnd.ms-excel
    extension VARCHAR(10) NOT NULL,        -- pdf, docx, xlsx
    tamanio BIGINT CHECK (tamanio > 0),    -- en bytes
    url TEXT NOT NULL,                     -- ruta en almacenamiento
    usuario_id INT REFERENCES usuarios(id),-- quién lo subió
    creado_en TIMESTAMP DEFAULT NOW()
);

-- ========================
-- 9. RELACIÓN DOCUMENTOS - ENTIDADES
-- ========================
CREATE TABLE documentos_relaciones (
    id SERIAL PRIMARY KEY,
    documento_id INT NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
    entidad VARCHAR(50) NOT NULL CHECK (entidad IN ('REUNION','ACUERDO','TRANSCRIPCION','USUARIO')),
    entidad_id INT NOT NULL,
    creado_en TIMESTAMP DEFAULT NOW()
);

-- ========================
-- 10. ÍNDICES RECOMENDADOS
-- ========================
CREATE INDEX idx_reuniones_estado ON reuniones(estado);
CREATE INDEX idx_reuniones_fechas ON reuniones(fecha_inicio, fecha_fin);
CREATE INDEX idx_participantes_reunion ON participantes_reunion(reunion_id, usuario_id);
CREATE INDEX idx_acuerdos_estado ON acuerdos(estado, fecha_limite);
CREATE INDEX idx_notificaciones_programadas ON notificaciones(programada_para, enviada);
CREATE INDEX idx_documentos_relaciones ON documentos_relaciones(entidad, entidad_id);
