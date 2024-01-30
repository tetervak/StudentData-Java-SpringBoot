package ca.tetervak.studentdata.data.repositories;


import ca.tetervak.studentdata.data.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDataRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByUserName(String userName);

    void deleteByUserName(String useName);
}
