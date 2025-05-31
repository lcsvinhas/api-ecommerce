package org.serratec.backend.dto;

import org.serratec.backend.entity.Endereco;

public record ClienteResponseDTO(String nome, String email, String telefone, Endereco endereco) {
}
