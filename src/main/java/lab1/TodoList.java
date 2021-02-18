package lab1;

import java.util.ArrayList;

public class TodoList {
    private ArrayList<Todo> todoList = new ArrayList<Todo>();
    private Database database;

    public TodoList(Database database) {
        this.database = database;
    }

    public Todo createTodo(String description) {
        description = description.trim();
        if(description.isEmpty()) {
            return null; 
        }
        Todo todo = new Todo(description);
        todoList.add(todo);
        return todo;
    }

    public ArrayList<Todo> fetchTodoList() {
        return todoList;
    }

    public void printTodoList() {
        for(int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i); 
            System.out.println("Id: " + todo.getId());
            System.out.println("Task: "+ todo.getDescription());
            System.out.println("Completed: "+ todo.getCompletedStatus());
            System.out.println("\n");
        }
    }

    public Todo getTodoByIndex(int i) {
        try {
            Todo todo = todoList.get(i);
            return todo;
        } catch (Exception e) {
            return null;
        }
    }

    public Todo updateTodoStatus(int id) {
        Todo todo = this.getTodoByIndex(id);
        if(todo != null) {
            todo.toggleTodoStatus();
            return todo;
        }
        return null;
    }

    public Todo updateTodoDescriptionById(int id, String newString) {
        Todo todo = this.getTodoByIndex(id);
        if(todo != null) {
            todo.updateDescription(newString);
            return todo;
        }
        return null;
    }

    public int getTodoListSize() {
        return todoList.size();
    }

    public ArrayList<Todo> clearList() {
        todoList.clear();
        return todoList;
    }

    public boolean isThereConnection() {
        return database.isConnectionAvailable();
    }

    public String runSqlQuery(String query) {
        boolean haveConnection = database.isConnectionAvailable();
        String result;
        if(haveConnection) {
            result = "Connection Established: " + query;
            return result;
        } else {
            return result = null;
        }
    }
}
