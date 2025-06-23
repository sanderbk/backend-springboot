package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountIbanService {

    private static final String COUNTRY_CODE = "NL";
    private static final String BANK_CODE = "INHO";
    private static final int MAX_ATTEMPTS = 10;

    private final AccountRepo accountRepo;

    @Autowired
    public AccountIbanService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    /**
     * Assigns an IBAN to an account: uses fixed IBAN for the bank user, otherwise generates a new one.
     *
     * @param account the account to assign IBAN to
     */
    public void assignIbanIfMissing(Account account) {
        if (account.getUser() != null &&
                "InhollandBank".equals(account.getUser().getUsername())) {
            account.setIban("NL01INHO0000000001");
            return;
        }

        if (account.getIban() == null || account.getIban().isBlank()) {
            account.setIban(generateIban());
        }
    }

    /**
     * Generates a unique IBAN not already present in the database.
     *
     * @return unique IBAN string
     */
    public String generateIban() {
        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            String iban = createRandomIban();
            if (!accountRepo.existsByIban(iban)) {
                return iban;
            }
        }
        throw new IllegalStateException("Failed to generate unique IBAN after " + MAX_ATTEMPTS + " attempts.");
    }

    /**
     * Generates a random IBAN in the format: NL[10-99]INHOXXXXXXXXXX
     */
    private String createRandomIban() {
        int checkDigits = ThreadLocalRandom.current().nextInt(10, 100); // 10–99
        long accountNumber = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L); // 10-digit number

        return COUNTRY_CODE + checkDigits + BANK_CODE + accountNumber;
    }

    /**
     * Checks if the given IBAN already exists.
     *
     * @param iban IBAN to check
     * @return true if IBAN exists, false otherwise
     */
    public boolean isIbanPresent(String iban) {
        return iban != null && !iban.isBlank() && accountRepo.existsByIban(iban);
    }
}
