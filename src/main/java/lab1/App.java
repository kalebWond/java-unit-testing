package lab1;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        TodoList list = new TodoList();
        list.createTodo("do some work");
        list.createTodo("do some work 2");
        list.createTodo("do some work 3");
        // System.out.println(list.getTodoByIndex(2).getDescription());
        list.printTodoList();
        list.updateTodoStatus(1);
        list.updateTodoDescriptionById(2, "do junit testing");
        list.printTodoList();
    }
}
