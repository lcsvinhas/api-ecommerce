package org.serratec.backend.dto;

import org.serratec.backend.entity.Status;

import java.time.LocalDate;

public record PedidoResponseDTO(LocalDate dataPedido, ClienteResponseDTO cliente, Double totalPedido, Status status) {
}
