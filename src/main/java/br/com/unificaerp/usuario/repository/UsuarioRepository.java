package br.com.unificaerp.usuario.repository;

import br.com.unificaerp.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
