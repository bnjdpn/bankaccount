package fr.arolla.bankaccount.domain;

import java.math.BigDecimal;
import java.util.Currency;

@FunctionalInterface
public interface CurrencyChangeRate {

    BigDecimal getRate(Currency from, Currency to);

}
