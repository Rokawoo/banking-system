package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHistoryTest {
    CommandHistory commandHistory;
    Bank bank;
    Savings savings;
    Checking checking;

    @BeforeEach
    void setup() {
        bank = new Bank();
        savings = new Savings(9);
        checking = new Checking(9);
        bank.addAccount("00000001", savings);
        bank.addAccount("00000002", checking);
        commandHistory = new CommandHistory(bank);
    }

    private void assertValidTransactionCommandStored(String command, boolean expectedResult) {
        boolean actualResult = commandHistory.storeValidTransactionCommand(command);
        assertEquals(expectedResult, actualResult);
    }

    private void assertInvalidCommandStored(String command, boolean expectedResult) {
        boolean actualResult = commandHistory.storeInvalidCommand(command);
        assertEquals(expectedResult, actualResult);
    }

    private void storeMultipleCommands(String command, int count) {
        for (int i = 0; i < count; i++) {
            commandHistory.storeInvalidCommand(command);
        }
    }

    @Test
    void store_no_command_valid() {
        assertInvalidCommandStored("", true);
    }

    @Test
    void store_invalid_command_valid() {
        assertInvalidCommandStored("Create Investment 12345678 0", true);
    }

    @Test
    void store_two_invalid_command_valid() {
        assertInvalidCommandStored("Create Investment 12345678 0", true);
        assertInvalidCommandStored("Create Investment 12345678 0", true);
    }

    @Test
    void store_valid_command_invalid() {
        assertInvalidCommandStored("Create Savings 12345678 0", false);
    }

    @Test
    void store_two_valid_commands_in_invalid_list_invalid() {
        assertInvalidCommandStored("Create Savings 12345678 0", false);
        assertInvalidCommandStored("Create Savings 87654321 0", false);
    }

    @Test
    void store_invalid_command_after_valid_command_valid() {
        assertInvalidCommandStored("Create Savings 12345678 0", false);
        assertInvalidCommandStored("Create Investment 12345678 0", true);
    }

    @Test
    void store_valid_command_after_invalid_command_invalid() {
        assertInvalidCommandStored("Create Investment 12345678 0", true);
        assertInvalidCommandStored("Create Savings 12345678 0", false);
    }

    @Test
    void retrieve_all_stored_commands_valid() {
        List<String> expectedCommands = Arrays.asList("Checking 00000002 0.00 9.00", "Savings 00000001 0.00 9.00", "Create Investment 12345678 0", "Create Investment 12345678 0");

        storeMultipleCommands("Create Investment 12345678 0", 2);
        List<String> actualCommands = commandHistory.retrieveAllStored();

        assertEquals(expectedCommands, actualCommands);
    }

    @Test
    void store_valid_deposit_command_valid() {
        assertValidTransactionCommandStored("Deposit 00000001 500", true);
    }

    @Test
    void store_valid_withdraw_command_valid() {
        assertValidTransactionCommandStored("Withdraw 00000001 200", true);
    }

    @Test
    void store_valid_transfer_command_valid() {
        assertValidTransactionCommandStored("Transfer 00000001 00000002 300", true);
    }

    @Test
    void store_invalid_deposit_command_in_valid_list_invalid() {
        assertValidTransactionCommandStored("Deposit 00000001 -500", false);
    }

    @Test
    void store_invalid_withdraw_command_invalid() {
        assertValidTransactionCommandStored("Withdraw 00000002 2000", false);
    }

    @Test
    void store_invalid_transfer_command_invalid() {
        assertValidTransactionCommandStored("Transfer 00000001 00000002 -300", false);
    }

    @Test
    void store_valid_and_invalid_deposit_commands_valid() {
        assertValidTransactionCommandStored("Deposit 00000001 500", true);
        assertValidTransactionCommandStored("Deposit 00000001 -200", false);
    }

    @Test
    void store_valid_and_get_them_from_account_valid() {
        assertValidTransactionCommandStored("Deposit 00000001 500", true);
        List<String> actual = bank.retrieveAccount("00000001").retrieveTransactionHistory();

        assertEquals("Deposit 00000001 500", actual.get(0));
    }

}

