package com.empresa.recibo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReciboRequest(
        @NotNull(message = "idCliente es obligatorio") Long idCliente,
        @NotNull(message = "idServicio es obligatorio") Long idServicio,
        @NotBlank(message = "El periodo es obligatorio")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "El periodo debe tener formato yyyy-MM (ej. 2026-10)") String periodo,
        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor que cero") BigDecimal monto,
        @NotNull(message = "La fecha de vencimiento es obligatoria") LocalDate fechaVencimiento) {
}
