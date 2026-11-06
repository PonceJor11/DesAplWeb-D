package com.empresa.recibo.service;

import com.empresa.recibo.client.ReferenciaClient;
import com.empresa.recibo.dto.ReciboRequest;
import com.empresa.recibo.dto.ReciboResponse;
import com.empresa.recibo.exception.ConflictException;
import com.empresa.recibo.exception.ResourceNotFoundException;
import com.empresa.recibo.model.EstadoPago;
import com.empresa.recibo.model.Recibo;
import com.empresa.recibo.repository.ReciboRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReciboService {

    private final ReciboRepository repository;
    private final ReferenciaClient referencias;

    public ReciboService(ReciboRepository repository, ReferenciaClient referencias) {
        this.repository = repository;
        this.referencias = referencias;
    }

    @Transactional
    public ReciboResponse crear(ReciboRequest req) {
        referencias.validarCliente(req.idCliente());
        referencias.validarServicio(req.idServicio());
        if (repository.existsByIdClienteAndIdServicioAndPeriodo(req.idCliente(), req.idServicio(), req.periodo())) {
            throw new ConflictException("Ya existe un recibo de ese cliente y servicio para el periodo " + req.periodo());
        }
        Recibo r = toEntity(new Recibo(), req);
        r.setEstadoPago(EstadoPago.PENDIENTE);   // todo recibo nace PENDIENTE
        return toResponse(repository.save(r));
    }

    @Transactional(readOnly = true)
    public List<ReciboResponse> listar(Long idCliente, EstadoPago estado) {
        List<Recibo> lista;
        if (idCliente != null && estado != null) {
            lista = repository.findByIdClienteAndEstadoPago(idCliente, estado);
        } else if (idCliente != null) {
            lista = repository.findByIdCliente(idCliente);
        } else if (estado != null) {
            lista = repository.findByEstadoPago(estado);
        } else {
            lista = repository.findAll();
        }
        return lista.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ReciboResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional
    public ReciboResponse actualizar(Long id, ReciboRequest req) {
        Recibo r = buscar(id);
        if (r.getEstadoPago() == EstadoPago.PAGADO) {
            throw new ConflictException("No se puede modificar un recibo ya PAGADO");
        }
        referencias.validarCliente(req.idCliente());
        referencias.validarServicio(req.idServicio());
        if (repository.existsByIdClienteAndIdServicioAndPeriodoAndIdNot(
                req.idCliente(), req.idServicio(), req.periodo(), id)) {
            throw new ConflictException("Ya existe un recibo de ese cliente y servicio para el periodo " + req.periodo());
        }
        return toResponse(repository.save(toEntity(r, req)));
    }

    @Transactional
    public ReciboResponse marcarPagado(Long id) {
        Recibo r = buscar(id);
        if (r.getEstadoPago() == EstadoPago.PAGADO) {
            throw new ConflictException("El recibo " + id + " ya esta PAGADO");
        }
        r.setEstadoPago(EstadoPago.PAGADO);
        return toResponse(repository.save(r));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Recibo buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recibo con id " + id + " no existe"));
    }

    private Recibo toEntity(Recibo r, ReciboRequest q) {
        r.setIdCliente(q.idCliente());
        r.setIdServicio(q.idServicio());
        r.setPeriodo(q.periodo());
        r.setMonto(q.monto());
        r.setFechaVencimiento(q.fechaVencimiento());
        return r;
    }

    private ReciboResponse toResponse(Recibo r) {
        return new ReciboResponse(r.getId(), r.getIdCliente(), r.getIdServicio(), r.getPeriodo(),
                r.getMonto(), r.getFechaVencimiento(), r.getEstadoPago());
    }
}
