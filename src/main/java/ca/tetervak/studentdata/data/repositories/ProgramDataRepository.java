package ca.tetervak.studentdata.data.repositories;

import ca.tetervak.studentdata.data.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramDataRepository extends JpaRepository<Program, Integer> {
}
