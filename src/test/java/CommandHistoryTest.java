import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandHistoryTest {
    CommandHistory commandHistory;

    @BeforeEach
    void setup() {
        commandHistory = new CommandHistory();
    }

    @Test
    void store_no_command_valid() {
        boolean actual = commandHistory.storeCommand("");
        assertTrue(actual);
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
        boolean actual2 = commandHistory.storeCommand("Create Investment 12345678 0");
        assertTrue(actual2);
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
        boolean actual2 = commandHistory.storeCommand("Create Investment 12345678 0");
        assertTrue(actual2);
    }

    @Test
    void store_valid_command_after_invalid_command_invalid() {
        boolean actual = commandHistory.storeCommand("Create Investment 12345678 0");
        assertTrue(actual);
        boolean actual2 = commandHistory.storeCommand("Create Savings 12345678 0");
        assertFalse(actual2);
    }

    @Test
    void retrieve_all_stored_commands_valid() {
        List<String> expectedCommands = Arrays.asList("Create Investment 12345678 0", "Create Investment 12345678 0");

        commandHistory.storeCommand("Create Investment 12345678 0");
        commandHistory.storeCommand("Create Investment 12345678 0");
        List<String> actualCommands = commandHistory.retrieveAllStored();

        assertEquals(expectedCommands, actualCommands);;
    }
}
