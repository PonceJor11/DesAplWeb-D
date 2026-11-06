package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no existe"));
    }

    public Producto guardar(Producto p) {
        p.setId(null);
        return repo.save(p);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto p = obtener(id);
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        return repo.save(p);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
