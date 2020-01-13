package fr.arolla.bankaccount.infra;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.operation.CheckOperations;

import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;
import static java.time.format.FormatStyle.SHORT;
import static java.util.Locale.FRANCE;

public final class OperationsConsolePrinter {

    private static final String PRINTING_PATTERN = "%s | %s | %s | %s";

    private final CheckOperations checkOperations;
    private final DateTimeFormatter dateTimeFormatter;

    public OperationsConsolePrinter(CheckOperations checkOperations) {
        this.checkOperations = checkOperations;
        this.dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(SHORT)
                .withLocale(FRANCE)
                .withZone(UTC);
    }

    public void printOperations(Account account) {
        System.out.println(String.format(PRINTING_PATTERN, "OPERATION", "DATE", "AMOUNT", "BALANCE"));
        checkOperations.seeHistory(account)
                .forEach(history -> System.out.println(
                        String.format(
                                PRINTING_PATTERN,
                                history.operation.operationType,
                                dateTimeFormatter.format(history.operation.date),
                                formatAmount(history.operation.amount),
                                formatAmount(history.balance)
                        )));
    }

    private String formatAmount(Amount amount) {
        return amount.amount + " " + amount.currency;
    }

}
