package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHistoryTest {
    CommandHistory commandHistory;

    @BeforeEach
    void setup() {
        commandHistory = new CommandHistory(new Bank());
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
    void store_two_valid_commands_invalid() {
        assertInvalidCommandStored("Create Savings 12345678 0", false);
        assertInvalidCommandStored("Create Savings 00000001 0", false);
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
        List<String> expectedCommands = Arrays.asList("Create Investment 12345678 0", "Create Investment 12345678 0");

        storeMultipleCommands("Create Investment 12345678 0", 2);
        List<String> actualCommands = commandHistory.retrieveAllStored();

        assertEquals(expectedCommands, actualCommands);
    }
}
