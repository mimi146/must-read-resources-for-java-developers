/**
 * Code examples for Database section
 * Demonstrates database concepts: Isolation levels, CAP theorem, ACID vs BASE
 * Note: These are conceptual examples. In production, use actual JDBC or JPA
 */

import java.util.*;
import java.util.concurrent.*;

// Example 1: Database Transaction Isolation Levels (Conceptual)
class BankAccountDB {
    private double balance;
    private final Object lock = new Object();
    
    public BankAccountDB(double initialBalance) {
        this.balance = initialBalance;
    }
    
    // READ UNCOMMITTED simulation - can read uncommitted data
    public double readUncommitted() {
        return balance; // No locking
    }
    
    // READ COMMITTED simulation - only reads committed data
    public double readCommitted() {
        synchronized (lock) {
            return balance;
        }
    }
    
    // REPEATABLE READ simulation - same read returns same result
    public double repeatableRead() {
        synchronized (lock) {
            return balance;
        }
    }
    
    // SERIALIZABLE simulation - full isolation
    public void withdraw(double amount) {
        synchronized (lock) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew " + amount + ", new balance: " + balance);
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }
    
    public double getBalance() {
        return balance;
    }
}

// Example 2: ACID Properties
class ACIDTransaction {
    
    // Atomicity: All operations succeed or all fail
    public static class AtomicOperation {
        private boolean completed = false;
        
        public void executeTransaction(BankAccountDB from, BankAccountDB to, double amount) {
            try {
                // Start transaction
                double fromBalance = from.getBalance();
                double toBalance = to.getBalance();
                
                if (fromBalance < amount) {
                    throw new IllegalStateException("Insufficient funds");
                }
                
                // Perform operations
                from.withdraw(amount);
                // Simulate deposit
                System.out.println("Deposited " + amount + " to recipient");
                
                completed = true;
                System.out.println("Transaction completed atomically");
            } catch (Exception e) {
                // Rollback if any operation fails
                System.out.println("Transaction rolled back: " + e.getMessage());
                completed = false;
            }
        }
    }
    
    // Consistency: Database moves from one valid state to another
    public static void demonstrateConsistency() {
        System.out.println("\nConsistency: Account balance never negative");
        BankAccountDB account = new BankAccountDB(100);
        account.withdraw(50);  // Valid
        account.withdraw(100); // Invalid - maintains consistency
    }
    
    // Isolation: Concurrent transactions don't interfere
    public static void demonstrateIsolation() throws InterruptedException {
        System.out.println("\nIsolation: Concurrent transactions are isolated");
        BankAccountDB account = new BankAccountDB(1000);
        
        Thread t1 = new Thread(() -> account.withdraw(300));
        Thread t2 = new Thread(() -> account.withdraw(400));
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("Final balance: " + account.getBalance());
    }
    
    // Durability: Once committed, changes persist
    public static void demonstrateDurability() {
        System.out.println("\nDurability: Committed changes persist even after system failure");
        System.out.println("(In real systems, this is handled by transaction logs)");
    }
}

// Example 3: BASE Properties (Eventually Consistent)
class BASESystem {
    // Basically Available: System remains operational
    // Soft state: State may change over time
    // Eventually consistent: Will become consistent eventually
    
    private Map<String, Integer> primaryStore = new ConcurrentHashMap<>();
    private Map<String, Integer> replicaStore = new ConcurrentHashMap<>();
    private List<String> pendingUpdates = Collections.synchronizedList(new ArrayList<>());
    
    // Write to primary (Basically Available)
    public void write(String key, Integer value) {
        primaryStore.put(key, value);
        pendingUpdates.add(key);
        System.out.println("Written to primary: " + key + " = " + value);
    }
    
    // Read from primary or replica (Soft state)
    public Integer read(String key) {
        Integer value = primaryStore.get(key);
        if (value == null) {
            value = replicaStore.get(key);
        }
        return value;
    }
    
    // Background sync for eventual consistency
    public void syncReplicas() {
        for (String key : pendingUpdates) {
            Integer value = primaryStore.get(key);
            replicaStore.put(key, value);
        }
        pendingUpdates.clear();
        System.out.println("Replicas synchronized - Eventually consistent");
    }
}

// Example 4: CAP Theorem Illustration
class CAPTheorem {
    // CAP: Consistency, Availability, Partition Tolerance
    // You can only guarantee 2 out of 3
    
    // CA System (Consistency + Availability): No partition tolerance
    static class CASystem {
        private Map<String, String> data = new HashMap<>();
        
        public void write(String key, String value) {
            data.put(key, value);
            System.out.println("CA: Consistent and Available write");
        }
        
        public String read(String key) {
            return data.get(key);
        }
    }
    
    // CP System (Consistency + Partition Tolerance): May be unavailable
    static class CPSystem {
        private Map<String, String> data = new HashMap<>();
        private boolean available = true;
        
        public void write(String key, String value) {
            if (!available) {
                throw new IllegalStateException("System unavailable during partition");
            }
            data.put(key, value);
            System.out.println("CP: Consistent write, but may be unavailable");
        }
        
        public String read(String key) {
            if (!available) {
                throw new IllegalStateException("System unavailable during partition");
            }
            return data.get(key);
        }
    }
    
    // AP System (Availability + Partition Tolerance): Eventually consistent
    static class APSystem {
        private Map<String, String> data = new HashMap<>();
        
        public void write(String key, String value) {
            data.put(key, value);
            System.out.println("AP: Always available, eventual consistency");
        }
        
        public String read(String key) {
            return data.get(key);
        }
    }
}

// Example 5: Database Sharding
class ShardedDatabase {
    private Map<Integer, Map<String, String>> shards = new HashMap<>();
    private int numberOfShards;
    
    public ShardedDatabase(int numberOfShards) {
        this.numberOfShards = numberOfShards;
        for (int i = 0; i < numberOfShards; i++) {
            shards.put(i, new HashMap<>());
        }
    }
    
    private int getShardKey(String key) {
        return Math.abs(key.hashCode()) % numberOfShards;
    }
    
    public void put(String key, String value) {
        int shardId = getShardKey(key);
        shards.get(shardId).put(key, value);
        System.out.println("Stored in shard " + shardId + ": " + key);
    }
    
    public String get(String key) {
        int shardId = getShardKey(key);
        return shards.get(shardId).get(key);
    }
}

public class DatabaseExamples {
    
    public static void demonstrateIsolationLevels() {
        System.out.println("=== Database Isolation Levels ===");
        BankAccountDB account = new BankAccountDB(1000);
        
        System.out.println("Initial balance: " + account.readCommitted());
        account.withdraw(200);
        System.out.println("Balance after withdrawal: " + account.readCommitted());
    }
    
    public static void demonstrateACID() throws InterruptedException {
        System.out.println("\n=== ACID Properties ===");
        
        // Atomicity
        System.out.println("\nAtomicity:");
        BankAccountDB account1 = new BankAccountDB(500);
        BankAccountDB account2 = new BankAccountDB(100);
        ACIDTransaction.AtomicOperation op = new ACIDTransaction.AtomicOperation();
        op.executeTransaction(account1, account2, 200);
        
        // Consistency
        ACIDTransaction.demonstrateConsistency();
        
        // Isolation
        ACIDTransaction.demonstrateIsolation();
        
        // Durability
        ACIDTransaction.demonstrateDurability();
    }
    
    public static void demonstrateBASE() throws InterruptedException {
        System.out.println("\n=== BASE Properties ===");
        BASESystem baseSystem = new BASESystem();
        
        baseSystem.write("user1", 100);
        baseSystem.write("user2", 200);
        
        System.out.println("Before sync - reading from primary");
        System.out.println("user1: " + baseSystem.read("user1"));
        
        Thread.sleep(100);
        baseSystem.syncReplicas();
    }
    
    public static void demonstrateCAP() {
        System.out.println("\n=== CAP Theorem ===");
        
        System.out.println("\nCA System (Consistency + Availability):");
        CAPTheorem.CASystem caSystem = new CAPTheorem.CASystem();
        caSystem.write("key1", "value1");
        
        System.out.println("\nCP System (Consistency + Partition Tolerance):");
        CAPTheorem.CPSystem cpSystem = new CAPTheorem.CPSystem();
        cpSystem.write("key1", "value1");
        
        System.out.println("\nAP System (Availability + Partition Tolerance):");
        CAPTheorem.APSystem apSystem = new CAPTheorem.APSystem();
        apSystem.write("key1", "value1");
    }
    
    public static void demonstrateSharding() {
        System.out.println("\n=== Database Sharding ===");
        ShardedDatabase db = new ShardedDatabase(3);
        
        db.put("user_001", "John");
        db.put("user_002", "Jane");
        db.put("user_003", "Bob");
        db.put("user_004", "Alice");
        
        System.out.println("\nRetrieving: user_001 = " + db.get("user_001"));
    }
    
    public static void main(String[] args) throws InterruptedException {
        demonstrateIsolationLevels();
        demonstrateACID();
        demonstrateBASE();
        demonstrateCAP();
        demonstrateSharding();
    }
}
