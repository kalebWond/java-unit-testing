package lab1;
public final class App {
    private App() {
    }
    public static void main(String[] args) {
        TodoList list = new TodoList(new Database());
        // System.out.println(list.runSqlQuery("insert into table todo"));
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
