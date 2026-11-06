package com.tecsup.Semana02.Service;

import com.tecsup.Semana02.model.Categoria;
import com.tecsup.Semana02.model.Producto;
import com.tecsup.Semana02.Repository.CategoriaRepository;
import com.tecsup.Semana02.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepo;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto p) {
        if (p.getCategoria() != null && p.getCategoria().getIdCategoria() != null) {
            Categoria cat = categoriaRepo.findById(p.getCategoria().getIdCategoria()).orElse(null);
            p.setCategoria(cat);
        }
        return repo.save(p);
    }

    public Producto obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}