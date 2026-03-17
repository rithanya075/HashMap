package week1;

import java.util.*;

public class Problem1 {

    private Map<String, Integer> users;
    private Map<String, Integer> attempts;

    public Problem1() {
        users = new HashMap<>();
        attempts = new HashMap<>();
    }

    // Check availability
    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    // Register user
    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    // Suggest alternatives
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            suggestions.add(username + i);
        }

        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {
        if (attempts.isEmpty()) return "No attempts yet";

        return Collections.max(
                attempts.entrySet(),
                Map.Entry.comparingByValue()
        ).getKey();
    }

    public static void main(String[] args) {

        Problem1 checker = new Problem1();

        checker.registerUser("john_doe", 1);

        System.out.println("john_doe available? " + checker.checkAvailability("john_doe"));
        System.out.println("jane_smith available? " + checker.checkAvailability("jane_smith"));

        System.out.println("Suggestions: " + checker.suggestAlternatives("john_doe"));

        System.out.println("Most attempted: " + checker.getMostAttempted());
    }
}