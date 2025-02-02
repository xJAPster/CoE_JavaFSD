import java.util.*;

/*
 * Objective: Use Java collections to create a custom data structure that manages a list of tasks, each with a priority level.
    • Details: Create a class TaskManager that uses a PriorityQueue to manage tasks. Each task should have a unique ID, description, and priority level.
    • Functions to Implement:
        ◦ addTask(String id, String description, int priority): Adds a new task.
        ◦ removeTask(String id): Removes a task by ID.
        ◦ getHighestPriorityTask(): Returns the task with the highest priority without removing it from the queue.
 */

class Task {
    int id;
    String description;
    int priority;

    
    public Task(int id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }
}

class TaskManager {
    private PriorityQueue<Task> pq;
    private HashMap<Integer, Task> taskMap;

    public TaskManager() {
        pq = new PriorityQueue<>(new Comparator<Task>() {

            //custom comparator
            @Override
            public int compare(Task a, Task b) {
                return Integer.compare(b.priority, a.priority); 
            }
        });
        taskMap = new HashMap<>();
    }

    
    public void addTask(int id, String description, int priority) {
        Task task = new Task(id, description, priority);
        pq.add(task);
        taskMap.put(id, task);
        System.out.println("Task added successfully");
    }

    public void removeTask(int id) {
        Task taskToRemove = taskMap.get(id);
        if (taskToRemove != null) {
            pq.remove(taskToRemove);
            taskMap.remove(id);
            System.out.println("Task removed successfully");
        } else {
            System.out.println("Task with ID :" + id + " was not found");
        }
    }

    public void getHighestPriorityTask() {
        if (!pq.isEmpty()) {
            Task highestPriorityTask = pq.peek();
            System.out.println("\nThe highest priority task is: Task " + highestPriorityTask.id + " : " + highestPriorityTask.description);
        } else {
            System.out.println("No tasks available");
        }
    }
}

public class task1 {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask(1, "C++ practice", 10);
        taskManager.addTask(2, "Java practice", 5);
        taskManager.addTask(3, "Play games", 2);
        taskManager.addTask(4, "Catch up with friends", 4);
        taskManager.addTask(5, "Learn Java", 6);

        taskManager.removeTask(5);  
        taskManager.getHighestPriorityTask();  
    }
}
