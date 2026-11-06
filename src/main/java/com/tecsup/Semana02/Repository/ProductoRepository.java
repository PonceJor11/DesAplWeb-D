package com.tecsup.Semana02.Repository;

import com.tecsup.Semana02.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}