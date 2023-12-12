package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
    CommandValidator commandValidator;
    Bank bank;
    Savings savings;
    Checking checking;
    CD cd;

    @BeforeEach
    void setup() {
        savings = new Savings(1.4);
        checking = new Checking(1.4);
        cd = new CD(1.4, 5000);
        bank = new Bank();
        bank.addAccount("00000001", savings);
        bank.addAccount("00000002", checking);
        bank.addAccount("00000003", cd);
        cd.setInitialWithdrawHold(bank.getTime());
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void empty_command_invalid() {
        boolean actual = commandValidator.validate("");
        assertFalse(actual);
    }

    // Account Creation
    @Test
    void no_command_create_account_invalid() {
        boolean actual = commandValidator.validate("create 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_no_account_type_invalid() {
        boolean actual = commandValidator.validate("create 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_invalid_account_type_invalid() {
        boolean actual = commandValidator.validate("create Investment 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_noncase_sensitive_command_valid() {
        boolean actual = commandValidator.validate("cReatE SaViNgs 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_savings_account_valid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_savings_account_valid_with_initial_balance_invalid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 0 1500");
        assertFalse(actual);
    }

    @Test
    void create_cd_account_valid_with_no_initial_balance_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0");
        assertFalse(actual);
    }

    @Test
    void create_cd_account_valid_with_negative_initial_balance_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 -20.50");
        assertFalse(actual);
    }

    @Test
    void create_cd_account_valid_with_999_initial_balance_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 999");
        assertFalse(actual);
    }

    @Test
    void create_cd_account_valid_with_1000_initial_balance_valid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 1000");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_float_initial_balance_valid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 5000.20");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_5000_initial_balance_valid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 5000.");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_10000_initial_balance_valid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 10000");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_10001_initial_balance_invalid() {
        boolean actual = commandValidator.validate("Create CD 12345678 0 10001");
        assertFalse(actual);
    }

    @Test
    void create_account_with_no_command_invalid() {
        boolean actual = commandValidator.validate(" Savings 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_no_id_or_apr_invalid() {
        boolean actual = commandValidator.validate("create Savings");
        assertFalse(actual);
    }

    @Test
    void create_account_with_no_apr_invalid() {
        boolean actual = commandValidator.validate("create Savings 12345678");
        assertFalse(actual);
    }

    @Test
    void create_account_with_negative_apr_invalid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 -0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_0_apr_valid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_account_with_5_apr_valid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 5");
        assertTrue(actual);
    }

    @Test
    void create_account_with_float_apr_valid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 5.2");
        assertTrue(actual);
    }

    @Test
    void create_account_with_10_apr_valid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 10");
        assertTrue(actual);
    }

    @Test
    void create_account_with_11_apr_invalid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 11");
        assertFalse(actual);
    }

    @Test
    void create_account_with_no_id_invalid() {
        boolean actual = commandValidator.validate("create Savings 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_7len_id_invalid() {
        boolean actual = commandValidator.validate("Create Savings 1234567 0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_8len_id_valid() {
        boolean actual = commandValidator.validate("Create Savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_account_with_9len_id_invalid() {
        boolean actual = commandValidator.validate("Create Savings 123456789 0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_string_id_invalid() {
        boolean actual = commandValidator.validate("Create Savings ABCDEFGH 0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_duplicate_id_invalid() {
        boolean actual = commandValidator.validate("Create Savings 00000001 0");
        assertFalse(actual);
    }

    // Deposit Tests

    @Test
    void savings_checking_no_command_deposit_invalid() {
        boolean actual = commandValidator.validate("00000001 500");
        assertFalse(actual);
    }

    @Test
    void savings_checking_no_balance_deposit_invalid() {
        boolean actual = commandValidator.validate("Deposit 00000001");
        assertFalse(actual);
    }

    @Test
    void savings_checking_no_id_deposit_invalid() {
        boolean actual = commandValidator.validate("Deposit 500");
        assertFalse(actual);
    }

    @Test
    void savings_checking_invalid_id_deposit_invalid() {
        boolean actual = commandValidator.validate("Deposit ABCDEFGH 500");
        assertFalse(actual);
    }

    @Test
    void savings_checking_nonexistent_account_deposited_invalid() {
        boolean actual = commandValidator.validate("Deposit 99999999 500");
        assertFalse(actual);
    }

    @Test
    void savings_checking_negative_amount_deposited_invalid() {
        boolean actual = commandValidator.validate("Deposit 00000001 -1000");
        assertFalse(actual);
    }

    @Test
    void savings_checking_noncase_sensitive_deposit_command_valid() {
        boolean actual = commandValidator.validate("dEpOSiT 00000001 0");
        assertTrue(actual);
    }

    @Test
    void savings_checking_0_zero_amount_deposited_valid() {
        boolean actual = commandValidator.validate("Deposit 00000001 0");
        assertTrue(actual);
    }

    @Test
    void savings_checking_25000000_super_over_max_amount_deposited_invalid() {
        boolean actual = commandValidator.validate("Deposit 00000001 25000000");
        assertFalse(actual);
    }

    @Test
    void savings_checking_float_amount_deposited_valid() {
        boolean actual = commandValidator.validate("Deposit 00000001 500.50");
        assertTrue(actual);
    }

    @Test
    void savings_2500_max_amount_deposited_valid() {
        boolean actual = commandValidator.validate("Deposit 00000001 2500");
        assertTrue(actual);
    }

    @Test
    void savings_2501_over_max_amount_deposited_invalid() {
        boolean actual = commandValidator.validate("Deposit 00000001 2501");
        assertFalse(actual);
    }

    @Test
    void checking_1001_over_max_amount_deposited_invalid() {
        boolean actual = commandValidator.validate("Deposit 00000002 1001");
        assertFalse(actual);
    }

    @Test
    void savings_checking_consecutive_amount_deposited_to_same_account_valid() {
        boolean actual = commandValidator.validate("Deposit 00000001 500.50");
        assertTrue(actual);
        boolean actual2 = commandValidator.validate("Deposit 00000001 500");
        assertTrue(actual2);
    }

    @Test
    void savings_checking_consecutive_amount_deposited_to_different_account_valid() {
        boolean actual = commandValidator.validate("Deposit 00000001 500.50");
        assertTrue(actual);
        boolean actual2 = commandValidator.validate("Deposit 00000002 500");
        assertTrue(actual2);
    }

    // Withdraw

    @Test
    void savings_checking_consecutive_amount_withdrawn_invalid() {
        bank.bankUpdateWithdrawHold("00000001");
        boolean actual = commandValidator.validate("Withdraw 00000001 400.50");
        assertFalse(actual);
    }

    @Test
    void savings_consecutive_and_passtime1_amount_withdrawn_valid() {
        bank.bankUpdateWithdrawHold("00000001");
        bank.passTime(1);
        boolean actual = commandValidator.validate("Withdraw 00000001 400.50");
        assertTrue(actual);
    }

    @Test
    void cd_and_passtime11_amount_withdrawn_invalid() {
        bank.passTime(11);
        boolean actual = commandValidator.validate("Withdraw 00000003 5000");
        assertFalse(actual);
    }

    @Test
    void cd_and_passtime12_amount_withdrawn_valid() {
        bank.passTime(12);
        boolean actual = commandValidator.validate("Withdraw 00000003 5000");
        assertTrue(actual);
    }

    @Test
    void cd_and_passtime60_amount_withdrawn_valid() {
        bank.passTime(999);
        boolean actual = commandValidator.validate("Withdraw 00000003 5000");
        assertTrue(actual);
    }

    @Test
    void cd_and_passtime12_wrong_amount_withdrawn_invalid() {
        bank.passTime(12);
        boolean actual = commandValidator.validate("Withdraw 00000003 200");
        assertFalse(actual);
    }

    @Test
    void test_valid_withdrawal_from_checking_within_range() {
        boolean actual = commandValidator.validate("Withdraw 00000002 300");
        assertTrue(actual);
    }

    @Test
    void test_valid_withdrawal_from_checking_at_min_range() {
        boolean actual = commandValidator.validate("Withdraw 00000002 0");
        assertTrue(actual);
    }

    @Test
    void test_valid_withdrawal_from_checking_at_max_range() {
        boolean actual = commandValidator.validate("Withdraw 00000002 400");
        assertTrue(actual);
    }

    @Test
    void test_invalid_withdrawal_from_checking_exceeding_range() {
        boolean actual = commandValidator.validate("Withdraw 00000002 500");
        assertFalse(actual);
    }

    @Test
    void test_valid_withdrawal_from_savings_within_range() {
        boolean actual = commandValidator.validate("Withdraw 00000001 500");
        assertTrue(actual);
    }

    @Test
    void test_valid_withdrawal_from_savings_at_min_range() {
        boolean actual = commandValidator.validate("Withdraw 00000001 0");
        assertTrue(actual);
    }

    @Test
    void test_valid_withdrawal_from_savings_at_max_range() {
        boolean actual = commandValidator.validate("Withdraw 00000001 1000");
        assertTrue(actual);
    }

    @Test
    void test_invalid_withdrawal_from_savings_exceeding_range() {
        boolean actual = commandValidator.validate("Withdraw 00000001 1500");
        assertFalse(actual);
    }

    @Test
    void test_valid_withdrawal_from_cd_after_12_months() {
        bank.passTime(12);
        boolean actual = commandValidator.validate("Withdraw 00000003 5000");
        assertTrue(actual);
    }

    @Test
    void test_invalid_withdrawal_from_cd_before_12_months() {
        bank.passTime(11);
        boolean actual = commandValidator.validate("Withdraw 12345678 5000");
        assertFalse(actual);
    }

    @Test
    void test_invalid_withdrawal_from_cd_under_balance_after_12_months() {
        bank.passTime(12);
        boolean actual = commandValidator.validate("Withdraw 00000003 4999");
        assertFalse(actual);
    }

    @Test
    void test_invalid_withdrawal_from_cd_exceeding_balance_after_12_months() {
        bank.passTime(12);
        boolean actual = commandValidator.validate("Withdraw 00000003 5001");
        assertFalse(actual);
    }

    @Test
    void test_valid_withdrawal_zero_amount() {
        boolean actual = commandValidator.validate("Withdraw 00000002 0");
        assertTrue(actual);
    }


    @Test
    void test_invalid_withdrawal_negative_amount() {
        boolean actual = commandValidator.validate("Withdraw 00000002 -100");
        assertFalse(actual);
    }

    // Transfer

    @Test
    void test_valid_transfer_from_savings_to_checking() {
        boolean actual = commandValidator.validate("Transfer 00000001 00000002 300");
        assertTrue(actual);
    }

    @Test
    void test_valid_transfer_from_checking_to_savings() {
        boolean actual = commandValidator.validate("Transfer 00000002 00000001 200");
        assertTrue(actual);
    }

    @Test
    void test_valid_transfer_from_savings_to_savings() {
        Savings savings2 = new Savings(1);
        bank.addAccount("00000022", savings2);
        boolean actual = commandValidator.validate("Transfer 00000002 00000022 200");
        assertTrue(actual);
    }

    @Test
    void test_valid_transfer_from_checking_to_checking() {
        Checking checking2 = new Checking(1);
        bank.addAccount("00000022", checking2);
        boolean actual = commandValidator.validate("Transfer 00000002 00000022 400");
        assertTrue(actual);
    }

    @Test
    void test_invalid_transfer_from_cd_to_savings() {
        boolean actual = commandValidator.validate("Transfer 00000003 00000001 200");
        assertFalse(actual);
    }

    @Test
    void test_invalid_transfer_negative_amount() {
        boolean actual = commandValidator.validate("Transfer 00000001 00000002 -100");
        assertFalse(actual);
    }

    @Test
    void test_invalid_transfer_exceeding_max_range() {
        boolean actual = commandValidator.validate("Transfer 00000001 00000002 1001");
        assertFalse(actual);
    }

    @Test
    void test_invalid_transfer_same_from_and_to_accounts() {
        boolean actual = commandValidator.validate("Transfer 00000001 00000001 500");
        assertFalse(actual);
    }

    @Test
    void test_valid_transfer_zero_amount() {
        boolean actual = commandValidator.validate("Transfer 00000002 00000001 0");
        assertTrue(actual);
    }

    @Test
    void test_valid_pass_time_1_month() {
        boolean actual = commandValidator.validate("Pass 1");
        assertTrue(actual);
    }

    @Test
    void test_valid_pass_time_12_months() {
        boolean actual = commandValidator.validate("Pass 12");
        assertTrue(actual);
    }

    @Test
    void test_invalid_pass_time_0_months() {
        boolean actual = commandValidator.validate("Pass 0");
        assertFalse(actual);
    }

    @Test
    void test_invalid_pass_time_61_months() {
        boolean actual = commandValidator.validate("Pass 61");
        assertFalse(actual);
    }

    @Test
    void test_invalid_pass_time_999_months() {
        boolean actual = commandValidator.validate("Pass 61");
        assertFalse(actual);
    }

    @Test
    void test_valid_pass_time_multiple_iterations() {
        boolean actual1 = commandValidator.validate("Pass 6");
        assertTrue(actual1);

        boolean actual2 = commandValidator.validate("Pass 3");
        assertTrue(actual2);
    }

    @Test
    void test_invalid_pass_time_negative_months() {
        boolean actual = commandValidator.validate("Pass -1");
        assertFalse(actual);
    }
}
