package by.sunserg.grandcapital.repository.repo;

import by.sunserg.grandcapital.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u JOIN u.emailData ed WHERE ed.email = :email")
    User findUserByEmail(@Param("email") String email);

}