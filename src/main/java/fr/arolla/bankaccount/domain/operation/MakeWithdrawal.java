package fr.arolla.bankaccount.domain.operation;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.CurrencyChangeRate;
import fr.arolla.bankaccount.domain.operation.Operation.OperationType;

import java.math.BigDecimal;
import java.time.Clock;

import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.WITHDRAWAL;

public final class MakeWithdrawal extends MakeOperation {

    public MakeWithdrawal(Clock clock,
                          CurrencyChangeRate currencyChangeRate) {
        super(clock, currencyChangeRate);
    }

    @Override
    public Account make(Account account, Amount amount, String name) {

        if (isAccountBalanceWillBeNegativeAfterWithdrawal(account, amount)) {
            throw new UnsupportedOperationException("Cannot withdraw money you don't have !");
        }

        return super.make(account, amount, name);
    }

    private boolean isAccountBalanceWillBeNegativeAfterWithdrawal(Account account, Amount amount) {
        var amountInAccountCurrency = amount.convertTo(account.currency, super.currencyChangeRate);
        var accountBalance = account.getBalance();

        var newPotentialAccountBalanceAfterWithdraw = accountBalance.subtract(amountInAccountCurrency);

        return newPotentialAccountBalanceAfterWithdraw.amount.compareTo(new BigDecimal(0)) < 0;
    }

    @Override
    public OperationType getOperationType() {
        return WITHDRAWAL;
    }
}
