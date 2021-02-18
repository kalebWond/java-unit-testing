package lab1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;

/**
 * Unit test for simple App.
 */
// @TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class AppTest {
    /**
     * Rigorous Test.
     */
    @Mock
    static Database databaseMock;

    static TodoList todoList = new TodoList(databaseMock);

    public AppTest() {
        todoList.createTodo("todo from constructor");
    }

    // LifeCycle - create one todo before all 
    @BeforeAll
    public static void beforeAll() {
        todoList.createTodo("todo fro life cycle hook");
    }

    @AfterEach
    public void afterEach() {
        // todoList.printTodoList();
    }

    @Order(1)  // so that we can see the assumption fail, because two has been added
    @Test
    @DisplayName("Assmuption 1- to do list is initially empty")
    void createFirstTodo() {
        assumeTrue(todoList.getTodoListSize() == 0);
        // test if there is only one item in the list
        todoList.createTodo("first task on the list");
        assertEquals(1, todoList.getTodoListSize());
    }

    @Order(2) // so that we can see the assumption pass, because two has been added
    @Test
    @DisplayName("Assmuption 2- to do list is not initially empty")
    void createSecondTodo() {
        assumeFalse(todoList.getTodoListSize() == 0);
        // test if there is only one item in the list
        todoList.createTodo("second task on the list");
        assertNotEquals(1, todoList.getTodoListSize());
    }

    @DisplayName("Assertion - Create todo with empty description")
    @Test
    void testCreateTodo() {
        assertNull(todoList.createTodo(" "));
    }

    /* 
        Parameterized test with two method arguments.
        This can be done in two ways
        1) using a @CsvSource
        2) using a @MethodSource 
    */

    @DisplayName("Parameterized 1- Completed field tester by csv source")
    @ParameterizedTest
    @CsvSource({"Another task,false", "A second another task,false", "A third task,false"})
    public void testCompletedField(String description, Boolean status) {
        Todo todo = todoList.createTodo(description);
        assertEquals(status, todo.getCompletedStatus());
    }

    @DisplayName("Parameterized 2- Completed field tester by method source")
    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testByMethodSource(String description, Boolean status) {
        Todo todo = todoList.createTodo(description);
        assertEquals(status, todo.getCompletedStatus());
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("do task 1", false),
                Arguments.of("do task 2", false),
                Arguments.of("do task 3", false)
        );
    }

    @DisplayName("Repeated test- check completed status toggles")
    @RepeatedTest(5)
    void testToggleStatus(RepetitionInfo repetition) {
        Todo todo = todoList.updateTodoStatus(0);
        if(repetition.getCurrentRepetition() % 2 != 0) {
            assertTrue(todo.getCompletedStatus());
        } else {
            assertFalse(todo.getCompletedStatus());
        }
    }

    @DisplayName("Conditional test 1 - Run if on linux or windows")
    @Test
    @EnabledOnOs(value = {OS.LINUX, OS.WINDOWS})
    void runBasedOnOs() {
        String descriptionBeforeUpdate = todoList.getTodoByIndex(0).getDescription();
        Todo todoAfterUpdate = todoList.updateTodoDescriptionById(0, "Description updated");
        assertNotEquals(descriptionBeforeUpdate, todoAfterUpdate.getDescription());
    }
    
    @Order(3) // let's wipe the list and only three
    @DisplayName("Dynamic test - Test if todo instances refere to the same object")
    @TestFactory
    public Collection<DynamicTest> referenceTestFromCollection() {
        /* 
            this will help ensure that changes will reflect on all location
            the instanace is called
        */
        todoList.clearList();
        List<String> tasks = Arrays.asList("Learn java", "Do java", "Test java");
        List<String> updates = Arrays.asList("Learn flutter", "Do flutter", "Test flutter");
        Collection<DynamicTest> tests = new ArrayList<>();
        
        for (int i = 0; i < tasks.size(); i++) {
            Todo newTask = todoList.createTodo(tasks.get(i));
            Todo afterUpdate = todoList.updateTodoDescriptionById(i, updates.get(i));

            DynamicTest dynamicTest = dynamicTest("Test if update is reflected on the first Todo instance", () -> {
                assertEquals(newTask, afterUpdate, "Both instances refer to the same object");
            });

            tests.add(dynamicTest);
        }
        
        return tests;
    }

    @Test
    @DisplayName("Using mock objects- try running an sql query")
    public void runInsertQueryTest() {
        assertNotNull(databaseMock);
        TodoList newList = new TodoList(databaseMock);
        when(databaseMock.isConnectionAvailable()).thenReturn(true);
        
        String query = "SELECT * FROM todos WHERE `completed` is false";
        String result = newList.runSqlQuery(query);
        
        System.out.println(result);
        assertNotNull(result);
    }
}
