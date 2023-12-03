package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest {
    @Test
    // CD: when created, its starting balance is whatever the supplied balance was.
    public void cd_created_with_specified_balance_by_default() {
        CD cd = new CD(9.8, 100.50);
        double actual = cd.getBalance();

        assertEquals(100.50, actual);
    }

}
