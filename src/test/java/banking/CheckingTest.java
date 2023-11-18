package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {
    @Test
    // Checking and Savings: when created, its starting balance is $0.
    public void checking_created_with_0_balance_by_default() {
        Checking checking = new Checking(9.8);
        double actual = checking.getBalance();

        assertEquals(0, actual);
    }
}
