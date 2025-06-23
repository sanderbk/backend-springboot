package io.swagger.repo;

import io.swagger.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UsersSpecifications {
    public static Specification<User> withUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> withFirstname(String firstname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%");
    }

    public static Specification<User> withLastname(String lastname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%");
    }

    public static Specification<User> withEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> withHasAccounts(Boolean hasAccounts) {
        return (root, query, criteriaBuilder) -> {
            if (hasAccounts) {
                // Users with at least one account
                return criteriaBuilder.isNotEmpty(root.get("accounts"));
            } else {
                // Users with no accounts
                return criteriaBuilder.isEmpty(root.get("accounts"));
            }
        };
    }
}
