import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandHistoryTest {
    CommandHistory commandHistory;

    @BeforeEach
    void setup() {
        commandHistory = new CommandHistory(new Bank());
    }

    private void assertCommandStored(String command, boolean expectedResult) {
        boolean actualResult = commandHistory.storeCommand(command);
        assertEquals(expectedResult, actualResult);
    }

    private void storeMultipleCommands(String command, int count) {
        for (int i = 0; i < count; i++) {
            commandHistory.storeCommand(command);
        }
    }

    @Test
    void store_no_command_valid() {
        assertCommandStored("", true);
    }

    @Test
    void store_invalid_command_valid() {
        assertCommandStored("Create Investment 12345678 0", true);
    }

    @Test
    void store_two_invalid_command_valid() {
        assertCommandStored("Create Investment 12345678 0", true);
        assertCommandStored("Create Investment 12345678 0", true);
    }

    @Test
    void store_valid_command_invalid() {
        assertCommandStored("Create Savings 12345678 0", false);
    }

    @Test
    void store_two_valid_commands_invalid() {
        assertCommandStored("Create Savings 12345678 0", false);
        assertCommandStored("Create Savings 00000001 0", false);
    }

    @Test
    void store_invalid_command_after_valid_command_valid() {
        assertCommandStored("Create Savings 12345678 0", false);
        assertCommandStored("Create Investment 12345678 0", true);
    }

    @Test
    void store_valid_command_after_invalid_command_invalid() {
        assertCommandStored("Create Investment 12345678 0", true);
        assertCommandStored("Create Savings 12345678 0", false);
    }

    @Test
    void retrieve_all_stored_commands_valid() {
        List<String> expectedCommands = Arrays.asList("Create Investment 12345678 0", "Create Investment 12345678 0");

        storeMultipleCommands("Create Investment 12345678 0", 2);
        List<String> actualCommands = commandHistory.retrieveAllStored();

        assertEquals(expectedCommands, actualCommands);
    }
}
