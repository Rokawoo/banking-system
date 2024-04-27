package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandProcessorTest {
    CommandProcessor commandProcessor;
    Bank bank;
    Savings savings;
    CD cd;
    Checking checking;

    @BeforeEach
    void setup() {
        savings = new Savings(1);
        checking = new Checking(1);
        cd = new CD(2.1, 2000);
        bank = new Bank();
        bank.addAccount("00000001", savings);
        bank.addAccount("00000002", checking);
        bank.addAccount("00000003", cd);
        commandProcessor = new CommandProcessor(bank);
    }

    private String[] getCommandData(String command) {
        return command.split("\\s+");
    }

    private void assertAccountCreation(String command) {
        String[] commandData = getCommandData(command);
        String accountType = commandData[1].toLowerCase();
        String accountId = commandData[2];
        double apr = Double.parseDouble(commandData[3]);

        commandProcessor.process(command);
        Account createdAccount = bank.retrieveAccount(accountId);
        assertNotNull(createdAccount);
        assertEquals(accountType, createdAccount.getType());
        assertEquals(apr, createdAccount.getApr());

        if (accountType.equals("cd")) {
            double balance = Double.parseDouble(commandData[4]);
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

    private void assertAccountWithdrawal(String command) {
        String[] commandData = getCommandData(command);
        String accountId = commandData[1];
        float withdrawAmount = Float.parseFloat(commandData[2]);

        Account withdrawnAccount = bank.retrieveAccount(accountId);
        double accountBalanceBeforeWithdrawal = withdrawnAccount.getBalance();
        commandProcessor.process(command);
        double accountBalanceAfterWithdrawal = withdrawnAccount.getBalance();
        double expectedBalance = Math.max(accountBalanceBeforeWithdrawal - withdrawAmount, 0);

        assertEquals(expectedBalance, accountBalanceAfterWithdrawal);
    }

    private void assertTransfer(String command) {
        String[] commandData = getCommandData(command);
        String fromAccountId = commandData[1];
        String toAccountId = commandData[2];
        float transferAmount = Float.parseFloat(commandData[3]);

        Account fromAccount = bank.retrieveAccount(fromAccountId);
        Account toAccount = bank.retrieveAccount(toAccountId);

        double fromAccountBalanceBeforeTransfer = fromAccount.getBalance();
        double toAccountBalanceBeforeTransfer = toAccount.getBalance();

        commandProcessor.process(command);

        double fromAccountBalanceAfterTransfer = fromAccount.getBalance();
        double toAccountBalanceAfterTransfer = toAccount.getBalance();
        double expectedFromAccountBalance = Math.max(fromAccountBalanceBeforeTransfer - transferAmount, 0);

        assertEquals(expectedFromAccountBalance, fromAccountBalanceAfterTransfer);
        assertEquals(toAccountBalanceBeforeTransfer + transferAmount, toAccountBalanceAfterTransfer);
    }

    private void assertPassTime(String command, double expectedSavingsBalance, double expectedCheckingBalance, double expectedCDBalance) {
        commandProcessor.process(command);

        assertEquals(expectedSavingsBalance, bank.retrieveAccount("00000001").getBalance());
        assertEquals(expectedCheckingBalance, bank.retrieveAccount("00000002").getBalance());
        assertEquals(expectedCDBalance, bank.retrieveAccount("00000003").getBalance());
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

    @Test
    void creation_withdraw_hold_set_cd_valid() {
        bank.retrieveAccount("00000003").setInitialWithdrawHold(bank.getTime());
        int actual = bank.retrieveAccount("00000003").getWithdrawHoldUntil();

        assertEquals(bank.getTime() + 12, actual);
    }

    @Test
    void creation_withdraw_hold_set_savings_checking_valid() {
        bank.retrieveAccount("00000002").setInitialWithdrawHold(bank.getTime());
        int actual = bank.retrieveAccount("00000002").getWithdrawHoldUntil();

        assertEquals(-1, actual);
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

    // Withdraw Tests
    @Test
    void savings_checking_noncase_sensitive_withdraw_command_valid() {
        assertAccountWithdrawal("wItHDrAW 00000001 0");
    }

    @Test
    void savings_checking_0_zero_amount_withdrawn_valid() {
        assertAccountWithdrawal("Withdraw 00000001 0");
    }

    @Test
    void savings_checking_float_amount_withdrawn_valid() {
        assertAccountWithdrawal("Withdraw 00000001 500.50");
    }

    @Test
    void savings_checking_max_amount_withdrawn_valid() {
        assertAccountWithdrawal("Withdraw 00000001 2500");
    }

    @Test
    void savings_checking_greater_than_funds_amount_withdrawn_is_zero_valid() {
        bank.bankWithdraw("00000001", 5000);
        assertAccountWithdrawal("Withdraw 00000001 0");
        double actual = bank.retrieveAccount("00000001").getBalance();

        assertEquals(0, actual);
    }

    @Test
    void savings_checking_consecutive_amount_withdrawn_to_same_account_valid() {
        assertAccountWithdrawal("Withdraw 00000001 500.50");
        assertAccountWithdrawal("Withdraw 00000001 500");
    }

    @Test
    void savings_checking_consecutive_amount_withdrawn_to_different_account_valid() {
        assertAccountWithdrawal("Withdraw 00000001 500.50");
        assertAccountWithdrawal("Withdraw 00000002 500");
    }

    @Test
    void savings_withdraw_hold_updated_valid() {
        assertAccountWithdrawal("Withdraw 00000001 0");
        int actual = bank.retrieveAccount("00000001").getWithdrawHoldUntil();
        assertEquals(bank.getTime() + 1, actual);
    }

    // Transfer Tests
    @Test
    void transfer_from_savings_to_checking_valid() {
        bank.bankDeposit("00000001", 500);
        assertTransfer("Transfer 00000001 00000002 500");
    }

    @Test
    void transfer_from_checking_to_savings_valid() {
        bank.bankDeposit("00000002", 500);
        assertTransfer("Transfer 00000002 00000001 200");
    }

    @Test
    void transfer_with_zero_amount_valid() {
        assertTransfer("Transfer 00000001 00000002 0");
    }

    @Test
    void transfer_with_float_amount_valid() {
        bank.bankDeposit("00000001", 300.50);
        assertTransfer("Transfer 00000001 00000002 300.50");
    }

    @Test
    void transfer_with_max_amount_valid() {
        bank.bankDeposit("00000001", 2500);
        assertTransfer("Transfer 00000001 00000002 2500");
    }

    @Test
    void transfer_with_lacking_funds_amount_valid() {
        bank.bankDeposit("00000001", 100);
        commandProcessor.process("Transfer 00000001 00000002 500");
        double actual = bank.retrieveAccount("00000002").getBalance();

        assertEquals(100, actual);
    }

    @Test
    void transfer_from_savings_to_checking_consecutive_valid() {
        bank.bankDeposit("00000001", 800);
        assertTransfer("Transfer 00000001 00000002 500");
        assertTransfer("Transfer 00000001 00000002 300");
    }

    @Test
    void transfer_withdraw_hold_updated_valid() {
        commandProcessor.process("Transfer 00000001 00000002 0");
        int actual = bank.retrieveAccount("00000001").getWithdrawHoldUntil();
        assertEquals(bank.getTime() + 1, actual);
    }

    // Pass Time Tests
    @Test
    void pass_time_with_1_month_updates_bank_time() {
        commandProcessor.process("Pass 1");
        int actual = bank.getTime();
        assertEquals(1, actual);
    }

    @Test
    void pass_time_with_60_month_updates_bank_time() {
        commandProcessor.process("Pass 60");
        int actual = bank.getTime();
        assertEquals(60, actual);
    }

    @Test
    void pass_time_with_1_month() {
        bank.bankDeposit("00000001", 500);
        bank.bankDeposit("00000002", 100);
        assertPassTime("Pass 1", 500.42, 100.08, 2014.04);
    }

    @Test
    void pass_time_with_12_months() {
        bank.bankDeposit("00000001", 500);
        bank.bankDeposit("00000002", 100);
        assertPassTime("Pass 12", 505.02, 101.00, 2175.10);
    }

    @Test
    void close_zero_accounts() {
        commandProcessor.process("Pass 1");
        int actual = bank.getNumberOfAccounts();
        assertEquals(1, actual);
    }

    @Test
    void deduct_minimum_fee_from_accounts() {
        bank.bankDeposit("00000001", 75);
        bank.bankDeposit("00000002", 50);
        commandProcessor.process("Pass 1");
        double actual = bank.retrieveAccount("00000001").getBalance();
        double actual2 = bank.retrieveAccount("00000002").getBalance();
        assertEquals(50.04, actual);
        assertEquals(25.02, actual2);
    }

}




