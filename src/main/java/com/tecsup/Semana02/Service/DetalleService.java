package com.tecsup.Semana02.Service;

import com.tecsup.Semana02.model.Detalle;
import com.tecsup.Semana02.model.Producto;
import com.tecsup.Semana02.model.Venta;
import com.tecsup.Semana02.Repository.DetalleRepository;
import com.tecsup.Semana02.Repository.ProductoRepository;
import com.tecsup.Semana02.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository repo;

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public List<Detalle> listar() {
        return repo.findAll();
    }

    public Detalle guardar(Detalle d) {
        if (d.getVenta() != null && d.getVenta().getIdVenta() != null) {
            Venta v = ventaRepo.findById(d.getVenta().getIdVenta()).orElse(null);
            d.setVenta(v);
        }
        if (d.getProducto() != null && d.getProducto().getIdProducto() != null) {
            Producto p = productoRepo.findById(d.getProducto().getIdProducto()).orElse(null);
            d.setProducto(p);
        }
        return repo.save(d);
    }

    public Detalle obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}