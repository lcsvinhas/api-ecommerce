package org.serratec.backend.service;

import org.serratec.backend.dto.*;
import org.serratec.backend.entity.*;
import org.serratec.backend.entity.PK.PedidoProdutoPK;
import org.serratec.backend.repository.ClienteRepository;
import org.serratec.backend.repository.PedidoRepository;
import org.serratec.backend.repository.ProdutoRepository;
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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<PedidoResponseDTO> listar() {
        List<Pedido> pedidos = repository.findAll();
        List<PedidoResponseDTO> pedidosDTO = new ArrayList<>();
        pedidos.forEach(pedido -> {
            Cliente cliente = pedido.getCliente();
            double total = pedido.getPedidoProdutos().stream().mapToDouble(item -> item.getQuantidade() * item.getValorVenda()).sum();
            PedidoResponseDTO dto = new PedidoResponseDTO(pedido.getDataPedido(), new ClienteResponseDTO(cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getEndereco()), total, pedido.getStatus());
            pedidosDTO.add(dto);
        });
        return pedidosDTO;
    }


    public PedidoResponseDTO inserir(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getCliente().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setCliente(dto.getCliente());
        pedido.setStatus(dto.getStatus());

        List<PedidoProduto> pedidoProdutos = new ArrayList<>();

        for (ItemPedidoDTO item : dto.getPedidos()) {
            Produto produto = produtoRepository.findById(item.id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            PedidoProdutoPK pk = new PedidoProdutoPK();
            pk.setPedido(pedido);
            pk.setProduto(produto);

            PedidoProduto p = new PedidoProduto();
            p.setId(pk);
            p.setValorVenda(item.valorVenda());
            p.setDesconto(item.desconto());
            p.setQuantidade(item.quantidade());

            pedidoProdutos.add(p);
        }

        pedido.setPedidoProdutos(pedidoProdutos);

        Pedido savedPedido = repository.save(pedido);

        double total = savedPedido.getPedidoProdutos().stream()
                .mapToDouble(x -> (x.getValorVenda() - x.getDesconto()) * x.getQuantidade())
                .sum();

        Cliente clienteEntity = savedPedido.getCliente();

        return new PedidoResponseDTO(
                savedPedido.getDataPedido(),
                new ClienteResponseDTO(
                        clienteEntity.getNome(),
                        clienteEntity.getEmail(),
                        clienteEntity.getTelefone(),
                        clienteEntity.getEndereco()
                ),
                total, pedido.getStatus()
        );
    }


    public PedidoResponseDTO atualizar(PedidoRequestDTO pedido) {
        Pedido p = repository.findById(pedido.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        p.setDataPedido(pedido.getDataPedido());
        p.setCliente(pedido.getCliente());
        p.setStatus(pedido.getStatus());
        Pedido savedPedido = repository.save(p);

        double totalPedido = savedPedido.getPedidoProdutos().stream().mapToDouble(x -> (x.getValorVenda() - x.getDesconto()) * x.getQuantidade()).sum();

        return new PedidoResponseDTO(savedPedido.getDataPedido(), new ClienteResponseDTO(savedPedido.getCliente().getNome(), savedPedido.getCliente().getEmail(), savedPedido.getCliente().getTelefone(), savedPedido.getCliente().getEndereco()), totalPedido, pedido.getStatus());
    }

    public void deletar(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente!"));
        repository.delete(pedido);
    }

    public PedidoResponseDTO buscar(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente!"));
        Cliente cliente = pedido.getCliente();
        double total = pedido.getPedidoProdutos().stream().mapToDouble(item -> item.getQuantidade() * item.getValorVenda()).sum();
        return new PedidoResponseDTO(pedido.getDataPedido(), new ClienteResponseDTO(cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getEndereco()), total, pedido.getStatus());
    }

    public List<PedidoResponseDTO> listarPorCliente (Long id){
        List<Pedido> pedidos=repository.findByClienteId(id);
        return pedidos.stream().map(p -> {
            double total = p.getPedidoProdutos().stream()
                    .mapToDouble(i -> (i.getValorVenda() - i.getDesconto()) * i.getQuantidade())
                    .sum();
            return new PedidoResponseDTO(p.getDataPedido(), new ClienteResponseDTO(p.getCliente().getNome(), p.getCliente().getEmail(), p.getCliente().getTelefone(), p.getCliente().getEndereco()), total, p.getStatus());
        }).toList();
    }

    public List<PedidoResponseDTO> listarPorStatus (Status status){
        List<Pedido> pedidos = repository.findByStatus(status);
        return pedidos.stream().map(pedido -> {
            Double total = pedido.getPedidoProdutos().stream().mapToDouble(i -> (i.getValorVenda() - i.getDesconto()) * i.getQuantidade()).sum();
            return new PedidoResponseDTO(pedido.getDataPedido(), new ClienteResponseDTO(pedido.getCliente().getNome(), pedido.getCliente().getEmail(), pedido.getCliente().getTelefone(), pedido.getCliente().getEndereco()), total, pedido.getStatus());
        }).toList();
    }

}

