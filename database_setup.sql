-- Script para crear la base de datos y tablas para TCSWPractica04
-- Base de datos: empleados

-- Crear tabla departamentos
CREATE TABLE IF NOT EXISTS departamentos (
    clave_depto BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

-- Crear tabla empleados
CREATE TABLE IF NOT EXISTS empleados (
    clave BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(500),
    telefono VARCHAR(20),
    departamento VARCHAR(255)
);

-- Crear secuencias para Hibernate
CREATE SEQUENCE IF NOT EXISTS dep_clave_seq START 1;
CREATE SEQUENCE IF NOT EXISTS empleados_clave_seq START 1;

-- Sincronizar secuencias con datos existentes
SELECT setval('dep_clave_seq', COALESCE((SELECT MAX(clave_depto) FROM departamentos), 0) + 1);
SELECT setval('empleados_clave_seq', COALESCE((SELECT MAX(clave) FROM empleados), 0) + 1);

-- Insertar datos de ejemplo
INSERT INTO departamentos (nombre) VALUES 
    ('Recursos Humanos'),
    ('Tecnología'),
    ('Ventas'),
    ('Contabilidad');

INSERT INTO empleados (nombre, direccion, telefono, departamento) VALUES 
    ('Juan Pérez', 'Av. Siempre Viva 123', '555-0101', 'Tecnología'),
    ('María García', 'Calle Falsa 456', '555-0102', 'Recursos Humanos'),
    ('Carlos López', 'Boulevard Central 789', '555-0103', 'Ventas'),
    ('Ana Martínez', 'Zona Norte 321', '555-0104', 'Contabilidad');
