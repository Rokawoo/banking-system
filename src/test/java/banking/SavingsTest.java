package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsTest {

    Savings savings;

    @BeforeEach
    public void setUp() {
        savings = new Savings(5.0);
    }

    @Test
    // Checking and Savings: when created, its starting balance is $0.
    public void savings_created_with_0_balance_by_default() {
        double actual = savings.getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void is_valid_deposit_returns_true_for_valid_deposit_amount_in_savings_account() {
        boolean actual = savings.isValidDeposit(500);

        assertTrue(actual);
    }

    @Test
    public void is_valid_deposit_returns_false_for_negative_deposit_amount_in_savings_account() {
        boolean actual = savings.isValidDeposit(-100);

        assertFalse(actual);
    }

    @Test
    public void is_valid_deposit_returns_false_for_exceeding_max_deposit_amount_in_savings_account() {
        boolean actual = savings.isValidDeposit(3000);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_true_for_valid_withdraw_amount_in_savings_account_when_holds_expired() {
        savings.setInitialWithdrawHold(6);
        boolean actual = savings.isValidWithdraw(800, 10);

        assertTrue(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_negative_withdraw_amount_in_savings_account() {
        boolean actual = savings.isValidWithdraw(-100, 10);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_exceeding_max_withdraw_amount_in_savings_account() {
        savings.updateWithdrawHold(6);
        boolean actual = savings.isValidWithdraw(1500, 10);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_withdraw_before_holds_expire_in_savings_account() {
        savings.updateWithdrawHold(6);
        boolean actual = savings.isValidWithdraw(800, 5);

        assertFalse(actual);
    }

    @Test
    public void set_initial_withdraw_hold_sets_withdraw_hold_correctly_for_savings_account() {
        savings.setInitialWithdrawHold(6);

        assertEquals(-1, savings.withdrawHoldUntil);
    }

    @Test
    public void update_withdraw_hold_sets_withdraw_hold_correctly_for_savings_account() {
        savings.updateWithdrawHold(8);

        assertEquals(9, savings.withdrawHoldUntil);
    }
}
