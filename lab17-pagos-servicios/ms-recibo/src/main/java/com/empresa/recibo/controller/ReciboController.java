package com.empresa.recibo.controller;

import com.empresa.recibo.dto.ReciboRequest;
import com.empresa.recibo.dto.ReciboResponse;
import com.empresa.recibo.model.EstadoPago;
import com.empresa.recibo.service.ReciboService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recibos")
public class ReciboController {

    private final ReciboService service;

    public ReciboController(ReciboService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReciboResponse> crear(@Valid @RequestBody ReciboRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    /** Filtros opcionales: ?idCliente=1&estado=PENDIENTE */
    @GetMapping
    public List<ReciboResponse> listar(@RequestParam(required = false) Long idCliente,
                                       @RequestParam(required = false) EstadoPago estado) {
        return service.listar(idCliente, estado);
    }

    @GetMapping("/{id}")
    public ReciboResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PutMapping("/{id}")
    public ReciboResponse actualizar(@PathVariable Long id, @Valid @RequestBody ReciboRequest req) {
        return service.actualizar(id, req);
    }

    @PatchMapping("/{id}/pagar")
    public ReciboResponse pagar(@PathVariable Long id) {
        return service.marcarPagado(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
