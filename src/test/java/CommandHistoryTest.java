import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandHistoryTest {
    CommandHistory commandHistory;
    Bank bank;

    @BeforeEach
    void setup() {
        bank = new Bank();
        commandHistory = new CommandHistory(bank);
    }

    @Test
    void store_invalid_command_valid() {
        boolean actual = commandHistory.storeCommand("Create Investment 12345678 0");
        assertTrue(actual);
    }

    @Test
    void store_two_invalid_command_valid() {
        boolean actual = commandHistory.storeCommand("Create Investment 12345678 0");
        assertTrue(actual);
        boolean actual2 = commandHistory.storeCommand("Create Crypto 12345678 0");assertTrue(actual2);
    }

    @Test
    void store_valid_command_invalid() {
        boolean actual = commandHistory.storeCommand("Create Savings 12345678 0");
        assertFalse(actual);
    }

    @Test
    void store_two_valid_commands_invalid() {
        boolean actual = commandHistory.storeCommand("Create Savings 12345678 0");
        assertFalse(actual);
        boolean actual2 = commandHistory.storeCommand("Create Savings 00000001 0");
        assertFalse(actual2);
    }

    @Test
    void store_invalid_command_after_valid_command_valid() {
        boolean actual = commandHistory.storeCommand("Create Savings 12345678 0");
        assertFalse(actual);
        boolean actual2 = commandHistory.storeCommand("Create 01");
        assertTrue(actual2);
    }
}
