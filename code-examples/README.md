# Code Examples for Java Developers

This directory contains practical Java code examples for each section of the learning resources guide. Each file demonstrates key concepts from its respective section.

## 📁 Files Overview

### 1. AlgorithmsDataStructure.java
**Topics Covered:**
- Big O complexity analysis (O(1), O(n), O(log n), O(n²))
- Linear search and binary search
- Sorting algorithms (bubble sort)
- Stack data structure implementation

**To run:**
```bash
javac AlgorithmsDataStructure.java && java AlgorithmsDataStructure
```

### 2. JavaExamples.java
**Topics Covered:**
- Object-Oriented Programming (inheritance, polymorphism)
- Proper implementation of equals() and hashCode()
- Java Generics with bounded type parameters
- Exception handling with custom exceptions
- Lambda expressions and Stream API
- CompletableFuture for asynchronous programming

**To run:**
```bash
javac JavaExamples.java && java JavaExamples
```

### 3. TDDTestingExamples.java
**Topics Covered:**
- AAA (Arrange-Act-Assert) testing pattern
- FIRST principles (Fast, Independent, Repeatable, Self-validating, Timely)
- Mocking and stubbing concepts
- Edge case testing
- Unit testing best practices

**To run:**
```bash
javac TDDTestingExamples.java && java TDDTestingExamples
```

### 4. SoftwareDesignExamples.java
**Topics Covered:**
- SOLID principles (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion)
- Design Patterns (Singleton, Factory, Builder, Observer)
- Immutability and final classes
- Fluent interface pattern

**To run:**
```bash
javac SoftwareDesignExamples.java && java SoftwareDesignExamples
```

### 5. DatabaseExamples.java
**Topics Covered:**
- Database transaction isolation levels
- ACID properties (Atomicity, Consistency, Isolation, Durability)
- BASE properties (Basically Available, Soft state, Eventually consistent)
- CAP theorem (Consistency, Availability, Partition tolerance)
- Database sharding

**To run:**
```bash
javac DatabaseExamples.java && java DatabaseExamples
```

### 6. RESTAPIExamples.java
**Topics Covered:**
- RESTful API design principles
- HTTP methods (GET, POST, PUT, PATCH, DELETE)
- HTTP status codes (200, 201, 204, 400, 404, 409, 500)
- API response structure
- Error handling in REST APIs
- Query parameters and filtering

**To run:**
```bash
javac RESTAPIExamples.java && java RESTAPIExamples
```

### 7. SpringFrameworkExamples.java
**Topics Covered:**
- Dependency Injection (DI)
- Inversion of Control (IoC)
- Spring Boot REST controllers
- Spring Security authentication concepts
- Spring Data JPA repository pattern
- Bean management and configuration

**To run:**
```bash
javac SpringFrameworkExamples.java && java SpringFrameworkExamples
```

### 8. ScalabilityExamples.java
**Topics Covered:**
- Vertical vs horizontal scaling
- Load balancing strategies (Round Robin, Least Connections)
- Caching for performance
- Stateless vs stateful applications
- Database connection pooling
- Scalability best practices

**To run:**
```bash
javac ScalabilityExamples.java && java ScalabilityExamples
```

### 9. ArchitectureExamples.java
**Topics Covered:**
- Monolithic vs microservices architecture
- Three-tier architecture pattern
- 12 Factor App methodology
- API Gateway pattern
- DNS and CDN concepts
- Service-oriented architecture

**To run:**
```bash
javac ArchitectureExamples.java && java ArchitectureExamples
```

### 10. DevOpsCloudExamples.java
**Topics Covered:**
- Build tools (Maven, Gradle)
- Continuous Integration/Continuous Deployment (CI/CD) pipelines
- Docker containers and images
- Git workflow and branching strategies
- AWS services (EC2, S3, RDS)
- Kubernetes concepts (Pods, Services)

**To run:**
```bash
javac DevOpsCloudExamples.java && java DevOpsCloudExamples
```

### 11. AgileExamples.java
**Topics Covered:**
- User stories and sprint planning
- Extreme Programming (XP) practices
- Pair programming
- Test-Driven Development (TDD) cycle
- Continuous integration
- Collective code ownership
- Agile ceremonies (standup, review, retrospective)

**To run:**
```bash
javac AgileExamples.java && java AgileExamples
```

## 🚀 Quick Start

To compile and run all examples:

```bash
# Navigate to the code-examples directory
cd code-examples

# Compile all files
javac *.java

# Run a specific example
java AlgorithmsDataStructure
java JavaExamples
java TDDTestingExamples
java SoftwareDesignExamples
java DatabaseExamples
java RESTAPIExamples
java SpringFrameworkExamples
java ScalabilityExamples
java ArchitectureExamples
java DevOpsCloudExamples
java AgileExamples
```

## 📝 Notes

- These are **educational examples** designed to demonstrate concepts
- In production applications, you should use:
  - JUnit 5 and Mockito for testing
  - Actual Spring Framework with annotations
  - Real database connections (JDBC/JPA)
  - Production-ready libraries and frameworks
- All examples are self-contained and runnable with standard Java (Java 8+)
- No external dependencies required for running these examples

## 💡 Learning Path

1. Start with **JavaExamples.java** to understand core Java concepts
2. Move to **TDDTestingExamples.java** to learn testing practices
3. Study **SoftwareDesignExamples.java** for design patterns and SOLID principles
4. Progress through architecture and scalability topics
5. End with **AgileExamples.java** to understand development workflows

## 🤝 Contributing

These examples are meant to complement the main README.md resources. Feel free to:
- Suggest improvements
- Add more examples
- Fix any issues
- Enhance documentation

## 📚 Related Resources

See the main [README.md](../README.md) for comprehensive learning resources, articles, videos, and books on each topic.
