package org.serratec.backend.dto;

import org.serratec.backend.entity.Categoria;

public record ProdutoResponseDTO(String nome, String descricao, Double preco, Categoria categoria) {
}
