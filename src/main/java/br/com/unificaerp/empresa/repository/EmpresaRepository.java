package br.com.unificaerp.empresa.repository;

import br.com.unificaerp.empresa.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
