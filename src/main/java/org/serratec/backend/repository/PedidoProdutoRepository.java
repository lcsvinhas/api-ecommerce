package org.serratec.backend.repository;

import org.serratec.backend.entity.PK.PedidoProdutoPK;
import org.serratec.backend.entity.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, PedidoProdutoPK> {
    @Query("SELECT pp.id.produto.nome, SUM(pp.quantidade) " +
            "FROM PedidoProduto pp GROUP BY pp.id.produto ORDER BY SUM(pp.quantidade) DESC")
    List<Object[]> buscarProdutosMaisVendidos();

}
