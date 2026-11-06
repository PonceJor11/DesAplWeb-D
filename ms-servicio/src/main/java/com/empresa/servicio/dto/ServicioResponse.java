package com.empresa.servicio.dto;

import java.math.BigDecimal;

public record ServicioResponse(Long id, String nombre, String descripcion, BigDecimal tarifaBase) {
}
