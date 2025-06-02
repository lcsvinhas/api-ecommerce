package org.serratec.backend.repository;

import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long id);

    List<Pedido> findByStatus(Status status);
}
