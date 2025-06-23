package io.swagger.repo;

import io.swagger.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    default Page<User> findUsers(
            String username, String firstname, String lastname, String email, Boolean hasAccounts, Pageable pageable) {
        Specification<User> specification = Specification.where(null);

        if (!username.isEmpty()) {
            specification = specification.and(UsersSpecifications.withUsername(username));
        }
        if (!firstname.isEmpty()) {
            specification = specification.and(UsersSpecifications.withFirstname(firstname));
        }
        if (!lastname.isEmpty()) {
            specification = specification.and(UsersSpecifications.withLastname(lastname));
        }
        if (!email.isEmpty()) {
            specification = specification.and(UsersSpecifications.withEmail(email));
        }
        if (hasAccounts != null) {
            specification = specification.and(UsersSpecifications.withHasAccounts(hasAccounts));
        }

        return findAll(specification, pageable);
    }

    Page<User> findAll(Specification<User> specification, Pageable pageable);

}
