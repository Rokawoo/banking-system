package banking;

public class AprUtils {
    private AprUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static double calculateTotalInterest(Account account, int monthsPassed) {
        double apr = account.getApr() / 100;
        double monthlyRate = apr / 12;
        double totalInterest = 0.0;

        for (int monthAccrued = 0; monthAccrued < monthsPassed; ++monthAccrued) {
            totalInterest += (isCD(account) ? calculateCDInterest(account, monthlyRate) : calculateRegularInterest(account, monthlyRate));
        }

        return totalInterest;
    }

    private static boolean isCD(Account account) {
        return "cd".equals(account.getType());
    }

    private static double calculateCDInterest(Account account, double monthlyRate) {
        double totalInterest = 0.0;
        for (int i = 0; i < 4; ++i) {
            totalInterest += account.getBalance() * monthlyRate;
        }
        return totalInterest;
    }

    private static double calculateRegularInterest(Account account, double monthlyRate) {
        return account.getBalance() * monthlyRate;
    }
}

