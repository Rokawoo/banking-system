package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckingTest {
    Checking checking;

    @BeforeEach
    public void setUp() {
        checking = new Checking(3.5);
    }

    @Test
    // Checking and Savings: when created, its starting balance is $0.
    public void checking_created_with_0_balance_by_default() {
        double actual = checking.getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void is_valid_deposit_returns_true_for_valid_deposit_amount_in_checking_account() {
        boolean actual = checking.isValidDeposit(500);

        assertTrue(actual);
    }

    @Test
    public void is_valid_deposit_returns_false_for_negative_deposit_amount_in_checking_account() {
        boolean actual = checking.isValidDeposit(-100);

        assertFalse(actual);
    }

    @Test
    public void is_valid_deposit_returns_false_for_exceeding_max_deposit_amount_in_checking_account() {
        boolean actual = checking.isValidDeposit(1200);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_true_for_valid_withdraw_amount_in_checking_account_when_holds_expired() {
        checking.setInitialWithdrawHold(6);
        boolean actual = checking.isValidWithdraw(300, 10);

        assertTrue(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_negative_withdraw_amount_in_checking_account() {
        boolean actual = checking.isValidWithdraw(-100, 10);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_exceeding_max_withdraw_amount_in_checking_account() {
        checking.setInitialWithdrawHold(6);
        boolean actual = checking.isValidWithdraw(500, 10);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_true_for_withdraw_after_setting_holds_expire_in_6months() {
        checking.updateWithdrawHold(6);
        boolean actual = checking.isValidWithdraw(300, 5);

        assertTrue(actual);
    }

    @Test
    public void set_initial_withdraw_hold_sets_withdraw_hold_correctly_for_checking_account() {
        checking.setInitialWithdrawHold(6);

        assertEquals(-1, checking.withdrawHoldUntil);
    }

    @Test
    public void test_is_valid_withdraw_boundary_survived() {
        Checking checking = new Checking(0.05);

        assertTrue(checking.isValidWithdraw(0, 0));
        assertTrue(checking.isValidWithdraw(400, 0));
        assertTrue(checking.isValidWithdraw(200, 10));
        assertTrue(checking.isValidWithdraw(200.50, 10));
    }

    @Test
    public void test_is_valid_withdraw_boundary_not_survived() {
        Checking checking = new Checking(0.05);

        assertFalse(checking.isValidWithdraw(500, 0));
        assertFalse(checking.isValidWithdraw(200, -2));
    }

    @Test
    public void update_withdraw_hold_sets_withdraw_hold_correctly_for_checking_account() {
        checking.updateWithdrawHold(8);

        assertEquals(-1, checking.withdrawHoldUntil);
    }
}

