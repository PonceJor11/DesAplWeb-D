package com.tecsup.repository;

import com.tecsup.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNroDni(String nroDni);
    boolean existsByNroDniAndIdNot(String nroDni, Long id);
}
