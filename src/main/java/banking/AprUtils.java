package banking;

public class AprUtils {
    private AprUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void calculateTotalInterest(Account account, int monthsPassed) {
        double apr = account.getApr() / 100;
        double monthlyRate = apr / 12;

        for (int monthAccrued = 0; monthAccrued < monthsPassed; ++monthAccrued) {
            if (isCD(account)) {
                calculateCDInterest(account, monthlyRate);
            } else {
                calculateRegularInterest(account, monthlyRate);
            }
        }
    }

    private static boolean isCD(Account account) {
        return account.getType().equals("cd");
    }

    private static void calculateCDInterest(Account account, double monthlyRate) {
        for (int i = 0; i < 4; ++i) {
            account.deposit(account.getBalance() * monthlyRate);
        }
    }

    private static void calculateRegularInterest(Account account, double monthlyRate) {
        account.deposit(account.getBalance() * monthlyRate);
    }
}

