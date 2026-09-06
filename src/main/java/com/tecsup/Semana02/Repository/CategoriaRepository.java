package com.tecsup.Semana02.Repository;

import com.tecsup.Semana02.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}