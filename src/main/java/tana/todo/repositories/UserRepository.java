package tana.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tana.todo.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
