package br.com.unificaerp.role.repository;

import br.com.unificaerp.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
