package andrisbriedis.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;

class Account {
    @Id
    private String id;

    @Indexed(unique = true)
    private String owner;

    private BigDecimal balance = BigDecimal.ZERO;

    Account(String owner) {
        this.owner = owner;
    }

    String id() {
        return id;
    }

    String owner() {
        return owner;
    }

    BigDecimal balance() {
        return balance;
    }

    void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
