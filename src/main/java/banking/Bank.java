package banking;

import java.util.*;

public class Bank {

    private final Map<String, Account> accounts;
    private int month;

    Bank() {
        this.accounts = new HashMap<>();
        this.month = 0;
    }

    public void addAccount(String id, Account account) {
        accounts.put(id, account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Account retrieveAccount(String id) {
        return accounts.get(id);
    }

    public void bankDeposit(String id, double depositAmount) {
        accounts.get(id).deposit(depositAmount);
    }

    public double bankWithdraw(String id, double withdrawAmount) {
        return accounts.get(id).withdraw(withdrawAmount);
    }

    public void bankUpdateWithdrawHold(String id) {
        accounts.get(id).updateWithdrawHold(this.month);
    }

    public int getTime() {
        return this.month;
    }

    public void passTime(int monthAmount) {
        this.month += monthAmount;
    }

    public void closeZeroBalanceAccounts() {
        Iterator<Map.Entry<String, Account>> iterator = accounts.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Account> entry = iterator.next();
            Account account = entry.getValue();

            if (account.getBalance() == 0) {
                iterator.remove();
            }
        }
    }

    public void applyMinimumBalanceFee() {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();

            if (account.getBalance() < 100) {
                account.withdraw(25);
            }
        }
    }

    public void accrueAPR(int monthsPassed) {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            double totalInterest = AprUtils.calculateTotalInterest(account, monthsPassed);
            account.deposit(totalInterest);
        }
    }

    public List<String> printOpenAccountInformation() {
        List<String> result = new ArrayList<>();

        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            String accountId = entry.getKey();
            Account account = entry.getValue();

            String accountInfo = String.format("%s %s %.2f %.2f",
                    account.getType().substring(0, 1).toUpperCase() + account.getType().substring(1),
                    accountId, account.getBalance(), account.getApr());

            result.add(accountInfo);

            List<String> transactionHistory = account.retrieveTransactionHistory();
            result.addAll(transactionHistory);
        }

        return result;
    }

}