import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank bank;
    Account checking;
    Account cd;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        checking = new Checking(9.8);
        cd = new CD(5, 100);
        //Bank.resetNextID();
    }

    @Test
    // When a Bank is created, it has no accounts
    public void bank_has_no_accounts_initially() {
        int actual = bank.getNumberOfAccounts();
        assertEquals(0, actual);
    }

    @Test
    // When an Account is added to the Bank, the Bank has 1 Account in it.
    public void when_account_is_added_bank_has_1_account() {
        bank.addAccount("00000001", checking);
        int actual = bank.getNumberOfAccounts();

        assertEquals(1, actual);
    }

    @Test
    // When 2 Accounts are added to the Bank, the Bank has 2 Accounts in it.
    public void when_2_accounts_are_added_bank_has_2_account() {
        bank.addAccount("00000001", checking);
        bank.addAccount("00000002", cd);
        int actual = bank.getNumberOfAccounts();

        assertEquals(2, actual);
    }

    @Test
    // When retrieving 1 Account from the Bank, the correct Account is retrieved.
    public void correct_account_can_be_retrieved_from_bank() {
        bank.addAccount("00000001", checking);
        Account actual = bank.retrieveAccount("00000001");

        assertEquals(checking, actual);
    }

    @Test
    // When depositing money by ID through the Bank, the correct Account gets the money
    public void money_deposited_through_ID_in_bank_goes_to_correct_account() {
        bank.addAccount("00000001", checking);
        bank.bankDeposit("00000001", 200.50);
        double actual = bank.retrieveAccount("00000001").getBalance();

        assertEquals(200.50, actual);
    }

    @Test
    // Depositing twice through the Bank works as expected.
    public void depositing_money_twice_to_account_works() {
        bank.addAccount("00000001", checking);
        bank.bankDeposit("00000001", 200.50);
        bank.bankDeposit("00000001", 10);
        double actual = bank.retrieveAccount("00000001").getBalance();

        assertEquals(210.50, actual);
    }

    @Test
    // When withdrawing money by ID through the Bank, the correct Account loses the money.
    public void money_withdrew_through_ID_in_bank_comes_from_correct_account() {
        bank.addAccount("00000002", cd);
        bank.bankWithdraw("00000002", 25.50);
        double actual = bank.retrieveAccount("00000002").getBalance();

        assertEquals(74.50, actual);
    }

    @Test
    // Withdrawing twice through the Bank works as expected
    public void withdrawing_money_twice_from_account_works() {
        bank.addAccount("00000002", cd);
        bank.bankWithdraw("00000002", 25.50);
        bank.bankWithdraw("00000002", 25);
        double actual = bank.retrieveAccount("00000002").getBalance();

        assertEquals(49.50, actual);
    }
}

