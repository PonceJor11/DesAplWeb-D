package com.tecsup.dto;

import com.tecsup.model.EstadoPedido;

import java.time.LocalDate;

public record PedidoResponse(Long id, Integer cantidad, EstadoPedido estado, LocalDate fecha,
                             ClienteResumen cliente, ProductoResumen producto) {
}
