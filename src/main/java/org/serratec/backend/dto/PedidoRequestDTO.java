package org.serratec.backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoRequestDTO {

    private Long id;

    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private List<ItemPedidoDTO> pedidos;

    @Enumerated(EnumType.STRING)
    private Status status;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = LocalDate.now();
        this.cliente = pedido.getCliente();
        this.status = pedido.getStatus();
        this.pedidos = new ArrayList<>();
    }

    public PedidoRequestDTO(Long id, LocalDate dataPedido, Cliente cliente, Status status, List<ItemPedidoDTO> pedidos) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.cliente = cliente;
        this.status = status;
        this.pedidos = pedidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ItemPedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
