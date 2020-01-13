package fr.arolla.bankaccount.domain.operation;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.History;
import fr.arolla.bankaccount.domain.IBAN;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import static fr.arolla.bankaccount.AccountTestFactory.EUR;
import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.DEPOSIT;
import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.WITHDRAWAL;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.assertj.core.api.Assertions.assertThat;

class CheckOperationsTest {

    @Test
    void should_return_account_history() {
        // Given
        var firstOperation = new Operation(DEPOSIT, new Amount(new BigDecimal(500), EUR), "Salary", Instant.now().minus(1, DAYS));
        var secondOperation = new Operation(WITHDRAWAL, new Amount(new BigDecimal(100), EUR), "Rent", Instant.now().minus(1, HOURS));
        var thirdOperation = new Operation(WITHDRAWAL, new Amount(new BigDecimal(75), EUR), "Shopping", Instant.now());

        var account = new Account(
                new IBAN("FR", "12", "34567890"),
                "My account",
                Currency.getInstance("EUR"),
                Set.of(firstOperation, secondOperation, thirdOperation));

        // When
        List<History> histories = new CheckOperations().seeHistory(account);

        // Then
        assertThat(histories).containsExactly(
                new History(new Amount(new BigDecimal(500), EUR), firstOperation),
                new History(new Amount(new BigDecimal(400), EUR), secondOperation),
                new History(new Amount(new BigDecimal(325), EUR), thirdOperation)
        );
    }

}
