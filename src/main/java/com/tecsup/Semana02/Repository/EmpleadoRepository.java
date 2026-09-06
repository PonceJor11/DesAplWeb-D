package com.tecsup.Semana02.Repository;

import com.tecsup.Semana02.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}