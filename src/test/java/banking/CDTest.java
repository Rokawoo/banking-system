package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CDTest {
    CD cd;

    @BeforeEach
    public void setUp() {
        cd = new CD(7.5, 1000);
    }

    @Test
    // CD: when created, its starting balance is whatever the supplied balance was.
    public void cd_created_with_specified_balance_by_default() {
        double actual = cd.getBalance();

        assertEquals(1000, actual);
    }

    @Test
    public void is_valid_deposit_always_returns_false_for_CD_account() {
        boolean actual = cd.isValidDeposit(500);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_true_for_CD_account_when_amount_equals_balance_and_holds_expired() {
        cd.setInitialWithdrawHold(6);  // Assuming CD created in month 6
        boolean actual = cd.isValidWithdraw(1000, 19);  // Trying to withdraw in month 19

        assertTrue(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_CD_account_when_amount_does_not_equal_balance() {
        cd.setInitialWithdrawHold(6);
        boolean actual = cd.isValidWithdraw(500, 19);

        assertFalse(actual);
    }

    @Test
    public void is_valid_withdraw_returns_false_for_CD_account_when_holds_not_expired() {
        cd.setInitialWithdrawHold(6);
        boolean actual = cd.isValidWithdraw(1000, 10);  // Trying to withdraw before holds expire

        assertFalse(actual);
    }

    @Test
    public void set_initial_withdraw_hold_sets_withdraw_hold_correctly_for_CD_account() {
        cd.setInitialWithdrawHold(6);

        assertEquals(18, cd.withdrawHoldUntil);
    }

}
