package org.serratec.backend.service;

import org.serratec.backend.dto.ProdutoRequestDTO;
import org.serratec.backend.dto.ProdutoResponseDTO;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.CategoriaRepository;
import org.serratec.backend.repository.PedidoProdutoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private PedidoProdutoRepository pedidoProduto;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoResponseDTO> produtoResponseDTOs = new ArrayList<ProdutoResponseDTO>();
        for (Produto produto : produtos) {
            produtoResponseDTOs.add(new ProdutoResponseDTO(produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getCategoria()));
        }
        return produtoResponseDTOs;
    }

    public ProdutoResponseDTO inserir(ProdutoRequestDTO produto) {
        Optional<Produto> p = repository.findByNome(produto.getNome());
        if (p.isPresent()) {
            throw new ProdutoException("Produto já cadastrado!");
        }

        Produto p1 = new Produto();
        p1.setNome(produto.getNome());
        p1.setDescricao(produto.getDescricao());
        p1.setPreco(produto.getPreco());
        p1.setCategoria(produto.getCategoria());
        Produto salvo = repository.save(p1);

        return new ProdutoResponseDTO(salvo.getNome(), salvo.getDescricao(), salvo.getPreco(), salvo.getCategoria());
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produto) {
        Produto p = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));

        p.setNome(produto.getNome());
        p.setDescricao(produto.getDescricao());
        p.setPreco(produto.getPreco());
        p.setCategoria(produto.getCategoria());

        Produto salvo = repository.save(p);

        return new ProdutoResponseDTO(salvo.getNome(), salvo.getDescricao(), salvo.getPreco(), salvo.getCategoria());
    }


    public void deletar(Long id) {
        Produto produto = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));
        repository.delete(produto);
    }
}

