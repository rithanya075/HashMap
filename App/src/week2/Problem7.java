package week1;

import java.util.*;

public class Problem7 {

    private Map<String, Integer> queryMap; // query → frequency

    public Problem7() {
        queryMap = new HashMap<>();
    }

    // Add or update query
    public void updateFrequency(String query) {
        queryMap.put(query, queryMap.getOrDefault(query, 0) + 1);
    }

    // Get suggestions based on prefix
    public List<String> getSuggestions(String prefix) {

        List<String> result = new ArrayList<>();

        // Filter queries by prefix
        for (String query : queryMap.keySet()) {
            if (query.startsWith(prefix)) {
                result.add(query);
            }
        }

        // Sort by frequency (descending)
        result.sort((a, b) -> queryMap.get(b) - queryMap.get(a));

        // Return top 5 suggestions
        return result.subList(0, Math.min(5, result.size()));
    }

    public static void main(String[] args) {

        Problem7 system = new Problem7();

        // Add queries
        system.updateFrequency("java tutorial");
        system.updateFrequency("java tutorial");
        system.updateFrequency("javascript");
        system.updateFrequency("java download");
        system.updateFrequency("java download");
        system.updateFrequency("java download");

        // Search
        System.out.println("Suggestions for 'jav':");
        System.out.println(system.getSuggestions("jav"));
    }
}