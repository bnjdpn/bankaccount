package fr.arolla.bankaccount.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static fr.arolla.bankaccount.AccountTestFactory.EUR;
import static fr.arolla.bankaccount.AccountTestFactory.USD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    @Test
    void should_sum_amount() {
        // Given
        var tenEuros = new Amount(new BigDecimal(10), EUR);
        var oneEuro = new Amount(new BigDecimal(1), EUR);

        // When
        var euros = tenEuros.add(oneEuro);

        // Then
        assertThat(euros).isEqualTo(new Amount(new BigDecimal(11), EUR));
    }

    @Test
    void should_throw_exception_when_trying_to_add_differents_currencies() {
        // Given
        var oneEuro = new Amount(new BigDecimal(1), EUR);
        var oneDollar = new Amount(new BigDecimal(1), USD);

        // Then
        assertThatThrownBy(() -> oneEuro.add(oneDollar))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_substract_amount() {
        // Given
        var tenEuros = new Amount(new BigDecimal(10), EUR);
        var fourEuro = new Amount(new BigDecimal(4), EUR);

        // When
        var euros = tenEuros.subtract(fourEuro);

        // Then
        assertThat(euros).isEqualTo(new Amount(new BigDecimal(6), EUR));
    }

    @Test
    void should_throw_exception_when_trying_to_substract_differents_currencies() {
        // Given
        var oneEuro = new Amount(new BigDecimal(1), EUR);
        var oneDollar = new Amount(new BigDecimal(1), USD);

        // Then
        assertThatThrownBy(() -> oneEuro.subtract(oneDollar))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_convert_amount() {
        // Given
        var threeEuros = new Amount(new BigDecimal(3), EUR);
        CurrencyChangeRate changeRate = (from, to) -> new BigDecimal(2);

        // When
        var dollars = threeEuros.convertTo(USD, changeRate);

        // Then
        assertThat(dollars).isEqualTo(new Amount(new BigDecimal(6), USD));
    }
}
