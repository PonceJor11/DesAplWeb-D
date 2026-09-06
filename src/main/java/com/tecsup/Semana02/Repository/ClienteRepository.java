package com.tecsup.Semana02.Repository;

import com.tecsup.Semana02.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}