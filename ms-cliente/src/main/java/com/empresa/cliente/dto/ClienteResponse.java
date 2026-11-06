package com.empresa.cliente.dto;

public record ClienteResponse(Long id, String nombre, String apellido, String dni, String email, String telefono) {
}
