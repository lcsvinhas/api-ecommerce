package org.serratec.backend.repository;

import org.serratec.backend.entity.PK.PedidoProdutoPK;
import org.serratec.backend.entity.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, PedidoProdutoPK> {
}
