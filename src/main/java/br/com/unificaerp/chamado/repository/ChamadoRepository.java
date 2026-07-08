package br.com.unificaerp.chamado.repository;

import br.com.unificaerp.chamado.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
