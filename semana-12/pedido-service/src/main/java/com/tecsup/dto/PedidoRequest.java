package com.tecsup.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoRequest(
        @NotNull(message = "clienteId es obligatorio") Long clienteId,
        @NotNull(message = "productoId es obligatorio") Long productoId,
        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor que cero") Integer cantidad) {
}
