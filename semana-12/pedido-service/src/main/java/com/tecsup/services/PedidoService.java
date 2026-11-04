package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.client.ProductoClient;
import com.tecsup.dto.*;
import com.tecsup.exception.ConflictException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.exception.ServiceUnavailableException;
import com.tecsup.model.EstadoPedido;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repo;
    private final ClienteClient clienteClient;
    private final ProductoClient productoClient;

    public PedidoService(PedidoRepository repo, ClienteClient clienteClient, ProductoClient productoClient) {
        this.repo = repo;
        this.clienteClient = clienteClient;
        this.productoClient = productoClient;
    }

    /** Registra un pedido: valida por Feign que cliente y producto existan y guarda SOLO los ids. */
    public PedidoResponse registrar(PedidoRequest req) {
        ClienteDTO cliente = buscarCliente(req.clienteId());
        ProductoDTO producto = buscarProducto(req.productoId());
        if (!"ACTIVO".equalsIgnoreCase(cliente.getEstado())) {
            throw new ConflictException("El cliente " + req.clienteId() + " esta " + cliente.getEstado()
                    + " y no puede registrar pedidos");
        }
        Pedido p = new Pedido();
        p.setClienteId(req.clienteId());
        p.setProductoId(req.productoId());
        p.setCantidad(req.cantidad());
        p.setEstado(EstadoPedido.PENDIENTE);
        p.setFecha(LocalDate.now());
        return toResponse(repo.save(p), cliente, producto);
    }

    /** Busca el pedido en su BD y completa cliente y producto mediante Feign. */
    public PedidoResponse obtener(Long id) {
        return completar(buscar(id));
    }

    public List<PedidoResponse> listar(Long clienteId, EstadoPedido estado) {
        List<Pedido> lista;
        if (clienteId != null && estado != null) {
            lista = repo.findByClienteIdAndEstado(clienteId, estado);
        } else if (clienteId != null) {
            lista = repo.findByClienteId(clienteId);
        } else if (estado != null) {
            lista = repo.findByEstado(estado);
        } else {
            lista = repo.findAll();
        }
        return lista.stream().map(this::completar).toList();
    }

    public PedidoResponse atender(Long id) {
        return cambiarEstado(id, EstadoPedido.ATENDIDO);
    }

    public PedidoResponse anular(Long id) {
        return cambiarEstado(id, EstadoPedido.ANULADO);
    }

    /** Un pedido nunca se elimina: solo cambia de estado, y unicamente desde PENDIENTE. */
    private PedidoResponse cambiarEstado(Long id, EstadoPedido nuevo) {
        Pedido p = buscar(id);
        if (p.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ConflictException("El pedido " + id + " esta " + p.getEstado()
                    + "; solo un pedido PENDIENTE puede pasar a " + nuevo);
        }
        p.setEstado(nuevo);
        return completar(repo.save(p));
    }

    private Pedido buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con id " + id + " no existe"));
    }

    private PedidoResponse completar(Pedido p) {
        ClienteDTO cliente = null;
        ProductoDTO producto = null;
        try {
            cliente = buscarCliente(p.getClienteId());
        } catch (ResourceNotFoundException e) {
            // el cliente fue eliminado: el pedido se conserva con cliente = null
        }
        try {
            producto = buscarProducto(p.getProductoId());
        } catch (ResourceNotFoundException e) {
            // el producto fue eliminado: el pedido se conserva con producto = null
        }
        return toResponse(p, cliente, producto);
    }

    private ClienteDTO buscarCliente(Long id) {
        try {
            return clienteClient.obtenerCliente(id);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Cliente con codigo " + id + " no existe");
        } catch (FeignException e) {
            throw new ServiceUnavailableException("cliente-service no esta disponible. Verifique Eureka y el servicio.");
        }
    }

    private ProductoDTO buscarProducto(Long id) {
        try {
            return productoClient.obtenerProducto(id);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Producto con id " + id + " no existe");
        } catch (FeignException e) {
            throw new ServiceUnavailableException("producto-service no esta disponible. Verifique Eureka y el servicio.");
        }
    }

    private PedidoResponse toResponse(Pedido p, ClienteDTO c, ProductoDTO pr) {
        ClienteResumen cliente = (c == null) ? null : new ClienteResumen(c.getId(), c.getNombre(), c.getApellidos());
        ProductoResumen producto = (pr == null) ? null : new ProductoResumen(pr.getId(), pr.getNombre(), pr.getPrecio());
        return new PedidoResponse(p.getId(), p.getCantidad(), p.getEstado(), p.getFecha(), cliente, producto);
    }
}
