/**
 * Code examples for DevOps and Cloud section
 * Demonstrates CI/CD concepts, Docker basics, Git workflows
 */

import java.util.*;
import java.io.*;

// Example 1: Build Tool Concepts
class BuildToolExample {
    
    static class MavenBuild {
        private List<String> dependencies = new ArrayList<>();
        private List<String> phases = Arrays.asList(
            "validate", "compile", "test", "package", "verify", "install", "deploy"
        );
        
        public void addDependency(String dependency) {
            dependencies.add(dependency);
            System.out.println("Added dependency: " + dependency);
        }
        
        public void build() {
            System.out.println("Maven Build Lifecycle:");
            for (String phase : phases) {
                System.out.println("  - " + phase);
            }
            System.out.println("Build completed: Generated JAR file");
        }
    }
    
    static class GradleBuild {
        private List<String> tasks = Arrays.asList(
            "clean", "compileJava", "test", "build"
        );
        
        public void executeBuild() {
            System.out.println("Gradle Build:");
            for (String task : tasks) {
                System.out.println("  Task: " + task);
            }
            System.out.println("Build successful");
        }
    }
}

// Example 2: Continuous Integration Pipeline
class ContinuousIntegrationPipeline {
    
    static class Pipeline {
        private List<Stage> stages = new ArrayList<>();
        
        static class Stage {
            private String name;
            private boolean passed;
            
            public Stage(String name) {
                this.name = name;
                this.passed = false;
            }
            
            public boolean execute() {
                System.out.println("Executing stage: " + name);
                // Simulate stage execution
                passed = true;
                System.out.println("  ✓ Stage passed");
                return passed;
            }
            
            public String getName() {
                return name;
            }
        }
        
        public void addStage(Stage stage) {
            stages.add(stage);
        }
        
        public boolean run() {
            System.out.println("Starting CI Pipeline...\n");
            
            for (Stage stage : stages) {
                if (!stage.execute()) {
                    System.out.println("Pipeline failed at: " + stage.getName());
                    return false;
                }
                System.out.println();
            }
            
            System.out.println("Pipeline completed successfully!");
            return true;
        }
    }
    
    public static Pipeline createTypicalPipeline() {
        Pipeline pipeline = new Pipeline();
        
        pipeline.addStage(new Pipeline.Stage("Checkout Code"));
        pipeline.addStage(new Pipeline.Stage("Install Dependencies"));
        pipeline.addStage(new Pipeline.Stage("Compile Code"));
        pipeline.addStage(new Pipeline.Stage("Run Unit Tests"));
        pipeline.addStage(new Pipeline.Stage("Code Quality Analysis"));
        pipeline.addStage(new Pipeline.Stage("Build Artifact"));
        pipeline.addStage(new Pipeline.Stage("Deploy to Staging"));
        
        return pipeline;
    }
}

// Example 3: Docker Container Concepts
class DockerExample {
    
    static class DockerImage {
        private String name;
        private String version;
        private List<String> layers = new ArrayList<>();
        
        public DockerImage(String name, String version) {
            this.name = name;
            this.version = version;
        }
        
        public void addLayer(String layer) {
            layers.add(layer);
            System.out.println("Added layer: " + layer);
        }
        
        public void build() {
            System.out.println("Building Docker image: " + name + ":" + version);
            for (String layer : layers) {
                System.out.println("  Layer: " + layer);
            }
            System.out.println("Image built successfully");
        }
        
        public DockerContainer run() {
            System.out.println("Running container from image: " + name + ":" + version);
            return new DockerContainer(name + "-container");
        }
    }
    
    static class DockerContainer {
        private String name;
        private boolean running;
        
        public DockerContainer(String name) {
            this.name = name;
            this.running = false;
        }
        
        public void start() {
            running = true;
            System.out.println("Container " + name + " started");
        }
        
        public void stop() {
            running = false;
            System.out.println("Container " + name + " stopped");
        }
        
        public boolean isRunning() {
            return running;
        }
    }
    
    static class Dockerfile {
        public static String getExample() {
            return "# Dockerfile example\n" +
                   "FROM openjdk:11-jre-slim\n" +
                   "WORKDIR /app\n" +
                   "COPY target/myapp.jar app.jar\n" +
                   "EXPOSE 8080\n" +
                   "ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]";
        }
    }
}

// Example 4: Git Workflow
class GitWorkflow {
    
    static class Repository {
        private Map<String, Branch> branches = new HashMap<>();
        private List<Commit> commits = new ArrayList<>();
        
        public Repository() {
            branches.put("main", new Branch("main"));
        }
        
        public void createBranch(String branchName, String fromBranch) {
            System.out.println("Creating branch: " + branchName + " from " + fromBranch);
            branches.put(branchName, new Branch(branchName));
        }
        
        public void commit(String branch, String message) {
            Commit commit = new Commit(message);
            commits.add(commit);
            System.out.println("Committed to " + branch + ": " + message);
        }
        
        public void merge(String sourceBranch, String targetBranch) {
            System.out.println("Merging " + sourceBranch + " into " + targetBranch);
            System.out.println("Merge successful");
        }
        
        public void push(String branch) {
            System.out.println("Pushing branch: " + branch + " to remote");
        }
        
        public void pull(String branch) {
            System.out.println("Pulling latest changes for: " + branch);
        }
    }
    
    static class Branch {
        private String name;
        
        public Branch(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
    }
    
    static class Commit {
        private String message;
        private String hash;
        
        public Commit(String message) {
            this.message = message;
            this.hash = generateHash();
        }
        
        private String generateHash() {
            return "abc" + (int)(Math.random() * 1000);
        }
        
        public String getMessage() {
            return message;
        }
    }
}

// Example 5: AWS Cloud Services (Conceptual)
class AWSServices {
    
    static class EC2Instance {
        private String instanceId;
        private String instanceType;
        private boolean running;
        
        public EC2Instance(String instanceType) {
            this.instanceId = "i-" + UUID.randomUUID().toString().substring(0, 8);
            this.instanceType = instanceType;
            this.running = false;
        }
        
        public void start() {
            running = true;
            System.out.println("EC2 instance " + instanceId + 
                             " (" + instanceType + ") started");
        }
        
        public void stop() {
            running = false;
            System.out.println("EC2 instance " + instanceId + " stopped");
        }
    }
    
    static class S3Bucket {
        private String bucketName;
        private Map<String, String> objects = new HashMap<>();
        
        public S3Bucket(String bucketName) {
            this.bucketName = bucketName;
            System.out.println("Created S3 bucket: " + bucketName);
        }
        
        public void upload(String key, String content) {
            objects.put(key, content);
            System.out.println("Uploaded to S3: " + key);
        }
        
        public String download(String key) {
            String content = objects.get(key);
            System.out.println("Downloaded from S3: " + key);
            return content;
        }
    }
    
    static class RDSDatabase {
        private String dbInstanceId;
        private String engine;
        
        public RDSDatabase(String dbInstanceId, String engine) {
            this.dbInstanceId = dbInstanceId;
            this.engine = engine;
            System.out.println("Created RDS instance: " + dbInstanceId + 
                             " (Engine: " + engine + ")");
        }
        
        public void connect() {
            System.out.println("Connected to RDS database: " + dbInstanceId);
        }
    }
}

// Example 6: Kubernetes Concepts (Conceptual)
class KubernetesExample {
    
    static class Pod {
        private String name;
        private List<Container> containers = new ArrayList<>();
        
        public Pod(String name) {
            this.name = name;
        }
        
        public void addContainer(Container container) {
            containers.add(container);
            System.out.println("Added container to pod: " + container.getName());
        }
        
        public void deploy() {
            System.out.println("Deploying pod: " + name);
            for (Container c : containers) {
                c.start();
            }
        }
        
        static class Container {
            private String name;
            private String image;
            
            public Container(String name, String image) {
                this.name = name;
                this.image = image;
            }
            
            public void start() {
                System.out.println("  Starting container: " + name + 
                                 " (Image: " + image + ")");
            }
            
            public String getName() {
                return name;
            }
        }
    }
    
    static class Service {
        private String name;
        private int port;
        
        public Service(String name, int port) {
            this.name = name;
            this.port = port;
        }
        
        public void expose() {
            System.out.println("Service " + name + " exposed on port " + port);
        }
    }
}

public class DevOpsCloudExamples {
    
    public static void demonstrateBuildTools() {
        System.out.println("=== Build Tools ===\n");
        
        System.out.println("1. Maven Build:");
        BuildToolExample.MavenBuild maven = new BuildToolExample.MavenBuild();
        maven.addDependency("spring-boot-starter-web");
        maven.addDependency("junit");
        maven.build();
        
        System.out.println("\n2. Gradle Build:");
        BuildToolExample.GradleBuild gradle = new BuildToolExample.GradleBuild();
        gradle.executeBuild();
    }
    
    public static void demonstrateCICD() {
        System.out.println("\n\n=== Continuous Integration/Deployment ===\n");
        
        ContinuousIntegrationPipeline.Pipeline pipeline = 
            ContinuousIntegrationPipeline.createTypicalPipeline();
        pipeline.run();
    }
    
    public static void demonstrateDocker() {
        System.out.println("\n\n=== Docker ===\n");
        
        System.out.println("Dockerfile example:");
        System.out.println(DockerExample.Dockerfile.getExample());
        
        System.out.println("\nBuilding and running container:");
        DockerExample.DockerImage image = 
            new DockerExample.DockerImage("myapp", "1.0");
        image.addLayer("Base OS");
        image.addLayer("Java Runtime");
        image.addLayer("Application Code");
        image.build();
        
        System.out.println();
        DockerExample.DockerContainer container = image.run();
        container.start();
        System.out.println("Container running: " + container.isRunning());
    }
    
    public static void demonstrateGitWorkflow() {
        System.out.println("\n\n=== Git Workflow ===\n");
        
        GitWorkflow.Repository repo = new GitWorkflow.Repository();
        
        // Feature branch workflow
        repo.createBranch("feature/new-feature", "main");
        repo.commit("feature/new-feature", "Add new feature");
        repo.commit("feature/new-feature", "Fix bug in feature");
        repo.push("feature/new-feature");
        
        // Merge to main
        repo.merge("feature/new-feature", "main");
        repo.push("main");
    }
    
    public static void demonstrateAWS() {
        System.out.println("\n\n=== AWS Services ===\n");
        
        System.out.println("1. EC2 (Elastic Compute Cloud):");
        AWSServices.EC2Instance ec2 = new AWSServices.EC2Instance("t2.micro");
        ec2.start();
        
        System.out.println("\n2. S3 (Simple Storage Service):");
        AWSServices.S3Bucket s3 = new AWSServices.S3Bucket("my-app-bucket");
        s3.upload("config.json", "{\"env\": \"production\"}");
        
        System.out.println("\n3. RDS (Relational Database Service):");
        AWSServices.RDSDatabase rds = 
            new AWSServices.RDSDatabase("mydb", "PostgreSQL");
        rds.connect();
    }
    
    public static void demonstrateKubernetes() {
        System.out.println("\n\n=== Kubernetes ===\n");
        
        KubernetesExample.Pod pod = new KubernetesExample.Pod("myapp-pod");
        pod.addContainer(
            new KubernetesExample.Pod.Container("app", "myapp:1.0")
        );
        pod.deploy();
        
        System.out.println();
        KubernetesExample.Service service = 
            new KubernetesExample.Service("myapp-service", 8080);
        service.expose();
    }
    
    public static void demonstrateDevOpsPrinciples() {
        System.out.println("\n\n=== DevOps Principles ===\n");
        
        System.out.println("1. Continuous Integration:");
        System.out.println("   - Commit code frequently");
        System.out.println("   - Automated testing");
        System.out.println("   - Fast feedback");
        
        System.out.println("\n2. Continuous Deployment:");
        System.out.println("   - Automated deployment");
        System.out.println("   - Deploy small changes");
        System.out.println("   - Rollback capability");
        
        System.out.println("\n3. Infrastructure as Code:");
        System.out.println("   - Version controlled infrastructure");
        System.out.println("   - Reproducible environments");
        System.out.println("   - Automated provisioning");
        
        System.out.println("\n4. Monitoring and Logging:");
        System.out.println("   - Real-time monitoring");
        System.out.println("   - Centralized logging");
        System.out.println("   - Alerting");
        
        System.out.println("\n5. Collaboration:");
        System.out.println("   - Dev and Ops work together");
        System.out.println("   - Shared responsibility");
        System.out.println("   - Continuous improvement");
    }
    
    public static void main(String[] args) {
        demonstrateBuildTools();
        demonstrateCICD();
        demonstrateDocker();
        demonstrateGitWorkflow();
        demonstrateAWS();
        demonstrateKubernetes();
        demonstrateDevOpsPrinciples();
    }
}
