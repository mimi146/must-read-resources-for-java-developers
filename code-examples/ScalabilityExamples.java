/**
 * Code examples for Scalability section
 * Demonstrates horizontal vs vertical scaling, load balancing concepts
 */

import java.util.*;
import java.util.concurrent.*;

// Example 1: Vertical Scaling vs Horizontal Scaling (Conceptual)
class ScalabilityExample {
    
    // Vertical Scaling: Increase resources on single machine
    static class VerticalScaling {
        private int cpuCores;
        private int memoryGB;
        
        public VerticalScaling(int cpuCores, int memoryGB) {
            this.cpuCores = cpuCores;
            this.memoryGB = memoryGB;
        }
        
        public void scaleUp() {
            cpuCores *= 2;
            memoryGB *= 2;
            System.out.println("Scaled up to " + cpuCores + " cores and " + memoryGB + " GB RAM");
        }
        
        public void processRequest(String request) {
            System.out.println("Processing " + request + " on single server with " + cpuCores + " cores");
        }
    }
    
    // Horizontal Scaling: Add more machines
    static class HorizontalScaling {
        private List<String> servers = new ArrayList<>();
        
        public HorizontalScaling(int initialServers) {
            for (int i = 1; i <= initialServers; i++) {
                servers.add("Server-" + i);
            }
        }
        
        public void scaleOut() {
            String newServer = "Server-" + (servers.size() + 1);
            servers.add(newServer);
            System.out.println("Added " + newServer + ". Total servers: " + servers.size());
        }
        
        public void processRequest(String request, int serverIndex) {
            String server = servers.get(serverIndex % servers.size());
            System.out.println("Processing " + request + " on " + server);
        }
        
        public int getServerCount() {
            return servers.size();
        }
    }
}

// Example 2: Load Balancer
class LoadBalancer {
    private List<Server> servers = new ArrayList<>();
    private int currentIndex = 0;
    
    static class Server {
        private String name;
        private int activeConnections;
        
        public Server(String name) {
            this.name = name;
            this.activeConnections = 0;
        }
        
        public void handleRequest(String request) {
            activeConnections++;
            System.out.println(name + " handling: " + request + 
                             " (Active connections: " + activeConnections + ")");
        }
        
        public int getActiveConnections() {
            return activeConnections;
        }
        
        public String getName() {
            return name;
        }
    }
    
    public void addServer(Server server) {
        servers.add(server);
        System.out.println("Added server: " + server.getName());
    }
    
    // Round Robin load balancing
    public void distributeRequestRoundRobin(String request) {
        if (servers.isEmpty()) {
            System.out.println("No servers available");
            return;
        }
        
        Server server = servers.get(currentIndex);
        server.handleRequest(request);
        currentIndex = (currentIndex + 1) % servers.size();
    }
    
    // Least Connections load balancing
    public void distributeRequestLeastConnections(String request) {
        if (servers.isEmpty()) {
            System.out.println("No servers available");
            return;
        }
        
        Server leastBusyServer = servers.get(0);
        for (Server server : servers) {
            if (server.getActiveConnections() < leastBusyServer.getActiveConnections()) {
                leastBusyServer = server;
            }
        }
        
        leastBusyServer.handleRequest(request);
    }
}

// Example 3: Caching for scalability
class CacheLayer {
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private Database database = new Database();
    
    static class Database {
        public String getData(String key) {
            // Simulate slow database query
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Data for " + key + " from DB";
        }
    }
    
    public String get(String key) {
        // Check cache first
        if (cache.containsKey(key)) {
            System.out.println("Cache HIT for " + key);
            return (String) cache.get(key);
        }
        
        // Cache miss - fetch from database
        System.out.println("Cache MISS for " + key);
        String data = database.getData(key);
        cache.put(key, data);
        return data;
    }
    
    public void invalidate(String key) {
        cache.remove(key);
        System.out.println("Cache invalidated for " + key);
    }
}

// Example 4: Stateless application design
class StatelessService {
    // Stateless - no session data stored
    public String processRequest(String userId, String action) {
        // All necessary data passed in request
        return "Processed " + action + " for user " + userId;
    }
}

class StatefulService {
    // Stateful - maintains session data (harder to scale)
    private Map<String, String> sessions = new HashMap<>();
    
    public void createSession(String userId) {
        sessions.put(userId, "session-data");
    }
    
    public String processRequest(String userId, String action) {
        String sessionData = sessions.get(userId);
        if (sessionData == null) {
            return "No active session";
        }
        return "Processed " + action + " using " + sessionData;
    }
}

// Example 5: Database connection pooling
class ConnectionPool {
    private Queue<Connection> availableConnections;
    private int maxConnections;
    
    static class Connection {
        private String id;
        
        public Connection(String id) {
            this.id = id;
        }
        
        public String getId() {
            return id;
        }
    }
    
    public ConnectionPool(int maxConnections) {
        this.maxConnections = maxConnections;
        this.availableConnections = new ConcurrentLinkedQueue<>();
        
        // Initialize pool
        for (int i = 0; i < maxConnections; i++) {
            availableConnections.offer(new Connection("conn-" + i));
        }
    }
    
    public Connection getConnection() {
        Connection conn = availableConnections.poll();
        if (conn != null) {
            System.out.println("Acquired connection: " + conn.getId());
            return conn;
        }
        System.out.println("No connections available - waiting...");
        return null;
    }
    
    public void releaseConnection(Connection conn) {
        availableConnections.offer(conn);
        System.out.println("Released connection: " + conn.getId());
    }
}

public class ScalabilityExamples {
    
    public static void demonstrateVerticalVsHorizontal() {
        System.out.println("=== Vertical vs Horizontal Scaling ===\n");
        
        // Vertical Scaling
        System.out.println("Vertical Scaling (Scale-Up):");
        ScalabilityExample.VerticalScaling verticalApp = 
            new ScalabilityExample.VerticalScaling(4, 16);
        verticalApp.processRequest("Request-1");
        verticalApp.scaleUp();
        verticalApp.processRequest("Request-2");
        
        // Horizontal Scaling
        System.out.println("\nHorizontal Scaling (Scale-Out):");
        ScalabilityExample.HorizontalScaling horizontalApp = 
            new ScalabilityExample.HorizontalScaling(2);
        horizontalApp.processRequest("Request-1", 0);
        horizontalApp.scaleOut();
        horizontalApp.processRequest("Request-2", 1);
    }
    
    public static void demonstrateLoadBalancing() {
        System.out.println("\n\n=== Load Balancing ===\n");
        
        LoadBalancer lb = new LoadBalancer();
        lb.addServer(new LoadBalancer.Server("Server-1"));
        lb.addServer(new LoadBalancer.Server("Server-2"));
        lb.addServer(new LoadBalancer.Server("Server-3"));
        
        System.out.println("\nRound Robin:");
        lb.distributeRequestRoundRobin("Request-1");
        lb.distributeRequestRoundRobin("Request-2");
        lb.distributeRequestRoundRobin("Request-3");
        lb.distributeRequestRoundRobin("Request-4");
        
        System.out.println("\nLeast Connections:");
        lb.distributeRequestLeastConnections("Request-5");
        lb.distributeRequestLeastConnections("Request-6");
    }
    
    public static void demonstrateCaching() {
        System.out.println("\n\n=== Caching for Performance ===\n");
        
        CacheLayer cache = new CacheLayer();
        
        // First access - cache miss
        long start1 = System.currentTimeMillis();
        cache.get("user-123");
        long time1 = System.currentTimeMillis() - start1;
        System.out.println("Time taken: " + time1 + "ms");
        
        // Second access - cache hit
        long start2 = System.currentTimeMillis();
        cache.get("user-123");
        long time2 = System.currentTimeMillis() - start2;
        System.out.println("Time taken: " + time2 + "ms");
        
        System.out.println("Performance improvement: " + (time1 - time2) + "ms faster");
    }
    
    public static void demonstrateStatelessVsStateful() {
        System.out.println("\n\n=== Stateless vs Stateful ===\n");
        
        System.out.println("Stateless Service (Easy to scale):");
        StatelessService stateless = new StatelessService();
        System.out.println(stateless.processRequest("user1", "action1"));
        System.out.println(stateless.processRequest("user1", "action2"));
        
        System.out.println("\nStateful Service (Harder to scale):");
        StatefulService stateful = new StatefulService();
        stateful.createSession("user1");
        System.out.println(stateful.processRequest("user1", "action1"));
    }
    
    public static void demonstrateConnectionPooling() {
        System.out.println("\n\n=== Connection Pooling ===\n");
        
        ConnectionPool pool = new ConnectionPool(3);
        
        ConnectionPool.Connection conn1 = pool.getConnection();
        ConnectionPool.Connection conn2 = pool.getConnection();
        ConnectionPool.Connection conn3 = pool.getConnection();
        
        // Pool exhausted
        pool.getConnection();
        
        // Release and reuse
        pool.releaseConnection(conn1);
        pool.getConnection();
    }
    
    public static void demonstrateBestPractices() {
        System.out.println("\n\n=== Scalability Best Practices ===\n");
        
        System.out.println("1. Horizontal Scaling:");
        System.out.println("   - Add more servers instead of upgrading single server");
        System.out.println("   - Easier to scale indefinitely");
        System.out.println("   - Better fault tolerance");
        
        System.out.println("\n2. Load Balancing:");
        System.out.println("   - Distribute traffic across multiple servers");
        System.out.println("   - Use Round Robin, Least Connections, or IP Hash");
        
        System.out.println("\n3. Caching:");
        System.out.println("   - Cache frequently accessed data");
        System.out.println("   - Use Redis, Memcached, or in-memory cache");
        
        System.out.println("\n4. Stateless Design:");
        System.out.println("   - Don't store session data on servers");
        System.out.println("   - Use external session storage (Redis)");
        
        System.out.println("\n5. Database Optimization:");
        System.out.println("   - Connection pooling");
        System.out.println("   - Read replicas");
        System.out.println("   - Sharding for horizontal scaling");
        
        System.out.println("\n6. Asynchronous Processing:");
        System.out.println("   - Use message queues for background tasks");
        System.out.println("   - Non-blocking I/O");
    }
    
    public static void main(String[] args) {
        demonstrateVerticalVsHorizontal();
        demonstrateLoadBalancing();
        demonstrateCaching();
        demonstrateStatelessVsStateful();
        demonstrateConnectionPooling();
        demonstrateBestPractices();
    }
}
