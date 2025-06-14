package org.serratec.backend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.serratec.backend.entity.PK.PedidoProdutoPK;

@Entity
public class PedidoProduto {

    @EmbeddedId
    private PedidoProdutoPK id = new PedidoProdutoPK();

    @NotNull
    private Double valorVenda;

    @NotNull
    private Integer desconto;

    @NotNull
    private Integer quantidade;

    public PedidoProduto() {
    }

    public PedidoProduto(Pedido pedido, Produto produto, Double valorVenda, Integer desconto, Integer quantidade) {
        id.setPedido(pedido);
        id.setProduto(produto);
        this.valorVenda = valorVenda;
        this.desconto = desconto;
        this.quantidade = quantidade;
    }

    public PedidoProdutoPK getId() {
        return id;
    }

    public void setId(PedidoProdutoPK id) {
        this.id = id;
    }

    public @NotNull Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(@NotNull Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public @NotNull Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(@NotNull Integer desconto) {
        this.desconto = desconto;
    }

    public @NotNull Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(@NotNull Integer quantidade) {
        this.quantidade = quantidade;
    }
}
