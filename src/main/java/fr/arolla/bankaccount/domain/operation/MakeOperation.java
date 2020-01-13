package fr.arolla.bankaccount.domain.operation;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.CurrencyChangeRate;
import fr.arolla.bankaccount.domain.operation.Operation.OperationType;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

public abstract class MakeOperation {

    protected final Clock clock;
    protected final CurrencyChangeRate currencyChangeRate;

    public MakeOperation(Clock clock,
                         CurrencyChangeRate currencyChangeRate) {
        this.clock = clock;
        this.currencyChangeRate = currencyChangeRate;
    }

    public Account make(Account account, Amount amount, String name) {

        var accountCurrency = account.currency;
        var amountInAccountCurrency = amount.convertTo(accountCurrency, currencyChangeRate);

        var operation = new Operation(getOperationType(), amountInAccountCurrency, name, Instant.now(clock));

        var operations = new HashSet<>(
                Optional.ofNullable(account.operations)
                        .orElseGet(Collections::emptySet)
        );

        operations.add(operation);

        return new Account(
                account.iban,
                account.name,
                account.currency,
                operations
        );
    }

    public abstract OperationType getOperationType();

}
