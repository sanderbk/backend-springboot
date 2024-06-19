package io.swagger.repo;

import io.swagger.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t WHERE t.userPerforming = :userId AND t.timestamp BETWEEN :startTime AND :endTime AND t.accountType = io.swagger.model.enumeration.AccountType.CURRENT")
    List<Transaction> findAllByUserPerformingAndTimestampBetweenAndAccountTypeCurrent(@Param("userId") UUID userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    List<Transaction> findByUserPerforming(UUID userId);
}
