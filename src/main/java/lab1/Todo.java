package lab1;

import java.util.Random;

public class Todo {
    private int _id;
    private String description;
    private boolean completed;

    Todo(String desc) {
        Random rand = new Random();
        int id = rand.nextInt(100);
        _id = id;
        description = desc;
        completed = false;
    };

    public boolean toggleTodoStatus() {
        return completed = !this.completed;
    }

    public String updateDescription(String str) {
        description = str;
        return description;
    }

    public int getId() {
        return _id;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompletedStatus() {
        return completed;
    }
}
