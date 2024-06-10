package io.swagger.repo;

import io.swagger.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {
    Account findAccountByUserId(UUID userid);

    Account save(Account a);

    Optional<Account> findAccountByIban(String iban);

    List<Account> findAccountsByUserId(UUID userid);
}
