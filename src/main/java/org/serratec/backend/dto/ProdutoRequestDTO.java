package org.serratec.backend.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;

public class ProdutoRequestDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
    }

    public ProdutoRequestDTO(String nome, String descricao, Double preco, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @NotBlank String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank String descricao) {
        this.descricao = descricao;
    }

    public @NotNull Double getPreco() {
        return preco;
    }

    public void setPreco(@NotNull Double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
