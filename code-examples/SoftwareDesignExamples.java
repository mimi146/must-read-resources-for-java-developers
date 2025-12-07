/**
 * Code examples for Software Design section
 * Demonstrates SOLID principles, Design Patterns, Immutability, and Fluent Interfaces
 */

// ============= SOLID PRINCIPLES =============

// S - Single Responsibility Principle
// Each class has one responsibility
class UserValidator {
    public boolean isValid(String email) {
        return email != null && email.contains("@");
    }
}

class UserPersistence {
    public void save(String userData) {
        System.out.println("Saving user: " + userData);
    }
}

// O - Open/Closed Principle
// Open for extension, closed for modification
interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

// L - Liskov Substitution Principle
// Subtypes must be substitutable for their base types
abstract class Bird {
    abstract void move();
}

class Sparrow extends Bird {
    @Override
    void move() {
        System.out.println("Sparrow is flying");
    }
}

class Penguin extends Bird {
    @Override
    void move() {
        System.out.println("Penguin is swimming");
    }
}

// I - Interface Segregation Principle
// Clients should not be forced to depend on interfaces they don't use
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    @Override
    public void work() {
        System.out.println("Human is working");
    }
    
    @Override
    public void eat() {
        System.out.println("Human is eating");
    }
}

class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }
}

// D - Dependency Inversion Principle
// Depend on abstractions, not concretions
interface MessageService {
    void sendMessage(String message, String recipient);
}

class EmailService implements MessageService {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("Email sent to " + recipient + ": " + message);
    }
}

class SMSService implements MessageService {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("SMS sent to " + recipient + ": " + message);
    }
}

class NotificationService {
    private MessageService messageService;
    
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }
    
    public void notify(String message, String recipient) {
        messageService.sendMessage(message, recipient);
    }
}

// ============= DESIGN PATTERNS =============

// Singleton Pattern
class DatabaseConnection {
    private static DatabaseConnection instance;
    
    private DatabaseConnection() {
        System.out.println("Database connection created");
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

// Factory Pattern
interface Animal {
    void makeSound();
}

class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

class AnimalFactory {
    public static Animal createAnimal(String type) {
        switch (type.toLowerCase()) {
            case "dog":
                return new Dog();
            case "cat":
                return new Cat();
            default:
                throw new IllegalArgumentException("Unknown animal type");
        }
    }
}

// Builder Pattern (Fluent Interface)
class PersonBuilder {
    private String name;
    private int age;
    private String email;
    private String address;
    
    public PersonBuilder() {}
    
    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public PersonBuilder withAge(int age) {
        this.age = age;
        return this;
    }
    
    public PersonBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    
    public PersonBuilder withAddress(String address) {
        this.address = address;
        return this;
    }
    
    public PersonData build() {
        return new PersonData(name, age, email, address);
    }
}

// Immutable class example
final class PersonData {
    private final String name;
    private final int age;
    private final String email;
    private final String address;
    
    public PersonData(String name, int age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + 
               ", email='" + email + "', address='" + address + "'}";
    }
}

// Observer Pattern
interface Observer {
    void update(String event);
}

class ConcreteObserver implements Observer {
    private String name;
    
    public ConcreteObserver(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String event) {
        System.out.println(name + " received event: " + event);
    }
}

class Subject {
    private java.util.List<Observer> observers = new java.util.ArrayList<>();
    
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }
}

public class SoftwareDesignExamples {
    
    public static void demonstrateSOLID() {
        System.out.println("=== SOLID Principles ===\n");
        
        // Single Responsibility
        System.out.println("1. Single Responsibility:");
        UserValidator validator = new UserValidator();
        System.out.println("Email valid? " + validator.isValid("test@example.com"));
        
        // Open/Closed
        System.out.println("\n2. Open/Closed:");
        Shape circle = new Circle(5);
        System.out.println("Circle area: " + circle.calculateArea());
        
        // Liskov Substitution
        System.out.println("\n3. Liskov Substitution:");
        Bird bird = new Sparrow();
        bird.move();
        
        // Interface Segregation
        System.out.println("\n4. Interface Segregation:");
        Human human = new Human();
        human.work();
        Robot robot = new Robot();
        robot.work();
        
        // Dependency Inversion
        System.out.println("\n5. Dependency Inversion:");
        NotificationService emailNotification = new NotificationService(new EmailService());
        emailNotification.notify("Hello!", "user@example.com");
    }
    
    public static void demonstrateDesignPatterns() {
        System.out.println("\n\n=== Design Patterns ===\n");
        
        // Singleton
        System.out.println("1. Singleton Pattern:");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("Same instance? " + (db1 == db2));
        
        // Factory
        System.out.println("\n2. Factory Pattern:");
        Animal dog = AnimalFactory.createAnimal("dog");
        dog.makeSound();
        
        // Builder with Fluent Interface
        System.out.println("\n3. Builder Pattern (Fluent Interface):");
        PersonData person = new PersonBuilder()
            .withName("John Doe")
            .withAge(30)
            .withEmail("john@example.com")
            .withAddress("123 Main St")
            .build();
        System.out.println(person);
        
        // Observer
        System.out.println("\n4. Observer Pattern:");
        Subject subject = new Subject();
        subject.attach(new ConcreteObserver("Observer1"));
        subject.attach(new ConcreteObserver("Observer2"));
        subject.notifyObservers("System event occurred");
    }
    
    public static void demonstrateImmutability() {
        System.out.println("\n\n=== Immutability ===");
        PersonData immutablePerson = new PersonData("Alice", 25, "alice@example.com", "456 Oak St");
        System.out.println("Immutable object: " + immutablePerson);
        System.out.println("Cannot modify fields - all fields are final!");
    }
    
    public static void main(String[] args) {
        demonstrateSOLID();
        demonstrateDesignPatterns();
        demonstrateImmutability();
    }
}
