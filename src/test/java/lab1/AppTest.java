package lab1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.*;

/**
 * Unit test for simple App.
 */
// @TestInstance(Lifecycle.PER_CLASS)
class AppTest {
    /**
     * Rigorous Test.
     */

    public AppTest() {
        System.out.println("constructor");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("before all");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("after each");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("after all");
    }

    @Test
    void testOne() {
        System.out.println("test one");
    }
    @Test
    void testTwo() {
        System.out.println("test two");
    }
}
