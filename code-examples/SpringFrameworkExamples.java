/**
 * Code examples for Spring Framework section
 * Demonstrates Spring concepts: Dependency Injection, IoC, Spring Boot basics
 * Note: These are conceptual examples showing Spring principles
 * In production, use actual Spring Framework with annotations and configuration
 */

import java.util.*;

// Example 1: Dependency Injection (DI) and Inversion of Control (IoC)

// Service interface
interface UserRepository {
    User findById(String id);
    void save(User user);
}

// Implementation
class JpaUserRepository implements UserRepository {
    private Map<String, User> database = new HashMap<>();
    
    @Override
    public User findById(String id) {
        System.out.println("JpaUserRepository: Finding user with ID " + id);
        return database.get(id);
    }
    
    @Override
    public void save(User user) {
        System.out.println("JpaUserRepository: Saving user " + user.getName());
        database.put(user.getId(), user);
    }
}

// Alternative implementation
class MongoUserRepository implements UserRepository {
    private Map<String, User> database = new HashMap<>();
    
    @Override
    public User findById(String id) {
        System.out.println("MongoUserRepository: Finding user with ID " + id);
        return database.get(id);
    }
    
    @Override
    public void save(User user) {
        System.out.println("MongoUserRepository: Saving user " + user.getName());
        database.put(user.getId(), user);
    }
}

// Domain model
class User {
    private String id;
    private String name;
    private String email;
    
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    
    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}

// Service with Dependency Injection
class UserService {
    private final UserRepository userRepository;
    
    // Constructor injection (preferred in Spring)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUser(String id) {
        return userRepository.findById(id);
    }
    
    public void createUser(User user) {
        userRepository.save(user);
    }
}

// Example 2: Simple IoC Container (Spring-like)
class SimpleIoCContainer {
    private Map<Class<?>, Object> beans = new HashMap<>();
    
    public <T> void registerBean(Class<T> clazz, T instance) {
        beans.put(clazz, instance);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}

// Example 3: Spring Boot-like REST Controller (Conceptual)
class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // Simulates @GetMapping("/users/{id}")
    public String getUserById(String id) {
        User user = userService.getUser(id);
        if (user != null) {
            return "HTTP 200: " + user.toString();
        }
        return "HTTP 404: User not found";
    }
    
    // Simulates @PostMapping("/users")
    public String createUser(User user) {
        userService.createUser(user);
        return "HTTP 201: User created successfully";
    }
}

// Example 4: Spring Security concepts (Conceptual)
interface AuthenticationService {
    boolean authenticate(String username, String password);
    String getCurrentUser();
}

class SimpleAuthenticationService implements AuthenticationService {
    private Map<String, String> users = new HashMap<>();
    private String currentUser = null;
    
    public SimpleAuthenticationService() {
        // Mock users
        users.put("admin", "admin123");
        users.put("user", "user123");
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            currentUser = username;
            System.out.println("Authentication successful for: " + username);
            return true;
        }
        System.out.println("Authentication failed for: " + username);
        return false;
    }
    
    @Override
    public String getCurrentUser() {
        return currentUser;
    }
}

// Example 5: Spring Data JPA-like Repository (Conceptual)
interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String id);
    List<Product> findByCategory(String category);
    Product save(Product product);
    void deleteById(String id);
}

class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    
    public Product(String id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + 
               "', category='" + category + "', price=" + price + "}";
    }
}

class SimpleProductRepository implements ProductRepository {
    private Map<String, Product> database = new HashMap<>();
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(database.values());
    }
    
    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }
    
    @Override
    public List<Product> findByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : database.values()) {
            if (p.getCategory().equals(category)) {
                result.add(p);
            }
        }
        return result;
    }
    
    @Override
    public Product save(Product product) {
        database.put(product.getId(), product);
        return product;
    }
    
    @Override
    public void deleteById(String id) {
        database.remove(id);
    }
}

// Example 6: Application Configuration
class ApplicationConfig {
    public UserRepository userRepository() {
        return new JpaUserRepository();
    }
    
    public UserService userService() {
        return new UserService(userRepository());
    }
    
    public UserController userController() {
        return new UserController(userService());
    }
    
    public AuthenticationService authenticationService() {
        return new SimpleAuthenticationService();
    }
}

public class SpringFrameworkExamples {
    
    public static void demonstrateDependencyInjection() {
        System.out.println("=== Dependency Injection Example ===\n");
        
        // Manual DI (Spring does this automatically)
        UserRepository repository = new JpaUserRepository();
        UserService service = new UserService(repository);
        
        // Create and save user
        User user = new User("1", "John Doe", "john@example.com");
        service.createUser(user);
        
        // Retrieve user
        User retrieved = service.getUser("1");
        System.out.println("Retrieved: " + retrieved);
        
        // Easy to swap implementations
        System.out.println("\nSwapping to MongoDB implementation:");
        UserRepository mongoRepo = new MongoUserRepository();
        UserService mongoService = new UserService(mongoRepo);
        mongoService.createUser(user);
    }
    
    public static void demonstrateIoCContainer() {
        System.out.println("\n\n=== IoC Container Example ===\n");
        
        SimpleIoCContainer container = new SimpleIoCContainer();
        
        // Register beans
        container.registerBean(UserRepository.class, new JpaUserRepository());
        container.registerBean(UserService.class, 
            new UserService(container.getBean(UserRepository.class)));
        
        // Retrieve and use beans
        UserService service = container.getBean(UserService.class);
        User user = new User("2", "Jane Smith", "jane@example.com");
        service.createUser(user);
        
        System.out.println("Bean retrieved from container and used successfully");
    }
    
    public static void demonstrateRESTController() {
        System.out.println("\n\n=== Spring Boot REST Controller Example ===\n");
        
        ApplicationConfig config = new ApplicationConfig();
        UserController controller = config.userController();
        
        // Create user via REST endpoint
        User newUser = new User("3", "Bob Johnson", "bob@example.com");
        System.out.println(controller.createUser(newUser));
        
        // Get user via REST endpoint
        System.out.println(controller.getUserById("3"));
        System.out.println(controller.getUserById("999")); // Not found
    }
    
    public static void demonstrateSpringSecurity() {
        System.out.println("\n\n=== Spring Security Example ===\n");
        
        AuthenticationService authService = new SimpleAuthenticationService();
        
        // Successful authentication
        boolean result1 = authService.authenticate("admin", "admin123");
        System.out.println("Login result: " + result1);
        System.out.println("Current user: " + authService.getCurrentUser());
        
        // Failed authentication
        boolean result2 = authService.authenticate("admin", "wrongpassword");
        System.out.println("Login result: " + result2);
    }
    
    public static void demonstrateSpringDataJPA() {
        System.out.println("\n\n=== Spring Data JPA Example ===\n");
        
        ProductRepository repo = new SimpleProductRepository();
        
        // Save products
        repo.save(new Product("1", "Laptop", "Electronics", 999.99));
        repo.save(new Product("2", "Mouse", "Electronics", 29.99));
        repo.save(new Product("3", "Desk", "Furniture", 299.99));
        
        // Find all
        System.out.println("All products: " + repo.findAll());
        
        // Find by ID
        System.out.println("\nFind by ID '1': " + repo.findById("1"));
        
        // Find by category (derived query method)
        System.out.println("\nFind by category 'Electronics': " + 
            repo.findByCategory("Electronics"));
        
        // Delete
        repo.deleteById("2");
        System.out.println("\nAfter deleting ID '2': " + repo.findAll());
    }
    
    public static void demonstrateSpringConcepts() {
        System.out.println("\n\n=== Spring Framework Core Concepts ===\n");
        
        System.out.println("1. Inversion of Control (IoC):");
        System.out.println("   - Framework controls object creation and lifecycle");
        System.out.println("   - Objects don't create their dependencies");
        
        System.out.println("\n2. Dependency Injection:");
        System.out.println("   - Constructor injection (preferred)");
        System.out.println("   - Setter injection");
        System.out.println("   - Field injection");
        
        System.out.println("\n3. Spring Beans:");
        System.out.println("   - Objects managed by Spring container");
        System.out.println("   - Configured via annotations or XML");
        
        System.out.println("\n4. Spring Boot:");
        System.out.println("   - Auto-configuration");
        System.out.println("   - Embedded server (Tomcat)");
        System.out.println("   - Starter dependencies");
        
        System.out.println("\n5. Spring Data JPA:");
        System.out.println("   - Repository pattern");
        System.out.println("   - Derived query methods");
        System.out.println("   - Automatic CRUD operations");
        
        System.out.println("\n6. Spring Security:");
        System.out.println("   - Authentication and authorization");
        System.out.println("   - Method-level security");
        System.out.println("   - OAuth2 and JWT support");
    }
    
    public static void main(String[] args) {
        demonstrateDependencyInjection();
        demonstrateIoCContainer();
        demonstrateRESTController();
        demonstrateSpringSecurity();
        demonstrateSpringDataJPA();
        demonstrateSpringConcepts();
    }
}
