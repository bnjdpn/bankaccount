package fr.arolla.bankaccount.domain.operation;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.History;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CheckOperations {

    public List<History> seeHistory(Account account) {
        return Optional.ofNullable(account.operations)
                .orElseGet(Collections::emptySet)
                .stream()
                .sorted(Comparator.comparing(operation -> operation.date))
                .reduce(new ArrayList<>(),
                        (histories, operation) -> accumulateHistories(account, histories, operation),
                        this::combineHistories
                );
    }

    private ArrayList<History> accumulateHistories(Account account, ArrayList<History> histories, Operation operation) {

        var history = histories.stream()
                .skip(Math.max(histories.size() - 1, 0))
                .findFirst()
                .map(lastHistory -> {
                    var accountBalance = operation.calculNewBalance(lastHistory.balance);
                    return new History(accountBalance, operation);
                })
                .orElseGet(() -> {
                    var initialBalance = new Amount(new BigDecimal(0), account.currency);
                    var accountBalance = operation.calculNewBalance(initialBalance);
                    return new History(accountBalance, operation);
                });

        histories.add(history);

        return histories;
    }

    private ArrayList<History> combineHistories(ArrayList<History> histories1, ArrayList<History> histories2) {
        return Stream.concat(
                histories1.stream(),
                histories2.stream()
        )
                .distinct()
                .sorted(Comparator.comparing(history -> history.operation.date))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
