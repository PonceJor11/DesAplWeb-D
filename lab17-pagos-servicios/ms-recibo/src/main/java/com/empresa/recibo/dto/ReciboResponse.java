package com.empresa.recibo.dto;

import com.empresa.recibo.model.EstadoPago;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReciboResponse(Long id, Long idCliente, Long idServicio, String periodo,
                             BigDecimal monto, LocalDate fechaVencimiento, EstadoPago estadoPago) {
}
