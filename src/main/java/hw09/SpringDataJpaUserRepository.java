package hw09;

import hw08.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.name = :name, u.surname = :surname, u.age = :age " +
            "WHERE u.id = :id")
    void update(@Param("id") Long id, @Param("name") String name, @Param("surname") String surname, @Param("age") Integer age);

    User save(User user);

    void deleteById(Long id);
}
