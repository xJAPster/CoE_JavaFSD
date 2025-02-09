import java.util.*;
import java.util.concurrent.*;

class Product {
    private String productID, name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        if (quantity < 0) throw new IllegalArgumentException("quantity cannot be negative");
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void reduceQuantity(int amount) { 
        if (amount <= 0 || amount > quantity) throw new IllegalArgumentException("invalid quantity reduction");
        quantity -= amount; 
    }
    public Location getLocation() { return location; }
}

class Location {
    private int aisle, shelf, bin;

    public Location(int aisle, int shelf, int bin) {
        if (aisle < 0 || shelf < 0 || bin < 0) throw new IllegalArgumentException("invalid location values");
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }
    
    @Override
    public String toString() {
        return "aisle: " + aisle + ", shelf: " + shelf + ", bin: " + bin;
    }
}

class Order {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;

    public enum Priority { STANDARD, EXPEDITED }
    public Order(String orderID, List<String> productIDs, Priority priority) {
        if (orderID.isEmpty() || productIDs.isEmpty()) throw new IllegalArgumentException("order id and products cannot be empty");
        this.orderID = orderID;
        this.productIDs = new ArrayList<>(productIDs);
        this.priority = priority;
    }
    public String getOrderID() { return orderID; }
    public Priority getPriority() { return priority; }
    public List<String> getProductIDs() { return productIDs; }
}

class OrderComparator implements Comparator<Order> {
    public int compare(Order o1, Order o2) {
        return o2.getPriority().compareTo(o1.getPriority());
    }
}

class InventoryManager {
    private ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();
    private PriorityQueue<Order> orderQueue = new PriorityQueue<>(new OrderComparator());

    public synchronized void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }
    
    public synchronized void addOrder(Order order) {
        orderQueue.add(order);
    }

    public void processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        while (!orderQueue.isEmpty()) {
            Order order;
            synchronized (this) {
                order = orderQueue.poll();
            }
            if (order != null) {
                System.out.println("processing order: " + order.getOrderID());
                executor.execute(() -> fulfillOrder(order));
            }
        }
        executor.shutdown();
    }

    private synchronized void fulfillOrder(Order order) {
        for (String productID : order.getProductIDs()) {
            Product product = products.get(productID);
            if (product != null && product.getQuantity() > 0) {
                try {
                    product.reduceQuantity(1);
                    System.out.println("product " + productID + " fulfilled for order " + order.getOrderID() + " from " + product.getLocation());
                } catch (IllegalArgumentException e) {
                    System.out.println("error fulfilling order " + order.getOrderID() + ": " + e.getMessage());
                }
            } else {
                System.out.println("product " + productID + " out of stock for order " + order.getOrderID());
            }
        }
    }

    public void displayInventory() {
        System.out.println("current inventory:");
        for (Product product : products.values()) {
            System.out.println(product.getProductID() + " - " + product.getName() + " (quantity: " + product.getQuantity() + ", location: " + product.getLocation() + ")");
        }
    }
}

public class task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        while (true) {
            System.out.println("\nwarehouse inventory management system");
            System.out.println("1. add product");
            System.out.println("2. add order");
            System.out.println("3. process orders");
            System.out.println("4. display inventory");
            System.out.println("5. exit");
            System.out.print("enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("enter product id: ");
                        String productID = scanner.nextLine();
                        System.out.print("enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("enter quantity: ");
                        int quantity = scanner.nextInt();
                        System.out.print("enter aisle: ");
                        int aisle = scanner.nextInt();
                        System.out.print("enter shelf: ");
                        int shelf = scanner.nextInt();
                        System.out.print("enter bin: ");
                        int bin = scanner.nextInt();
                        inventoryManager.addProduct(new Product(productID, name, quantity, new Location(aisle, shelf, bin)));
                        System.out.println("product added successfully.");
                        break;
                    case 2:
                        System.out.print("enter order id: ");
                        String orderID = scanner.nextLine();
                        System.out.print("enter product ids (comma separated): ");
                        List<String> productIDs = Arrays.asList(scanner.nextLine().split(","));
                        System.out.print("enter priority (standard or expedited): ");
                        Order.Priority priority = Order.Priority.valueOf(scanner.nextLine().toUpperCase());
                        inventoryManager.addOrder(new Order(orderID, productIDs, priority));
                        System.out.println("order added successfully.");
                        break;
                    case 3:
                        inventoryManager.processOrders();
                        System.out.println("order processing completed.");
                        break;
                    case 4:
                        inventoryManager.displayInventory();
                        break;
                    case 5:
                        System.out.println("exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("invalid choice. please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }
}
