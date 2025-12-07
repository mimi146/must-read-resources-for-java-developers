/**
 * Code examples for Architecture section
 * Demonstrates Monoliths vs Microservices, 12 Factor App, Three-tier architecture
 */

import java.util.*;

// Example 1: Monolithic Architecture
class MonolithicApp {
    // All components in single application
    
    static class UserController {
        private UserService userService = new UserService();
        
        public String getUser(String id) {
            return userService.getUser(id);
        }
    }
    
    static class UserService {
        private UserRepository repository = new UserRepository();
        
        public String getUser(String id) {
            return repository.findUser(id);
        }
    }
    
    static class UserRepository {
        public String findUser(String id) {
            return "User data from database";
        }
    }
    
    static class OrderController {
        private OrderService orderService = new OrderService();
        
        public String createOrder(String userId, String product) {
            return orderService.createOrder(userId, product);
        }
    }
    
    static class OrderService {
        private OrderRepository repository = new OrderRepository();
        
        public String createOrder(String userId, String product) {
            return repository.saveOrder(userId, product);
        }
    }
    
    static class OrderRepository {
        public String saveOrder(String userId, String product) {
            return "Order saved for user " + userId;
        }
    }
    
    public void start() {
        System.out.println("Monolithic application started");
        System.out.println("All services running in single process");
    }
}

// Example 2: Microservices Architecture
class MicroservicesArchitecture {
    
    // User Service - Independent microservice
    static class UserService {
        private int port;
        
        public UserService(int port) {
            this.port = port;
        }
        
        public void start() {
            System.out.println("User Service started on port " + port);
        }
        
        public String getUser(String id) {
            return "User data from User Service";
        }
    }
    
    // Order Service - Independent microservice
    static class OrderService {
        private int port;
        private String userServiceUrl;
        
        public OrderService(int port, String userServiceUrl) {
            this.port = port;
            this.userServiceUrl = userServiceUrl;
        }
        
        public void start() {
            System.out.println("Order Service started on port " + port);
        }
        
        public String createOrder(String userId, String product) {
            // Call User Service via HTTP
            System.out.println("Calling " + userServiceUrl + " to verify user");
            return "Order created for user " + userId;
        }
    }
    
    // API Gateway - Entry point for all services
    static class APIGateway {
        private UserService userService;
        private OrderService orderService;
        
        public APIGateway(UserService userService, OrderService orderService) {
            this.userService = userService;
            this.orderService = orderService;
        }
        
        public String routeRequest(String service, String endpoint) {
            System.out.println("API Gateway routing to: " + service);
            if ("user".equals(service)) {
                return userService.getUser(endpoint);
            } else if ("order".equals(service)) {
                return orderService.createOrder(endpoint, "product");
            }
            return "Service not found";
        }
    }
}

// Example 3: Three-tier Architecture
class ThreeTierArchitecture {
    
    // Presentation Layer (Tier 1)
    static class PresentationLayer {
        private BusinessLogicLayer businessLayer;
        
        public PresentationLayer(BusinessLogicLayer businessLayer) {
            this.businessLayer = businessLayer;
        }
        
        public void displayUserProfile(String userId) {
            String userData = businessLayer.getUserData(userId);
            System.out.println("Presentation Layer: Displaying " + userData);
        }
        
        public void displayProductList() {
            List<String> products = businessLayer.getProducts();
            System.out.println("Presentation Layer: Displaying products: " + products);
        }
    }
    
    // Business Logic Layer (Tier 2)
    static class BusinessLogicLayer {
        private DataAccessLayer dataLayer;
        
        public BusinessLogicLayer(DataAccessLayer dataLayer) {
            this.dataLayer = dataLayer;
        }
        
        public String getUserData(String userId) {
            System.out.println("Business Layer: Processing user request");
            return dataLayer.fetchUser(userId);
        }
        
        public List<String> getProducts() {
            System.out.println("Business Layer: Processing product request");
            List<String> products = dataLayer.fetchProducts();
            // Apply business rules
            return products;
        }
    }
    
    // Data Access Layer (Tier 3)
    static class DataAccessLayer {
        private Map<String, String> userDatabase = new HashMap<>();
        private List<String> productDatabase = new ArrayList<>();
        
        public DataAccessLayer() {
            userDatabase.put("1", "John Doe");
            userDatabase.put("2", "Jane Smith");
            productDatabase.add("Laptop");
            productDatabase.add("Mouse");
        }
        
        public String fetchUser(String userId) {
            System.out.println("Data Layer: Querying database for user " + userId);
            return userDatabase.get(userId);
        }
        
        public List<String> fetchProducts() {
            System.out.println("Data Layer: Querying database for products");
            return productDatabase;
        }
    }
}

// Example 4: 12 Factor App Principles
class TwelveFactorApp {
    
    // I. Codebase - One codebase tracked in version control
    static class Codebase {
        private String repository = "https://github.com/company/app";
        
        public void deploy(String environment) {
            System.out.println("Deploying from " + repository + " to " + environment);
        }
    }
    
    // II. Dependencies - Explicitly declare and isolate dependencies
    static class Dependencies {
        private List<String> dependencies = Arrays.asList(
            "spring-boot:2.7.0",
            "postgresql:42.5.0"
        );
        
        public void installDependencies() {
            System.out.println("Installing dependencies: " + dependencies);
        }
    }
    
    // III. Config - Store config in environment
    static class Configuration {
        public String getDatabaseUrl() {
            return System.getenv("DATABASE_URL");
        }
        
        public String getApiKey() {
            return System.getenv("API_KEY");
        }
    }
    
    // IV. Backing services - Treat backing services as attached resources
    static class BackingServices {
        public void connectDatabase(String url) {
            System.out.println("Connecting to database: " + url);
        }
        
        public void connectCache(String url) {
            System.out.println("Connecting to cache: " + url);
        }
    }
    
    // V. Build, release, run - Strictly separate build and run stages
    static class BuildReleaseRun {
        public void build() {
            System.out.println("Build: Compiling code, running tests");
        }
        
        public void release() {
            System.out.println("Release: Combining build with config");
        }
        
        public void run() {
            System.out.println("Run: Executing the application");
        }
    }
    
    // VI. Processes - Execute the app as stateless processes
    static class StatelessProcess {
        public String handleRequest(Map<String, String> requestData) {
            // No persistent state in process
            String userId = requestData.get("userId");
            return "Processed for user: " + userId;
        }
    }
    
    // VII. Port binding - Export services via port binding
    static class PortBinding {
        private int port;
        
        public PortBinding(int port) {
            this.port = port;
        }
        
        public void start() {
            System.out.println("Service listening on port " + port);
        }
    }
}

// Example 5: DNS and CDN concepts
class NetworkConcepts {
    
    static class DNSResolver {
        private Map<String, String> dnsRecords = new HashMap<>();
        
        public DNSResolver() {
            dnsRecords.put("example.com", "192.168.1.1");
            dnsRecords.put("api.example.com", "192.168.1.2");
        }
        
        public String resolve(String domain) {
            String ip = dnsRecords.get(domain);
            System.out.println("DNS: " + domain + " -> " + ip);
            return ip;
        }
    }
    
    static class CDN {
        private Map<String, String> edgeServers = new HashMap<>();
        
        public CDN() {
            edgeServers.put("US-East", "cdn-us-east.example.com");
            edgeServers.put("EU-West", "cdn-eu-west.example.com");
            edgeServers.put("Asia", "cdn-asia.example.com");
        }
        
        public String serveContent(String userLocation, String content) {
            String server = edgeServers.get(userLocation);
            System.out.println("CDN: Serving " + content + " from " + server);
            return "Content delivered with low latency";
        }
    }
}

public class ArchitectureExamples {
    
    public static void demonstrateMonolithVsMicroservices() {
        System.out.println("=== Monolith vs Microservices ===\n");
        
        System.out.println("1. Monolithic Architecture:");
        MonolithicApp monolith = new MonolithicApp();
        monolith.start();
        MonolithicApp.UserController userController = new MonolithicApp.UserController();
        System.out.println(userController.getUser("123"));
        
        System.out.println("\n2. Microservices Architecture:");
        MicroservicesArchitecture.UserService userService = 
            new MicroservicesArchitecture.UserService(8081);
        MicroservicesArchitecture.OrderService orderService = 
            new MicroservicesArchitecture.OrderService(8082, "http://localhost:8081");
        
        userService.start();
        orderService.start();
        
        MicroservicesArchitecture.APIGateway gateway = 
            new MicroservicesArchitecture.APIGateway(userService, orderService);
        System.out.println(gateway.routeRequest("user", "123"));
    }
    
    public static void demonstrateThreeTierArchitecture() {
        System.out.println("\n\n=== Three-Tier Architecture ===\n");
        
        ThreeTierArchitecture.DataAccessLayer dataLayer = 
            new ThreeTierArchitecture.DataAccessLayer();
        ThreeTierArchitecture.BusinessLogicLayer businessLayer = 
            new ThreeTierArchitecture.BusinessLogicLayer(dataLayer);
        ThreeTierArchitecture.PresentationLayer presentationLayer = 
            new ThreeTierArchitecture.PresentationLayer(businessLayer);
        
        presentationLayer.displayUserProfile("1");
        System.out.println();
        presentationLayer.displayProductList();
    }
    
    public static void demonstrate12FactorApp() {
        System.out.println("\n\n=== 12 Factor App ===\n");
        
        TwelveFactorApp.Codebase codebase = new TwelveFactorApp.Codebase();
        codebase.deploy("production");
        
        TwelveFactorApp.Dependencies deps = new TwelveFactorApp.Dependencies();
        deps.installDependencies();
        
        System.out.println("\nConfiguration from environment:");
        System.out.println("DATABASE_URL, API_KEY loaded from env vars");
        
        TwelveFactorApp.BuildReleaseRun lifecycle = new TwelveFactorApp.BuildReleaseRun();
        lifecycle.build();
        lifecycle.release();
        lifecycle.run();
        
        TwelveFactorApp.PortBinding service = new TwelveFactorApp.PortBinding(8080);
        service.start();
    }
    
    public static void demonstrateNetworkConcepts() {
        System.out.println("\n\n=== DNS and CDN ===\n");
        
        System.out.println("DNS Resolution:");
        NetworkConcepts.DNSResolver dns = new NetworkConcepts.DNSResolver();
        dns.resolve("example.com");
        dns.resolve("api.example.com");
        
        System.out.println("\nContent Delivery Network:");
        NetworkConcepts.CDN cdn = new NetworkConcepts.CDN();
        cdn.serveContent("US-East", "image.jpg");
        cdn.serveContent("EU-West", "image.jpg");
    }
    
    public static void demonstrateArchitecturePatterns() {
        System.out.println("\n\n=== Architecture Patterns Summary ===\n");
        
        System.out.println("1. Monolithic:");
        System.out.println("   + Simple to develop and deploy");
        System.out.println("   + Easy to test");
        System.out.println("   - Hard to scale specific components");
        System.out.println("   - Technology stack locked");
        
        System.out.println("\n2. Microservices:");
        System.out.println("   + Independent deployment");
        System.out.println("   + Technology flexibility");
        System.out.println("   + Easier to scale");
        System.out.println("   - Distributed system complexity");
        System.out.println("   - Network latency");
        
        System.out.println("\n3. Three-Tier:");
        System.out.println("   + Separation of concerns");
        System.out.println("   + Reusability");
        System.out.println("   + Maintainability");
        
        System.out.println("\n4. 12 Factor App:");
        System.out.println("   + Cloud-native");
        System.out.println("   + Portable");
        System.out.println("   + Scalable");
    }
    
    public static void main(String[] args) {
        demonstrateMonolithVsMicroservices();
        demonstrateThreeTierArchitecture();
        demonstrate12FactorApp();
        demonstrateNetworkConcepts();
        demonstrateArchitecturePatterns();
    }
}
