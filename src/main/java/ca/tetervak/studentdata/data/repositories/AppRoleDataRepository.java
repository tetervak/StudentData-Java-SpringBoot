package ca.tetervak.studentdata.data.repositories;

import ca.tetervak.studentdata.data.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleDataRepository extends JpaRepository<AppRole, Integer> {

    AppRole findByRoleName(String roleName);
}
