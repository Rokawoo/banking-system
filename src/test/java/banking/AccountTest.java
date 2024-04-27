package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    Account cd;
    Account savings;

    @BeforeEach
    public void setUp() {
        savings = new Savings(0.6);
        cd = new CD(9.8, 100.50);
    }

    @Test
    public void account_apr_is_specified_apr_value() {
        double actual = cd.getApr();

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

    @Test
    public void account_type_is_specified_type_value() {
        String actual = savings.getType();

        assertEquals("savings", actual);
    }

    @Test
    public void deposit_into_savings_account_increases_balance() {
        savings.deposit(100.50);
        double actual = savings.getBalance();

        assertEquals(100.50, actual);
    }

    @Test
    public void withdraw_from_savings_account_decreases_balance() {
        savings.deposit(500.75);
        savings.withdraw(50.25);
        double actual = savings.getBalance();

        assertEquals(450.50, actual);
    }

    @Test
    public void withdraw_more_than_balance_sets_balance_to_zero_in_savings_account() {
        savings.withdraw(600);
        double actual = savings.getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void valid_deposit_returns_true_for_savings_account() {
        boolean actual = savings.isValidDeposit(300);

        assertTrue(actual);
    }

    @Test
    public void invalid_deposit_returns_false_for_savings_account() {
        boolean actual = savings.isValidDeposit(-100);

        assertFalse(actual);
    }

    @Test
    public void valid_withdraw_returns_true_for_savings_account() {
        boolean actual = savings.isValidWithdraw(200, 3);

        assertTrue(actual);
    }

    @Test
    public void invalid_withdraw_amount_returns_false_for_savings_account() {
        boolean actual = savings.isValidWithdraw(1001, 3);

        assertFalse(actual);
    }

    @Test
    public void invalid_withdraw_month_returns_false_for_savings_account() {
        savings.updateWithdrawHold(3);
        boolean actual = savings.isValidWithdraw(1000, 3);
        assertFalse(actual);
    }

    @Test
    public void transaction_history_includes_commands_for_savings_account() {
        savings.addCommandToTransactionHistory("Deposit 12345678 200");
        savings.addCommandToTransactionHistory("Withdraw 12345678 50.75");

        assertEquals(2, savings.retrieveTransactionHistory().size());
    }
}
