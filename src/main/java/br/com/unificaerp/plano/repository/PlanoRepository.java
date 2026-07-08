package br.com.unificaerp.plano.repository;

import br.com.unificaerp.plano.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
}
