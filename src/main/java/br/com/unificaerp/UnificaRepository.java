package br.com.unificaerp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UnificaRepository<T, ID> extends JpaRepository<T, ID> {
}
