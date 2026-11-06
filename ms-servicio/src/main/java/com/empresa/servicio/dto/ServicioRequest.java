package com.empresa.servicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServicioRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String nombre,
        @Size(max = 255, message = "Descripcion maximo 255 caracteres") String descripcion,
        @NotNull(message = "La tarifa base es obligatoria")
        @Positive(message = "La tarifa base debe ser mayor que cero") BigDecimal tarifaBase) {
}
