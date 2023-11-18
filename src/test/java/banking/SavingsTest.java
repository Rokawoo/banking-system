package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsTest {
    @Test
    // Checking and Savings: when created, its starting balance is $0.
    public void savings_created_with_0_balance_by_default() {
        Savings savings = new Savings(9.8);
        double actual = savings.getBalance();

        assertEquals(0, actual);
    }
}
