/**
 * Code examples for TDD and Automation Testing section
 * Demonstrates unit testing with AAA pattern, mocking concepts
 * Note: This uses basic assertions. In a real project, you would use JUnit 5 and Mockito
 */

// Class to be tested - Calculator
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
    
    public int multiply(int a, int b) {
        return a * b;
    }
    
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}

// Service class that depends on external data source
interface UserRepository {
    User findById(String id);
    void save(User user);
}

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
    public void setName(String name) { this.name = name; }
}

class UserService {
    private UserRepository repository;
    
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User getUserById(String id) {
        return repository.findById(id);
    }
    
    public void updateUserName(String id, String newName) {
        User user = repository.findById(id);
        if (user != null) {
            user.setName(newName);
            repository.save(user);
        }
    }
}

// Mock implementation for testing (in real projects, use Mockito)
class MockUserRepository implements UserRepository {
    private java.util.Map<String, User> users = new java.util.HashMap<>();
    
    @Override
    public User findById(String id) {
        return users.get(id);
    }
    
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }
}

public class TDDTestingExamples {
    
    /**
     * Example of AAA Pattern (Arrange-Act-Assert)
     * FIRST Principles: Fast, Independent, Repeatable, Self-validating, Timely
     */
    public static void testCalculatorAdd() {
        System.out.println("=== Test: Calculator Add ===");
        // Arrange - Set up test data
        Calculator calculator = new Calculator();
        int a = 5;
        int b = 3;
        
        // Act - Execute the method being tested
        int result = calculator.add(a, b);
        
        // Assert - Verify the result
        assert result == 8 : "Expected 8 but got " + result;
        System.out.println("✓ Test passed: add(5, 3) = 8");
    }
    
    public static void testCalculatorDivideByZero() {
        System.out.println("\n=== Test: Calculator Divide by Zero ===");
        // Arrange
        Calculator calculator = new Calculator();
        
        // Act & Assert
        try {
            calculator.divide(10, 0);
            System.out.println("✗ Test failed: Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test passed: Exception correctly thrown for division by zero");
        }
    }
    
    /**
     * Example of testing with a mock (Stub pattern)
     * Demonstrates the difference between mocks and stubs
     */
    public static void testUserServiceWithMock() {
        System.out.println("\n=== Test: UserService with Mock ===");
        // Arrange
        MockUserRepository mockRepo = new MockUserRepository();
        User testUser = new User("1", "John Doe", "john@example.com");
        mockRepo.save(testUser);
        
        UserService userService = new UserService(mockRepo);
        
        // Act
        User retrievedUser = userService.getUserById("1");
        
        // Assert
        assert retrievedUser != null : "User should not be null";
        assert "John Doe".equals(retrievedUser.getName()) : "Name should match";
        System.out.println("✓ Test passed: User retrieved successfully");
        
        // Test update
        userService.updateUserName("1", "Jane Doe");
        User updatedUser = userService.getUserById("1");
        assert "Jane Doe".equals(updatedUser.getName()) : "Name should be updated";
        System.out.println("✓ Test passed: User name updated successfully");
    }
    
    /**
     * Example of a test that follows FIRST principles
     * - Fast: Runs quickly
     * - Independent: Doesn't depend on other tests
     * - Repeatable: Can be run multiple times with same result
     * - Self-validating: Has clear pass/fail
     * - Timely: Written alongside production code
     */
    public static void testCalculatorMultiply() {
        System.out.println("\n=== Test: Calculator Multiply (FIRST principles) ===");
        // Arrange
        Calculator calculator = new Calculator();
        
        // Act
        int result = calculator.multiply(4, 5);
        
        // Assert
        assert result == 20 : "Expected 20 but got " + result;
        System.out.println("✓ Test passed: multiply(4, 5) = 20");
    }
    
    /**
     * Example: Testing edge cases
     */
    public static void testEdgeCases() {
        System.out.println("\n=== Test: Edge Cases ===");
        Calculator calculator = new Calculator();
        
        // Test with zero
        assert calculator.add(0, 0) == 0 : "0 + 0 should be 0";
        System.out.println("✓ Test passed: add(0, 0) = 0");
        
        // Test with negative numbers
        assert calculator.subtract(-5, -3) == -2 : "-5 - (-3) should be -2";
        System.out.println("✓ Test passed: subtract(-5, -3) = -2");
        
        // Test with large numbers
        assert calculator.multiply(1000, 1000) == 1000000 : "1000 * 1000 should be 1000000";
        System.out.println("✓ Test passed: multiply(1000, 1000) = 1000000");
    }
    
    public static void main(String[] args) {
        System.out.println("Running TDD and Testing Examples");
        System.out.println("=================================\n");
        
        testCalculatorAdd();
        testCalculatorDivideByZero();
        testUserServiceWithMock();
        testCalculatorMultiply();
        testEdgeCases();
        
        System.out.println("\n=================================");
        System.out.println("All tests completed!");
        System.out.println("\nNote: In a real project, use JUnit 5 for testing framework");
        System.out.println("and Mockito for mocking dependencies.");
    }
}
