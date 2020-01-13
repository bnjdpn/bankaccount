package fr.arolla.bankaccount;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.IBAN;
import fr.arolla.bankaccount.domain.operation.Operation;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Set;

import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.DEPOSIT;

public class AccountTestFactory {

    public static final Currency USD = Currency.getInstance("USD");
    public static final Currency EUR = Currency.getInstance("EUR");

    private AccountTestFactory() {
        // Do nothing
    }

    public static Account an_account_in_euros_with_a_first_deposit_of_ten_euros() {
        return new Account(
                new IBAN("FR", "76", "30001007941234567890185"),
                "Test Bank Account",
                Currency.getInstance("EUR"),
                Set.of(
                        new Operation(
                                DEPOSIT,
                                new Amount(new BigDecimal(10), EUR),
                                "First deposit",
                                Instant.now().minus(1, ChronoUnit.DAYS)
                        )
                )
        );
    }

}
