package com.tecsup.Semana02.Service;

import com.tecsup.Semana02.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import com.tecsup.Semana02.Repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria guardar(Categoria c) {
        return repo.save(c);
    }

    public Categoria obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}