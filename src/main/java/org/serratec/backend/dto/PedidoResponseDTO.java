package org.serratec.backend.dto;

import java.time.LocalDate;

public record PedidoResponseDTO(LocalDate dataPedido, ClienteResponseDTO cliente, Double totalPedido) {
}
