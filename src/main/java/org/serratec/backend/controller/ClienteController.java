package org.serratec.backend.controller;

import jakarta.validation.Valid;
import org.serratec.backend.dto.ClienteRequestDTO;
import org.serratec.backend.dto.ClienteResponseDTO;
import org.serratec.backend.dto.PedidoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.repository.ClienteRepository;
import org.serratec.backend.service.ClienteService;
import org.serratec.backend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> inserir(@Valid @RequestBody ClienteRequestDTO cliente) {
        return ResponseEntity.ok().body(service.inserir(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO cliente) {
        cliente.setId(id);
        return ResponseEntity.ok(service.atualizar(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("/{id}/pedidos")
    public ResponseEntity <List<PedidoResponseDTO>> listarPedidosPorCliente(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.listarPorCliente(id));
    }

}
