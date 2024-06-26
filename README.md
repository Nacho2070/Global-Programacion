Tablas para la BD
create database bd_documento;

CREATE TABLE Documento (
    id_documento INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT,-- Identificador único para cada documento
    autor VARCHAR(100) NOT NULL,        -- Autor del documento
    destinatario VARCHAR(100) NOT NULL, -- Destinatario del documento
    fecha_creacion varchar(100) NOT NULL,
    palabra_clave JSON NOT NULL,
    FOREIGN KEY (id_empleado) REFERENCES Empleados(id_empleado)
);
CREATE TABLE Empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,  -- Identificador único para cada documento
    nombre VARCHAR(100) NOT NULL,        -- Autor del documento
    direccion VARCHAR(100) NOT NULL, -- Destinatario del documento
    telefono VARCHAR(100) NOT NULL,
    fecha_ingreso varchar(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL
);

CREATE TABLE Envio (
    id_envio INT AUTO_INCREMENT PRIMARY KEY,
    estado_enviado BOOLEAN NOT NULL DEFAULT false,
    nro_seguimiento INT
);
