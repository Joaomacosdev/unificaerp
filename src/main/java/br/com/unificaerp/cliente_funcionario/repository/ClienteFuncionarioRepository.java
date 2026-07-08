package br.com.unificaerp.cliente_funcionario.repository;

import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteFuncionarioRepository extends JpaRepository<ClienteFuncionario, Long> {
}
