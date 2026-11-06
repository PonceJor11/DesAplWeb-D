package com.empresa.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String nombre,
        @NotBlank(message = "El apellido es obligatorio") @Size(max = 80) String apellido,
        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 digitos") String dni,
        @NotBlank(message = "El email es obligatorio") @Email(message = "Email invalido") String email,
        @Size(max = 15, message = "Telefono maximo 15 caracteres") String telefono) {
}
