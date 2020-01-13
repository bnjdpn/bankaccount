package fr.arolla.bankaccount.domain.operation;

import fr.arolla.bankaccount.domain.CurrencyChangeRate;
import fr.arolla.bankaccount.domain.operation.Operation.OperationType;

import java.time.Clock;

import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.DEPOSIT;

public final class MakeDeposit extends MakeOperation {

    public MakeDeposit(Clock clock, CurrencyChangeRate currencyChangeRate) {
        super(clock, currencyChangeRate);
    }

    @Override
    public OperationType getOperationType() {
        return DEPOSIT;
    }
}
