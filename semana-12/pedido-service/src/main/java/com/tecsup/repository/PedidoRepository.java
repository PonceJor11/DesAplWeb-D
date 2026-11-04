package com.tecsup.repository;

import com.tecsup.model.EstadoPedido;
import com.tecsup.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByEstado(EstadoPedido estado);
    List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);
}
