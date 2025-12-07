/**
 * Code examples for Java section
 * Demonstrates OOP, Static/Dynamic typing, Generics, Lambdas, and Streams
 */

// Example 1: Object-Oriented Programming
class Animal {
    private String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("Some generic animal sound");
    }
    
    public String getName() {
        return name;
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof!");
    }
}

// Example 2: Proper equals() and hashCode() override
class Person {
    private final String name;
    private final int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person person = (Person) obj;
        return age == person.age && 
               (name != null ? name.equals(person.name) : person.name == null);
    }
    
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

// Example 3: Generics
class Box<T> {
    private T content;
    
    public void set(T content) {
        this.content = content;
    }
    
    public T get() {
        return content;
    }
}

// Generic method with bounded type parameter
class Util {
    public static <T extends Comparable<T>> T findMax(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }
}

// Example 4: Exceptions
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    private double balance;
    
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Attempted to withdraw " + amount + " but only " + balance + " available"
            );
        }
        balance -= amount;
    }
    
    public double getBalance() {
        return balance;
    }
}

public class JavaExamples {
    
    // Example 5: Lambdas and Streams
    public static void demonstrateLambdasAndStreams() {
        java.util.List<Integer> numbers = java.util.Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Filter even numbers and square them
        java.util.List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .collect(java.util.stream.Collectors.toList());
        
        System.out.println("Even squares: " + evenSquares);
        
        // Find sum using reduce
        int sum = numbers.stream()
            .reduce(0, (a, b) -> a + b);
        System.out.println("Sum: " + sum);
    }
    
    // Example 6: CompletableFuture for asynchronous programming
    public static void demonstrateAsyncProgramming() {
        java.util.concurrent.CompletableFuture<String> future = 
            java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Hello from async task";
            });
        
        future.thenAccept(result -> System.out.println("Result: " + result));
        
        // Wait for completion
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        // OOP example
        System.out.println("=== OOP Example ===");
        Animal dog = new Dog("Buddy");
        dog.makeSound();
        
        // equals() and hashCode() example
        System.out.println("\n=== Equals and HashCode Example ===");
        Person p1 = new Person("John", 30);
        Person p2 = new Person("John", 30);
        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println("p1 hashCode: " + p1.hashCode());
        System.out.println("p2 hashCode: " + p2.hashCode());
        
        // Generics example
        System.out.println("\n=== Generics Example ===");
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Generics");
        System.out.println("Box content: " + stringBox.get());
        
        System.out.println("Max of 5 and 10: " + Util.findMax(5, 10));
        
        // Exception handling example
        System.out.println("\n=== Exception Handling Example ===");
        BankAccount account = new BankAccount(100.0);
        try {
            account.withdraw(150.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Lambdas and Streams
        System.out.println("\n=== Lambdas and Streams Example ===");
        demonstrateLambdasAndStreams();
        
        // Async programming
        System.out.println("\n=== Async Programming Example ===");
        demonstrateAsyncProgramming();
    }
}
