package io.swagger.repo;

import io.swagger.model.entity.Account;
import io.swagger.model.enumeration.AccountType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class AccountsSpecifications {
    public static Specification<Account> withIBAN(String iban) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("iban"), iban);
    }

    public static Specification<Account> withType(AccountType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("accountType"), type);
    }

    public static Specification<Account> withCustomerName(String firstname, String lastname, String username) {
        return (root, query, criteriaBuilder) -> {
            Path<Object> userPath = root.get("user");
            Predicate predicate = criteriaBuilder.conjunction();

            if (firstname != null && !firstname.isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(userPath.get("firstname")),
                                "%" + firstname.toLowerCase() + "%"
                        )
                );
            }
            if (lastname != null && !lastname.isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(userPath.get("lastname")),
                                "%" + lastname.toLowerCase() + "%"
                        )
                );
            }
            if (username != null && !username.isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(userPath.get("username")),
                                "%" + username.toLowerCase() + "%"
                        )
                );
            }
            return predicate;
        };
    }
}
