package com.tecsup.Semana02.Service;

import com.tecsup.Semana02.Repository.*;
import com.tecsup.Semana02.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private EmpleadoRepository empleadoRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public List<Venta> listar() {
        return repo.findAll();
    }

    public Venta obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Venta guardar(Venta v) {
        if (v.getFecha() == null) {
            v.setFecha(LocalDate.now());
        }

        if (v.getCliente() != null && v.getCliente().getIdCliente() != null) {
            Cliente cliente = clienteRepo.findById(v.getCliente().getIdCliente()).orElse(null);
            v.setCliente(cliente);
        }

        if (v.getEmpleado() != null && v.getEmpleado().getIdEmpleado() != null) {
            Empleado empleado = empleadoRepo.findById(v.getEmpleado().getIdEmpleado()).orElse(null);
            v.setEmpleado(empleado);
        }

        if (v.getDetalles() != null) {
            for (Detalle detalle : v.getDetalles()) {
                detalle.setVenta(v);
                if (detalle.getProducto() != null && detalle.getProducto().getIdProducto() != null) {
                    Producto producto = productoRepo.findById(detalle.getProducto().getIdProducto()).orElse(null);
                    detalle.setProducto(producto);
                }
            }
        }

        Venta ventaGuardada = repo.save(v);

        return repo.findById(ventaGuardada.getIdVenta()).orElse(ventaGuardada);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}