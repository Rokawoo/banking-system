import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

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

    private String[] getCommandData(String command) {
        return command.split("\\s+");
    }

    private void assertAccountCreation(String command) {
        String[] commandData = getCommandData(command);
        String accountType = commandData[1].toLowerCase();
        String accountId = commandData[2];
        double apr = Float.parseFloat(commandData[3]);

        commandProcessor.process(command);
        Account createdAccount = bank.retrieveAccount(accountId);
        assertNotNull(createdAccount);
        assertEquals(accountType, createdAccount.getType());
        assertEquals(apr, createdAccount.getApr());

        if (accountType.equals("cd")) {
            double balance = Float.parseFloat(commandData[4]);
            assertEquals(balance, createdAccount.getBalance());
        }
    }

    private void assertAccountDeposit(String command) {
        String[] commandData = getCommandData(command);
        String accountId = commandData[1];
        double depositBalance = Float.parseFloat(commandData[2]);

        Account depositedAccount = bank.retrieveAccount(accountId);
        double accountBalanceBeforeDeposit = depositedAccount.getBalance();
        commandProcessor.process(command);
        double accountBalanceAfterDeposit = depositedAccount.getBalance();
        assertEquals(accountBalanceBeforeDeposit + depositBalance, accountBalanceAfterDeposit);
    }

    // Account Creation
    @Test
    void create_account_with_noncase_sensitive_command_valid() {
        assertAccountCreation("cReatE SaViNgs 12345678 0");
    }

    @Test
    void create_savings_account_valid() {
        assertAccountCreation("Create Savings 12345678 0");
    }

    @Test
    void create_cd_account_valid_with_1000_initial_balance_valid() {
        assertAccountCreation("Create CD 12345678 0 1000");
    }

    @Test
    void create_cd_account_valid_with_float_initial_balance_valid() {
        assertAccountCreation("Create CD 12345678 0 5000.20");
    }

    @Test
    void create_cd_account_valid_with_5000_initial_balance_valid() {
        assertAccountCreation("Create CD 12345678 0 5000.");
    }

    @Test
    void create_cd_account_valid_with_10000_initial_balance_valid() {
        assertAccountCreation("Create CD 12345678 0 10000");
        
    }

    @Test
    void create_account_with_0_apr_valid() {
        assertAccountCreation("Create Savings 12345678 0");
        
    }

    @Test
    void create_account_with_5_apr_valid() {
        assertAccountCreation("Create Savings 12345678 5");
        
    }

    @Test
    void create_account_with_float_apr_valid() {
        assertAccountCreation("Create Savings 12345678 5.2");
        
    }

    @Test
    void create_account_with_10_apr_valid() {
        assertAccountCreation("Create Savings 12345678 10");
        
    }

    @Test
    void create_account_with_8len_id_valid() {
        assertAccountCreation("Create Savings 12345678 0");

    }

    // Deposit Tests
    @Test
    void savings_checking_noncase_sensitive_deposit_command_valid() {
        assertAccountDeposit("dEpOSiT 00000001 0");
        
    }

    @Test
    void savings_checking_0_zero_amount_deposited_valid() {
        assertAccountDeposit("Deposit 00000001 0");
        
    }

    @Test
    void savings_checking_float_amount_deposited_valid() {
        assertAccountDeposit("Deposit 00000001 500.50");
        
    }

    @Test
    void savings_2500_max_amount_deposited_valid() {
        assertAccountDeposit("Deposit 00000001 2500");
        
    }

    @Test
    void savings_checking_consecutive_amount_deposited_to_same_account_valid() {
        assertAccountDeposit("Deposit 00000001 500.50");
        assertAccountDeposit("Deposit 00000001 500");
    }

    @Test
    void savings_checking_consecutive_amount_deposited_to_different_account_valid() {
        assertAccountDeposit("Deposit 00000001 500.50");
        assertAccountDeposit("Deposit 00000002 500");
    }

}

