package com.empresa.cliente.service;

import com.empresa.cliente.dto.ClienteRequest;
import com.empresa.cliente.dto.ClienteResponse;
import com.empresa.cliente.exception.ConflictException;
import com.empresa.cliente.exception.ResourceNotFoundException;
import com.empresa.cliente.model.Cliente;
import com.empresa.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ClienteResponse crear(ClienteRequest req) {
        if (repository.existsByDni(req.dni())) {
            throw new ConflictException("Ya existe un cliente con DNI " + req.dni());
        }
        return toResponse(repository.save(toEntity(new Cliente(), req)));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional
    public ClienteResponse actualizar(Long id, ClienteRequest req) {
        Cliente c = buscar(id);
        if (repository.existsByDniAndIdNot(req.dni(), id)) {
            throw new ConflictException("Ya existe otro cliente con DNI " + req.dni());
        }
        return toResponse(repository.save(toEntity(c, req)));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Cliente buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no existe"));
    }

    private Cliente toEntity(Cliente c, ClienteRequest r) {
        c.setNombre(r.nombre());
        c.setApellido(r.apellido());
        c.setDni(r.dni());
        c.setEmail(r.email());
        c.setTelefono(r.telefono());
        return c;
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNombre(), c.getApellido(), c.getDni(), c.getEmail(), c.getTelefono());
    }
}
