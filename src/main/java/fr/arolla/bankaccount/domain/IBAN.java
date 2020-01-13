package fr.arolla.bankaccount.domain;

import java.util.Objects;

public final class IBAN {

    public final String cp;
    public final String cc;
    public final String bban;

    public IBAN(String cp, String cc, String bban) {
        this.cp = cp;
        this.cc = cc;
        this.bban = bban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IBAN)) {
            return false;
        }
        IBAN iban = (IBAN) o;
        return Objects.equals(cp, iban.cp) &&
                Objects.equals(cc, iban.cc) &&
                Objects.equals(bban, iban.bban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cp, cc, bban);
    }

    @Override
    public String toString() {
        return cp + cc + bban;
    }
}
