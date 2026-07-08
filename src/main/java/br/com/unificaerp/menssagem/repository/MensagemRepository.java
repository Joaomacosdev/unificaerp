package br.com.unificaerp.menssagem.repository;

import br.com.unificaerp.menssagem.model.Menssagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Menssagem, Long> {
}
