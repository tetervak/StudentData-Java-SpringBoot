package ca.tetervak.studentdata.data.repositories;


import ca.tetervak.studentdata.data.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDataRepository extends JpaRepository<Student, Integer> {
}
