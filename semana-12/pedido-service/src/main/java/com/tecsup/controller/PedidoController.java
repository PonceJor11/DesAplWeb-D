package com.tecsup.controller;

import com.tecsup.dto.PedidoRequest;
import com.tecsup.dto.PedidoResponse;
import com.tecsup.model.EstadoPedido;
import com.tecsup.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Nota: no existe DELETE. Los pedidos solo cambian de estado (PENDIENTE, ATENDIDO, ANULADO). */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> registrar(@Valid @RequestBody PedidoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(req));
    }

    /** Filtros opcionales: ?clienteId=1&estado=PENDIENTE */
    @GetMapping
    public List<PedidoResponse> listar(@RequestParam(required = false) Long clienteId,
                                       @RequestParam(required = false) EstadoPedido estado) {
        return service.listar(clienteId, estado);
    }

    @GetMapping("/{id}")
    public PedidoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PatchMapping("/{id}/atender")
    public PedidoResponse atender(@PathVariable Long id) {
        return service.atender(id);
    }

    @PatchMapping("/{id}/anular")
    public PedidoResponse anular(@PathVariable Long id) {
        return service.anular(id);
    }
}
