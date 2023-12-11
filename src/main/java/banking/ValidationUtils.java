package banking;

class ValidationUtils {
    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidAccountId(String accountId) {
        return accountId.matches("\\d{8}"); // Check if it's an 8-digit account ID
    }

    public static boolean accountExists(Bank bank, String accountId) {
        return bank.retrieveAccount(accountId) != null;
    }
}
