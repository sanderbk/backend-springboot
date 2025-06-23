package io.swagger.repo;

import io.swagger.model.entity.Account;
import io.swagger.model.enumeration.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {

    Account save(Account a);


    /**
     * Finds an account by its IBAN.
     *
     * @param iban the IBAN string
     * @return an Optional of Account if found
     */
    Optional<Account> findAccountByIban(String iban);

    /**
     * Checks if an account exists by its IBAN.
     *
     * @param iban the IBAN to check
     * @return true if an account with the given IBAN exists, false otherwise
     */
    boolean existsByIban(String iban);

    List<Account> findAccountsByUserId(UUID userid);

    List<Account> findByUser_UsernameContainingOrUser_FirstnameContainingOrUser_LastnameContaining(String username, String firstname, String lastname);

    @Query("SELECT a FROM Account a WHERE a.user.username = :username")
    List<Account> findAccountsByUser_Username(@Param("username") String username);

    default Page<Account> findAccounts(
            String IBAN, String firstname, String lastname, String username, AccountType type, Pageable pageable) {
        Specification<Account> specification = Specification.where(null);

        if (!IBAN.isEmpty()) {
            specification = specification.and(AccountsSpecifications.withIBAN(IBAN));
        }

        if (type != null) {
            specification = specification.and(AccountsSpecifications.withType(type));
        }

        if (!firstname.isEmpty() || !lastname.isEmpty() || !username.isEmpty()) {
            specification = specification.and(AccountsSpecifications.withCustomerName(firstname, lastname, username));
        }

        return findAll(specification, pageable);
    }

    Page<Account> findAll(Specification<Account> specification, Pageable pageable);
}

