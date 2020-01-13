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
import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MakeWithdrawalTest {

    private Clock clock;
    private MakeWithdrawal makeWithdrawal;

    @BeforeEach
    void beforeEach() {
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        CurrencyChangeRate currencyChangeRate = (from, to) -> new BigDecimal(2);
        makeWithdrawal = new MakeWithdrawal(clock, currencyChangeRate);
    }

    @Test
    void should_make_a_withdrawal_of_ten_euros_when_withdraw_five_dollars() {
        // Given
        var account = an_account_in_euros_with_a_first_deposit_of_ten_euros();

        // When
        var accountWithNewOperation = makeWithdrawal.make(
                account,
                new Amount(new BigDecimal(5), USD),
                "A withdrawal"
        );

        // Then
        assertThat(accountWithNewOperation.operations)
                .hasSize(2)
                .contains(
                        new Operation(
                                WITHDRAWAL,
                                new Amount(new BigDecimal(10), EUR), "A withdrawal",
                                Instant.now(clock)
                        )
                );
    }

    @Test
    void should_not_make_a_withdrawal_when_withdraw_money_you_dont_have() {
        // Given
        var account = an_account_in_euros_with_a_first_deposit_of_ten_euros();

        // Then
        assertThatThrownBy(() -> makeWithdrawal.make(
                account,
                new Amount(new BigDecimal(100), EUR),
                "A big withdrawal"
        )).isInstanceOf(UnsupportedOperationException.class);
    }

}
