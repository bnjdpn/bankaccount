package fr.arolla.bankaccount.infra;

import fr.arolla.bankaccount.domain.Account;
import fr.arolla.bankaccount.domain.Amount;
import fr.arolla.bankaccount.domain.IBAN;
import fr.arolla.bankaccount.domain.operation.CheckOperations;
import fr.arolla.bankaccount.domain.operation.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Set;

import static fr.arolla.bankaccount.AccountTestFactory.EUR;
import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.DEPOSIT;
import static fr.arolla.bankaccount.domain.operation.Operation.OperationType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;

class OperationsConsolePrinterTest {

    private OperationsConsolePrinter operationsConsolePrinter;

    private PrintStream oldPrintStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void beforeEach() {
        operationsConsolePrinter = new OperationsConsolePrinter(new CheckOperations());

        byteArrayOutputStream = new ByteArrayOutputStream();
        var printStream = new PrintStream(byteArrayOutputStream);

        oldPrintStream = System.out;

        System.setOut(printStream);
    }

    @AfterEach
    void afterEach() {
        System.out.flush();
        System.setOut(oldPrintStream);
    }

    @Test
    void should_print_operations() {
        // Given
        var account = new Account(
                new IBAN("FR", "12", "34567890"),
                "My account",
                Currency.getInstance("EUR"),
                Set.of(
                        new Operation(DEPOSIT, new Amount(new BigDecimal(500), EUR), "Salary", Instant.parse("2020-01-10T12:34:56.00Z")),
                        new Operation(WITHDRAWAL, new Amount(new BigDecimal(100), EUR), "Rent", Instant.parse("2020-01-11T10:10:10.00Z")),
                        new Operation(WITHDRAWAL, new Amount(new BigDecimal(75), EUR), "Shopping", Instant.parse("2020-01-12T11:11:11.00Z"))
                )
        );

        // When
        operationsConsolePrinter.printOperations(account);

        // Then
        assertThat(byteArrayOutputStream.toString())
                .isEqualToNormalizingNewlines("""
                        OPERATION | DATE | AMOUNT | BALANCE
                        DEPOSIT | 10/01/2020 12:34 | 500 EUR | 500 EUR
                        WITHDRAWAL | 11/01/2020 10:10 | 100 EUR | 400 EUR
                        WITHDRAWAL | 12/01/2020 11:11 | 75 EUR | 325 EUR
                        """);
    }

}
