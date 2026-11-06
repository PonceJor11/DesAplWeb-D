-- Script para el perfil PROD (ddl-auto: validate). En DEV Hibernate crea todo solo.
CREATE DATABASE IF NOT EXISTS bd_cliente;
CREATE DATABASE IF NOT EXISTS bd_servicio;
CREATE DATABASE IF NOT EXISTS bd_recibo;

USE bd_cliente;
CREATE TABLE IF NOT EXISTS clientes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL,
  apellido VARCHAR(80) NOT NULL,
  dni VARCHAR(8) NOT NULL UNIQUE,
  email VARCHAR(120) NOT NULL,
  telefono VARCHAR(15)
);

USE bd_servicio;
CREATE TABLE IF NOT EXISTS servicios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL UNIQUE,
  descripcion VARCHAR(255),
  tarifa_base DECIMAL(10,2) NOT NULL
);

USE bd_recibo;
CREATE TABLE IF NOT EXISTS recibos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_cliente BIGINT NOT NULL,
  id_servicio BIGINT NOT NULL,
  periodo VARCHAR(7) NOT NULL,
  monto DECIMAL(10,2) NOT NULL,
  fecha_vencimiento DATE NOT NULL,
  estado_pago VARCHAR(10) NOT NULL
);
