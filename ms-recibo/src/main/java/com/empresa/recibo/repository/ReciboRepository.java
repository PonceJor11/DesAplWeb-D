package com.empresa.recibo.repository;

import com.empresa.recibo.model.EstadoPago;
import com.empresa.recibo.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {
    List<Recibo> findByIdCliente(Long idCliente);
    List<Recibo> findByEstadoPago(EstadoPago estadoPago);
    List<Recibo> findByIdClienteAndEstadoPago(Long idCliente, EstadoPago estadoPago);
    boolean existsByIdClienteAndIdServicioAndPeriodo(Long idCliente, Long idServicio, String periodo);
    boolean existsByIdClienteAndIdServicioAndPeriodoAndIdNot(Long idCliente, Long idServicio, String periodo, Long id);
}
