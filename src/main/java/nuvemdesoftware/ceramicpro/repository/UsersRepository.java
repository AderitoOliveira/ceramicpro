package nuvemdesoftware.ceramicpro.repository;


import java.util.List;
import java.util.Optional;

import nuvemdesoftware.ceramicpro.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Integer> {
    List<User> findByUsername(String username);
}