package fr.arolla.bankaccount.infra;

import fr.arolla.bankaccount.domain.CurrencyChangeRate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Double.MAX_VALUE;

public final class RandomCurrencyChangeRate implements CurrencyChangeRate {

    @Override
    public BigDecimal getRate(Currency from, Currency to) {

        if (from.equals(to)) {
            return new BigDecimal(1);
        }

        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, MAX_VALUE));
    }
}
