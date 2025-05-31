package org.serratec.backend.service;

import org.serratec.backend.dto.ClienteResponseDTO;
import org.serratec.backend.dto.PedidoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public List<PedidoResponseDTO> listar() {
        List<Pedido> pedidos = repository.findAll();
        List<PedidoResponseDTO> pedidosDTO = new ArrayList<>();
        pedidos.forEach(pedido -> {
            Cliente cliente = pedido.getCliente();
            double total = pedido.getPedidoProdutos().stream().mapToDouble(item -> item.getQuantidade() * item.getValorVenda()).sum();
            PedidoResponseDTO dto = new PedidoResponseDTO(pedido.getDataPedido(), new ClienteResponseDTO(cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getEndereco()), total);
            pedidosDTO.add(dto);
        });
        return pedidosDTO;
    }


    public Pedido inserir(Pedido pedido) {
        return repository.save(pedido);
    }

    public Pedido atualizar(Pedido pedido) {
        Pedido p = repository.findById(pedido.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente!"));
        p.setId(pedido.getId());
        p.setDataPedido(pedido.getDataPedido());
        p.setCliente(pedido.getCliente());
        p.setStatus(pedido.getStatus());
        return repository.save(p);
    }

    public void deletar(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente!"));
        repository.delete(pedido);
    }

    public PedidoResponseDTO buscar(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente!"));
        Cliente cliente = pedido.getCliente();
        double total = pedido.getPedidoProdutos().stream().mapToDouble(item -> item.getQuantidade() * item.getValorVenda()).sum();
        return new PedidoResponseDTO(pedido.getDataPedido(), new ClienteResponseDTO(cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getEndereco()), total);
    }
}
