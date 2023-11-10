import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    CommandProcessor commandProcessor;
    Bank bank;
    Savings savings;
    Checking checking;

    @BeforeEach
    void setup() {
        savings = new Savings(1.4);
        checking = new Checking(1.4);
        bank = new Bank();
        bank.addAccount("00000001", savings);
        bank.addAccount("00000002", checking);
        commandProcessor = new CommandProcessor(bank);
    }

    // Account Creation
    @Test
    void create_account_with_noncase_sensitive_command_valid() {
        boolean actual = commandProcessor.process("cReatE SaViNgs 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_savings_account_valid() {
        boolean actual = commandProcessor.process("Create Savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_1000_initial_balance_valid() {
        boolean actual = commandProcessor.process("Create CD 12345678 0 1000");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_float_initial_balance_valid() {
        boolean actual = commandProcessor.process("Create CD 12345678 0 5000.20");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_5000_initial_balance_valid() {
        boolean actual = commandProcessor.process("Create CD 12345678 0 5000.");
        assertTrue(actual);
    }

    @Test
    void create_cd_account_valid_with_10000_initial_balance_valid() {
        boolean actual = commandProcessor.process("Create CD 12345678 0 10000");
        assertTrue(actual);
    }

    @Test
    void create_account_with_0_apr_valid() {
        boolean actual = commandProcessor.process("Create Savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void create_account_with_5_apr_valid() {
        boolean actual = commandProcessor.process("Create Savings 12345678 5");
        assertTrue(actual);
    }

    @Test
    void create_account_with_float_apr_valid() {
        boolean actual = commandProcessor.process("Create Savings 12345678 5.2");
        assertTrue(actual);
    }

    @Test
    void create_account_with_10_apr_valid() {
        boolean actual = commandProcessor.process("Create Savings 12345678 10");
        assertTrue(actual);
    }

    @Test
    void create_account_with_8len_id_valid() {
        boolean actual = commandProcessor.process("Create Savings 12345678 0");
        assertTrue(actual);
    }

    // Deposit Tests
    @Test
    void savings_checking_noncase_sensitive_deposit_command_valid() {
        boolean actual = commandProcessor.process("dEpOSiT 00000001 0");
        assertTrue(actual);
    }

    @Test
    void savings_checking_0_zero_amount_deposited_valid() {
        boolean actual = commandProcessor.process("Deposit 00000001 0");
        assertTrue(actual);
    }

    @Test
    void savings_checking_float_amount_deposited_valid() {
        boolean actual = commandProcessor.process("Deposit 00000001 500.50");
        assertTrue(actual);
    }

    @Test
    void savings_2500_max_amount_deposited_valid() {
        boolean actual = commandProcessor.process("Deposit 00000001 2500");
        assertTrue(actual);
    }

    @Test
    void savings_checking_consecutive_amount_deposited_to_same_account_valid() {
        boolean actual = commandProcessor.process("Deposit 00000001 500.50");
        assertTrue(actual);
        boolean actual2 = commandProcessor.process("Deposit 00000001 500");
        assertTrue(actual2);
    }

    @Test
    void savings_checking_consecutive_amount_deposited_to_different_account_valid() {
        boolean actual = commandProcessor.process("Deposit 00000001 500.50");
        assertTrue(actual);
        boolean actual2 = commandProcessor.process("Deposit 00000002 500");
        assertTrue(actual2);
    }


}

