import java.util.*;

public class HashMap {

    private Map<String, Integer> users = new java.util.HashMap<>();
    private Map<String, Integer> attempts = new java.util.HashMap<>();

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

    // Most attempted username
    public String getMostAttempted() {
        return Collections.max(attempts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static void main(String[] args) {

        HashMap checker = new HashMap();

        checker.registerUser("john_doe", 1);

        System.out.println(checker.checkAvailability("john_doe")); // false
        System.out.println(checker.checkAvailability("jane_smith")); // true

        System.out.println(checker.suggestAlternatives("john_doe"));
        System.out.println("Most attempted: " + checker.getMostAttempted());
    }
}