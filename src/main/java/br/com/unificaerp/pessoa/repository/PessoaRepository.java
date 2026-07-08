package br.com.unificaerp.pessoa.repository;

import br.com.unificaerp.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
