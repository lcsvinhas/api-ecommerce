package org.serratec.backend.service;

import org.serratec.backend.entity.PedidoProduto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.PedidoProdutoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private PedidoProdutoRepository pedidoProduto;

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto inserir(Produto produto) {
        Optional<Produto> produtoOptional = repository.findByNome(produto.getNome());
        if (produtoOptional.isPresent()) {
            throw new ProdutoException("Produto já cadastrado!");
        }

        Produto produtoInserido = repository.save(produto);

        for (PedidoProduto p : produto.getPedidoProdutos()) {
            if (p.getId().getPedido() == null || p.getId().getPedido().getId() == null) {
                throw new ProdutoException("Pedido associado ao produto não pode ser nulo.");
            }

            p.getId().setProduto(produtoInserido);
        }

        pedidoProduto.saveAll(produto.getPedidoProdutos());

        return produtoInserido;
    }

    public Produto atualizar(Produto produto) {
        Produto p = repository.findById(produto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));

        p.setNome(produto.getNome());
        p.setDescricao(produto.getDescricao());
        p.setPreco(produto.getPreco());
        p.setCategoria(produto.getCategoria());

        return repository.save(p);
    }

    public void deletar(Long id) {
        Produto produto = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));
        repository.delete(produto);
    }
}

