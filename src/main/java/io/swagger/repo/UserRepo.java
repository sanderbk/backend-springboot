package io.swagger.repo;

import io.swagger.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    // AddUser (uses .save() in UserService)

    // UpdateUser (uses .save() in UserService)

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    // Select all Users who do not have an account via JPQL
    @Query(value = "SELECT u FROM User u WHERE NOT EXISTS (SELECT a FROM u.accounts a)")
    List<User> findAllWithoutAccount();

}
