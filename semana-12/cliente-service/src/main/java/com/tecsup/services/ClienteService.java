package com.tecsup.services;

import com.tecsup.exception.ConflictException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Cliente;
import com.tecsup.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con codigo " + id + " no existe"));
    }

    public Cliente guardar(Cliente c) {
        if (repo.existsByNroDni(c.getNroDni())) {
            throw new ConflictException("Ya existe un cliente con nro_dni " + c.getNroDni());
        }
        c.setId(null);
        return repo.save(c);
    }

    public Cliente actualizar(Long id, Cliente datos) {
        Cliente c = obtener(id);
        if (repo.existsByNroDniAndIdNot(datos.getNroDni(), id)) {
            throw new ConflictException("Ya existe otro cliente con nro_dni " + datos.getNroDni());
        }
        c.setNombre(datos.getNombre());
        c.setApellidos(datos.getApellidos());
        c.setDireccion(datos.getDireccion());
        c.setEstado(datos.getEstado());
        c.setNroDni(datos.getNroDni());
        return repo.save(c);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
