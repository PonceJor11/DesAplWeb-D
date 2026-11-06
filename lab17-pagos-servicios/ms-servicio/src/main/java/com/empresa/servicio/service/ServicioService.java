package com.empresa.servicio.service;

import com.empresa.servicio.dto.ServicioRequest;
import com.empresa.servicio.dto.ServicioResponse;
import com.empresa.servicio.exception.ConflictException;
import com.empresa.servicio.exception.ResourceNotFoundException;
import com.empresa.servicio.model.Servicio;
import com.empresa.servicio.repository.ServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepository repository;

    public ServicioService(ServicioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServicioResponse crear(ServicioRequest req) {
        if (repository.existsByNombreIgnoreCase(req.nombre())) {
            throw new ConflictException("Ya existe un servicio llamado " + req.nombre());
        }
        return toResponse(repository.save(toEntity(new Servicio(), req)));
    }

    @Transactional(readOnly = true)
    public List<ServicioResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ServicioResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional
    public ServicioResponse actualizar(Long id, ServicioRequest req) {
        Servicio s = buscar(id);
        if (repository.existsByNombreIgnoreCaseAndIdNot(req.nombre(), id)) {
            throw new ConflictException("Ya existe otro servicio llamado " + req.nombre());
        }
        return toResponse(repository.save(toEntity(s, req)));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Servicio buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio con id " + id + " no existe"));
    }

    private Servicio toEntity(Servicio s, ServicioRequest r) {
        s.setNombre(r.nombre());
        s.setDescripcion(r.descripcion());
        s.setTarifaBase(r.tarifaBase());
        return s;
    }

    private ServicioResponse toResponse(Servicio s) {
        return new ServicioResponse(s.getId(), s.getNombre(), s.getDescripcion(), s.getTarifaBase());
    }
}
