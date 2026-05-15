package test;

import main.DiscountCalculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountCalculatorTest {

    @ParameterizedTest
    @CsvSource({
        "NEW, 5, true, 7",
        "NEW, 3, false, 5",
        "REGULAR, 5, true, 10",
        "REGULAR, 10, false, 13",
        "PREMIUM, 2, false, 10",
        "PREMIUM, 12, true, 15"
    })
    void testValidCases(String type, int orders, boolean sub, int expected) {
        assertEquals(expected,
            DiscountCalculator.calculateDiscount(type, orders, sub));
    }

    @ParameterizedTest
    @CsvSource({
        "NEW, 10, true"
    })
    void testInvalidCase(String type, int orders, boolean sub) {
        assertThrows(IllegalArgumentException.class, () -> {
            DiscountCalculator.calculateDiscount(type, orders, sub);
        });
    }
}