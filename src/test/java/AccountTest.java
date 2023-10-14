import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    Account cd;

    @BeforeEach
    public void setUp() {cd = new CD(9.8, 100.50);}

    @Test
    public void account_apr_is_specified_apr_value() {
        double actual = cd.getAPR();

        assertEquals(9.8, actual);
    }

    @Test
    // All accounts: when depositing into it, its balance increases by the amount deposited.
    public void account_balance_increases_by_deposit_amount() {
        cd.deposit(100.25);
        double actual = cd.getBalance();

        assertEquals(200.75, actual);
    }

    @Test
    // Depositing twice into the same account works as expected.
    public void depositing_twice_into_same_account_works() {
        cd.deposit(100.25);
        cd.deposit(50);
        double actual = cd.getBalance();

        assertEquals(250.75, actual);
    }

    @Test
    // All accounts: when withdrawing from it, its balance decreased by the amount withdrawn.
    public void account_balance_decreases_by_withdraw_amount() {
        cd.withdraw(100.25);
        double actual = cd.getBalance();

        assertEquals(0.25, actual);
    }

    @Test
    // All accounts: when withdrawing from it, its balance decreased by the amount withdrawn.
    public void withdrawing_twice_from_same_account_works() {
        cd.withdraw(25);
        cd.withdraw(50.25);
        double actual = cd.getBalance();

        assertEquals(25.25, actual);
    }

    @Test
    // Withdrawing twice into the same account works as expected.
    public void when_withdrawing_balance_cannot_go_below_0() {
        cd.withdraw(1000);
        double actual = cd.getBalance();

        assertEquals(0, actual);
    }
}
