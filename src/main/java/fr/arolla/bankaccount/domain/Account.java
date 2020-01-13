package fr.arolla.bankaccount.domain;

import fr.arolla.bankaccount.domain.operation.Operation;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import java.util.Set;

public final class Account {

    public final IBAN iban;
    public final String name;
    public final Currency currency;
    public final Set<Operation> operations;

    public Account(IBAN iban, String name, Currency currency, Set<Operation> operations) {
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.operations = operations;
    }

    public Amount getBalance() {
        return operations
                .stream()
                .reduce(
                        new Amount(new BigDecimal(0), currency),
                        (amount, operation) -> operation.calculNewBalance(amount),
                        (amount1, amount2) -> amount1
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(iban, account.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban=" + iban +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                '}';
    }
}
