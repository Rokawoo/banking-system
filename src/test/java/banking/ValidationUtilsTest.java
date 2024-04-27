package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationUtilsTest {

    @Test
    void valid_int_works_with_small_int() {
        boolean actual = ValidationUtils.isValidInt("1");
        assertTrue(actual);
    }

    @Test
    void valid_int_works_with_big_int() {
        boolean actual = ValidationUtils.isValidInt("999999");
        assertTrue(actual);
    }

    @Test
    void valid_int_does_not_work_with_non_int() {
        boolean actual = ValidationUtils.isValidInt("999awewa999");
        assertFalse(actual);
    }

    @Test
    void valid_float_works_with_small_float() {
        boolean actual = ValidationUtils.isValidFloat("0.1");
        assertTrue(actual);
    }

    @Test
    void valid_float_works_with_big_float() {
        boolean actual = ValidationUtils.isValidFloat("0.3523525235");
        assertTrue(actual);
    }

    @Test
    void valid_float_works_with_bigger_float() {
        boolean actual = ValidationUtils.isValidFloat("12551.3554234");
        assertTrue(actual);
    }

    @Test
    void valid_float_does_not_work_with_non_float() {
        boolean actual = ValidationUtils.isValidFloat("aaw0.1");
        assertFalse(actual);
    }

    @Test
    void positive_check_works() {
        boolean actual = ValidationUtils.validatePositiveAmount(51.334);
        assertTrue(actual);
    }

    void positive_check_works_with_negative() {
        boolean actual = ValidationUtils.validatePositiveAmount(-51.334);
        assertFalse(actual);
    }

}
