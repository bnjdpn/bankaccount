package fr.arolla.bankaccount.domain.operation;

import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.CurrencyChangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static fr.arolla.bankaccount.AccountTestFactory.*;
import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.DEPOSIT;
import static org.assertj.core.api.Assertions.assertThat;

class MakeDepositTest {

    private Clock clock;
    private MakeDeposit makeDeposit;

    @BeforeEach
    void beforeEach() {
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        CurrencyChangeRate currencyChangeRate = (from, to) -> new BigDecimal(2);
        makeDeposit = new MakeDeposit(clock, currencyChangeRate);
    }

    @Test
    void should_make_a_deposit_of_ten_euros_when_deposit_five_dollars() {
        // Given
        var account = an_account_in_euros_with_a_first_deposit_of_ten_euros();

        // When
        var accountWithNewOperation = makeDeposit.make(
                account,
                new Amount(new BigDecimal(5), USD),
                "A deposit"
        );

        // Then
        assertThat(accountWithNewOperation.operations)
                .hasSize(2)
                .contains(
                        new Operation(
                                DEPOSIT,
                                new Amount(new BigDecimal(10), EUR), "A deposit",
                                Instant.now(clock)
                        )
                );
    }

}
