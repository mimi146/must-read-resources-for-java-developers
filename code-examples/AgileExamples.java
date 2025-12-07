/**
 * Code examples for Agile Software Development section
 * Demonstrates Extreme Programming (XP) practices
 */

import java.util.*;

// Example 1: User Story representation
class UserStory {
    private String id;
    private String asA;
    private String iWant;
    private String soThat;
    private int storyPoints;
    private String status;
    
    public UserStory(String id, String asA, String iWant, String soThat, int storyPoints) {
        this.id = id;
        this.asA = asA;
        this.iWant = iWant;
        this.soThat = soThat;
        this.storyPoints = storyPoints;
        this.status = "To Do";
    }
    
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("User Story " + id + " moved to: " + status);
    }
    
    public void display() {
        System.out.println("User Story: " + id);
        System.out.println("As a " + asA);
        System.out.println("I want " + iWant);
        System.out.println("So that " + soThat);
        System.out.println("Story Points: " + storyPoints);
        System.out.println("Status: " + status);
    }
    
    public String getId() { return id; }
    public int getStoryPoints() { return storyPoints; }
    public String getStatus() { return status; }
}

// Example 2: Sprint (Iteration)
class Sprint {
    private String name;
    private int durationDays;
    private List<UserStory> stories;
    private int capacity;
    
    public Sprint(String name, int durationDays, int capacity) {
        this.name = name;
        this.durationDays = durationDays;
        this.capacity = capacity;
        this.stories = new ArrayList<>();
    }
    
    public boolean addStory(UserStory story) {
        int totalPoints = stories.stream()
            .mapToInt(UserStory::getStoryPoints)
            .sum();
        
        if (totalPoints + story.getStoryPoints() <= capacity) {
            stories.add(story);
            System.out.println("Added story " + story.getId() + " to " + name);
            return true;
        } else {
            System.out.println("Cannot add story " + story.getId() + 
                             " - exceeds sprint capacity");
            return false;
        }
    }
    
    public void startSprint() {
        System.out.println("\n" + name + " started!");
        System.out.println("Duration: " + durationDays + " days");
        System.out.println("Stories: " + stories.size());
        System.out.println("Total story points: " + 
            stories.stream().mapToInt(UserStory::getStoryPoints).sum());
    }
    
    public void dailyStandup(int day) {
        System.out.println("\nDay " + day + " Daily Standup:");
        System.out.println("Team updates on progress...");
        for (UserStory story : stories) {
            System.out.println("  - " + story.getId() + ": " + story.getStatus());
        }
    }
    
    public void sprintReview() {
        System.out.println("\n" + name + " Review:");
        int completed = 0;
        for (UserStory story : stories) {
            if ("Done".equals(story.getStatus())) {
                completed++;
            }
        }
        System.out.println("Completed stories: " + completed + "/" + stories.size());
    }
    
    public void sprintRetrospective() {
        System.out.println("\n" + name + " Retrospective:");
        System.out.println("What went well:");
        System.out.println("  - Good team collaboration");
        System.out.println("  - Delivered features on time");
        System.out.println("What can be improved:");
        System.out.println("  - Better estimation");
        System.out.println("  - More automated tests");
    }
}

// Example 3: Extreme Programming (XP) Practices
class ExtremeProgramming {
    
    // Practice 1: Pair Programming
    static class PairProgramming {
        private String driver;
        private String navigator;
        
        public PairProgramming(String driver, String navigator) {
            this.driver = driver;
            this.navigator = navigator;
        }
        
        public void writeCode(String feature) {
            System.out.println("Pair Programming:");
            System.out.println("  Driver: " + driver + " writing code");
            System.out.println("  Navigator: " + navigator + " reviewing and guiding");
            System.out.println("  Working on: " + feature);
        }
        
        public void switchRoles() {
            String temp = driver;
            driver = navigator;
            navigator = temp;
            System.out.println("Roles switched!");
        }
    }
    
    // Practice 2: Test-Driven Development (TDD)
    static class TDDCycle {
        public void writeFailingTest() {
            System.out.println("1. RED: Write a failing test");
        }
        
        public void makeTestPass() {
            System.out.println("2. GREEN: Write minimal code to make test pass");
        }
        
        public void refactor() {
            System.out.println("3. REFACTOR: Improve code quality");
        }
        
        public void cycle(String feature) {
            System.out.println("\nTDD Cycle for: " + feature);
            writeFailingTest();
            makeTestPass();
            refactor();
            System.out.println("Cycle complete!\n");
        }
    }
    
    // Practice 3: Continuous Integration
    static class ContinuousIntegration {
        public void commit(String developer, String changes) {
            System.out.println(developer + " commits: " + changes);
        }
        
        public void runAutomatedTests() {
            System.out.println("Running automated test suite...");
            System.out.println("All tests passed ✓");
        }
        
        public void integrate() {
            System.out.println("Changes integrated to main branch");
        }
    }
    
    // Practice 4: Collective Code Ownership
    static class CollectiveOwnership {
        private Map<String, List<String>> codeContributions = new HashMap<>();
        
        public void contribute(String developer, String file) {
            codeContributions.putIfAbsent(file, new ArrayList<>());
            codeContributions.get(file).add(developer);
            System.out.println(developer + " modified " + file);
        }
        
        public void showOwnership(String file) {
            List<String> contributors = codeContributions.get(file);
            System.out.println("\nContributors to " + file + ":");
            if (contributors != null) {
                for (String dev : contributors) {
                    System.out.println("  - " + dev);
                }
            }
            System.out.println("Everyone owns the code!");
        }
    }
    
    // Practice 5: Simple Design (YAGNI - You Aren't Gonna Need It)
    static class SimpleDesign {
        public void principle1() {
            System.out.println("1. Passes all tests");
        }
        
        public void principle2() {
            System.out.println("2. Reveals intention");
        }
        
        public void principle3() {
            System.out.println("3. No duplication");
        }
        
        public void principle4() {
            System.out.println("4. Fewest elements");
        }
        
        public void demonstrate() {
            System.out.println("\nSimple Design Principles:");
            principle1();
            principle2();
            principle3();
            principle4();
        }
    }
    
    // Practice 6: Refactoring
    static class Refactoring {
        public void improveCode(String codeSection) {
            System.out.println("Refactoring: " + codeSection);
            System.out.println("  - Extract method");
            System.out.println("  - Rename variable for clarity");
            System.out.println("  - Remove duplication");
            System.out.println("Code improved without changing behavior");
        }
    }
}

// Example 4: Agile Team
class AgileTeam {
    private List<TeamMember> members = new ArrayList<>();
    
    static class TeamMember {
        private String name;
        private String role;
        
        public TeamMember(String name, String role) {
            this.name = name;
            this.role = role;
        }
        
        public void participate(String activity) {
            System.out.println(name + " (" + role + ") participating in: " + activity);
        }
        
        public String getName() { return name; }
    }
    
    public void addMember(TeamMember member) {
        members.add(member);
    }
    
    public void dailyStandup() {
        System.out.println("\nDaily Standup:");
        for (TeamMember member : members) {
            member.participate("Daily Standup");
        }
    }
    
    public void planning() {
        System.out.println("\nSprint Planning:");
        for (TeamMember member : members) {
            member.participate("Sprint Planning");
        }
    }
}

// Example 5: Product Backlog
class ProductBacklog {
    private List<UserStory> backlog = new ArrayList<>();
    
    public void addStory(UserStory story) {
        backlog.add(story);
        System.out.println("Added to backlog: " + story.getId());
    }
    
    public void prioritize() {
        System.out.println("\nPrioritizing backlog...");
        // In real scenario, would sort by business value, dependencies, etc.
        System.out.println("Backlog prioritized by business value");
    }
    
    public List<UserStory> getTopStories(int count) {
        return backlog.subList(0, Math.min(count, backlog.size()));
    }
    
    public void displayBacklog() {
        System.out.println("\nProduct Backlog:");
        for (int i = 0; i < backlog.size(); i++) {
            UserStory story = backlog.get(i);
            System.out.println((i + 1) + ". " + story.getId() + 
                             " (" + story.getStoryPoints() + " points)");
        }
    }
}

public class AgileExamples {
    
    public static void demonstrateSprintWorkflow() {
        System.out.println("=== Agile Sprint Workflow ===\n");
        
        // Create user stories
        UserStory story1 = new UserStory(
            "US-001",
            "user",
            "to login with email",
            "I can access my account",
            3
        );
        
        UserStory story2 = new UserStory(
            "US-002",
            "user",
            "to reset my password",
            "I can recover my account",
            5
        );
        
        UserStory story3 = new UserStory(
            "US-003",
            "user",
            "to view my profile",
            "I can see my information",
            2
        );
        
        // Create sprint
        Sprint sprint = new Sprint("Sprint 1", 14, 10);
        sprint.addStory(story1);
        sprint.addStory(story2);
        sprint.addStory(story3);
        
        // Sprint workflow
        sprint.startSprint();
        
        // Simulate progress
        story1.updateStatus("In Progress");
        sprint.dailyStandup(1);
        
        story1.updateStatus("Done");
        story2.updateStatus("In Progress");
        sprint.dailyStandup(5);
        
        story2.updateStatus("Done");
        story3.updateStatus("Done");
        
        // Sprint ceremonies
        sprint.sprintReview();
        sprint.sprintRetrospective();
    }
    
    public static void demonstrateXPPractices() {
        System.out.println("\n\n=== Extreme Programming Practices ===\n");
        
        // Pair Programming
        System.out.println("1. Pair Programming:");
        ExtremeProgramming.PairProgramming pair = 
            new ExtremeProgramming.PairProgramming("Alice", "Bob");
        pair.writeCode("User authentication");
        pair.switchRoles();
        
        // TDD
        System.out.println("\n2. Test-Driven Development:");
        ExtremeProgramming.TDDCycle tdd = new ExtremeProgramming.TDDCycle();
        tdd.cycle("Login feature");
        
        // Continuous Integration
        System.out.println("3. Continuous Integration:");
        ExtremeProgramming.ContinuousIntegration ci = 
            new ExtremeProgramming.ContinuousIntegration();
        ci.commit("Alice", "Add login validation");
        ci.runAutomatedTests();
        ci.integrate();
        
        // Collective Ownership
        System.out.println("\n4. Collective Code Ownership:");
        ExtremeProgramming.CollectiveOwnership ownership = 
            new ExtremeProgramming.CollectiveOwnership();
        ownership.contribute("Alice", "UserService.java");
        ownership.contribute("Bob", "UserService.java");
        ownership.contribute("Charlie", "UserService.java");
        ownership.showOwnership("UserService.java");
        
        // Simple Design
        ExtremeProgramming.SimpleDesign design = 
            new ExtremeProgramming.SimpleDesign();
        design.demonstrate();
        
        // Refactoring
        System.out.println();
        ExtremeProgramming.Refactoring refactoring = 
            new ExtremeProgramming.Refactoring();
        refactoring.improveCode("UserController.java");
    }
    
    public static void demonstrateAgileTeam() {
        System.out.println("\n\n=== Agile Team ===\n");
        
        AgileTeam team = new AgileTeam();
        team.addMember(new AgileTeam.TeamMember("Alice", "Developer"));
        team.addMember(new AgileTeam.TeamMember("Bob", "Developer"));
        team.addMember(new AgileTeam.TeamMember("Charlie", "Tester"));
        team.addMember(new AgileTeam.TeamMember("Diana", "Scrum Master"));
        team.addMember(new AgileTeam.TeamMember("Eve", "Product Owner"));
        
        team.planning();
        team.dailyStandup();
    }
    
    public static void demonstrateProductBacklog() {
        System.out.println("\n\n=== Product Backlog ===\n");
        
        ProductBacklog backlog = new ProductBacklog();
        
        backlog.addStory(new UserStory("US-001", "user", "login", 
                                       "access account", 3));
        backlog.addStory(new UserStory("US-002", "user", "search products", 
                                       "find items", 5));
        backlog.addStory(new UserStory("US-003", "admin", "manage users", 
                                       "control access", 8));
        backlog.addStory(new UserStory("US-004", "user", "checkout", 
                                       "purchase items", 13));
        
        backlog.prioritize();
        backlog.displayBacklog();
    }
    
    public static void demonstrateAgilePrinciples() {
        System.out.println("\n\n=== Agile Principles ===\n");
        
        System.out.println("Extreme Programming (XP) Values:");
        System.out.println("  1. Communication - Constant communication within team");
        System.out.println("  2. Simplicity - Do the simplest thing that works");
        System.out.println("  3. Feedback - Continuous feedback from tests and customers");
        System.out.println("  4. Courage - Make hard decisions and changes");
        System.out.println("  5. Respect - Everyone contributes value");
        
        System.out.println("\nXP Practices:");
        System.out.println("  - Pair Programming");
        System.out.println("  - Test-Driven Development");
        System.out.println("  - Continuous Integration");
        System.out.println("  - Collective Code Ownership");
        System.out.println("  - Simple Design");
        System.out.println("  - Refactoring");
        System.out.println("  - Small Releases");
        System.out.println("  - Sustainable Pace");
        
        System.out.println("\nScrum Framework:");
        System.out.println("  Roles: Product Owner, Scrum Master, Development Team");
        System.out.println("  Artifacts: Product Backlog, Sprint Backlog, Increment");
        System.out.println("  Events: Sprint Planning, Daily Standup, Sprint Review, Retrospective");
    }
    
    public static void main(String[] args) {
        demonstrateSprintWorkflow();
        demonstrateXPPractices();
        demonstrateAgileTeam();
        demonstrateProductBacklog();
        demonstrateAgilePrinciples();
    }
}
