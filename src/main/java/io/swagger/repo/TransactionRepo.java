package io.swagger.repo;

import io.swagger.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllByUserPerformingAndTimestamp(UUID userId, LocalDateTime timestamp);
    List<Transaction> findByUserPerforming(UUID userId);
}
