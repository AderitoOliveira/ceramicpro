package nuvemdesoftware.ceramicpro.repository;


import java.util.List;
import java.util.Optional;

import nuvemdesoftware.ceramicpro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
    // Since email is unique, we'll find users by email
    Optional<User> findByEmail(String email);
}